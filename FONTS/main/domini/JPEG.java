package main.domini;

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.nio.*;
import java.io.FileInputStream;
import java.util.*;
import java.lang.Object;
import java.util.Comparator;
import java.util.PriorityQueue;

/* Input uncompressed (ASCII Header && 8 bits pixels):
P6
(Width ASCII char in decimal) (Height ASCII char in decimal)
(MaxColor in ASCII 0 < X < 255)
0x0000 0x0001 0x0003 .... (Width pixels [in binary, one byte per component])
  (R)    (G)    (B)
.
.
.
0x0000 0x0001 0x0002 0x0003 .... (until the height row)


Input Compressed && OutputCompression (All in ASCII):
P6C\n
width\nheight\nwidthPixel\nheightPixel\n
0x0001, 0x0002, ... (values for Y for all pixels, followed
by the Cb block and the Cr block)
.
*/

/* Huffman Compressed file structure
Size_of_dictionary\n
NumerOfBits\n
Entry0_of_Dictionary\n
Translation_for_Entry0_in_bits\n
.
.
.
EntryN_of_Dictionary\n
Translation_for_EntryN_in_bits\n
*/

public class JPEG extends Algorisme {

	/* height and width are the original values from de image
	while heightPixel and widthPixel are equal or bigger than 
	the original but multiple of 8 */
	int height;
	int heightPixel;
	int width;
	int widthPixel;

	/* All the matrix used to manipulate the 
	pixel values in some steps */
	int [][][] pixel;
	double [][][] pixelDecimal;
	int [][][] pixelDownsampled;

	/* The quantitzation table used in the 
	quantitzation step */
	int quantitzationTableChroma[][] = {
								{70, 70, 70, 70, 70, 70, 70, 70},
							  	{70, 70, 70, 70, 70, 70, 70, 70},
								{70, 70, 70, 70, 70, 70, 70, 70},
							  	{70, 70, 70, 70, 70, 70, 70, 70},
							  	{70, 70, 70, 70, 70, 70, 70, 70},
							  	{70, 70, 70, 70, 70, 70, 70, 70},
							  	{70, 70, 70, 70, 70, 70, 70, 70},
							  	{70, 70, 70, 70, 70, 70, 70, 70}};
/*	int quantitzationTableChroma[][] = {
								{17, 18, 24, 47, 99, 99, 99, 99},
							  	{18, 21, 26, 66, 99, 99, 99, 99},
								{24, 26, 56, 99, 99, 99, 99, 99},
							  	{47, 66, 99, 99, 99, 99, 99, 99},
							  	{99, 99, 99, 99, 99, 99, 99, 99},
							  	{99, 99, 99, 99, 99, 99, 99, 99},
							  	{99, 99, 99, 99, 99, 99, 99, 99},
							  	{99, 99, 99, 99, 99, 99, 99, 99}};*/

	/* Both T and transposed T, used in the DCT2 and
	inverse DCT2 transformations */
	double dctMatrix[][] = {
								{0.3536, 0.3536, 0.3536, 0.3536, 0.3536, 0.3536, 0.3536, 0.3536},
								{0.4904, 0.4157, 0.2778, 0.0975, -0.0975, -0.2778, -0.4157, -0.4904},
								{0.4619, 0.1913, -0.1913, -0.4619, -0.4619, -0.1913, 0.1913, 0.4619},
								{0.4157, -0.0975, -0.4904, -0.2778, 0.2778, 0.4904, 0.0975, -0.4157},
								{0.3536, -0.3536, -0.3536, 0.3536, 0.3536, -0.3536, -0.3536, 0.3536},
								{0.2778, -0.4904, 0.0975, 0.4157, -0.4157, -0.0975, 0.4904, -0.2778},
								{0.1913, -0.4619, 0.4619, -0.1913, -0.1913, 0.4619, -0.4619, 0.1913},
								{0.0975, -0.2778, 0.4157, -0.4904, 0.4904, -0.4157, 0.2778, -0.0975}};
	double transposedDctMatrix[][] = {
								{0.3536, 0.4904, 0.4619, 0.4157, 0.3536, 0.2778, 0.1913, 0.0975},
								{0.3536, 0.4157, 0.1913, -0.0975, -0.3536, -0.4904, -0.4619, -0.2778},
								{0.3536, 0.2778, -0.1913, -0.4904, -0.3536, 0.0975, 0.4619, 0.4157},
								{0.3536, 0.0975, -0.4619, -0.2778, 0.3536, 0.4157, -0.1913, -0.4904},
								{0.3536, -0.0975, -0.4619, 0.2778, 0.3536, -0.4157, -0.1913, 0.4904},
								{0.3536, -0.2778, -0.1913, 0.4904, -0.3536, -0.0975, 0.4619, -0.4157},
								{0.3536, -0.4157, 0.1913, 0.0975, -0.3536, 0.4904, -0.4619, 0.2778},
								{0.3536, -0.4904, 0.4619, -0.4157, 0.3536, -0.2778, 0.1913, -0.0975}};

	/* String[ original ] = new representation of the bits in ASCII */
	private String[] map;

