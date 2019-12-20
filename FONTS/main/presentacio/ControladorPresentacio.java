package main.presentacio;

import main.domini.ControladorDomini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ControladorPresentacio implements ActionListener {

    private ControladorDomini cDom;
    private JFrame frame;
    private JTabbedPane pestanyes;
    private JLabel label_selecciona_algorisme; //{C,D,COMPARAR,CC,DC}
    private JLabel[] label_estadistiques; //{C,D,COMPARAR,VisualitzarEstadistiques}
    private JTextField[] introdueix_path; //{C,D,COMPARAR,CC,DC}
    private JLabel[] introdueix_estgen; //{escullAlgorisme, escullTipus}
    private JButton boto_manual_usuari;
    private JButton[] boto_clear; //{C,D,CC,DC,COMPARAR,VisualitzarEstadistiques}
    private JButton[] boto_buscador_path; //{C,D,COMPARAR,CC,DC}
    private JButton[] ok_path; //{C,COMPARAR,CC,VisualitzarEstadistiques}
    private JButton[] comprimeix_o_descomprimeix; //{C,D,COMPARAR,CC,DC}
    private JComboBox[] menu_algorismes;  //{C,COMPARAR,CC,VisualitzarEstadistiques}
    private JComboBox menu_tipus; //{VisualitzarEstadistiques}


    public ControladorPresentacio() { //Inicialitza tots els atributs

        cDom = new ControladorDomini();

        frame =  new JFrame("Compresor");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        frame.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        cDom.saveAllEstadistiques();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                }
        );

        frame.setSize(1000, 400);

        pestanyes = new JTabbedPane();

        label_selecciona_algorisme = new JLabel("Selecciona l'algorisme: ");
        label_estadistiques = new JLabel[4];
        introdueix_path = new JTextField[5];
        introdueix_estgen = new JLabel[2];
        boto_manual_usuari = new JButton("Obrir Manual d'usuari");
        boto_clear = new JButton[6];
        boto_buscador_path = new JButton[5];
        ok_path = new JButton[4];
        comprimeix_o_descomprimeix = new JButton[5];
        menu_algorismes = new JComboBox[4];
        menu_tipus = new JComboBox();
    }

    public void frameVisible() { //Crida a les totes les pestanyes i fa visible el frame
        pestanyaUsage();
        pestanyaComprimir();
        pestanyaDescomprimir();
        pestanyaComprimirCarpeta();
        pestanyaDescomprimirCarpeta();
        pestanyaComparar();
        pestanyaVisualitzarEstadistiques();

        frame.getContentPane().add(pestanyes);
        frame.setVisible(true);
    }

    public void pestanyaUsage() { //crea la pestanya de usage
        JPanel panel_usage = new JPanel();

        panel_usage.setLayout(null);

        JLabel manual_usuari = new JLabel("<html>Manual d'usuari:<br/>" +
        "Pestanya 1 - Usage, es mostra aquest missatge per pantalla.<br/>" +
        "Pestanya 2 - Comprimir, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat.<br/>" +
        "Pestanya 3 - Descomprimir, es descomprimeix un arxiu introduit per l'usuari, amb l'algorisme amb el que es va comprimir.<br/>" +
        "Pestanya 4 - Comprimir Carpeta, es comprimeix una carpeta introduida per l'usuari, amb l'algorisme especificat.<br/>" +
        "Pestanya 5 - Descomprimir Carpeta, es descomprimeix la carpeta introduida per l'usuari, amb l'algorisme amb el que es va comprimir.<br/>" +
        "Pestanya 6 - Visualitzar Estadístiques, es mostren per pantalla les estadístiques globals del programa.<br/>" +
        "Pestanya 7 - Comparar, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat, i seguidament es descomprimeix, a continuació s'obren ambdos per poder veure'n les diferències.<br/>" +
        "Amb el botó vermell - Sortir, es tanca el programa.</html>");

        manual_usuari.setBounds(85,5,815, 300);
        boto_manual_usuari.setBounds(700,250,200,30);

        panel_usage.add(manual_usuari);
        panel_usage.add(boto_manual_usuari);

        pestanyes.addTab("Usage", panel_usage);

        boto_manual_usuari.addActionListener(this);
    }

    private String contrueix_text_estadistiques(Object[] estgen){
        return "<html>Estadístiques:<br/>" +
        "Grau: " + estgen[0] + " bytes<br/>" +
        "Velocitat: " + estgen[1] + " bytes/mil·lisegons<br/>" +
        "Temps: " + estgen[2] + " mil·lisegons</html>";
    }

    private void accio_boto_buscador(int i) {
        JFileChooser j = new JFileChooser(".");

        int r = j.showOpenDialog(null);

        // si ha triat
        if (r == JFileChooser.APPROVE_OPTION) {
            introdueix_path[i].setText(j.getSelectedFile().getAbsolutePath());
        }
    }

    private void accio_boto_buscador_carpeta(int i) {
        JFileChooser j = new JFileChooser(".");
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int r = j.showOpenDialog(null);

        // si ha triat
        if (r == JFileChooser.APPROVE_OPTION) {
            introdueix_path[i].setText(j.getSelectedFile().getAbsolutePath());
        }
    }


    private void pestanyaComprimir() { //crea la pestanya de comprimir
        JPanel panel_comprimir = new JPanel();

        panel_comprimir.setLayout(null);

        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        label_introdueix_path.setBounds(85,10,130, 30);
        introdueix_path[0] = new JTextField(30);
        introdueix_path[0].setBounds(220,10,550,30);
        boto_buscador_path[0] = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        boto_buscador_path[0].setBounds(780,10,50,30);
        ok_path[0] = new JButton("OK");
        ok_path[0].setBounds(840, 10, 60,30);
        JLabel label_selecciona_algorisme = new JLabel("Selecciona algorisme:");
        label_selecciona_algorisme.setBounds(85,100,200, 30);
        menu_algorismes[0] = new JComboBox();
        menu_algorismes[0].setBounds(295,100, 150, 30);
        comprimeix_o_descomprimeix[0] = new JButton("Comprimeix");
        comprimeix_o_descomprimeix[0].setBounds(730,100,170,30);
        label_estadistiques[0] = new JLabel("");
        label_estadistiques[0].setBounds(85, 150,300,100);
        boto_clear[0] = new JButton("Clear");
        boto_clear[0].setBounds(800,200,100,30);


        panel_comprimir.add(label_introdueix_path);
        panel_comprimir.add(introdueix_path[0]);
        panel_comprimir.add(boto_buscador_path[0]);
        panel_comprimir.add(ok_path[0]);
        panel_comprimir.add(label_selecciona_algorisme);
        panel_comprimir.add(menu_algorismes[0]);
        panel_comprimir.add(comprimeix_o_descomprimeix[0]);
        panel_comprimir.add(label_estadistiques[0]);
        panel_comprimir.add(boto_clear[0]);


        pestanyes.addTab("Comprimir", panel_comprimir);

        boto_buscador_path[0].addActionListener(this);
        ok_path[0].addActionListener(this);
        comprimeix_o_descomprimeix[0].addActionListener(this);
        boto_clear[0].addActionListener(this);
    }

    private void pestanyaDescomprimir() { //crea la pestanya de descomprimir
        JPanel panel_descomprimir = new JPanel();

        panel_descomprimir.setLayout(null);


        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        label_introdueix_path.setBounds(85,10,130, 30);
        introdueix_path[1] = new JTextField(20);
        introdueix_path[1].setBounds(220,10,620,30);
        boto_buscador_path[1] = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        boto_buscador_path[1].setBounds(850,10,50,30);
        comprimeix_o_descomprimeix[1] = new JButton("Descomprimeix");
        comprimeix_o_descomprimeix[1].setBounds(730,100,170,30);
        label_estadistiques[1] = new JLabel("");
        label_estadistiques[1].setBounds(85, 150,300,100);
        boto_clear[1] = new JButton("Clear");
        boto_clear[1].setBounds(800,200,100,30);

        panel_descomprimir.add(label_introdueix_path);
        panel_descomprimir.add(introdueix_path[1]);
        panel_descomprimir.add(boto_buscador_path[1]);
        panel_descomprimir.add(comprimeix_o_descomprimeix[1]);
        panel_descomprimir.add(boto_clear[1]);


        pestanyes.addTab("Descomprimir", panel_descomprimir);

        boto_buscador_path[1].addActionListener(this);
        comprimeix_o_descomprimeix[1].addActionListener(this);
        boto_clear[1].addActionListener(this);

        panel_descomprimir.add(label_estadistiques[1]);
    }

    private void pestanyaComprimirCarpeta() { //crea la pestanya de comprimir carpeta
        JPanel panel_comprimir_carpeta = new JPanel();

        panel_comprimir_carpeta.setLayout(null);

        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        label_introdueix_path.setBounds(85,10,130, 30);
        introdueix_path[3] = new JTextField(20);
        introdueix_path[3].setBounds(220,10,550,30);
        boto_buscador_path[3] = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        boto_buscador_path[3].setBounds(780,10,50,30);
        ok_path[2] = new JButton("OK");
        ok_path[2].setBounds(840, 10, 60,30);
        JLabel label_selecciona_algorisme = new JLabel("Selecciona algorisme:");
        label_selecciona_algorisme.setBounds(85,100,200, 30);
        menu_algorismes[2] = new JComboBox();
        menu_algorismes[2].setBounds(295,100, 150, 30);
        comprimeix_o_descomprimeix[3] = new JButton("Comprimeix carpeta");
        comprimeix_o_descomprimeix[3].setBounds(730,100,170,30);
        boto_clear[2] = new JButton("Clear");
        boto_clear[2].setBounds(800,200,100,30);


        panel_comprimir_carpeta.add(label_introdueix_path);
        panel_comprimir_carpeta.add(introdueix_path[3]);
        panel_comprimir_carpeta.add(boto_buscador_path[3]);
        panel_comprimir_carpeta.add(ok_path[2]);
        panel_comprimir_carpeta.add(label_selecciona_algorisme);
        panel_comprimir_carpeta.add(menu_algorismes[2]);
        panel_comprimir_carpeta.add(comprimeix_o_descomprimeix[3]);
        panel_comprimir_carpeta.add(boto_clear[2]);


        pestanyes.addTab("Comprimir Carpeta", panel_comprimir_carpeta);


        boto_buscador_path[3].addActionListener(this);
        ok_path[2].addActionListener(this);
        comprimeix_o_descomprimeix[3].addActionListener(this);
        boto_clear[2].addActionListener(this);
    }

    private void pestanyaDescomprimirCarpeta() {
        JPanel panel_descomprimir_carpeta = new JPanel();

        panel_descomprimir_carpeta.setLayout(null);


        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        label_introdueix_path.setBounds(85,10,130, 30);
        introdueix_path[4] = new JTextField(20);
        introdueix_path[4].setBounds(220,10,620,30);
        boto_buscador_path[4] = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        boto_buscador_path[4].setBounds(850,10,50,30);
        comprimeix_o_descomprimeix[4] = new JButton("Descomprimeix carpeta");
        comprimeix_o_descomprimeix[4].setBounds(700,100,200,30);
        boto_clear[3] = new JButton("Clear");
        boto_clear[3].setBounds(800,200,100,30);

        panel_descomprimir_carpeta.add(label_introdueix_path);
        panel_descomprimir_carpeta.add(introdueix_path[4]);
        panel_descomprimir_carpeta.add(boto_buscador_path[4]);
        panel_descomprimir_carpeta.add(comprimeix_o_descomprimeix[4]);
        panel_descomprimir_carpeta.add(boto_clear[3]);


        pestanyes.addTab("Descomprimir carpeta", panel_descomprimir_carpeta);


        boto_buscador_path[4].addActionListener(this);
        comprimeix_o_descomprimeix[4].addActionListener(this);
        boto_clear[3].addActionListener(this);
    }

    private void pestanyaComparar() {
        JPanel panel_comparar = new JPanel();

        panel_comparar.setLayout(null);


        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        label_introdueix_path.setBounds(85,10,130, 30);
        introdueix_path[2] = new JTextField(20);
        introdueix_path[2].setBounds(220,10,550,30);
        boto_buscador_path[2] = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        boto_buscador_path[2].setBounds(780,10,50,30);
        ok_path[1] = new JButton("OK");
        ok_path[1].setBounds(840, 10, 60,30);
        JLabel label_selecciona_algorisme = new JLabel("Selecciona algorisme:");
        label_selecciona_algorisme.setBounds(85,100,200, 30);
        menu_algorismes[1] = new JComboBox();
        menu_algorismes[1].setBounds(295,100, 150, 30);
        comprimeix_o_descomprimeix[2] = new JButton("Comparar");
        comprimeix_o_descomprimeix[2].setBounds(730,100,170,30);
        label_estadistiques[2] = new JLabel("");
        label_estadistiques[2].setBounds(85, 150,300,100);
        boto_clear[4] = new JButton("Clear");
        boto_clear[4].setBounds(800,200,100,30);


        panel_comparar.add(label_introdueix_path);
        panel_comparar.add(introdueix_path[2]);
        panel_comparar.add(boto_buscador_path[2]);
        panel_comparar.add(ok_path[1]);
        panel_comparar.add(label_selecciona_algorisme);
        panel_comparar.add(menu_algorismes[1]);
        panel_comparar.add(comprimeix_o_descomprimeix[2]);
        panel_comparar.add(boto_clear[4]);


        pestanyes.addTab("Comparar", panel_comparar);


        boto_buscador_path[2].addActionListener(this);
        ok_path[1].addActionListener(this);
        comprimeix_o_descomprimeix[2].addActionListener(this);

        panel_comparar.add(label_estadistiques[2]);
        boto_clear[4].addActionListener(this);
    }

    private void pestanyaVisualitzarEstadistiques() {
        JPanel panel_visualitzarEstadistiques = new JPanel();

        panel_visualitzarEstadistiques.setLayout(null);

        introdueix_estgen[0] = new JLabel("Escull algorisme: ");
        introdueix_estgen[0].setBounds(85,10,130, 30);
        menu_algorismes[3] = new JComboBox();
        menu_algorismes[3].setBounds(295,10, 150, 30);
        introdueix_estgen[1] = new JLabel("Escull tipus: ");
        introdueix_estgen[1].setBounds(85,100,200, 30);
        menu_tipus = new JComboBox();
        menu_tipus.setBounds(295,100, 150, 30);
        ok_path[3] = new JButton("Visualitzar Estadístiques");
        ok_path[3].setBounds(700, 100, 200,30);
        label_estadistiques[3] = new JLabel("");
        label_estadistiques[3].setBounds(85, 150,300,100);
        boto_clear[5] = new JButton("Clear");
        boto_clear[5].setBounds(800,200,100,30);


        menu_algorismes[3].addItem("LZ78");
        menu_algorismes[3].addItem("LZSS");
        menu_algorismes[3].addItem("LZW");
        menu_algorismes[3].addItem("JPEG");

        menu_tipus.addItem("Comprimit");
        menu_tipus.addItem("Descomprimit");

        panel_visualitzarEstadistiques.add(introdueix_estgen[0]);
        panel_visualitzarEstadistiques.add(menu_algorismes[3]);
        panel_visualitzarEstadistiques.add(introdueix_estgen[1]);
        panel_visualitzarEstadistiques.add(menu_tipus);
        panel_visualitzarEstadistiques.add(ok_path[3]);
        panel_visualitzarEstadistiques.add(boto_clear[5]);


        pestanyes.addTab("Visualitzar Estadístiques", panel_visualitzarEstadistiques);

        ok_path[3].addActionListener(this);
        boto_clear[5].addActionListener(this);

        panel_visualitzarEstadistiques.add(label_estadistiques[3]);
    }

    private void successPanel(){
            // Creem una nova finestra per mostrar l'error
            JFrame frame = new JFrame("Success");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 120);

            JPanel panel_success = new JPanel();

            JLabel success = new JLabel("Funcionalitat executada amb exit", new ImageIcon("success.png"), SwingConstants.CENTER);

            panel_success.add(success);

            frame.add(panel_success);
            frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        try {
            if(event.getSource() == boto_manual_usuari) {
                cDom.open("./Manual_usuari.pdf");
            }
            else if(event.getSource() == boto_buscador_path[0]){ //Click botó BUSCADOR_PATH pestanya comprimir
                accio_boto_buscador(0);
            }
            else if (event.getSource() == ok_path[0]) { //Click botó OK pestanya comprimir
                menu_algorismes[0].removeAllItems();
                String[] nomsAlgorismes = cDom.triaAlgorisme(introdueix_path[0].getText(), 1);

                for(int i = 0; i < nomsAlgorismes.length; ++i){
                    menu_algorismes[0].addItem(nomsAlgorismes[i]);
                }
            }
            else if(event.getSource() == comprimeix_o_descomprimeix[0]){ //Click botó COMPRIMEIX pestanya comprimir
                Object[] estgen = cDom.comprimir(introdueix_path[0].getText(), (String) menu_algorismes[0].getSelectedItem());

                label_estadistiques[0].setText(contrueix_text_estadistiques(estgen));
                successPanel();
            }
            else if(event.getSource() == boto_clear[0]){ //Click botó CLEAR pestanya comprimir
                introdueix_path[0].setText("");
                menu_algorismes[0].removeAllItems();
                label_estadistiques[0].setText("");
            }
            else if(event.getSource() == boto_buscador_path[1]){ //Click botó BUSCADOR_PATH pestanya descomprimir
                accio_boto_buscador(1);
            }
            else if (event.getSource() == comprimeix_o_descomprimeix[1]) { //Click botó DESCOMPRIMEIX pestanya descomprimir
                String[] nomAlgorisme = cDom.triaAlgorisme(introdueix_path[1].getText(), 2);
                Object[] estgen = cDom.descomprimir(introdueix_path[1].getText(), nomAlgorisme[0]);

                label_estadistiques[1].setText(contrueix_text_estadistiques(estgen));
            }
            else if(event.getSource() == boto_clear[1]){ //Click botó CLEAR pestanya descomprimir
                introdueix_path[1].setText("");
                label_estadistiques[1].setText("");
            }
            else if(event.getSource() == boto_buscador_path[3]){ //Click botó BUSCADOR_PATH pestanya comprimir carpeta
                accio_boto_buscador_carpeta(3);
            }
            else if(event.getSource() == ok_path[2]) { //Click botó OK pestanya comprimir carpeta
                menu_algorismes[2].removeAllItems();
                String[] nomsAlgorismes = cDom.triaAlgorisme(introdueix_path[3].getText(), 1);

                for(int i = 0; i < nomsAlgorismes.length; ++i){
                    menu_algorismes[2].addItem(nomsAlgorismes[i]);
                }
            }
            else if(event.getSource() == comprimeix_o_descomprimeix[3]) { //Click botó COMPRIMEIX CARPETA pestanya comprimir carpeta
                cDom.comprimirCarpeta(introdueix_path[3].getText(), (String) menu_algorismes[2].getSelectedItem());
            }
            else if(event.getSource() == boto_clear[2]){ //Click botó CLEAR pestanya comprimir carpeta
                introdueix_path[3].setText("");
                menu_algorismes[2].removeAllItems();
            }
            else if(event.getSource() == boto_buscador_path[4]){ //Click botó BUSCADOR_PATH pestanya descomprimir carpeta
                accio_boto_buscador(4);
            }
            else if(event.getSource() == comprimeix_o_descomprimeix[4]){ //Click botó DESCOMPRIMEIX CARPETA pestanya descomprimir carpeta
                cDom.descomprimirCarpeta(introdueix_path[4].getText());
            }
            else if(event.getSource() == boto_clear[3]){ //Click botó CLEAR pestanya descomprimir carpeta
                introdueix_path[4].setText("");
            }
            else if(event.getSource() == boto_buscador_path[2]){ //Click botó BUSCADOR_PATH pestanya comparar
                accio_boto_buscador(2);
            }
            else if(event.getSource() == ok_path[1]) { //Click botó OK pestanya comparar
                menu_algorismes[1].removeAllItems();
                String[] nomsAlgorismes = cDom.triaAlgorisme(introdueix_path[2].getText(), 1);

                for(int i = 0; i < nomsAlgorismes.length; ++i){
                    menu_algorismes[1].addItem(nomsAlgorismes[i]);
                }
            }
            else if(event.getSource() == comprimeix_o_descomprimeix[2]) { //Click botó COMPRIMEIX I COMPARAR pestanya comparar

                String path = introdueix_path[2].getText();

                int index = path.lastIndexOf("/");

                String path_nou;

                if(index == -1)
                    path_nou = "";
                else path_nou = path.substring(0,index);

                String[] arxius_directori = cDom.getNames(path_nou);


                Object[] estgen_comprimir = cDom.comprimir(introdueix_path[2].getText(), (String) menu_algorismes[1].getSelectedItem());


                String[] arxius_directori2 = cDom.getNames(path_nou);

                boolean trobat = false;
                String path_comprimit = "";
                for(int i = 0; i < arxius_directori.length && !trobat; ++i){
                    if(!arxius_directori[i].equals(arxius_directori2[i])) {
                        path_comprimit = arxius_directori2[i];
                        trobat = true;
                    }
                }

                path_comprimit = path_nou + "/" + path_comprimit;

                Object[] estgen_descomprimir = cDom.descomprimir(path_comprimit, (String) menu_algorismes[1].getSelectedItem());

                String[] arxius_directori3 = cDom.getNames(path_nou);

                trobat = false;
                path_comprimit = "";
                for(int i = 0; i < arxius_directori2.length && !trobat; ++i){
                    if(!arxius_directori2[i].equals(arxius_directori3[i])) {
                        path_comprimit = arxius_directori3[i];
                        trobat = true;
                    }
                }

                if(path_comprimit.equals("")) path_comprimit = arxius_directori3[arxius_directori3.length - 1];

                path_comprimit = path_nou + "/" + path_comprimit;

                System.out.println(path_comprimit);

                Object[] estgen = new Object[3];

                estgen[0] = ((double) estgen_comprimir[0] + (double) estgen_descomprimir[0]) / 2.0;
                estgen[1] = ((double) estgen_comprimir[1] + (double) estgen_descomprimir[1]) / 2.0;;
                estgen[2] = ((long) estgen_comprimir[2] + (long) estgen_descomprimir[2]) / 2;;

                label_estadistiques[2].setText(contrueix_text_estadistiques(estgen));

                cDom.open(introdueix_path[2].getText());
                cDom.open(path_comprimit);
            }
            else if(event.getSource() == boto_clear[4]){ //Click botó CLEAR pestanya comparar
                introdueix_path[2].setText("");
                menu_algorismes[1].removeAllItems();
                label_estadistiques[2].setText("");
            }
            else if(event.getSource() == ok_path[3]) {
                boolean comprimit;
                if(menu_tipus.getSelectedItem() == "Comprimit")
                    comprimit = true;
                else comprimit = false;

                Object[] estgen = cDom.getEstadistiquesGenerals((String) menu_algorismes[3].getSelectedItem(), comprimit);

                label_estadistiques[3].setText(contrueix_text_estadistiques(estgen));
            }
            else if(event.getSource() == boto_clear[5]){ //Click botó CLEAR pestanya Visualitzar estadistiques
                label_estadistiques[3].setText("");
            }
        }
        catch (Exception e){
            // Creem una nova finestra per mostrar l'error
            JFrame frame = new JFrame("Error");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 120);

            JPanel panel_error = new JPanel();

            JLabel error = new JLabel(e.getMessage(), new ImageIcon("error.png"), SwingConstants.CENTER);

            panel_error.add(error);

            frame.add(panel_error);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {

        ControladorPresentacio i = new ControladorPresentacio();
        i.frameVisible();
    }
}