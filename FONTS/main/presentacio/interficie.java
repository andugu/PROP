import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class interficie implements ActionListener{
    JFrame frame;  
    JTextField tf_compr;

    
    public static void main(String[] args) {
        interficie i = new interficie();
        i.interficie_pestaña();
    }

    public void interficie_pestaña() {
    frame = new JFrame("Compresor");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 256);
   
    
    //grid organitzar elements
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1;
    c.weighty = 1;
   c.fill = GridBagConstraints.HORIZONTAL;//que ocupin tot el que puguin






    
    //Creamos el conjunto de pestañas
    JTabbedPane pestañas=new JTabbedPane();
    //Creamos el panel y lo añadimos a las pestañas
    JPanel panel_usage=new JPanel(new GridBagLayout());

    //Componentes del panel1
    JLabel et_p1=new JLabel("Estas a la pestanya de USAGE");
    panel_usage.add(et_p1);
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



     tf_compr = new JTextField(20);
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 3;
    //c.gridheight = ;
    panel_comprimir.add(tf_compr,c);

    JButton boton_ok_compr=new JButton("OK");
    c.gridx = 3;
    c.gridy = 1;
    c.gridwidth = 1;
     panel_comprimir.add(boton_ok_compr, c);


    boton_ok_compr.addActionListener(this);



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
   JComboBox menu_combo_compr = new JComboBox();
    menu_combo_compr.addItem("LZ78");
    menu_combo_compr.addItem("LZSS");
    menu_combo_compr.addItem("LZW");
    menu_combo_compr.addItem("JPEG");
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 1;
    panel_comprimir.add(menu_combo_compr,c);


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

    JTextField tf_descompr = new JTextField(20);
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 3;
    //c.gridheight = ;
    panel_descomprimir.add(tf_descompr,c);

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
   JComboBox menu_combo_descompr = new JComboBox();
    menu_combo_descompr.addItem("LZ78");
    menu_combo_descompr.addItem("LZSS");
    menu_combo_descompr.addItem("LZW");
    menu_combo_descompr.addItem("JPEG");
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 1;
    panel_descomprimir.add(menu_combo_descompr,c);


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
    JTextField tf_comparar = new JTextField(20);
    panel_comparar.add(tf_comparar);

    //estadistiques
    JPanel panel_estadistiques = new JPanel();
    JLabel et_est = new JLabel("Estas a la pestanya estadistiques");
    panel_estadistiques.add(et_est);
    pestañas.addTab("Estadistiques", panel_estadistiques);
    JTextField tf_estad = new JTextField(20);
    panel_estadistiques.add(tf_estad);

    //comprimir carpeta
    JPanel panel_compr_carpeta = new JPanel();
    JLabel et_carpeta_compr = new JLabel("Estas a la pestanya comprimir carpeta");
    panel_compr_carpeta.add(et_carpeta_compr);
    pestañas.addTab("Comprimir carpeta", panel_compr_carpeta);
    JTextField tf_carpeta_compr = new JTextField(20);
    panel_compr_carpeta.add(tf_carpeta_compr);

    //descomprimir carpeta
    JPanel panel_descompr_carpeta = new JPanel();
    JLabel et_carpeta_descompr = new JLabel("Estas a la pestanya descomprimir carpeta");
    panel_descompr_carpeta.add(et_carpeta_descompr);
    pestañas.addTab("Descomprimir carpeta", panel_descompr_carpeta);
    JTextField tf_carpeta_descompr = new JTextField(20);
    panel_descompr_carpeta.add(tf_carpeta_descompr);


    
    frame.getContentPane().add(pestañas);

    frame.setVisible(true); 
    }
    public void interficie_excepcio() {
        //aqui va la interficie d'excepcio, s'haurà de cridar des de l'altre lloc
    }


    public void actionPerformed(ActionEvent e) {    
    System.out.println("Has apretat el boto OK");
    System.out.println(tf_compr.getText());
    


  
}    

}

