package test.domini;

import main.domini.*;

import java.io.*;

public class Descomprimit_Test {

    public Descomprimit_Test() {

    }

    public static void main(String[] args) throws IOException {

        System.out.println("########################################################");
        System.out.println("# Descomprimit Test #build 1.4 - last revision: 16.11.19  #");
        System.out.println("########################################################");

        String path = ""; //Si es mou la carpeta test, canviar path

        String[] inputString = new String[7];
        inputString[0] = "Fitxer_buit.txt.LZSS";
        inputString[1] = "Fitxer_frase.txt.LZSS";
        inputString[2] = "Fitxer_amb_accents_salts_linia.txt.LZSS";
        inputString[3] = "Fitxer_repeticions.txt.LZSS";
        inputString[4] = "Fitxer_Shakespeare.txt.LZSS";
        inputString[5] = "Fitxer_Quijote.txt.LZSS";
        inputString[6] = "Fitxer_gran.txt.LZSS";


        String[] outputString = new String[7];
        outputString[0] = "Fitxer_descomprimit_buit.txt";
        outputString[1] = "Fitxer_descomprimit_frase.txt";
        outputString[2] = "Fitxer_descomprimit_amb_accents_salts_linia.txt";
        outputString[3] = "Fitxer_decomprimit_repeticions.txt";
        outputString[4] = "Fitxer_descomprimit_Shakespeare.txt";
        outputString[5] = "Fitxer_descomprimit_Quijote.txt";
        outputString[6] = "Fitxer_descomprimit_gran.txt";


        double grau = 0.0;
        double velocitat = 0.0;
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

            Algorisme algorisme = new LZSS();
            Descomprimit descomprimit = new Descomprimit(path, b.length, algorisme);

            Object[] contingutIestadistiques = descomprimit.comprimir(b);

            File file2 = new File(path + outputString[i]);
            OutputStream os = new FileOutputStream(file2);
            os.write((byte[]) contingutIestadistiques[0]);

            System.out.println();
            System.out.println("File " + inputString[i] + " done!");
            System.out.print("Total grade: ");
            System.out.println((double) contingutIestadistiques[1]);
            System.out.print("Total speed: ");
            System.out.println((double) contingutIestadistiques[2]);
            System.out.print("Total time: ");
            System.out.print((long) contingutIestadistiques[3]);
            System.out.println(" milliseconds");
            System.out.println("Output file saved at " + path + "/" + outputString[i]);
            System.out.println();

            grau += (double) contingutIestadistiques[1];
            velocitat += (double) contingutIestadistiques[2];
            time += (long) contingutIestadistiques[3];
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Total grade: " + (int)grau/7.0);
        System.out.println("Total speed: " + (int)velocitat/7.0);
        System.out.println("Total time used: " + time + " milliseconds");
        System.out.println();
    }
}