	/* This class represents a binary tree */
	private class Node implements Comparable<Node> {

		byte value;
		int freq;
		Node left;
		Node right;
		Boolean isValue;

		Node(){}

		Node(byte value, int freq) {
			this.value = value;
			this.freq = freq;
			this.right = null;
			this.left = null;
			isValue = true;
		}

		Node(int freq, Node left, Node right){
			isValue = false;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		Node(byte value, int freq, Node left, Node right){
			this.value = value;
			this.freq = freq;
			this.left = left;
			this.right = right;
			isValue = true;
		}

		public int compareTo(Node that) {
            return this.freq - that.freq;
    	}
	}

	/* Set to true for verbose output */
	boolean debuging = false;

	/* Empty constructor function */
	public JPEG() {}

	/* Debuging constructor */
	public JPEG(boolean debug) {
		this.debuging = debug;
	}

	public static void main(String[] args) {}

	/* Gets a byte[] as a n input and returns an Object with the compressed
	byte[] in the position 0 and the time needed in position 1 */
	public Object[] comprimir(byte [] input) {

		long time_start = System.currentTimeMillis();

		processPPMFile(input);
		if (debuging)
			System.out.println("PPM File Readed");

		makePixelsMultipleOf8();
		if (debuging)
			System.out.println("Pixels are now multiple of 8");

		RGBtoYCbCr();
		if (debuging)
			System.out.println("RGB to YCbCr done");

		centerOn0();
		if (debuging)
			System.out.println("CenterOn0 done");

		downSampling();
		if (debuging)
			System.out.println("Downsampling done");

		// Working over PixelDecimal
		for (int i = 0; i < heightPixel/2; i += 8)
			for (int j = 0; j < widthPixel/2; j += 8){
				multiplyBlockForDTC(i, j, 8, 8, 0);
				multiplyBlockForDTC(i, j, 8, 8, 1);
			}
		if (debuging)
			System.out.println("DCT done");

		for (int i = 0; i < heightPixel/2; i += 8)
			for (int j = 0; j < widthPixel/2; j += 8){
				quantitzation(i, j, 8, 8, 0);
				quantitzation(i, j, 8, 8, 1);
			}
		if (debuging)
			System.out.println("Quantitzation done");

		// Working over Pixel
		String headerString = new String("P6C\n" + width + "\n" + height + "\n" + widthPixel + "\n" + heightPixel + "\n");
		byte[] preHuffman = headerString.getBytes();
		preHuffman = concatenateByteArray(preHuffman, joinK0K1K2toByteArray());
		if (debuging)
			System.out.println("Made String for Huffman");

		byte[] huffman = HuffmanCompression(preHuffman);
		if (debuging)
			System.out.println("HuffmanCompression done");

		long time = System.currentTimeMillis() - time_start;

		Object[] ret = new Object[2];
		ret[0] = huffman;
		ret[1] = time;

		return ret;
	}

	/* Gets a byte[] as a n input and returns an Object with the decompressed
	byte[] in the position 0 and the time needed in position 1 */
	public Object[] descomprimir(byte[] input){

		long time_start = System.currentTimeMillis();

		 byte[] postHuffman = HuffmanDecompression(input);
		 if (debuging)
			System.out.println("HuffmanDecompression done");

		processCompressedFile(postHuffman);
		if (debuging)
			System.out.println("CompressedPPM file readed");

		for (int i = 0; i < heightPixel/2; i += 8)
			for (int j = 0; j < widthPixel/2; j += 8){
				unQuantitzation(i, j, 8, 8, 0);
				unQuantitzation(i, j, 8, 8, 1);
			}
		if (debuging)
			System.out.println("UnQuantitzation done");

		// Working over PixelDecimal
		for (int i = 0; i < heightPixel/2; i += 8)
			for (int j = 0; j < widthPixel/2; j += 8){
				multiplyBlockForInverseDTC(i, j, 8, 8, 0);
				multiplyBlockForInverseDTC(i, j, 8, 8, 1);
			}
		if (debuging)
			System.out.println("I-DCT done");

		// Working over Pixel
		unDownSampling();
		if (debuging)
			System.out.println("UnDownSampling done");

		unCenterOn0();
		if (debuging)
			System.out.println("UnCenterOn0 done");

		YCbCrtoRGB();
		if (debuging)
			System.out.println("YCbCrtoRGB done");

		checkMinMax();
		if (debuging)
			System.out.println("CheckMinMax done");

		String headerString = new String("P6\n" + width + " " + height + "\n" + "255\n");
		byte[] descomprimit = headerString.getBytes();
		descomprimit = concatenateByteArray(descomprimit, joinPixelstoByteArray());
		if (debuging)
			System.out.println("Output byte[] created");
		
		long time = System.currentTimeMillis() - time_start;

		Object[] ret = new Object[2];
		ret[0] = descomprimit;
		ret[1] = time;

		return ret;
	}

