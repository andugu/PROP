package main.persistencia;

import java.io.*;


public class FitxerEstadistiquesGenerals {

    public FitxerEstadistiquesGenerals() {
    }

    public Object [] getAllEstadistiquesFile() throws IOException {
        Object[] AllEstadistiques = new Object[32];

        String path = "";

        /*
        Hem de llegir:
        - grau (double)
        - velocitat (double)
        - temps (double)
        - numeroElements (int)
        */


        String cadena;
        FileReader f = new FileReader(path);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            System.out.println(cadena);
        }
        b.close();



        return AllEstadistiques;
    }

    public void saveAllEstadistiquesFile(Object[] estadistiques) throws IOException {
        String path = "";

        String cadena;
        FileReader f = new FileReader(path);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            System.out.println(cadena);
        }
        b.close();
    }

    public static void main(String[] args) throws IOException {
        FitxerEstadistiquesGenerals fEstGen = new FitxerEstadistiquesGenerals();

        Object[] estadistiques = fEstGen.getAllEstadistiquesFile();

        fEstGen.saveAllEstadistiquesFile(estadistiques);
    }
}
