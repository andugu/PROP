// Por lo general, necesitarás paquetes Swing y Awt
// incluso si estás trabajando con solo swings.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
    
public class interficie implements ActionListener{    
JFrame frame;    
JMenuBar mb;    
JMenu m1;   
JMenuItem mcomprimir, mdescomprimir, mvisualitzar, mcomparar, mcomprimirCarpeta, mdescomprimirCarpeta, musage;  
JTextArea ta;    
MenuExample(){    
// Creando el Marco        
 frame = new JFrame("Compresor");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(400, 400);
// Creando MenuBar y agregando componentes   
 mb = new JMenuBar();
 m1 = new JMenu("Selecciona una opcio");

mb.add(m1);
 mcomprimir = new JMenuItem("Comprimir");
 mdescomprimir = new JMenuItem("Descomprimir");
 mvisualitzar = new JMenuItem("Visualitzar estadistiques");
 mcomparar = new JMenuItem("Comparar");
 mcomprimirCarpeta = new JMenuItem("Comprimir Carpeta");
 mdescomprimirCarpeta= new JMenuItem("Descomprimir Carpeta");
 musage = new JMenuItem("Usage");

m1.add(mcomprimir);
m1.add(mdescomprimir);
m1.add(mvisualitzar);
m1.add(mcomparar);
m1.add(mcomprimirCarpeta);
m1.add(mdescomprimirCarpeta);

m1.add(musage);

//events



// Agregar componentes al marco.      

frame.getContentPane().add(BorderLayout.NORTH, mb);

frame.setVisible(true);   
}     
public void actionPerformed(ActionEvent e) {    
if(e.getSource()==cut)    
ta.cut();    
if(e.getSource()==paste)    
ta.paste();    
if(e.getSource()==copy)    
ta.copy();    
if(e.getSource()==selectAll)    
ta.selectAll();    
}     
public static void main(String[] args) {    
    new interficie();    
}    
}    