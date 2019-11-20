package test.domini;

import main.domini.EstadistiquesGenerals;

public class EstadistiquesGenerals_Test {

    public EstadistiquesGenerals_Test() {

    }

    public static void main(String[] args) {

        System.out.println("#####################################################################");
        System.out.println("# EstadistiquesGenerals Test #build 1.4 - last revision: 16.11.19   #");
        System.out.println("#####################################################################");

        boolean comprimit;
        String nomalgorisme;

        for (int i = 0; i < 8; ++i){

            if(i <= 3) comprimit = true;
            else comprimit = false;

            if(i == 0 || i == 4) nomalgorisme = "LZ78";
            else if(i == 1 || i == 5) nomalgorisme = "LZSS";
            else if(i == 2 || i == 6) nomalgorisme = "LZW";
            else nomalgorisme = "JPEG";

            EstadistiquesGenerals estadgenerals = new EstadistiquesGenerals();
            estadgenerals.assignarNovaEstadistica(147.9, 23.56, 856, nomalgorisme, comprimit);
            estadgenerals.assignarNovaEstadistica(267.2, 356.1, 632, nomalgorisme, comprimit);
            estadgenerals.assignarNovaEstadistica(356.1, 256.5, 221, nomalgorisme, comprimit);
            estadgenerals.assignarNovaEstadistica(1346.7, 167.43, 33456, nomalgorisme, comprimit);

            Object[] estadistiques = estadgenerals.getEstadistiques(nomalgorisme, comprimit);

            System.out.println();
            if(comprimit) System.out.println("Estadistiques Generals dels fitxers comprimits amb l'algorisme " + nomalgorisme + " done!");
            else System.out.println("Estadistiques Generals dels fitxers descomprimits amb l'algorisme " + nomalgorisme + " done!");
            System.out.print("Total grade: ");
            System.out.println((double) estadistiques[0]);
            System.out.print("Total speed: ");
            System.out.println((double) estadistiques[1]);
            System.out.print("Total time: ");
            System.out.print((double) estadistiques[2]);
            System.out.println(" milliseconds");
            System.out.println();
        }
    }
}
