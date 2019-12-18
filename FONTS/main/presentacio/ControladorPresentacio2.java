package main.presentacio;

import main.domini.ControladorDomini;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPresentacio2 implements ActionListener {

    ControladorDomini cDom;
    JFrame frame;
    JTabbedPane pestanyes;
    JLabel label_selecciona_algorisme; //{C,D,COMPARAR,CC,DC}
    JLabel[] label_estadistiques; //{C,D,COMPARAR}
    JTextField[] introdueix_path; //{C,D,COMPARAR,CC,DC}
    JButton[] ok_path;
    JButton[] comprimeix_o_descomprimeix; //{C,D,COMPARAR,CC,DC}
    JComboBox[] menu_algorismes;  //{C,D,COMPARAR,CC}


    public ControladorPresentacio2() { //Inicialitza tots els atributs

        cDom = new ControladorDomini();

        frame =  new JFrame("Compresor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);

        pestanyes = new JTabbedPane();

        label_selecciona_algorisme = new JLabel("Selecciona l'algorisme: ");
        label_estadistiques = new JLabel[3];
        introdueix_path = new JTextField[5];
        ok_path = new JButton[4];
        comprimeix_o_descomprimeix = new JButton[5];
        menu_algorismes = new JComboBox[3];
    }

    public void frameVisible() { //Crida a les totes les pestanyes i fa visible el frame
        pestanyaUsage();
        comprimir();
        descomprimir();

        frame.getContentPane().add(pestanyes);
        frame.setVisible(true);
    }

    public void pestanyaUsage() { //crea la pestanya de usage
        JPanel panel_usage = new JPanel();
        JLabel manual_usuari = new JLabel("<html>Manual d'usuari:<br/>" +
        "Amb la pestanya 2 - Comprimir, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat<br/>" +
        "Amb la pestanya 3 - Descomprimir, es descomprimeix un arxiu introduit per l'usuari, amb l'algorisme amb el que es va comprimir<br/>" +
        "Amb la pestanya 4 - Comprimir Carpeta, es comprimeix una carpeta introduida per l'usuari, amb l'algorisme especificat<br/>" +
        "Amb la pestanya 5 - Descomprimir Carpeta, es descomprimeix una carpeta introduida per l'usuari, amb l'algorisme amb el que es va comprimir<br/>" +
        "Amb la pestanya 6 - Visualitzar Estadistiques, es mostren per pantalla les estadistiques globals del programa<br/>" +
        "Amb la pestanya 7 - Comparar, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat, i seguidament es descomprimeix, a continuació s'obren ambdos per poder veure'n les diferències<br/>" +
        "Amb la pestanya 1 - Usage, es mostra aquest missatge per pantalla<br/>" +
        "Amb el botó vermell - Sortir, es tanca el programa</html>");

        panel_usage.add(manual_usuari);

        pestanyes.addTab("Usage", panel_usage);
    }

    private String contrueix_text_estadistiques(Object[] estgen){
        return "<html>Estadistiques:<br/>" +
        "Grau: " + estgen[0] + " bytes<br/>" +
        "Velocitat: " + estgen[1] + " bytes/mil·lisegons<br/>" +
        "Temps: " + estgen[2] + " mil·lisegons</html>";
    }

    public void comprimir() { //crea la pestanya de comprimir
        JPanel panel_comprimir = new JPanel();

        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        introdueix_path[0] = new JTextField(20);
        ok_path[0] = new JButton("OK");
        menu_algorismes[0] = new JComboBox();
        comprimeix_o_descomprimeix[0] = new JButton("Comprimeix");
        label_estadistiques[0] = new JLabel("");


        panel_comprimir.add(label_introdueix_path);
        panel_comprimir.add(introdueix_path[0]);
        panel_comprimir.add(ok_path[0]);
        panel_comprimir.add(menu_algorismes[0]);
        panel_comprimir.add(comprimeix_o_descomprimeix[0]);


        pestanyes.addTab("Comprimir", panel_comprimir);

        ok_path[0].addActionListener(this);
        comprimeix_o_descomprimeix[0].addActionListener(this);

        panel_comprimir.add(label_estadistiques[0]);
    }

    public void descomprimir() { //crea la pestanya de descomprimir
        JPanel panel_descomprimir = new JPanel();

        JLabel label_introdueix_path = new JLabel("Introdueix el path:");
        introdueix_path[1] = new JTextField(20);
        comprimeix_o_descomprimeix[1] = new JButton("Descomprimeix");
        label_estadistiques[1] = new JLabel("");


        panel_descomprimir.add(label_introdueix_path);
        panel_descomprimir.add(introdueix_path[1]);
        panel_descomprimir.add(comprimeix_o_descomprimeix[1]);


        pestanyes.addTab("Descomprimir", panel_descomprimir);

        comprimeix_o_descomprimeix[1].addActionListener(this);

        panel_descomprimir.add(label_estadistiques[1]);
    }

    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource() == ok_path[0]) { //Click botó OK pestanya comprimir
                String[] nomsAlgorismes = cDom.triaAlgorisme(introdueix_path[0].getText(), 1);

                for(int i = 0; i < nomsAlgorismes.length; ++i){
                    menu_algorismes[0].addItem(nomsAlgorismes[i]);
                }
            }
            else if(event.getSource() == comprimeix_o_descomprimeix[0]){ //Click botó COMPRIMEIX pestanya comprimir
                Object[] estgen = cDom.comprimir(introdueix_path[0].getText(), (String) menu_algorismes[0].getSelectedItem());

                label_estadistiques[0].setText(contrueix_text_estadistiques(estgen));
            }

            else if (event.getSource() == comprimeix_o_descomprimeix[1]) { //Click botó DESCOMPRIMEIX pestanya comprimir
                Object[] estgen = cDom.descomprimir(introdueix_path[1].getText(), (String) menu_algorismes[1].getSelectedItem());

                label_estadistiques[1].setText(contrueix_text_estadistiques(estgen));
            }
        }
        catch (Exception e){
            // Creem una nova finestra per mostrar l'error
            JFrame frame = new JFrame("Error");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 150);

            JPanel panel_error = new JPanel();

            //JLabel error = new JLabel(e.getMessage(), new ImageIcon("main/presentacio/error.png"), SwingConstants.CENTER);
            JLabel error = new JLabel(e.getMessage());

            panel_error.add(error);

            frame.add(panel_error);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {

        ControladorPresentacio2 i = new ControladorPresentacio2();
        i.frameVisible();
    }
}