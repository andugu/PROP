package main.domini;

import main.persistencia.*;
import java.io.IOException;

public class ControladorDomini {
    //atributs:
    private static ControladorPersistencia CPer;
    private static EstadistiquesGenerals Est;
    private static Algorisme[] Algorismes;

    public ControladorDomini(){ //classe creadora que tindra l'assosiació EstadistiquesGenerals
        CPer = new ControladorPersistencia();
        Est = new EstadistiquesGenerals();
        Algorismes = new Algorisme[4];
        Algorismes[0] = new LZ78();
        Algorismes[1] = new LZSS();
        Algorismes[2] = new LZW();
        Algorismes[3] = new JPEG();

        setAllEstadistiques(); //Inicialitza les estadistiques generals amb els valors del fitxer Estadistiques Generals
    }

    public static Object[] comprimir(String path, String algorisme) throws Exception {
        if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");
        if (algorisme == "Automatic") {
            algorisme = "LZW"; //perque es el millor
        }
        byte[] contingut = llegirFitxer(path);
        int tamany = contingut.length;

        int i = 2;
        if (algorisme.equals("LZ78")) i = 0;
        else if (algorisme.equals("LZSS")) i = 1;
        else if (algorisme.equals("LZW")) i = 2;
        else if (algorisme.equals("JPEG")) i = 3;

        Descomprimit Des = new Descomprimit(path, tamany, Algorismes[i]); //oju al descomprimit canviarho a Comprimit
        Object[] A = Des.comprimir(contingut);

        byte[] contingut_retorn = (byte[]) A[0];
        double grau = (double) A[1];
        double velocitat = (double) A[2];
        long temps = (long) A[3];
        saveFile(path, algorisme, contingut_retorn, true);
        boolean comprimit = false;
        Est.assignarNovaEstadistica(grau, velocitat, temps, algorisme, comprimit);
        Object[] Estadistiques_generades = new Object[3];
        Estadistiques_generades[0] = grau;
        Estadistiques_generades[1] = velocitat;
        Estadistiques_generades[2] = temps;

        return Estadistiques_generades;
    }

    public static Object[] descomprimir(String path, String algorisme) throws Exception {

        if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");
        byte[] contingut = llegirFitxer(path);
        //Creem descomprimit
        int tamany = contingut.length;

        int i = 2;
        if (algorisme.equals("LZ78")) i = 0;
        else if (algorisme.equals("LZSS")) i = 1;
        else if (algorisme.equals("LZW")) i = 2;
        else if (algorisme.equals("JPEG")) i = 3;

        Comprimit Comp = new Comprimit(path, tamany, Algorismes[i]);
        Object[] A = Comp.descomprimir(contingut);

        byte[] contingut_retorn = (byte[]) A[0];
        double grau = (double) A[1];
        double velocitat = (double) A[2];
        long temps = (long) A[3];
        saveFile(path, algorisme, contingut_retorn, false);
        boolean comprimit = true;
        Est.assignarNovaEstadistica(grau, velocitat, temps, algorisme, comprimit);
        Object[] RETORN = new Object[3];
        RETORN[0] = grau;
        RETORN[1] = velocitat;
        RETORN[2] = temps;
        return RETORN;
    }

    public static byte[] llegirFitxer(String path) throws Exception {
        if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");
        return ControladorPersistencia.Llegeix(path);
    }

    public static void comprimirCarpeta(String path, String algorisme) throws Exception {

        if (algorisme.equals("Automatic"))
            algorisme = "LZW";

        Object[] a = comprimirCarpeta_rec(path, algorisme);
        String prefix = path.substring(path.lastIndexOf("/")+1, path.length()) + "\n" + algorisme + "\n";
        byte[] aux = concatenateByteArray(prefix.getBytes(), (byte[]) a[0]);
        ControladorPersistencia.Save(path + ".DirComp", aux);
    }

