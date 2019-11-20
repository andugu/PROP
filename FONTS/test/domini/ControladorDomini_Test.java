package test.domini;

import main.domini.*;
import java.io.IOException;
public class ControladorDomini_Test {

    private static ControladorDomini Controlador;

    public ControladorDomini_Test() {
        Controlador = new ControladorDomini();
    }

    public static void main(String[] args) throws Exception {
        String CarpetaOrigen = "";//posar path en cas de no estar ubicat a la carpeta amb el test
        String algorismes[] = new String[4];
        algorismes[0] = "LZ78";
        algorismes[1] = "LZSS";
        algorismes[2] = "LZW";
        algorismes[3] = "JPEG";
        //comprimir
        String pathbuit = CarpetaOrigen + "Fitxer_buit.txt";
        String pathrepetit = CarpetaOrigen + "Fitxer_gran.txt";
        String path_no_existent = CarpetaOrigen + "Fitxer_repeticions.txt";
        Controlador.comprimir(pathbuit, algorismes[0]);
        Controlador.comprimir(pathbuit, algorismes[1]);
        Controlador.comprimir(pathbuit, algorismes[2]);
        Controlador.comprimir(pathbuit, algorismes[3]);
        Controlador.comprimir(pathrepetit, algorismes[0]);
        Controlador.comprimir(pathrepetit, algorismes[1]);
        Controlador.comprimir(pathrepetit, algorismes[2]);
        Controlador.comprimir(pathrepetit, algorismes[3]);
        Controlador.comprimir(path_no_existent, algorismes[0]);
        Controlador.comprimir(path_no_existent, algorismes[1]);
        Controlador.comprimir(path_no_existent, algorismes[2]);
        Controlador.comprimir(path_no_existent, algorismes[3]);

        System.out.println("L'operació Comprimir feta correctament ");

        //descomprimir
        String pathbuitd = CarpetaOrigen + "Fitxer_buit.txt.LZ78";
        String pathrepetitd = CarpetaOrigen + "Fitxer_gran.txt.LZ78";
        String path_no_existentd = CarpetaOrigen + "Fitxer_repeticions.LZ78";

        Controlador.comprimir(pathbuitd, algorismes[0]);
        Controlador.comprimir(pathbuitd, algorismes[1]);
        Controlador.comprimir(pathbuitd, algorismes[2]);
        Controlador.comprimir(pathbuitd, algorismes[3]);
        Controlador.comprimir(pathrepetitd, algorismes[0]);
        Controlador.comprimir(pathrepetitd, algorismes[1]);
        Controlador.comprimir(pathrepetitd, algorismes[2]);
        Controlador.comprimir(pathrepetitd, algorismes[3]);
        Controlador.comprimir(path_no_existentd, algorismes[0]);
        Controlador.comprimir(path_no_existentd, algorismes[1]);
        Controlador.comprimir(path_no_existentd, algorismes[2]);
        Controlador.comprimir(path_no_existentd, algorismes[3]);
        System.out.println("L'operació Descomprimir feta correctament ");

        //llegirFitxer
        String path2b = CarpetaOrigen + "Fitxer_buit.txt;";
        String path2r = CarpetaOrigen + "Fitxer_gran.txt";
        String path2ne = CarpetaOrigen + "Fitxer_repetit.txt";

        Controlador.llegirFitxer(path2b);//buit
        Controlador.llegirFitxer(path2r);//2 amb mateix nom
        Controlador.llegirFitxer(path2ne);//No existent

        System.out.println("L'operació LlegirFitxer feta correctament ");
        //saveFile
        String path = "CarpetaOrigen";
        byte[] contingut1 = new byte[0];
        Controlador.saveFile(path,algorismes[0],contingut1,true);
        Controlador.saveFile(path,algorismes[1],contingut1,true);
        Controlador.saveFile(path,algorismes[2],contingut1,true);
        Controlador.saveFile(path,algorismes[3],contingut1,true);
        Controlador.saveFile(path,algorismes[0],contingut1,false);
        Controlador.saveFile(path,algorismes[1],contingut1,false);
        Controlador.saveFile(path,algorismes[2],contingut1,false);
        Controlador.saveFile(path,algorismes[3],contingut1,false);

        byte[] contingut2 = new byte[9];
        for (int i = 0; i < 9; ++i){
            if (i%2==0) contingut2[i] = 97;
            else contingut2[i] = 98;
        }
        Controlador.saveFile(path,algorismes[0],contingut2,true);
        Controlador.saveFile(path,algorismes[1],contingut2,true);
        Controlador.saveFile(path,algorismes[2],contingut2,true);
        Controlador.saveFile(path,algorismes[3],contingut2,true);
        Controlador.saveFile(path,algorismes[0],contingut2,false);
        Controlador.saveFile(path,algorismes[1],contingut2,false);
        Controlador.saveFile(path,algorismes[2],contingut2,false);
        Controlador.saveFile(path,algorismes[3],contingut2,false);

        System.out.println("L'operació Save File feta correctament ");

        //getEstadistiquesGenerals
        Controlador.getEstadistiquesGenerals(algorismes[0], true );
        Controlador.getEstadistiquesGenerals(algorismes[1], true );
        Controlador.getEstadistiquesGenerals(algorismes[2], true );
        Controlador.getEstadistiquesGenerals(algorismes[3], true );
        Controlador.getEstadistiquesGenerals(algorismes[0], false );
        Controlador.getEstadistiquesGenerals(algorismes[1], false );
        Controlador.getEstadistiquesGenerals(algorismes[2], false );
        Controlador.getEstadistiquesGenerals(algorismes[3], false );

        System.out.println("L'operació getEstadistiquesGenerals feta correctament ");
        //tria algorisme
        String path1 = CarpetaOrigen + "Fitxer_buit.txt";
        Controlador.triaAlgorisme(path1, 1);
        Controlador.triaAlgorisme(path1, 2);
        Controlador.triaAlgorisme(path1, 3);
        Controlador.triaAlgorisme(path1, 4);
        Controlador.triaAlgorisme(path1, 5);
        Controlador.triaAlgorisme(path1, 6);
        System.out.println("L'operació triaAlgorisme feta correctament ");

    }
}
