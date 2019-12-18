package main.presentacio;

import main.domini.ControladorDomini;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class ControladorPresentacio implements ActionListener{

    JFrame frame;  
    //textfields per introduir path
    JTextField[] introdueix_path = new JTextField[5];//{C,D,COMPARAR,CC,DC}
    JButton[] ok_path = new JButton[5];
    JComboBox[] menu_algorismes = new JComboBox[4];//{C,D,COMPARAR,CC}
    
    OKPATH ok_button_accio;

    public static void main(String[] args) {

        ControladorPresentacio i = new ControladorPresentacio();
        i.ControladorPresentacio_pestaña();
    }

    public void ControladorPresentacio_pestaña() {
        frame = new JFrame("Compresor");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);
       
        
        //grid organitzar elements
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;//que ocupin tot el que puguin


        //Creamos el conjunto de pestañas
        JTabbedPane pestañas=new JTabbedPane();
        //Creamos el panel y lo añadimos a las pestañas
        JPanel panel_usage=new JPanel();

        //Componentes del panel1
        JLabel et_p1=new JLabel("Manual d'usuari: ");
        JLabel et_p2=new JLabel("");
        JLabel et_p3=new JLabel("Amb la pestanya 2 - Comprimir, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme especificat");
        JLabel et_p4=new JLabel("Amb la pestanya 3 - Descomprimir, es descomprimeix un arxiu introduit per l'usuari, amb l'algorisme amb el que es va comprimir");
        JLabel et_p5=new JLabel("Amb la pestanya 4 - Comprimir Carpeta, es comprimeix una carpeta introduida per l'usuari,\n amb l'algorisme especificat");
        JLabel et_p6=new JLabel("Amb la pestanya 5 - Descomprimir Carpeta, es descomprimeix una carpeta introduida per \nl'usuari, amb l'algorisme amb el que es va comprimir");
        JLabel et_p7=new JLabel("Amb la pestanya 6 - Visualitzar Estadistiques, es mostren per pantalla les estadistiques\n globals del programa");
        JLabel et_p8=new JLabel("Amb la pestanya 7 - Comparar, es comprimeix un arxiu introduit per l'usuari, amb l'algorisme\n especificat, i seguidament es descomprimeix, a continuació s'obren ambdos per poder veure'n les diferències");
        JLabel et_p9=new JLabel("Amb la pestanya 1 - Usage, es mostra aquest missatge per pantalla");
        JLabel et_p10=new JLabel("Amb el botó vermell - Sortir, es tanca el programa");
        JLabel et_p11=new JLabel("");

        panel_usage.add(et_p1); panel_usage.add(et_p2); panel_usage.add(et_p3); panel_usage.add(et_p4); panel_usage.add(et_p5);
        panel_usage.add(et_p6); panel_usage.add(et_p7); panel_usage.add(et_p8); panel_usage.add(et_p9); panel_usage.add(et_p10);
        panel_usage.add(et_p11);
        //Añadimos un nombre de la pestaña y el panel
        pestañas.addTab("Usage", panel_usage);

        //JTextField tf_usage = new JTextField();
       

        //comprimir
        JPanel panel_comprimir = new JPanel(new GridBagLayout());


        JLabel et_comp = new JLabel("Introdueix el path");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        panel_comprimir.add(et_comp,c);
        //pestañas.addTab("Comprimir", panel_comprimir);


        introdueix_path[0] = new JTextField(20);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        //c.gridheight = ;
        panel_comprimir.add(introdueix_path[0],c);

        ok_path[0] = new JButton("OK");
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        panel_comprimir.add(ok_path[0], c);
        ok_button_accio = new OKPATH("Comprimir");
        ok_path[0].addActionListener(ok_button_accio);



        JLabel et_comp_2 = new JLabel("Selecciona l'algorisme");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel_comprimir.add(et_comp_2,c);
        //aqui s'haura de cridar a:
            /*
            String[] nomsAlgorisme = cDom.triaAlgorisme(path, opcio);
            aixo serà les opcions que ho haurem de posar com a combos
             */
        menu_algorismes[0] = new JComboBox();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        menu_algorismes[0] = new JComboBox();
        panel_comprimir.add(menu_algorismes[0],c);


         JButton boton_comprimir=new JButton("COMPRIMEIX");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
         panel_comprimir.add(boton_comprimir, c);

         

        JLabel et_comp_est = new JLabel("Estadistiques:");
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        panel_comprimir.add(et_comp_est,c);

       // String content_estadistiques = new String("Grau:  unitats\nVelocitat: unitats\nTemps: unitats");
        String content_estadistiques = new String("<html>Grau unitats <br/> elocitat: unitats <br/> Temps: unitats</html>");
        JLabel et_comp_est_dades = new JLabel(content_estadistiques);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        panel_comprimir.add(et_comp_est_dades,c);
        pestañas.addTab("Comprimir", panel_comprimir);



        /*JMenu menu_algorismes = new JMenu("Tria l'algorisme");
        JMenuItem lz78 = new JMenuItem("LZ78");
        JMenuItem lzss = new JMenuItem("LZSS");
        JMenuItem lzw = new JMenuItem("LZW");
        JMenuItem jpeg = new JMenuItem("JPEG");
        menu_algorismes.add(lz78);
        menu_algorismes.add(lzss);
        menu_algorismes.add(lzw);
        menu_algorismes.add(jpeg);
        menu_bar_compr.add(menu_algorismes);


        panel_comprimir.add(menu_bar_compr,c);*/
        


        //descomprimir
        JPanel panel_descomprimir = new JPanel(new GridBagLayout());
         JLabel et_compdes = new JLabel("Introdueix el path");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        panel_descomprimir.add(et_compdes,c);
        //pestañas.addTab("Comprimir", panel_comprimir);
        
        introdueix_path[1] = new JTextField(20);
       // JTextField tf_descompr = new JTextField(20);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        //c.gridheight = ;
        panel_descomprimir.add(introdueix_path[1],c);

        ok_path[1] = new JButton("OK");
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        panel_descomprimir.add(ok_path[1], c);
        ok_button_accio = new OKPATH("Descomprimir");
        ok_path[1].addActionListener(ok_button_accio);


        JLabel et_descomp_2 = new JLabel("Selecciona l'algorisme");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel_descomprimir.add(et_descomp_2,c);
        //aqui s'haura de cridar a:
            /*
            String[] nomsAlgorisme = cDom.triaAlgorisme(path, opcio);
            aixo serà les opcions que ho haurem de posar com a combos
             */
            menu_algorismes[1] = new JComboBox();
            c.gridx = 2;
            c.gridy = 3;
            c.gridwidth = 1;
            menu_algorismes[1] = new JComboBox();
            panel_comprimir.add(menu_algorismes[1],c);

        


         JButton boton_descomprimir=new JButton("COMPRIMEIX");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
         panel_descomprimir.add(boton_descomprimir, c);
         


        JLabel et_descomp_est = new JLabel("Estadistiques:");
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        panel_descomprimir.add(et_descomp_est,c);

       // String content_estadistiques = new String("Grau:  unitats\nVelocitat: unitats\nTemps: unitats");
        String content_estadistiques_desc = new String("<html>Grau unitats <br/> elocitat: unitats <br/> Temps: unitats</html>");
        JLabel et_descomp_est_dades = new JLabel(content_estadistiques);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        panel_descomprimir.add(et_descomp_est_dades,c);

        pestañas.addTab("Descomprimir", panel_descomprimir);




        //comparar
        JPanel panel_comparar = new JPanel();
        JLabel et_comparar = new JLabel("Estas a la pestanya de comprimir");
        panel_comparar.add(et_comparar);
        pestañas.addTab("Comparar", panel_comparar);
        //JTextField tf_comparar = new JTextField(20);
        introdueix_path[2] = new JTextField(20);
        panel_comparar.add(introdueix_path[2]);

        ok_path[2] = new JButton("OK");
        
        panel_comparar.add(ok_path[2]);
        ok_button_accio = new OKPATH("Comparar");
        ok_path[2].addActionListener(ok_button_accio);
        menu_algorismes[2] = new JComboBox();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        menu_algorismes[2] = new JComboBox();
        panel_comprimir.add(menu_algorismes[2],c);

        //estadistiques
        JPanel panel_estadistiques = new JPanel();
        JLabel et_est = new JLabel("Estas a la pestanya estadistiques");
        panel_estadistiques.add(et_est);
        pestañas.addTab("Estadistiques", panel_estadistiques);
       // JTextField tf_estad = new JTextField(20);
      //introdueix_path[3] = new JTextField(20);
        //panel_estadistiques.add(introdueix_path[3]);

        //comprimir carpeta
        JPanel panel_compr_carpeta = new JPanel();
        JLabel et_carpeta_compr = new JLabel("Estas a la pestanya comprimir carpeta");
        panel_compr_carpeta.add(et_carpeta_compr);
        pestañas.addTab("Comprimir carpeta", panel_compr_carpeta);
       // JTextField tf_carpeta_compr = new JTextField(20);
       introdueix_path[3] = new JTextField(20);
        panel_compr_carpeta.add(introdueix_path[3]);
        ok_path[3] = new JButton("OK");
        
        panel_compr_carpeta.add(ok_path[3]);
        ok_button_accio = new OKPATH("Comprimir_carpeta");
        ok_path[3].addActionListener(ok_button_accio);
        menu_algorismes[3] = new JComboBox();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        menu_algorismes[3] = new JComboBox();
        panel_comprimir.add(menu_algorismes[3],c);

        //descomprimir carpeta
        JPanel panel_descompr_carpeta = new JPanel();
        JLabel et_carpeta_descompr = new JLabel("Estas a la pestanya descomprimir carpeta");
        panel_descompr_carpeta.add(et_carpeta_descompr);
        pestañas.addTab("Descomprimir carpeta", panel_descompr_carpeta);
       // JTextField tf_carpeta_descompr = new JTextField(20);
       introdueix_path[4] = new JTextField(20);
        panel_descompr_carpeta.add(introdueix_path[4]);

        ok_path[4] = new JButton("OK");
        
        panel_descompr_carpeta.add(ok_path[4]);
        ok_button_accio = new OKPATH("Descomprimir_carpeta");
        ok_path[4].addActionListener(ok_button_accio);


        frame.getContentPane().add(pestañas);
        frame.setVisible(true); 
    }

    public void actionPerformed(ActionEvent e) {    
    System.out.println("Has apretat el boto OK");
    System.out.println(introdueix_path[0].getText());
    


  
}
private class OKPATH implements ActionListener
    {
        private ControladorDomini cDom = new ControladorDomini();

        public OKPATH(String funcionalitat)
        {
            this.funcionalitat = funcionalitat;
        }

        public void actionPerformed(ActionEvent event) {

            try {
                switch (funcionalitat){
                case("Comprimir"):
                    System.out.println("Crida a tria algorisme(path) = "+ introdueix_path[0].getText());
                    /*
                    menu_algorismes[0].addItem("LZW");
                    menu_algorismes[0].addItem("LZS");
                     */

                    String[] nomsAlgorisme = new String[0];
                    nomsAlgorisme = cDom.triaAlgorisme(introdueix_path[0].getText(), 1);

                    for(int i = 0; i < nomsAlgorisme.length; ++i){
                        menu_algorismes[0].addItem(nomsAlgorisme[i]);
                    }
                break;
                case("Descomprimir"):
                System.out.println("Crida a tria algorisme(path) = "+ introdueix_path[1].getText());
                menu_algorismes[1].addItem("LZW");
                    menu_algorismes[1].addItem("LZS");
                    menu_algorismes[1].addItem("LZ78");
                    menu_algorismes[1].addItem("JPEG");
                break;
                case("Comparar"):
                System.out.println("Crida a tria algorisme(path) = "+ introdueix_path[2].getText());
                menu_algorismes[2].addItem("LZ78");
                    menu_algorismes[2].addItem("JPEG");
                break;
                case("Comprimir_carpeta"):
                System.out.println("Crida a tria algorisme(path) = "+ introdueix_path[3].getText());
                menu_algorismes[0].addItem("JPEG");
                    menu_algorismes[0].addItem("LZS");
                break;
                case("Descomprimir_carpeta"):
                System.out.println("Crida a tria algorisme(path) = "+ introdueix_path[4].getText());
                break;
                }
            } catch (Exception e){

                // Creem una nova finestra per mostrar l'error
                JFrame frame = new JFrame("Error");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 150);

                JLabel error = new JLabel(e.getMessage(), new ImageIcon("main/presentacio/error.png"), SwingConstants.CENTER);

                frame.add(error);
                frame.setVisible(true); 
            }
        }

        private String funcionalitat;
    }

}