    private static Object[] comprimirCarpeta_rec(String path, String algorisme) throws Exception {
        if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");

        String [] noms = ControladorPersistencia.getNames(path);
        int n = noms.length;

        byte[] output = new byte[0];
        float total_time = 0.0f;

        for (int x = 0; x < n; ++x){

            if (!noms[x].startsWith(".")) {

                String path_arxiu = path + "/" + noms[x];

                int i = 2;
                if (algorisme.equals("LZ78")) i = 0;
                else if (algorisme.equals("LZSS")) i = 1;
                // LZW == 2
                if (noms[x].contains(".ppm"))
                    i = 3;

                // Comprimeix carpeta
                if (ControladorPersistencia.isCarpeta(path_arxiu)){
                    Object[] c = comprimirCarpeta_rec(path_arxiu, algorisme);
                    total_time += (float) c[1];
                    // Inici carpeta
                    String prefix = "IC\n" + noms[x] + "\n";
                    // Fi carpeta
                    String sufix = "FC\n";
                    byte[] aux = concatenateByteArray(prefix.getBytes(), (byte[]) c[0]);
                    aux = concatenateByteArray(aux, sufix.getBytes());
                    output = concatenateByteArray(output, aux);
                }

                // Comprimeix arxiu
                else {
                    byte[] contingut = ControladorPersistencia.Llegeix(path_arxiu);
                    int tamany = contingut.length;
                
                    Descomprimit Des = new Descomprimit(path_arxiu, tamany, Algorismes[i]);
                    Object[] A = Des.comprimir(contingut);

                    byte[] contingut_retorn = (byte[]) A[0];

                    double grau = (double) A[1];
                    double velocitat = (double) A[2];
                    long temps = (long) A[3];
                    Est.assignarNovaEstadistica(grau, velocitat, temps, algorisme, false);
                    total_time += temps;

                    tamany = ((byte[]) A[0]).length;

                    String prefix = noms[x] + "\n" + tamany + "\n";
                    byte[] aux = concatenateByteArray(prefix.getBytes(), contingut_retorn);

                    output = concatenateByteArray(output, aux);
                }
            }
        }

        Object[] ret = new Object[2];
        ret[0] = output;
        ret[1] = total_time;
        return ret;
    }

    public static void descomprimirCarpeta(String path) throws Exception {

        byte[] input = ControladorPersistencia.Llegeix(path);
        String[] prefix = new String(input).split(System.getProperty("line.separator"));
        String nom_carpeta = prefix[0];
        String algorisme = prefix[1];
        int index = path.lastIndexOf("/");
        if (index == -1)
            index = 0;
        String path_nou = path.substring(0, index);
        ControladorPersistencia.MakeDir(path_nou + "/" + nom_carpeta);

        descomprimirCarpeta_rec(path_nou + "/" + nom_carpeta, algorisme, input, nom_carpeta.length()+algorisme.length()+2);
    }

    // Retorna el index en el que s'ha quedat
    private static int descomprimirCarpeta_rec(String path, String algorisme, byte[] input, int index) throws Exception {
        while (index < input.length){
            byte[] content = segregateFromByteArray(input, index, input.length);
            String[] prefix = new String(content).split(System.getProperty("line.separator"));

            // Inici de carpeta
            if (prefix[0].contains("IC")) {
                String nomCarpeta = prefix[1];
                int aux_index = index + prefix[0].length() + prefix[1].length() + 2;
                ControladorPersistencia.MakeDir(path + "/" + nomCarpeta);
                index = descomprimirCarpeta_rec(path + "/" + nomCarpeta, algorisme, input, aux_index);
            }

            else if (prefix[0].contains("FC"))
                return index + prefix[0].length() + 1;

            // Tenim un fitxer
            else {
                String nomFitxer = prefix[0];

                int i = 2;
                if (algorisme.equals("LZ78")) i = 0;
                else if (algorisme.equals("LZSS")) i = 1;
                // LZW == 2
                if (nomFitxer.contains(".ppm"))
                    i = 3;

                int size = Integer.parseInt(prefix[1]);
                int start = index + prefix[0].length() + prefix[1].length() + 2;
                byte[] fitxer = segregateFromByteArray(input, start, start + size);

                Comprimit Comp = new Comprimit(path + "/" + nomFitxer, size, Algorismes[i]);
                Object[] A = Comp.descomprimir(fitxer);

                byte[] contingut_retorn = (byte[]) A[0];

                double grau = (double) A[1];
                double velocitat = (double) A[2];
                long temps = (long) A[3];
                Est.assignarNovaEstadistica(grau, velocitat, temps, algorisme, true);

                ControladorPersistencia.Save(path + "/" + nomFitxer, contingut_retorn);

                index += prefix[0].length() + prefix[1].length() + 2 + size;
            }
        }

        return index;
    }

