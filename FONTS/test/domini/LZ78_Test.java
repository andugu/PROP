package test.domini;

import main.domini.*;

import java.io.*;

public class LZ78_Test {
    public LZ78_Test() {

    }

    public static void main(String[] args) throws IOException {

        System.out.println("##################################################");
        System.out.println("# LZ78 Test #build 1.4 - last revision: 18.11.19 #");
        System.out.println("##################################################");

       String path = "";  //Si es mou la carpeta test, canviar path

        String[] inputString = new String[14];
        inputString[0] = "Fitxer_buit.txt";
        inputString[1] = "Fitxer_buit.txt.LZ78";
        inputString[2] = "Fitxer_frase.txt";
        inputString[3] = "Fitxer_frase.txt.LZ78";
        inputString[4] = "Fitxer_amb_accents_salts_linia.txt";
        inputString[5] = "Fitxer_amb_accents_salts_linia.txt.LZ78";
        inputString[6] = "Fitxer_repeticions.txt";
        inputString[7] = "Fitxer_repeticions.txt.LZ78";
        inputString[8] = "Fitxer_Shakespeare.txt";
        inputString[9] = "Fitxer_Shakespeare.txt.LZ78";
        inputString[10] = "Fitxer_Quijote.txt";
        inputString[11] = "Fitxer_Quijote.txt.LZ78";
        inputString[12] = "Fitxer_gran.txt";
        inputString[13] = "Fitxer_gran.txt.LZ78";


        String[] outputString = new String[14];
        outputString[0] = "Fitxer_buit.txt.LZ78";
        outputString[1] = "Fitxer_descomprimit_buit.txt";
        outputString[2] = "Fitxer_frase.txt.LZ78";
        outputString[3] = "Fitxer_descomprimit_frase.txt";
        outputString[4] = "Fitxer_amb_accents_salts_linia.txt.LZ78";
        outputString[5] = "Fitxer_descomprimit_amb_accents_salts_linia.txt";
        outputString[6] = "Fitxer_repeticions.txt.LZ78";
        outputString[7] = "Fitxer_decomprimit_repeticions.txt";
        outputString[8] = "Fitxer_Shakespeare.txt.LZ78";
        outputString[9] = "Fitxer_descomprimit_Shakespeare.txt";
        outputString[10] = "Fitxer_Quijote.txt.LZ78";
        outputString[11] = "Fitxer_descomprimit_Quijote.txt";
        outputString[12] = "Fitxer_gran.txt.LZ78";
        outputString[13] = "Fitxer_descomprimit_gran.txt";


        long time = 0;

        for (int i = 0; i < inputString.length; ++i){

            System.out.println();
            System.out.println("[" + i + "]" + "Starting to process " + path + "/" + inputString[i]);
            System.out.println();

            File file = new File(path + inputString[i]);
            byte[] b = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(b);
            fis.close();

            Object[] outputBytes;
            LZ78 lzss = new LZ78();

            if (i % 2 == 0)
                outputBytes = lzss.comprimir(b);

            else
                outputBytes = lzss.descomprimir(b);

            File file2 = new File(path + outputString[i]);
            OutputStream os = new FileOutputStream(file2);
            os.write((byte[]) outputBytes[0]);

            System.out.println();
            System.out.println("File " + inputString[i] + " done!");
            System.out.print("Total time: ");
            System.out.print(outputBytes[1]);
            System.out.println(" milliseconds");
            System.out.println("Output file saved at " + path + "/" + outputString[i]);
            System.out.println();

            time += (long) outputBytes[1];
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Total time used: " + time + " milliseconds");
        System.out.println();
    }

}
