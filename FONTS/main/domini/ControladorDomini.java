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

    public static Object[] comprimir(String path, String algorisme) throws IOException {
        try {
            if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");
            if (algorisme == "Automatic") {
                algorisme = "LZW"; //perque es el millor
            }
            byte[] contingut = llegirFitxer(path);
            int tamany = contingut.length;

            int i=2;
            if (algorisme.equals("LZ78")) i = 0;
            else if (algorisme.equals("LZSS")) i = 1;
            else if (algorisme.equals("LZW")) i = 2;
            else if (algorisme.equals("JPEG")) i = 3;

            Descomprimit Des = new Descomprimit(path, tamany, Algorismes[i]); //oju al descomprimit canviarho a Comprimit
            Object[] A = Des.comprimir(contingut);

            System.out.println("Ja s'ha comprimit l'arxiu que es troba al directori següent: " + path);

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
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);

        }
        return new Object[1];
    }

    public static Object[] descomprimir(String path, String algorisme) throws IOException {
        try {
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

            System.out.println("Ja s'ha descomprimit l'arxiu que es troba al directori següent: " + path);

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
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);

        }
        return new Object[1];

    }

    public static byte[] llegirFitxer(String path) throws IOException {
        try {
            if (!ControladorPersistencia.existeix_path(path)) throw new Exception("Path no existent");
            return ControladorPersistencia.Llegeix(path);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);

        }
        return new byte[1];
    }

    public static void comprimirCarpeta(String path, String path_nou, String algorisme){
        String [] noms = ControladorPersistencia.getNames(path);
        int n = noms.length;
        while(int i = 0; i < n; ++i){
            boolean es_pot_comprimir = true;
            String path_arxiu = path + noms[i];
            char[] nou_path_a_arxius = path_arxiu.toCharArray(); //per comprobar txt o ppm
            String algorisme_a_comprimir = algorisme;
            int mida = nou_path_a_arxius.length();
            if (nou_path_a_arxius[mida-1] = 't' & nou_path_a_arxius[mida-2] = 'x' & nou_path_a_arxius[mida-3] = 't' ){//arxiu es .txt -> cridar comprimir algorisme
                es_pot_comprimir = true;
                algorisme_a_comprimir = algorisme;
            }
            else if (nou_path_a_arxius[mida-1] = 'm' & nou_path_a_arxius[mida-2] = 'p' & nou_path_a_arxius[mida-3] = 'p'){//arxiu es .ppm -> cridar comprimir amb JPEG
                es_pot_comprimir = true;
                algorisme_a_comprimir = "JPEG";
            }
            else if (CPer.isCarpeta(path_arxiu)){ //arxiu es carpeta -> cridar
                comprimirCarpeta(path_arxiu, path_nou+noms[i], algorisme_a_comprimir);//cridarem a la carpeta amb el path_del seu arxiu, i el path nou TODO: mirarho!
                es_pot_comprimir = false;
            }
            else{//no fer res
                es_pot_comprimir = false;
                System.out.println("Aquest Fitxer amb nom: " + noms[i] + " no es pot Comprimir perque no es comprimible amb l'opció seleccionada");
            }
            if (es_pot_comprimir){//fer tot el proces de comprimir, sobretot guardar-ho amb el path nou
                byte[] contingut = llegirFitxer(path);
                int tamany = contingut.length;
                int j=2;
                if (algorisme_a_comprimir = "LZ78") j = 0;
                else if (algorisme_a_comprimir = "LZSS") j = 1;
                else if (algorisme_a_comprimir = "LZW") j = 2;
                else if (algorisme_a_comprimir = "JPEG") j = 3;

                Descomprimit Des = new Descomprimit(path_nou+noms[i], tamany, Algorismes[j]); //oju al descomprimit canviarho a Comprimit
                Object[] A = Des.comprimir(contingut)

                byte[] contingut_retorn = (byte[]) A[0];
                double grau = (double) A[1];
                double velocitat = (double) A[2];
                long temps = (long) A[3];
                saveFile(path_nou+noms[i], algorisme_a_comprimir, contingut_retorn, true);
                boolean comprimit = false; //canviarho a true al descomprimir
                Est.assignarNovaEstadistica(grau, velocitat, temps, algorisme_a_comprimir, comprimit);
            }
        }
    }

    public static void descomprimirCarpeta(String path, String path_nou, String algorisme){
        String [] noms = ControladorPersistencia.getNames(path);
        int n = noms.length;
        while(int i = 0; i < n; ++i){
            boolean es_pot_descomprimir = true;
            String path_arxiu = path + noms[i];
            char[] nou_path_a_arxius = path_arxiu.toCharArray(); //per comprobar txt o ppm
            String algorisme_a_descomprimir = algorisme;
            char[] algorisme_char = algorisme_a_descomprimir.toCharArray();
            int mida_path = nou_path_a_arxius.length();
            int mida_alg = algorisme_char.length();
            if (algorisme_char[mida_alg-1] == nou_path_a_arxius[mida_path-1] & algorisme_char[mida_alg-2] == nou_path_a_arxius[mida_path-2] & algorisme_char[mida_alg-3] == nou_path_a_arxius[mida_path-3]){
                es_pot_descomprimir = true;
            }
            else if (CPer.isCarpeta(path_arxiu)){ //arxiu es carpeta -> cridar
                descomprimirCarpeta(path_arxiu, path_nou+noms[i], algorisme_a_descomprimir);//cridarem a la carpeta amb el path_del seu arxiu, i el path nou TODO: mirarho!
                es_pot_descomprimir = false;
            }
            else{//no fer res
                es_pot_descomprimir = false;
                System.out.println("Aquest Fitxer amb nom: " + noms[i] + " no es pot Descomprimir perque no es compatible amb l'opció seleccionada");
            }
            if (es_pot_descomprimir){//fer tot el proces de comprimir, sobretot guardar-ho amb el path nou
                byte[] contingut = llegirFitxer(path);
                int tamany = contingut.length;
                int j=2;
                if (algorisme_a_descomprimir = "LZ78") j = 0;
                else if (algorisme_a_descomprimir = "LZSS") j = 1;
                else if (algorisme_a_descomprimir = "LZW") j = 2;
                else if (algorisme_a_descomprimir = "JPEG") j = 3;

                Comprimit Com = new Comprimit(path_nou+noms[i], tamany, Algorismes[j]);
                Object[] A = Com.descomprimir(contingut)

                byte[] contingut_retorn = (byte[]) A[0];
                double grau = (double) A[1];
                double velocitat = (double) A[2];
                long temps = (long) A[3];
                saveFile(path_nou+noms[i], algorisme_a_descomprimir, contingut_retorn, true);
                boolean comprimit = true;
                Est.assignarNovaEstadistica(grau, velocitat, temps, algorisme_a_descomprimir, comprimit);
            }
        }

    }

    public static void saveFile(String path, String algoritme, byte[] contingut, boolean comprimir) throws IOException {
        try{
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
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);

        }
    }

    public static Object[] getEstadistiquesGenerals(String nom_algorisme, boolean comprimit){
       return Est.getEstadistiques(nom_algorisme,comprimit);
    }

    //OPCIO POT SER: Comprimir (1), Descomprimir(2) o Comparar(4)
    public static String[] triaAlgorisme(String path_entrada, int opcio) throws Exception {
        try {
            if (!ControladorPersistencia.existeix_path(path_entrada)) throw new Exception("Path no existent");
            String[] algorismes;
            int llargada = path_entrada.length()-1;
            char [] path = path_entrada.toCharArray();
            if ((path[llargada] == 't') && (path[llargada-1] == 'x') && (path[llargada-2] == 't')) { //fitxer DESCOMPRIMIT TEXT
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
            else{
                System.out.println();
                throw new Exception("Path incorrecte");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return new String[1];
    }

    public void saveAllEstadistiques() {
        Object[] AllEstadistiques = Est.getAllEstadistiques();

        CPer.setAllEstadistiquesFile(AllEstadistiques);
    }

    public void setAllEstadistiques() {
        Object[] Allestadistiques = CPer.getAllEstadistiquesFile();

        if(Allestadistiques.length != 0)
            Est.setAllEstadistiques(Allestadistiques);
    }

 }