    public static void saveFile(String path, String algoritme, byte[] contingut, boolean comprimir) throws Exception {

        if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");
        String nou_path;
        if (comprimir) {
            nou_path = path + "." + algoritme;
        }
        else{
            int n = path.length();
            char[] aux1 = path.toCharArray();

            int mida = n;

            if (algoritme.equals("LZW")) mida-=4;
            else mida-=5;

            char[] aux2 = new char[mida];
            for (int i = 0; i < mida; ++i) {
                aux2[i] = aux1[i];
            }
            nou_path = new String(aux2);
        }
        ControladorPersistencia.Save(nou_path, contingut);
    }

    public static Object[] getEstadistiquesGenerals(String nom_algorisme, boolean comprimit){
       return Est.getEstadistiques(nom_algorisme,comprimit);
    }

    public static String[] triaAlgorisme(String path_entrada, int opcio) throws Exception {
            if (!ControladorPersistencia.existeix_path(path_entrada)) throw new Exception("Path no existent");
            String[] algorismes;
            int llargada = path_entrada.length()-1;
            char [] path = path_entrada.toCharArray();

            if (ControladorPersistencia.isCarpeta(path_entrada)){
                algorismes = new String[4];
                algorismes[0] = "Automatic";
                algorismes[1] = "LZ78";
                algorismes[2] = "LZSS";
                algorismes[3] = "LZW";
                return algorismes;
            }
            else if ((path[llargada] == 't') && (path[llargada-1] == 'x') && (path[llargada-2] == 't')) { //fitxer DESCOMPRIMIT TEXT
                if (opcio == 2)throw new Exception("No es pot descomprimir un fitxer que no esta comprimit");
                algorismes = new String[4];
                algorismes[0] = "Automatic";
                algorismes[1] = "LZ78";
                algorismes[2] = "LZSS";
                algorismes[3] = "LZW";
                return algorismes;
            }
            else if ((path[llargada] == 'm') && (path[llargada-1] == 'p') && (path[llargada-2] == 'p')) { //FITXER DESCOMPRIMIT IMATGE
                if (opcio == 2) throw new Exception("No es pot descomprimir un fitxer que no esta comprimit");
                algorismes = new String[1];
                algorismes[0] = "JPEG";
                return algorismes;
            }
            else if ((path[llargada] == 'G') && (path[llargada-1] == 'E') && (path[llargada-2] == 'P') && (path[llargada-3] == 'J')){
                if (opcio == 1 || opcio == 4) throw new Exception("No es pot comprimir un fitxer descomprimit");
                algorismes = new String[1];
                algorismes[0] = "JPEG";
                return algorismes;
            }
            else if ((path[llargada] == '8') && (path[llargada-1] == '7') && (path[llargada-2] == 'Z') && (path[llargada-3] == 'L')) {
                if (opcio == 1 || opcio == 4) throw new Exception("No es pot comprimir un fitxer descomprimit");
                algorismes = new String[1];
                algorismes[0] = "LZ78";
                return algorismes;
            }
            else if ((path[llargada] == 'S') && (path[llargada-1] == 'S') && (path[llargada-2] == 'Z') && (path[llargada-3] == 'L')) {
                if (opcio == 1 || opcio == 4) throw new Exception("No es pot comprimir un fitxer descomprimit");
                algorismes = new String[1];
                algorismes[0] = "LZSS";
                return algorismes;
            }
            else if ((path[llargada] == 'W') && (path[llargada-1] == 'Z') && (path[llargada-2] == 'L')) {
                if (opcio == 1 || opcio == 4) throw new Exception("No es pot comprimir un fitxer descomprimit");
                algorismes = new String[1];
                algorismes[0] = "LZW";
                return algorismes;
            }
            else {
                throw new Exception("Path incorrecte");
            }
    }

    public static void saveAllEstadistiques() {
        Object[] AllEstadistiques = Est.getAllEstadistiques();

        CPer.setAllEstadistiquesFile(AllEstadistiques);
    }

    private static void setAllEstadistiques() {
        Object[] Allestadistiques = CPer.getAllEstadistiquesFile();

        if(Allestadistiques.length != 0)
            Est.setAllEstadistiques(Allestadistiques);
    }

    // Concatena dos byte[]
    private static byte[] concatenateByteArray(byte[] b1, byte[] b2){

        byte[] b = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, b, 0, b1.length);
        System.arraycopy(b2, 0, b, b1.length, b2.length);
        return b;
    }

    // Retorna un subbyteArray de input amb el primer element apuntat per start i que te mida size
    private static byte[] segregateFromByteArray(byte[] input, int start, int end){
        byte[] b = new byte[end-start];
        System.arraycopy(input, start, b, 0, end-start);
        return b;
    }
 }
