import javax.swing.*;
import layout.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class gui{

    public static void main(String args[]){

       	JFrame frame = new JFrame("Compressor");

       	FlowLayout layout = new FlowLayout();
       	frame.setLayout(layout);

       	// Sortir
       	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	frame.setSize(300,600);

       	// Comprimir
       	JButton comprimirButton = new JButton("Comprimir");
       	frame.getContentPane().add(comprimirButton);
  
  		// Descomprimir
        JButton descomprimirButton = new JButton("Descomprimir");
       	frame.getContentPane().add(descomprimirButton);

       	// Comprimir carpeta
        JButton comprimirCarpetaButton = new JButton("Comprimir carpeta");
       	frame.getContentPane().add(comprimirCarpetaButton);

       	// Descomprimir carpeta
        JButton descomprimirCarpetaButton = new JButton("Descomprimir carpeta");
       frame.getContentPane().add(descomprimirCarpetaButton);


        JButton visualitzarEstadistiquesButton = new JButton("Visualitzar Estadistiques");
       	frame.getContentPane().add(visualitzarEstadistiquesButton);

       	JButton compararButton = new JButton("Comparar");
       	frame.getContentPane().add(compararButton);

        JButton usageButton = new JButton("Usage");
       	frame.getContentPane().add(usageButton);

       	frame.setVisible(true);
    }
}