package test.domini;

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.nio.*;
import java.io.FileInputStream;
import java.util.*;
import java.util.Scanner;
import java.lang.Object;
import main.domini.*;

public class JPEG_Test {

	public JPEG_Test() {}

	public static void main(String[] args) throws IOException {

		System.out.println("");
		System.out.println("##################################################");
		System.out.println("# JPEG_Test #build 1.4 - last revision: 14.11.19 #");
		System.out.println("##################################################");

		String path = new String("");


        String[] inputString = new String[44];
        inputString[0] = "2x2.ppm";
        inputString[1] = "2x2.compressedPPM";
        inputString[2] = "8x8.ppm";
        inputString[3] = "8x8.compressedPPM";
        inputString[4] = "black.ppm";
        inputString[5] = "black.compressedPPM";
        inputString[6] = "nailsS.ppm";
        inputString[7] = "nailsS.compressedPPM";
        inputString[8] = "pointsS.ppm";
        inputString[9] = "pointsS.compressedPPM";
        inputString[10] = "lines.ppm";
        inputString[11] = "lines.compressedPPM";
        inputString[12] = "semafor.ppm";
        inputString[13] = "semafor.compressedPPM";
        inputString[14] = "semaforLined.ppm";
        inputString[15] = "semaforLined.compressedPPM";
        inputString[16] = "points.ppm";
        inputString[17] = "points.compressedPPM";
        inputString[18] = "points2.ppm";
        inputString[19] = "points2.compressedPPM";
        inputString[20] = "fire.ppm";
        inputString[21] = "fire.compressedPPM";
        inputString[22] = "peper.ppm";
        inputString[23] = "peper.compressedPPM";
        inputString[24] = "forest.ppm";
        inputString[25] = "forest.compressedPPM";
        inputString[26] = "aqua.ppm";
        inputString[27] = "aqua.compressedPPM";
        inputString[28] = "exposure.ppm";
        inputString[29] = "exposure.compressedPPM";
        inputString[30] = "gradient.ppm";
        inputString[31] = "gradient.compressedPPM";
        inputString[32] = "darkness.ppm";
        inputString[33] = "darkness.compressedPPM";
        inputString[34] = "18MB.ppm";
        inputString[35] = "18MB.compressedPPM";
        inputString[36] = "abstract.ppm";
        inputString[37] = "abstract.compressedPPM";
        inputString[38] = "vertical.ppm";
        inputString[39] = "vertical.compressedPPM";
        inputString[40] = "Graph.ppm";
        inputString[41] = "Graph.compressedPPM";
        inputString[42] = "big.ppm";
        inputString[43] = "big.compressedPPM";


		String[] outputString = new String[44];
        outputString[0] = "2x2.compressedPPM";
        outputString[1] = "2x2-uncompressed.ppm";
        outputString[2] = "8x8.compressedPPM";
        outputString[3] = "8x8-uncompressed.ppm";
        outputString[4] = "black.compressedPPM";
        outputString[5] = "black-uncompressed.ppm";
        outputString[6] = "nailsS.compressedPPM";
        outputString[7] = "nailsS-uncompressed.ppm";
        outputString[8] = "pointsS.compressedPPM";
        outputString[9] = "pointsS-uncompressed.ppm";
        outputString[10] = "lines.compressedPPM";
        outputString[11] = "lines-uncompressed.ppm";
        outputString[12] = "semafor.compressedPPM";
        outputString[13] = "semafor-uncompressed.ppm";
        outputString[14] = "semaforLined.compressedPPM";
        outputString[15] = "semaforLined-uncompressed.ppm";
        outputString[16] = "points.compressedPPM";
        outputString[17] = "points-uncompressed.ppm";
        outputString[18] = "points2.compressedPPM";
        outputString[19] = "points2-uncompressed.ppm";
        outputString[20] = "fire.compressedPPM";
        outputString[21] = "fire-uncompressed.ppm";
        outputString[22] = "peper.compressedPPM";
        outputString[23] = "peper-uncompressed.ppm";
        outputString[24] = "forest.compressedPPM";
        outputString[25] = "forest-uncompressed.ppm";
        outputString[26] = "aqua.compressedPPM";
        outputString[27] = "aqua-uncompressed.ppm";
        outputString[28] = "exposure.compressedPPM";
        outputString[29] = "exposure-uncompressed.ppm";
        outputString[30] = "gradient.compressedPPM";
        outputString[31] = "gradient-uncompressed.ppm";
        outputString[32] = "darkness.compressedPPM";
        outputString[33] = "darkness-uncompressed.ppm";
        outputString[34] = "18MB.compressedPPM";
        outputString[35] = "18MB-uncompressed.ppm";
        outputString[36] = "abstract.compressedPPM";
        outputString[37] = "abstract-uncompressed.ppm";
        outputString[38] = "vertical.compressedPPM";
        outputString[39] = "vertical-uncompressed.ppm";
        outputString[40] = "Graph.compressedPPM";
        outputString[41] = "Graph-uncompressed.ppm";
        outputString[42] = "big.compressedPPM";
        outputString[43] = "big-uncompressed.ppm";

        long time = 0;

        for (int i = 0; i < inputString.length; ++i){

			System.out.println("");
	        System.out.println("[" + i + "]" + "Starting to process " + path + inputString[i]);
			System.out.println("THIS TEST MAY TAKE UP TO 600 SECONDS");
			System.out.println("");

	        File file = new File(path + inputString[i]);
	        byte[] b = new byte[(int) file.length()];
	        FileInputStream fis = new FileInputStream(file);
	        fis.read(b);
	        fis.close();

	        Object[] outputBytes = new Object[2];
			JPEG jpeg = new JPEG();

			if (i % 2 == 0)
				outputBytes = jpeg.comprimir(b);

			else
				outputBytes = jpeg.descomprimir(b);

			File file2 = new File(path + outputString[i]);
        	OutputStream os = new FileOutputStream(file2);
        	os.write((byte[]) outputBytes[0]);

        	System.out.println("");
			System.out.println("File " + inputString[i] + " done!");
			System.out.print("Total time: ");
			System.out.print(outputBytes[1]);
			System.out.println(" milliseconds");
	        System.out.println("Output file saved at " + path + outputString[i]);
	        System.out.println("");

	        time += (long) outputBytes[1];
        }

       	System.out.println("");
       	System.out.println("");
       	System.out.println("");
       	System.out.println("Total time used: " + time + " milliseconds");
       	System.out.println("");
	}
}