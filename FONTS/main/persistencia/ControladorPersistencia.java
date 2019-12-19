package main.persistencia;

import java.io.*;
import java.awt.Desktop;

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

            int num = 2;

            while (file.exists()) {
                file = new File(prefix + (num) + postfix);
                ++num;
            }
        }

        OutputStream os = new FileOutputStream(file);
        os.write(contingut);
        //TODO: Ara es guarda en la mateixa carpeta, hem de fer que es guardi en una nova?
    }

    public static void MakeDir(String path) throws IOException {
        new File(path).mkdir();
    }

    public static byte[] Llegeix(String path) throws IOException {

        File file = new File(path);
        byte[] b = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(b);
        fis.close();
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

    public static void open(String path) throws IOException{

        File file = new File(path);
        Desktop.getDesktop().open(file);
    }

    public static boolean isCarpeta(String path){

        File file = new File(path);
        return file.isDirectory();
    }

    public void setAllEstadistiquesFile(Object[] estadistiques) {
        try {
            FitxerEstGen.saveAllEstadistiquesFile(estadistiques);
        } catch (IOException e) {}
    }

    public Object [] getAllEstadistiquesFile() {
        try {
            return FitxerEstGen.getAllEstadistiquesFile();
        } catch (IOException e) {}
        return new Object[0];
    }

}