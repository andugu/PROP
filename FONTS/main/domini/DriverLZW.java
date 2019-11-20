package main.domini;
import java.io.*;
import java.util.Scanner;

public class DriverLZW {
    public static void main(String[] args) {
        
        int op=0;
        


        System.out.println("");
        System.out.println("Siusplau, escull una opcio introduint el seu numero");
        System.out.println("1: Provar constructora");
        System.out.println("2: Comprimir");
        System.out.println("3: Descomprimir");
        System.out.println("4: Sortir");
        System.out.println("");

        Scanner in = new Scanner(System.in);
        
         op = in.nextInt();
         
        
            switch (op) {
                case 1:
                    constructora();
                    System.out.println("S'ha creat un objecte LZW");
                break;

                case 2:
                    provaComprimir();    
                 break;

                case 3:
                    provaDescomprimir();
                break;

                case 4:
                    System.exit(0);
                break;

                default:
                    System.out.println("Opcio no valida");
                    System.out.println("Introdueix un numero del 1 al 4 per a triar la opcio");
                    
                    
                break;


            
        }
       
        






        
    }

    private static void constructora() {
        LZW n = new LZW();
    }

    private static void provaComprimir() {
        LZW lzw = new LZW();
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueixi el path de un arxiu de text");
        String path = sc.next();
        //comprimeix
        Object[] sortida;
        byte[] entrada= adaptaEntrada(path);
        sortida =lzw.comprimir(entrada);
        System.out.println("L'arxiu s'ha comprimit en "+ sortida[1]+" milisegons");

        //guarda
        System.out.println("Introdueixi el path de la carpeta on es vol guardar");
        path=sc.next();
        System.out.println("Introdueixi el nom amb el que es vol guardar el comprimit");
        String nom = sc.next();
        sc.close();
        try{
        File file = new File(path+ "/" + nom);
        OutputStream os2 = new FileOutputStream(file);
        byte[] sortida_bytes = (byte[])sortida[0];
        os2.write(sortida_bytes);
        int aaa = 432;
        System.out.println("S'ha guardat");
        }
        catch(Exception e) {System.out.println("Error al guardar");}


     }

    private static byte[] adaptaEntrada(String path) {
        try{
        
        File file = new File(path);
		byte[] contingutEnBytes = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(contingutEnBytes);
        fis.close();
        
        return contingutEnBytes;
        
        }
        catch(Exception e){System.out.println("Error al introduir el fitxer");
            System.exit(1);
        };

        byte[] error = new byte[1];
        return error;
    }

    private static void provaDescomprimir() {
        LZW lzw = new LZW();
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueixi el path de un arxiu comprimit");
        String path = sc.next();
        //descomprimeix
        Object[] sortida;
        byte[] entrada= adaptaEntrada(path);
        sortida =lzw.descomprimir(entrada);
        System.out.println("L'arxiu s'ha descomprimit en "+ sortida[1]+" milisegons");
        //guarda
        System.out.println("Introdueixi el path de la carpeta on es vol guardar");
        path=sc.next();
        System.out.println("Introdueixi el nom amb el que es vol guardar el descomprimit");
        String nom = sc.next();
        sc.close();
        try{
        File file = new File(path+ "/" + nom);
        OutputStream os2 = new FileOutputStream(file);
        byte[] sortida_bytes = (byte[])sortida[0];
        os2.write(sortida_bytes);
        int aaa = 432;
        System.out.println("S'ha guardat");
        }
        catch(Exception e) {System.out.println("Error al guardar");}



    }

    



}