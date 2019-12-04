package main.persistencia;

import java.io.*;


public class FitxerEstadistiquesGenerals {
    //atributs
    private final static String path = "FONTS/main/persistencia/FitxerEstadistiquesGenerals.txt";  //TODO: es correcte aquest path?

    public FitxerEstadistiquesGenerals() {
    }

    public Object [] getAllEstadistiquesFile() throws IOException {
        Object[] AllEstadistiques = new Object[32];


        String cadena;
        double valor;
        int i = 0;
        FileReader f = new FileReader(path);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            if(i == 3||i == 7||i == 11||i == 15||i == 19||i == 23||i == 27||i == 31) //Pels valors del fitxer que són enters
                AllEstadistiques[i] = Integer.parseInt(cadena);
            else //Pels valors del fitxer que són double
                AllEstadistiques[i] = Double.parseDouble(cadena);

            ++i;
        }
        b.close();

        return AllEstadistiques;
    }

    public void saveAllEstadistiquesFile(Object[] estadistiques) throws IOException {

        File file = new File(path);
        FileWriter w = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);

        for(int i = 0; i < estadistiques.length; ++i){

            if(i == 0)
                wr.write(Double.toString((double)estadistiques[i]));

            else {
                bw.newLine();

                if(i == 3||i == 7||i == 11||i == 15||i == 19||i == 23||i == 27||i == 31)
                    wr.write(String.valueOf((int) estadistiques[i]));
                else
                    wr.write(Double.toString((double) estadistiques[i]));
            }
        }

        wr.close();
        bw.close();

    }
}
