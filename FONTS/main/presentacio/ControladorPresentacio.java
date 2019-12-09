package main.presentacio;

import main.domini.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class ControladorPresentacio {
    //atributs
    private static ControladorDomini cDom = new ControladorDomini();

    public static void main (String[] args) throws Exception {

        try {
            while(true) {

                System.out.println("Selecciona la funció que vols fer");
                System.out.println("1 - Comprimir");
                System.out.println("2 - Descomprimir");
                System.out.println("3 - Comprimir Carpeta");
                System.out.println("4 - Descomprimir Carpeta");
                System.out.println("5 - Visualitzar Estadistiques");
                System.out.println("6 - Comparar");
                System.out.println("7 - Usage");
                System.out.println("8 - Sortir");


                Scanner in = new Scanner(System.in);
                int opcio = in.nextInt();

                while (opcio < 1 || opcio > 8) {
                    System.out.println("La funció que has escollit és erronia, torna a escriure-la: ");
                    opcio = in.nextInt();
                }

                String path;
                String nomalgorisme = "LZSS";

                if ((opcio > 0 && opcio < 5) || opcio == 6) {
                    System.out.println("Introdueix el path: ");
                    path = in.next();

                    if (opcio != 4){
    	                String[] nomsAlgorisme = cDom.triaAlgorisme(path, opcio);

    	                System.out.println("Selecciona l'algorisme que vols utilitzar:");
    	                for (int i = 0; i < nomsAlgorisme.length; ++i) {
    	                    System.out.println(nomsAlgorisme[i]);
    	                }

    	                nomalgorisme = in.next();

    	                boolean correcte;
    	                if (nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG") || nomalgorisme.equals("Automatic"))
    	                    correcte = true;
    	                else correcte = false;

    	                while (!correcte) {
    	                    System.out.print("No és un algorisme correcte. Torna a escriure el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG o Automatic: ");
    	                    nomalgorisme = in.next();
    	                    if (nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG") || nomalgorisme.equals("Automatic"))
    	                        correcte = true;
    	                }
    	            }

                    if (opcio == 1) {

                        Object[] estgen = cDom.comprimir(path, nomalgorisme);

                        System.out.println("El grau és: " + (double) estgen[0]);
                        System.out.println("La velocitat és: " + (double) estgen[1]);
                        System.out.println("El temps és: " + (long) estgen[2]);
                    } else if (opcio == 2) {

                        Object[] estgen = cDom.descomprimir(path, nomalgorisme);

                        System.out.println("El grau és: " + (double) estgen[0]);
                        System.out.println("La velocitat és: " + (double) estgen[1]);
                        System.out.println("El temps és: " + (long) estgen[2]);

                    } else if (opcio == 3){
                    	cDom.comprimirCarpeta(path, nomalgorisme);

                   	} else if (opcio == 4){
                   		cDom.descomprimirCarpeta(path);
                    } else { //opcio 6
                        cDom.comprimir(path, nomalgorisme);

                        String nou_path = path + '.' + nomalgorisme;

                        cDom.descomprimir(nou_path, nomalgorisme);

                        File file = new File(path);
                        Desktop.getDesktop().open(file);

                        if(file.exists()) {
                            int index = path.lastIndexOf('.');
                            String prefix = path.substring(0,index) + "output";
                            nou_path = prefix + path.substring(index);
                            file = new File(nou_path);
                        }

                        Desktop.getDesktop().open(file);
                    }
                }

                if (opcio == 5) {
                    System.out.println("Selecciona l'algorisme que vols utilitzar:");
                    System.out.println("LZ78 ");
                    System.out.println("LZSS ");
                    System.out.println("LZW ");
                    System.out.println("JPEG ");

                    nomalgorisme = in.next();

                    boolean correcte;
                    if (nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
                        correcte = true;
                    else correcte = false;

                    while (!correcte) {
                        System.out.print("No és un algorisme correcte. Torna a escriure el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
                        nomalgorisme = in.next();
                        if (nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
                            correcte = true;
                    }

                    System.out.println("Selecciona quines estadistiques vols: ");
                    System.out.println("1 - Comprimit ");
                    System.out.println("2 - Descomprimit ");

                    boolean comprimit;
                    int i = in.nextInt();

                    while (i != 1 && i != 2) {
                        System.out.println("Has seleccionat malament l'opció, torna-la a escriure: ");
                        i = in.nextInt();
                    }

                    if (i == 1) comprimit = true;
                    else comprimit = false;

                    Object[] estgen = cDom.getEstadistiquesGenerals(nomalgorisme, comprimit);

                    System.out.println("El grau mitjà és: " + (double) estgen[0]);
                    System.out.println("La velocitat mitjana és: " + (double) estgen[1]);
                    System.out.println("El temps mitjà és: " + (double) estgen[2]);
                }
                else if(opcio == 7) {
                    System.out.println("Manual d'usuari: ");
                    System.out.println("");
                    System.out.println("Amb la opcio 1 - Comprimir, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat");
                    System.out.println("Amb la opcio 2 - Descomprimir, es descomprimeix un arxiu introduit per l'usuari, amb l'algorisme amb el que es va comprimir");
                    System.out.println("Amb la opcio 3 - Comprimir Carpeta, es comprimeix una carpeta introduida per l'usuari, amb l'algorisme especificat");
                    System.out.println("Amb la opcio 4 - Descomprimir Carpeta, es descomprimeix una carpeta introduida per l'usuari, amb l'algorisme amb el que es va comprimir");
                    System.out.println("Amb la opcio 5 - Visualitzar Estadistiques, es mostren per pantalla les estadistiques globals del programa");
                    System.out.println("Amb la opcio 6 - Comparar, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat, i seguidament es descomprimeix, a continuació s'obren ambdos per poder veure'n les diferències");
                    System.out.println("Amb la opcio 7 - Usage, es mostra aquest missatge per pantalla");
                    System.out.println("Amb la opcio 8 - Sortir, es tanca el programa");
                    System.out.println("");
                }
                else if (opcio == 8) {
                    cDom.saveAllEstadistiques();
                    System.exit(-1);
                }

                System.out.println();

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
