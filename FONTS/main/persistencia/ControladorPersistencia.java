package main.persistencia;

import main.domini.ControladorDomini;

import java.io.*;

public class ControladorPersistencia {
    private FitxerEstadistiquesGenerals FitxerEstGen;

    public static void main(String[] args) {}

    public ControladorPersistencia(){
        FitxerEstGen = new FitxerEstadistiquesGenerals();
    }

    public static void Save(String nou_path, byte[] contingut) throws IOException {

        File file = new File(nou_path);

        if (file.exists()) {

            int index = nou_path.lastIndexOf('.');

            String prefix = nou_path.substring(0, index);
            String postfix = nou_path.substring(index);

            int num = 0;

            while (file.exists()) {
                file = new File(prefix + (num) + postfix);
                ++num;
            }
        }

        OutputStream os = new FileOutputStream(file);
        os.write(contingut);

        System.out.println("\u001B[32m" + "El fitxer ha estat guardat, en el següent directori: " + nou_path + "\u001B[0m");
    }

    public static byte[] Llegeix(String path) throws IOException {

        File file = new File(path);
        byte[] b = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(b);
        fis.close();

        System.out.println("\u001B[32m" + "El fitxer ha estat llegit" + "\u001B[0m");

        return b;
    }

    public static boolean existeix_path(String path){
        File file = new File(path);
        return file.exists();
    }

    public static String[] getNames(String path){

        File file = new File(path);
        return file.list();
    }

    public static boolean isCarpeta(String path){

        File file = new File(path);
        return file.isDirectory();
    }

    public void setAllEstadistiquesFile(Object[] estadistiques) throws IOException {
        FitxerEstGen.saveAllEstadistiquesFile(estadistiques);
    }

    public Object [] getAllEstadistiquesFile() throws IOException {
        return FitxerEstGen.getAllEstadistiquesFile();
    }

}