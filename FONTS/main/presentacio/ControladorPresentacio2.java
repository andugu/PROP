package main.presentacio;

import javax.swing.*;

public class ControladorPresentacio2 {

    JFrame frame;
    JTabbedPane pestanyes;
    JTextField[] introdueix_path; //{C,D,COMPARAR,CC,DC}
    JButton[] ok_path;
    JComboBox[] menu_algorismes;  //{C,D,COMPARAR,CC}


    public ControladorPresentacio2() {

        frame =  new JFrame("Compresor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);

        pestanyes = new JTabbedPane();

        introdueix_path = new JTextField[5];
        ok_path = new JButton[4];
        menu_algorismes = new JComboBox[3];
    }

    public void pestanyaUsage() {

        JPanel panel_usage = new JPanel();
        JLabel manual_usuari = new JLabel("Manual d'usuari: \n dfghgf");

        panel_usage.add(manual_usuari);

        pestanyes.addTab("Usage", panel_usage);

        frame.getContentPane().add(pestanyes);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        ControladorPresentacio2 i = new ControladorPresentacio2();
        i.pestanyaUsage();
    }
}
