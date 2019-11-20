package main.domini;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverClasseLZ78 {
    //Atributs
    private static LZ78 Algo;

    //Metodes
    public static void testConstructor() {
        Algo = new LZ78();
        System.out.println("Ja s'ha creat una instància de la classe LZSS.");
    }

    public static void testComprimir() throws IOException {
        System.out.println("Indica les següents dades per a realitzar la compressió:");

        Scanner in = new Scanner(System.in);

        System.out.print("Escriu el path que conté l'arxiu que vols comprimir, assegurat que aquest acabi amb l'arxiu de text a comprimir: ");
        String path =  in.next();

        System.out.print("Escriu el path on vols que es generi l'arxiu comprimit: ");
        String pathComprimit =  in.next();

        System.out.print("Escriu el nom que vols que tingui l'arxiu comprimit, sense incloure l'extensió: ");
        String nomComprimit = in.next();

        System.out.println("Ara es realitza el procés de compressió");

        //Llegim el fitxer
        File file = new File(path);
        byte[] contingutEnBytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(contingutEnBytes);
        fis.close();

        Object[] contingutcomprimitItemps = Algo.comprimir(contingutEnBytes);

        byte[] contingutComprimit = (byte[]) contingutcomprimitItemps[0];
        long tempsCompressio = (long) contingutcomprimitItemps[1];

        //Escribim el contingut al fitxer
        file = new File(pathComprimit + "/" + nomComprimit + ".txt");
        OutputStream os2 = new FileOutputStream(file);
        os2.write(contingutComprimit);

        System.out.println("Ja s'ha creat l'arxiu comprimit. Aquest es troba en el path " + pathComprimit + " i s'anomena " + nomComprimit + ".txt");
        System.out.println("Per a realitzar la compressió de l'arxiu s'ha trigat: " + tempsCompressio + " mil·lisegons");
    }

    public static void testDescomprimir() throws IOException {
        System.out.println("Indica les següents dades per a realitzar la descompressió:");

        Scanner in = new Scanner(System.in);

        System.out.print("Escriu el path que conté l'arxiu que vols descomprimir, assegurat que aquest acabi amb l'arxiu de text a descomprimir: ");
        String path =  in.next();

        System.out.print("Escriu el path on vols que es generi l'arxiu descomprimit: ");
        String pathDescomprimit =  in.next();

        System.out.print("Escriu el nom que vols que tingui l'arxiu descomprimit, sense incloure l'extensió: ");
        String nomDescomprimit = in.next();

        System.out.println("Ara es realitza el procés de descompressió");

        //Llegim el fitxer
        File file = new File(path);
        byte[] contingutEnBytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(contingutEnBytes);
        fis.close();

        Object[] contingutDescomprimitItemps = Algo.descomprimir(contingutEnBytes);

        byte[] contingutDescomprimit = (byte[]) contingutDescomprimitItemps[0];
        long tempsDescompressio = (long) contingutDescomprimitItemps[1];

        //Escribim el contingut al fitxer
        file = new File(pathDescomprimit + "/" + nomDescomprimit + ".txt");
        OutputStream os2 = new FileOutputStream(file);
        os2.write(contingutDescomprimit);

        System.out.println("Ja s'ha creat l'arxiu descomprimit. Aquest es troba en el path " + pathDescomprimit + " i s'anomena " + nomDescomprimit + ".txt");
        System.out.println("Per a realitzar la descompressió de l'arxiu s'ha trigat: " + tempsDescompressio + " mil·lisegons");
    }

    public static void testconverteix_a_text() throws IOException {
        ArrayList<Integer> Llista1 = new ArrayList<Integer>();
        ArrayList<Character> Llista2 = new ArrayList<Character>();
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu la llargada de vectors");
        int mida =  in.nextInt();
        System.out.print("Ves escribint simultaniament els dos vectors, el primer d'enters i el segon de caracters");
        for (int i = 0; i< mida; ++i){
            int ENTER =  in.nextInt();
            String caracter1 = in.next();
            char [] caracter2 = caracter1.toCharArray();
            char caracter3 = caracter2[0];
            Llista1.add(ENTER);
            Llista2.add(caracter3);
        }
        int mida_numero =  in.nextInt();
        LZ78.converteix_a_text(Llista1,Llista2,mida_numero);
        System.out.println("S'ha cridat correctament a la funció converteix_a_text");

    }


    public static void main(String[] args) throws Exception{
        System.out.println("Disposes de les següents opcions, però obligatoriament la primera en passar el test ha de ser la numero 1. Escriu l'opció:");
        System.out.println("1. Test Constructor");
        System.out.println("2. Test Comprimir");
        System.out.println("3. Test Descomprimir");
        System.out.println("4. Convertir a text");
        System.out.println("5. Sortir");

        Scanner in = new Scanner(System.in);
        int i = in.nextInt();

        boolean correcte = false;
        if(i == 5 || i == 1) correcte = true;

        while(!correcte) {
            System.out.println("Recorda que la primera opció que ha de passar el test ha de ser la numero 1.");
            System.out.println("Escriu una altra vegada l'opció: ");
            i = in.nextInt();

            if(i == 1 || i == 5) correcte = true;
        }

        while(i != 5) {
            if(i == 1) {
                testConstructor();
            }
            else if(i == 2) {
                testComprimir();
            }
            else if(i == 3) {
                testDescomprimir();
            }
            else if(i ==4){
                testconverteix_a_text();
            }
            else {
                System.out.println("Opció no valida");
            }

            System.out.println("Escull una altre opció:");
            i = in.nextInt();
        }
    }


}
