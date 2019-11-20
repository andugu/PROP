package main.domini;
import java.io.*;
import java.util.Scanner;

public class DriverClasseComprimit {
    //Atributs
    private static Comprimit comprimit;

    //Metodes
    public static void testConstructor() {
        System.out.println("Indica les següents dades per a realitzar la creació d'un comprimit:");

        Scanner in = new Scanner(System.in);

        System.out.print("Escriu el path que conté l'arxiu de tipus comprimit: ");
        String path =  in.next();

        System.out.print("Escriu el tamany que ocupa l'arxiu de tipus comprimit: ");
        int tamany =  in.nextInt();

        System.out.println("Escriu l'algorisme que vols utilitzar, les possibilitats són les següents:");

        System.out.println("LZ78");
        System.out.println("LZSS");
        System.out.println("LZW");
        System.out.println("JPEG");

        String nomalgorisme = in.next();

        boolean correcte;
        if(nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
            correcte = true;
        else correcte = false;

        while(!correcte) {
            System.out.print("No és un algorisme correcte. Torna a escriure el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
            nomalgorisme = in.next();
            if(nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
                correcte = true;
        }

        Algorisme algorisme;
        if(nomalgorisme.equals("LZ78"))
            algorisme = new LZ78();
        else if(nomalgorisme.equals("LZSS"))
            algorisme = new LZSS();
        else if(nomalgorisme.equals("LZW"))
            algorisme = new LZW();
        else algorisme = new JPEG();

        comprimit = new Comprimit(path, tamany, algorisme);
        System.out.println("Ja s'ha creat una instància de la classe Comprimit.");
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

        Object[] contingutIestadistiques= comprimit.descomprimir(contingutEnBytes);

        byte[] contingutDescomprimit = (byte[]) contingutIestadistiques[0];
        double grau = (double) contingutIestadistiques[1];
        double velocitat = (double) contingutIestadistiques[2];
        long tempsDescompressio = (long) contingutIestadistiques[3];

        //Escribim el contingut al fitxer
        file = new File(pathDescomprimit + "/" + nomDescomprimit + ".txt");
        OutputStream os2 = new FileOutputStream(file);
        os2.write(contingutDescomprimit);

        System.out.println("Ja s'ha creat l'arxiu descomprimit. Aquest es troba en el path " + pathDescomprimit + " i s'anomena " + nomDescomprimit + ".txt");
        System.out.println("Per a realitzar la descompressió de l'arxiu, aquesta s'ha fet amb un grau de: " + grau);
        System.out.println("Per a realitzar la descompressió de l'arxiu, aquesta s'ha fet amb una velocitat de: " + velocitat);
        System.out.println("Per a realitzar la descompressió de l'arxiu s'ha trigat: " + tempsDescompressio + " mil·lisegons");
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Disposes de les següents opcions, però obligatoriament la primera en passar el test ha de ser la numero 1. Escriu l'opció:");
        System.out.println("1. Test Constructor");
        System.out.println("2. Test Descomprimir");
        System.out.println("3. Sortir");

        Scanner in = new Scanner(System.in);
        int i = in.nextInt();

        boolean correcte = false;
        if(i == 3 || i == 1) correcte = true;

        while(!correcte) {
            System.out.println("Recorda que la primera opció que ha de passar el test ha de ser la numero 1.");
            System.out.println("Escriu una altra vegada l'opció: ");
            i = in.nextInt();

            if(i == 1 || i == 3) correcte = true;
        }

        while(i != 3) {
            if(i == 1)
                testConstructor();
            else if(i == 2)
                testDescomprimir();
            else
                System.out.println("Opció no valida");

            System.out.println("Escull una altre opció:");
            i = in.nextInt();
        }
    }
}