	/* Reads all the data from the .ppm file, and fills pixels with the
	image's values, it also reads all the atriutes of the image, such as sizes */
	private void processPPMFile(byte[] inputBytes) {

		String input = new String(inputBytes);

		int offsetHeader = 0;

		String[] noComments = input.split(System.getProperty("line.separator"));
		String s = "";

		for (int i = 0; i < noComments.length && i < 5; ++i){
			if (noComments[i].startsWith("#"))
				offsetHeader += noComments[i].length() + 1;
			else
				s += noComments[i] + "\n";
		}

		String[] words = s.split(" |\n");
		String prefix = words[0];
		width = Integer.parseInt(words[1]);
		height = Integer.parseInt(words[2]);
		int maxColor = Integer.parseInt(words[3]);

		offsetHeader += words[0].length() + words[1].length() + words[2].length() + words[3].length();
		// Counting 4 0x0A == \n or 0x20 after prefix, height, width & maxColor
		offsetHeader += 4;

		int offsetH = height % 8;
		if (offsetH != 0)
			heightPixel = height + 8 - offsetH;
		else
			heightPixel = height;

		int offsetW = width % 8;
		if (offsetW != 0)
			widthPixel = width + 8 - offsetW;
		else
			widthPixel = width;

		if (heightPixel < 16) heightPixel = 16;
		if (widthPixel < 16) widthPixel = 16; 

		// We create the pixel matrix with 8x8 size
		pixel = new int [heightPixel][widthPixel][3];

		// Filling pixels
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				for (int k = 0; k < 3; ++k){
					pixel[i][j][k] = inputBytes[offsetHeader] & 0xFF;
					++offsetHeader;
				}
	}

	/* Reads all the data from the .compressedPPM file, and fills pixels && 
	pixelDownsampled with the image's values, it also reads all the atriutes 
	of the image, such as sizes */
	private void processCompressedFile(byte[] inputBytes) {

		// Header management
		String input = new String(inputBytes);

		int offsetHeader = 0;

		String[] words = input.split("\n");
		width = Integer.parseInt(words[1]);
		height = Integer.parseInt(words[2]);
		widthPixel = Integer.parseInt(words[3]);
		heightPixel = Integer.parseInt(words[4]);

		offsetHeader += words[0].length() + words[1].length() + words[2].length() + words[3].length() + words[4].length();
		// Counting 5 0x0A == \n from , prefix, height, width, widthPixel & heightPixel
		offsetHeader += 5;

		int offsetH = (heightPixel/2) % 8;
		int offsetW = (widthPixel/2) % 8;
		int h, w;

		if (offsetH != 0)
			h = (heightPixel/2) + offsetH;
		else
			h = heightPixel/2;

		if (offsetW != 0)
			w = (widthPixel/2) + offsetW;
		else
			w = widthPixel/2;

		// We create 3 matrix that will be used to manipulate pixel's values
		pixel = new int [heightPixel][widthPixel][3];
		pixelDownsampled = new int[h][w][2];
		pixelDecimal = new double[h][w][2];

		// Filling pixels values
		K0K1K2fromByteArray(inputBytes, offsetHeader);
	}

