package main.domini;
import java.io.IOException;
import java.util.Scanner;

public class DriverControladorDomini {
    //Atributs
    private static ControladorDomini Controlador;

    //Metodes
    public static void testConstructor() {
        Controlador = new ControladorDomini();
        System.out.println("Ja s'ha creat una instància de la classe EstadistiquesGenerals.");
    }

    public static void testcomprimir() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba ubicat el fitxer a comprimir");
        String path = in.next();
        String nomalgorisme = escriu_algorisme();
        ControladorDomini.comprimir(path,nomalgorisme);
        System.out.print("La funció Comprimir ha estat cridada correctament ");
    }

    public static void testdescomprimir() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba ubicat el fitxer a descomprimir");
        String path = in.next();
        String nomalgorisme = escriu_algorisme();
        ControladorDomini.descomprimir(path,nomalgorisme);
        System.out.print("La funció Descomprimir ha estat cridada correctament ");

    }

    public static void testllegirFitxer() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba ubicat el fitxer a llegir");
        String path = in.next();
        ControladorDomini.llegirFitxer(path);
        System.out.print("La funció Llegir Fitxer ha estat cridada correctament ");
    }

    public static void testcompirmirCarpeta(){
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba la Carpeta a comprimir");
        String path = in.next();
        String nomalgorisme = escriu_algorisme();
        ControladorDomini.comprimirCarpeta(path,nomalgorisme);
        System.out.print("La funció Comprimir Carpeta ha estat cridada correctament ");

    }

    public static void testdescomprimirCarpeta(){
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba ubicat la Carpeta a descomprimir");
        String path = in.next();
        String nomalgorisme = escriu_algorisme();
        ControladorDomini.descomprimirCarpeta(path,nomalgorisme);

        System.out.print("La funció Descomprimir Carpeta ha estat cridada correctament ");
    }

    public static void testsaveFile() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba la Carpeta a comprimir");
        String path = in.next();
        String nomalgoritme = escriu_algorisme();
        System.out.print("Escriu 1 si el fitxer es comprimit o 2 si el fitxer es descomprimit");
        int opcio = in.nextInt();
        while (opcio != 1 && opcio != 2){
            System.out.println("La funció que has escollit és erronia, torna a escriure-la: ");
            opcio = in.nextInt();
        }
        boolean comprimit = false;
        if (opcio == 1){
            comprimit = true;
        }
        System.out.print("Escriu el contingut");
        String contingut = in.next();
        byte[] contingut_bytes = contingut.getBytes();
        ControladorDomini.saveFile(path,nomalgoritme,contingut_bytes,comprimit);
        System.out.print("La funció Save File ha estat cridada correctament ");
    }

    public static void testgetEstadistiquesGenerals(){
        String nomalgorisme = escriu_algorisme();
        Scanner in = new Scanner(System.in);

        System.out.print("Escriu 1 si vols triar dades generals de fitxers comprimits o 2 si vols triar dades generals de fitxers descomprimits");
        int opcio = in.nextInt();
        while (opcio != 1 && opcio != 2){
            System.out.println("La funció que has escollit és erronia, torna a escriure-la: ");
            opcio = in.nextInt();
        }
        boolean comprimit = false;
        if (opcio == 1){
            comprimit = true;
        }
        ControladorDomini.getEstadistiquesGenerals(nomalgorisme, comprimit);
        System.out.print("La funció getEstadistiquesGenerals ha estat cridada correctament ");
    }

    public static void testtriaAlgorisme() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Escriu el path on es troba ubicat el fitxer");
        String path = in.next();

        System.out.println("Selecciona la funció que vols fer");
        System.out.println("1 - Comprimir");
        System.out.println("2 - Descomprimir");
        System.out.println("3 - Visualitzar Estadistiques");
        System.out.println("4 - Comparar");
        System.out.println("5 - Usage");
        System.out.println("6 - Sortir");
        int opcio = in.nextInt();

        while(opcio != 1 && opcio != 2 && opcio != 3 && opcio != 4 && opcio != 5 && opcio != 6) {
            System.out.println("La funció que has escollit és erronia, torna a escriure-la: ");
            opcio = in.nextInt();
        }
        ControladorDomini.triaAlgorisme(path, opcio);
        System.out.print("La funció tria algorisme ha estat cridada correctament ");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Disposes de les següents opcions, però obligatoriament la primera en passar el test ha de ser la numero 1. Escriu l'opció:");
        System.out.println("1. Test Constructor");
        System.out.println("2. Test Comprimir");
        System.out.println("3. Test Descompirmir");
        System.out.println("4. Test Llegir Fitxer ");
        System.out.println("5. Test Comprimir Carpeta");
        System.out.println("6. Test Descomprimir Carpeta");
        System.out.println("7. Test Save File ");
        System.out.println("8. Test Get Estadistiques generals");
        System.out.println("9. Test Tria Algorisme");
        System.out.println("10. Sortir");

        Scanner in = new Scanner(System.in);
        int i = in.nextInt();

        boolean correcte = false;
        if(i == 10 || i == 1) correcte = true;
        while(!correcte) {
            System.out.println("Recorda que la primera opció que ha de passar el test ha de ser la numero 1.");
            System.out.println("Escriu una altra vegada l'opció: ");
            i = in.nextInt();

            if(i == 1 || i == 4) correcte = true;
        }
        System.out.println("Indica les dades que es demanen a continuació:");

        while(i != 10) {
            if(i == 1){
                testConstructor();
            }
            else if(i == 2){
                testcomprimir();
            }
            else if(i == 3){
                testdescomprimir();
            }
            else if(i == 4){
                testllegirFitxer();
            }
            else if(i == 5){
                testcompirmirCarpeta();
            }
            else if(i == 6){
                testdescomprimirCarpeta();
            }
            else if(i == 7){
                testsaveFile();
            }
            else if(i == 8){
                testgetEstadistiquesGenerals();
            }
            else if(i == 9){
                testtriaAlgorisme();
            }
            else {
                System.out.println("Opció no valida");
            }

            System.out.println("Escull una altre opció:");
            i = in.nextInt();
        }
    }

    private static String escriu_algorisme(){
        Scanner in = new Scanner(System.in);

        System.out.print("Escriu el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
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
        return nomalgorisme;
    }

}
