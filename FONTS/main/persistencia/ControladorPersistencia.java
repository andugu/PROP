package main.persistencia;

import java.io.*;

public class ControladorPersistencia {

    public ControladorPersistencia(String path, byte[] contingut){

    }

    public static void Save(String nou_path, byte[] contingut) throws IOException {

        File file2 = new File(nou_path);

        while(file2.exists()) {
            if (file2.exists()) {
                int index = nou_path.lastIndexOf('.');
                String prefix = nou_path.substring(0, index) + "output";
                nou_path = prefix + nou_path.substring(index);
                file2 = new File(nou_path);
            }
        }

        OutputStream os = new FileOutputStream(file2);
        os.write(contingut);

        System.out.println("El fitxer ha estat guardat, en el següent directori: " + nou_path);
    }

    public static byte[] Llegeix(String path) throws IOException {

        File file = new File(path);
        byte[] b = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(b);
        fis.close();

        System.out.println("El fitxer ha estat llegit");

        return b;
    }
    public static boolean existeix_path(String path){
        File file = new File(path);

        return file.exists();
    }

}