	/* Converts all pixels in pixel form RGB to YCbCr */
	private void RGBtoYCbCr() {

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				double y = 16.0f + pixel[i][j][0] * 65.481f/255.0f + pixel[i][j][1] * 128.553f/255.0f + pixel[i][j][2] * 24.966f/255.0f;
				double cb = 128.0f - pixel[i][j][0] * 37.797f/255.0f - pixel[i][j][1] * 74.203f/255.0f + pixel[i][j][2] * 112.0f/255.0f;
				double cr = 128.0f + pixel[i][j][0] * 112.0f/255.0f - pixel[i][j][1] * 93.786f/255.0f - pixel[i][j][2] * 18.214f/255.0f;
				
				pixel[i][j][0] = (int) y;
				pixel[i][j][1] = (int) cb;
				pixel[i][j][2] = (int) cr;
			}
		}
	}

	/* Converts all pixels in pixel form YCbCr to RGB */
	private void YCbCrtoRGB() {

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				double r = 1.164 * (pixel[i][j][0] - 16) + 1.596 * (pixel[i][j][2] - 128);
				double g = 1.164 * (pixel[i][j][0] - 16) - 0.813 * (pixel[i][j][1] - 128) -0.392 * (pixel[i][j][2] - 128);
				double b = 1.164 * (pixel[i][j][0] - 16) + 2.017 * (pixel[i][j][1] - 128);

				pixel[i][j][0] = (int) r;
				pixel[i][j][1] = (int) g;
				pixel[i][j][2] = (int) b;
			}
		}
	}

	/* Creates pixelDownsampled and pixelDecimal with half
	the color in pixels, and fills it's content */
	private void downSampling() {

		int offsetH = (heightPixel/2) % 8;
		int offsetW = (widthPixel/2) % 8;
		int h, w;

		if (offsetH != 0)
			h = (heightPixel/2) + offsetH;
		else
			h = heightPixel/2;

		if (offsetW != 0)
			w = (widthPixel/2) + offsetW;
		else
			w = widthPixel/2;

		pixelDownsampled = new int[h][w][2];
		pixelDecimal = new double[h][w][2];

		int r = 0, t = 0;

		for (int i = 0; i < heightPixel; i += 2){
			for (int j = 0; j < widthPixel; j += 2){
				pixelDownsampled[r][t][0] = pixel[i][j][1];
				pixelDownsampled[r][t][1] = pixel[i][j][2];
				++t;
			}
			t = 0;
			++r;
		}

		for (int i = 0; i < heightPixel; ++i)
			for (int j = widthPixel; j < w; ++j)
				for (int k = 0; k < 2; ++k)
					pixelDownsampled[i][j][k] = pixelDownsampled[i][widthPixel-1][k];

		for (int j = 0; j < widthPixel; ++j)
			for (int i = heightPixel; i < h; ++i)
				for (int k = 0; k < 2; ++k)
					pixelDownsampled[i][j][k] = pixelDownsampled[heightPixel-1][j][k];

		for (int i = heightPixel; i < h; ++i)
			for (int j = widthPixel; j < w; ++j)
				for (int k = 0; k < 2; ++k){
					if (h >= w)
						pixelDownsampled[i][j][k] = pixelDownsampled[heightPixel-1][j][k];
					else
						pixelDownsampled[i][j][k] = pixelDownsampled[i][widthPixel-1][k];
				}
	}

	/* Reverses the downsampling, by taking the values form
	pixelDownsampled and propagates the color of all values 
	to their neighbors */
	private void unDownSampling() {

		int r = 0, t = 0;

		for (int i = 0; i < heightPixel; i += 2){
			for (int j = 0; j < widthPixel; j += 2){

				pixel[i][j][1] = pixelDownsampled[r][t][0];
				pixel[i][j+1][1] = pixelDownsampled[r][t][0];
				pixel[i+1][j][1] = pixelDownsampled[r][t][0];
				pixel[i+1][j+1][1] = pixelDownsampled[r][t][0];

				pixel[i][j][2] = pixelDownsampled[r][t][1];
				pixel[i][j+1][2] = pixelDownsampled[r][t][1];
				pixel[i+1][j][2] = pixelDownsampled[r][t][1];
				pixel[i+1][j+1][2] = pixelDownsampled[r][t][1];
				++t;
			}
			t = 0;
			r++;
		}
	}

	/* Substracts 128 from the Cb & Cr components in pixel[][] */
	private void centerOn0() {
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j){
				pixel[i][j][1] -= 128;
				pixel[i][j][2] -= 128;
			}
	}

	/* Adds 128 from the Cb & Cr components in pixel[][] */
	private void unCenterOn0() {
		for (int i = 0; i < heightPixel; ++i)
			for (int j = 0; j < widthPixel; ++j){
				pixel[i][j][1] += 128;
				pixel[i][j][2] += 128;
			}
	}

	/* Makes sure that all values in pixel[][] are inside
	[0, 255] range */
	private void checkMinMax() {
		for (int i = 0; i < heightPixel; ++i)
			for (int j = 0; j < widthPixel; ++j){
				pixel[i][j][0] = Math.min(255, pixel[i][j][0]);
				pixel[i][j][0] = Math.max(0, pixel[i][j][0]);
				pixel[i][j][1] = Math.min(255, pixel[i][j][1]);
				pixel[i][j][1] = Math.max(0, pixel[i][j][1]);
				pixel[i][j][2] = Math.min(255, pixel[i][j][2]);
				pixel[i][j][2] = Math.max(0, pixel[i][j][2]);
			}
	}

	/* Propagates all the pixels in the right and bottom sides of
	the image to make height and width multiple of 8 */
	private void makePixelsMultipleOf8() {

		for (int i = 0; i < height; ++i)
			for (int j = width; j < widthPixel; ++j)
				for (int k = 0; k < 3; ++k)
					pixel[i][j][k] = pixel[i][width-1][k];

		for (int j = 0; j < width; ++j)
			for (int i = height; i < heightPixel; ++i)
				for (int k = 0; k < 3; ++k)
					pixel[i][j][k] = pixel[height-1][j][k];

		for (int i = height; i < heightPixel; ++i)
			for (int j = width; j < widthPixel; ++j)
				for (int k = 0; k < 3; ++k){
					if (heightPixel >= widthPixel)
						pixel[i][j][k] = pixel[height-1][j][k];
					else
						pixel[i][j][k] = pixel[i][width-1][k];
				}
	}

	/* Applies the DCT2 transformation to all pixels from pixelDownsampled and saves the
	values in pixelDecimal */
	private void multiplyBlockForDTC(int inputI, int inputJ, int n1, int n2, int k) {
		// T * M * T'
		double[][] result = new double[8][8];
		int[][] M = getBlockPixelDownsampled(inputI, inputJ, n1, n2, k);

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j)
				for (int r = 0; r < 8; ++r)
					result[i][j] += dctMatrix[i][r] * M[r][j];

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j)
				for (int r = 0; r < 8; ++r)
					result[i][j] += result[i][r] * transposedDctMatrix[r][j];

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j)
				result[i][j] = M[i][j];
		setBlockPixelDecimal(inputI, inputJ, n1, n2, k, result);
	}

	/* Applies the Inverse-DCT2 transformation to all pixels from pixelDecimal and saves the
	values in pixelDownsampled */
	private void multiplyBlockForInverseDTC(int inputI, int inputJ, int n1, int n2, int k) {
		// T' * M * T
		int[][] result = new int[8][8];
		double[][] M = getBlockPixelDecimal(inputI, inputJ, n1, n2, k);

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j)
				for (int r = 0; r < 8; ++r)
					result[i][j] += (int)(transposedDctMatrix[i][r] * M[r][j]);

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j)
				for (int r = 0; r < 8; ++r)
					result[i][j] += (int)(result[i][r] * transposedDctMatrix[r][j]);

		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j)
				result[i][j] = (int)(M[i][j]);
		setBlockPixelDowndampled(inputI, inputJ, n1, n2, k, result);
	}

	/* Applies the quantitzation step to all pixels in pixelDecimal, using the table 
	quantitzationTableChroma, and stores the values in pixelDownsampled */
	private void quantitzation(int inputI, int inputJ, int n1, int n2, int k) {

		for (int i = inputI; i < inputI + n1; ++i)
			for (int j = inputJ; j < inputJ + n2; ++j)
				pixelDownsampled[i][j][k] = (int) Math.round(pixelDecimal[i][j][k]/(quantitzationTableChroma[i%8][j%8]));
	}

	/* Applies the inverse-quantitzation step to all pixels in pixelDownsampled, using the table 
	quantitzationTableChroma, and stores the values in pixelDecimal */
	private void unQuantitzation(int inputI, int inputJ, int n1, int n2, int k) {

		for (int i = inputI; i < inputI + n1; ++i)
			for (int j = inputJ; j < inputJ + n2; ++j)
				pixelDecimal[i][j][k] = pixelDownsampled[i][j][k]*(quantitzationTableChroma[i%8][j%8]);
	}

	/* Returns a block of pixel[][][k] from (inputI, inputJ) to (n1, n2) */
	private int[][] getBlockPixel(int inputI, int inputJ, int n1, int n2, int k){

		int[][] res = new int[n1][n2];
		int r = 0, t = 0;

		for (int i = inputI; i < inputI + n1; ++i){
			for (int j = inputJ; j < inputJ + n2; ++j){
				res[r][t] = pixel[i][j][k];
				++t;
			}
			t = 0;
			++r;
		}

		return res;
	}

	/* Returns a block of pixelDownsampled[][][k] from (inputI, inputJ) to (n1, n2) */
	private int[][] getBlockPixelDownsampled(int inputI, int inputJ, int n1, int n2, int k){

		int[][] res = new int[n1][n2];
		int r = 0, t = 0;

		for (int i = inputI; i < inputI + n1; ++i){
			for (int j = inputJ; j < inputJ + n2; ++j){
				res[r][t] = pixelDownsampled[i][j][k];
				++t;
			}
			t = 0;
			++r;
		}

		return res;
	}

	/* Returns a block of pixelDecimal[][][k] from (inputI, inputJ) to (n1, n2) */
	private double[][] getBlockPixelDecimal(int inputI, int inputJ, int n1, int n2, int k){

		double[][] res = new double[n1][n2];
		int r = 0, t = 0;

		for (int i = inputI; i < inputI + n1; ++i){
			for (int j = inputJ; j < inputJ + n2; ++j){
				res[r][t] = pixelDecimal[i][j][k];
				++t;
			}
			t = 0;
			++r;
		}

		return res;
	}

	/* Sets a block of pixel[][][k] from (inputI, inputJ) to (n1, n2) with the values of input */
	private void setBlockPixel(int inputI, int inputJ, int n1, int n2, int k, int[][] input){

		int r = 0, t = 0;

		for (int i = inputI; i < inputI + n1; ++i){
			for (int j = inputJ; j < inputJ + n2; ++j){
				pixel[i][j][k] = input[r][t];
				++t;
			}
			t = 0;
			++r;
		}
	}

	/* Sets a block of pixelDownsampled[][][k] from (inputI, inputJ) to (n1, n2) with the values of input */
	private void setBlockPixelDowndampled(int inputI, int inputJ, int n1, int n2, int k, int[][] input){

		int r = 0, t = 0;

		for (int i = inputI; i < inputI + n1; ++i){
			for (int j = inputJ; j < inputJ + n2; ++j){
				pixelDownsampled[i][j][k] = input[r][t];
				++t;
			}
			t = 0;
			++r;
		}
	}

	/* Sets a block of pixelDecimal[][][k] from (inputI, inputJ) to (n1, n2) with the values of input */
	private void setBlockPixelDecimal(int inputI, int inputJ, int n1, int n2, int k, double[][] input){

		int r = 0, t = 0;

		for (int i = inputI; i < inputI + n1; ++i){
			for (int j = inputJ; j < inputJ + n2; ++j){
				pixelDecimal[i][j][k] = (int)input[r][t];
				++t;
			}
			t = 0;
			++r;
		}
	}

	/* Returns in zig-zag format the matrix block */
	private int[] zigZag(int[][] block){

		int [] ret = new int[8*8];
		int i = 1;
		int j = 1;

		for (int e = 0; e < 8 * 8; e++){
			ret[e] = block[i - 1][j - 1];
			if ((i + j) % 2 == 0) {
				if (j < 8)
					j++;
				else
					i += 2;
				if (i > 1)
					i--;
			}
			else {
				if (i < 8)
					i++;
				else
					j += 2;
				if (j > 1)
					j--;
			}
		}
		return ret;
	}

	/* Reverses the zig-zag format in the zigZaged vector */
	private int[][] unZigZag(int[] zigZaged){

		int [][] ret = new int[8][8];
		int i = 1;
		int j = 1;

		for (int e = 0; e < 8 * 8; e++){
			ret[i - 1][j - 1] = zigZaged[e];
			if ((i + j) % 2 == 0) {
				if (j < 8)
					j++;
				else
					i += 2;
				if (i > 1)
					i--;
			}
			else {
				if (i < 8)
					i++;
				else
					j += 2;
				if (j > 1)
					j--;
			}
		}
		return ret;
	}

	/* Joints two byte[] into one */
	private byte[] concatenateByteArray(byte[] b1, byte[] b2){

		byte[] b = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b, 0, b1.length);
		System.arraycopy(b2, 0, b, b1.length, b2.length);
		return b;
	}

	/* Returns as a byte[] all the values of pixel[][][0] and
	pixelDownsampled[][][0 & 1] */
	private byte[] joinK0K1K2toByteArray(){

		byte[] result = new byte[0];
		
		//Y
		for (int i = 0; i < heightPixel; i += 8){
			for (int j = 0; j < widthPixel; j += 8){

				int[][] block = getBlockPixel(i, j, 8, 8, 0);
				int[] blockZigZaged = zigZag(block);
				byte[] aux = new byte[0];

				for (int a = 0; a < blockZigZaged.length; ++a){
					byte[] aux2 = {(byte)(blockZigZaged[a] >> 8), (byte)(blockZigZaged[a])};
					aux = concatenateByteArray(aux, aux2);
				}
				result = concatenateByteArray(result, aux);
			}
		}

		//CbCr
		for (int k = 0; k < 2; ++k){
			for (int i = 0; i < heightPixel/2; i += 8){
				for (int j = 0; j < widthPixel/2; j += 8){

					int[][] block = getBlockPixelDownsampled(i, j, 8, 8, k);
					int[] blockZigZaged = zigZag(block);
					byte[] aux = new byte[0];

					for (int a = 0; a < blockZigZaged.length; ++a){
						byte[] aux2 = {(byte)(blockZigZaged[a] >> 8), (byte)(blockZigZaged[a])};
						aux = concatenateByteArray(aux, aux2);
					}
					result = concatenateByteArray(result, aux);
				}
			}
		}
		return result;
	}

	/* Returns a byte[] with all the values of pixels[][][] */
	private byte[] joinPixelstoByteArray() {

		byte[] result = new byte[0];

		for (int i = 0; i < height; ++i){
			byte[] partial = new byte[0];
			for (int j = 0; j < width; ++j){
					byte[] aux = {(byte)pixel[i][j][0], (byte)pixel[i][j][1], (byte)pixel[i][j][2]};
					partial = concatenateByteArray(partial, aux);
			}
			result = concatenateByteArray(result, partial);
		}
		return result;
	}

	/* Reads from the byte[] b, starting from the position offset,
	all the values of Y, and stores them to pixels, and all the
	values of Cb & Cr, and stores them at pixelDownsampled */
	private void K0K1K2fromByteArray(byte[] b, int offset){

		// Y
		for (int i = 0; i < heightPixel; i += 8){
			for (int j = 0; j < widthPixel; j += 8){
					
				int [] a = new int[8*8];
				int t = 0;
				for (int r = 0; r < 8*8*2; r += 2){
					a[t] = (int)((b[offset + r] << 8)) | (b[offset + r + 1] & 0xFF);
					++t;
				}
				int [][] noZigZaged = unZigZag(a);
				setBlockPixel(i, j, 8, 8, 0, noZigZaged);
				offset += 8*8*2;
			}
		}

		// CbCr
		for (int k = 0; k < 2; ++k){
			for (int i = 0; i < heightPixel/2; i += 8){
				for (int j = 0; j < widthPixel/2; j += 8){
						
					int [] a = new int[8*8];
					int t = 0;
					for (int r = 0; r < 8*8*2; r += 2){
						a[t] = (int)((b[offset + r] << 8)) | (b[offset + r + 1] & 0xFF);
						++t;
					}
					int [][] noZigZaged = unZigZag(a);
					setBlockPixelDowndampled(i, j, 8, 8, k, noZigZaged);
					offset += 8*8*2;
				}
			}
		}
	}

	/* Applies Huffman compression to the byte[] input */
	private byte[] HuffmanCompression(byte[] input) {

		// Step 1: counting frequency
		int size = input.length;
		int[] freq = new int[256];
		for (int i = 0; i < size; ++i)
			++freq[ (input[i] & 0xFF) ];

		// Step 2: building a binary tree
		Node root = buildTree(freq);
		if (debuging)
			System.out.println("Huffman Tree builded");

		// Step 3: build a dictionary with the new representation of bytes
		map = new String[256];
		buildDictionary(root, "");
		if (debuging)
			System.out.println("Dictionary builded");

		// Step 4: encode input with the new dictionary
		boolean[] encoded = encode(input);
		if (debuging)
			System.out.println("Content encoded");

		// Step 5: write offset as one byte + encode
		byte[] output = booleanToBytePlusDict(encoded);
		if (debuging)
			System.out.println("Transformed boolean[] -> byte[]");

		return output;
	}

	/* Reverses Huffman compression to the byte[] input */
	private byte[] HuffmanDecompression(byte[] input) {

		// Step 1: read map and input
		boolean[] booleanInput = readCompressedInput(input);
		if (debuging)
			System.out.println("Compressed file readed");

		// Step 2: decode booleanInput with map
		byte[] output = decode(booleanInput);
		if (debuging)
			System.out.println("Content decoded");

		return output;
	}

	/* Reads from a file, stored in input, the header with the 
	dictionary to translate the rest of the bites stored in input */
	private boolean[] readCompressedInput(byte[] input) {

		int offsetHeader = 0;

		String inputString = new String(input);

		String[] s = inputString.split(System.getProperty("line.separator"));

		int mapSize = Integer.parseInt(s[0]);
		int bitsSize = Integer.parseInt(s[1]);
		offsetHeader += s[0].length() + s[1].length() + 2;

		map = new String[256];

		for (int i = 2; i < mapSize * 2 + 2; i += 2) {

			int p = Integer.parseInt(s[i]);
			map[p] = s[i+1];
			offsetHeader += s[i].length() + s[i+1].length() + 2;
		}

		return byteToBoolean(input, offsetHeader, bitsSize);
	}

	/* Builds a huffman tree based on the frequency of each byte 
	stored in freq */
	private Node buildTree(int[] freq) {

		PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < 256; ++i)
            if (freq[(i & 0xFF)] > 0)
                pq.add(new Node((byte)i, freq[(i & 0xFF)], null, null));

        // special case only one character with a not zero frequency
        if (pq.size() == 1)
            pq.add(new Node(0, null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.poll();
            Node right = pq.poll();
            Node root = new Node(left.freq + right.freq, left, right);
            pq.add(root);
        }
        return pq.poll();
	}

	/* Builds the dictionary for the byte value of node with
	the string in b */
	private void buildDictionary(Node n, String b) {

		if (n.isValue)
			map[ (n.value & 0xFF) ] = b;

		else {

			if (n.right != null)
				buildDictionary(n.right, b + "1");

			if (n.left != null)
				buildDictionary(n.left, b + "0");
		}
	}

	/* Encodes the byte[] with the dictionary map */
	private boolean[] encode(byte[] input) {

		StringBuilder code = new StringBuilder();

		for (int i = 0; i < input.length; ++i)
			code.append(map[ (input[i] & 0xFF) ]);

		boolean[] encoded = new boolean[code.length()];

		for (int j = 0; j < code.length(); ++j)
			if (code.charAt(j) == '1')
				encoded[j] = true;

		return encoded;
	}

	/* Decodes the boolean[] with the dictionary map */
	private byte[] decode(boolean[] input) {

		StringBuilder code = new StringBuilder();
		byte[] b = new byte[input.length];
		int mapSize = getMapSize();

		for (int i = 0; i < input.length; ++i) {
			if (input[i])
				code.append('1');
			else
				code.append('0');
		}

		int start = 0;
		int size = 0;
		for (int end = 1; end <= code.length(); ++end) {
			for (int j = 0; j < 256; ++j){
				if (map[j] != null && map[j].equals(code.substring(start, end))) {
				b[size] = ((byte)(j & 0xFF));
				start = end;
				++size;
				break;
				}
			}
		}
		byte[] output = new byte[size];
		System.arraycopy(b, 0, output, 0, size);
		return output;
	}

	/* Creates a byte[], with a header containing the number of entries
	in map, the number of bits encoded and all the translations in map */
	private byte[] booleanToBytePlusDict(boolean[] input) {

		String s = String.valueOf(getMapSize()) + "\n" + String.valueOf(input.length) + "\n" + getMap();
		byte[] header = s.getBytes();

		byte[] body = booleanToByte(input);

		return concatenateByteArray(header, body);
	}

	/* Builds a byte[] with the content of a boolean[] */
	private byte[] booleanToByte(boolean[] input) {

		int size = (input.length/8);
		if (input.length % 8 > 0) ++size;

		byte[] output = new byte[size];

	    for (int i = 0; i < output.length; ++i)
	        for (int bit = 0; bit < 8 && (i*8+bit) < input.length; ++bit)
	            if (input[i * 8 + bit])
	                output[i] |= (128 >> bit);

	    return output;
	}

	/* Builds a boolean[] of size = bitsSize from a byte[], starting at
	position offset */
	private boolean[] byteToBoolean(byte[] input, int offset, int bitsSize) {

		boolean[] output = new boolean[bitsSize];
		byte[] noHeader = Arrays.copyOfRange(input, offset, input.length);
		int r = 0;

		for (int i = 0; i < noHeader.length; ++i) {
			for (int bit = 7; bit >= 0 && i*8+(7-bit) < bitsSize; --bit) {
				output[r] = (((noHeader[i] >>> bit) & 0x1) == 0x1);
				++r;
			}
		}

    	return output;
	}

	/* Returns a string with all the not-null entries of map */
	public String getMap() {

		String mapContent = new String();

		for (int i = 0; i < map.length; ++i) {
			if (map[i] != null) {
				mapContent += String.valueOf(i) + "\n";
				mapContent += map[i] + "\n";
			}
		}

		return mapContent;
	}

	/* Returns how many not-null entries of map exist */
	public int getMapSize() {

		int s = 0;

		for (int i = 0; i < map.length; ++i)
			if (map[i] != null) ++s;

		return s;
	}

	/* Joints two boolean[] */
	private boolean[] concatenateBooleanArray(boolean[] b1, boolean[] b2) {

		boolean[] b = new boolean[b1.length + b2.length];
		System.arraycopy(b1, 0, b, 0, b1.length);
		System.arraycopy(b2, 0, b, b1.length, b2.length);
		return b;
	}

	// DEBUGING PIXELS VALUES FOR TESTING
	private void printPixel() {
		for (int i = 0; i < heightPixel; ++i) {
			for (int j = 0; j < widthPixel; ++j) {
				for (int k = 0; k < 3; ++k) {
					if (k == 0)
						System.out.print("[");
					System.out.print(pixel[i][j][k]);
					if (k != 2)
						System.out.print(" ");
					else if (k == 2)
						System.out.print("]");
				}
				if (j == widthPixel-1)
					System.out.print("\n");
			}
		}
	}
	
	private void printPixelDecimal() {
		for (int i = 0; i < heightPixel/2; ++i) {
			for (int j = 0; j < widthPixel/2; ++j) {
				for (int k = 0; k < 2; ++k) {
					if (k == 0)
						System.out.print("[");
					System.out.print(pixelDecimal[i][j][k]);
					if (k != 2)
						System.out.print(" ");
					else if (k == 2)
						System.out.print("]");
				}
				if (j == widthPixel/2-1)
					System.out.print("\n");
			}
		}
	}

	private void printPixelDownsampled() {
		for (int i = 0; i < heightPixel/2; ++i) {
			for (int j = 0; j < widthPixel/2; ++j) {
				for (int k = 0; k < 2; ++k) {
					if (k == 0)
						System.out.print("[");
					System.out.print(pixelDownsampled[i][j][k]);
					if (k != 2)
						System.out.print(" ");
					else if (k == 2)
						System.out.print("]");
				}
				if (j == widthPixel/2-1)
					System.out.print("\n");
			}
		}
	}

	// ALTERNATIVE IMPLEMENTATION OF SOME STEPS, NOT USED BUT KEEPT FOR THE FUTURE
	/*
	private double C(int x){

		if (x == 0)
			return 1.0/Math.sqrt(2.0);
		else
			return 1;
	}

	private void dctTransformation(int inputI, int inputJ, int n1, int n2, int k) {

		double pi = 3.14159265;
		double[][] result = new double[8][8];
		int[][] M = getBlockPixelDownsampled(inputI, inputJ, n1, n2, k);

		for (int u = 0; u < 8; ++u){
			for (int v = 0; v < 8; ++v){

				double sumaIJ = (2.0/8)*C(u%8)*C(v%8);
				double sumaJ = 0;

				for (int x = 0; x < 8; ++x){
					for (int y = 0; y < 8; ++y)
						sumaJ += (M[x][y]*Math.cos(((2.0*(x%8)+1.0)*(u%8)*pi)/16.0)*Math.cos(((2.0*(y%8)+1.0)*(v%8)*pi))/16.0);
				}
				result[u][v] = sumaIJ*sumaJ;
			}
		}
		setBlockPixelDecimal(inputI, inputJ, n1, n2, k, result);
	}

	private void dctInverseTransformation(int inputI, int inputJ, int n1, int n2, int k) {

		double pi = 3.14159265;
		int[][] result = new int[8][8];
		double[][] M = getBlockPixelDecimal(inputI, inputJ, n1, n2, k);

		for (int x = 0; x < 8; ++x){
			for (int y = 0; y < 8; ++y){

				double sumaJ = 0;

				for (int u = 0; u < 8; ++u){
					for (int v = 0; v < 8; ++v)
						sumaJ += (C(u%8)*C(v%8)*M[u][v]*Math.cos(((2.0*(x%8)+1.0)*(u%8)*pi)/16.0)*Math.cos(((2.0*(y%8)+1.0)*(v%8)*pi))/16.0);
				}
				result[x][y] = (int)((2.0/8.0)*sumaJ);
			}
		}
		setBlockPixelDowndampled(inputI, inputJ, n1, n2, k, result);
	}
	*/
}