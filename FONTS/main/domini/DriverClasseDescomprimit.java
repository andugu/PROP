package main.domini;
import java.io.*;
import java.util.Scanner;

public class DriverClasseDescomprimit {
    public static void main(String[] args) throws UnsupportedEncodingException {
        int op=0;
        System.out.println("");
        System.out.println("Siusplau, escull una opcio introduint el seu numero");
        System.out.println("1: Provar constructora");
        System.out.println("2: Calcular estadistiques");
        System.out.println("3: Sortir");
        System.out.println("");

        Scanner in = new Scanner(System.in);
        
         op = in.nextInt();
            switch (op) {
                case 1:

                    constructora();
                    System.out.println("S'ha creat un objecte Fitxer");
                break;

                case 2:
                    provaComprimir();    
                 break;

                case 3:
                    System.exit(0);
                break;

                default:
                    System.out.println("Opcio no valida");
                    System.out.println("Introdueix un numero del 1 al 3 per a triar la opcio");
                break;

    }
}
    private static void constructora() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueixi el path del fitxer");
        String path = sc.next();
        System.out.println("Introdueix el tamany");
        int tamany = sc.nextInt();
        System.out.println("Introdueix el numero de l'algorisme desitjat");
        System.out.println("1:JPEG, 2:LZSS, 3:LZ78, 4:LZW");
        int algorisme = sc.nextInt();
        Algorisme aux = new JPEG();//per inicialitzar
        //assignarme l'algorisme
        switch (algorisme) {
            case 1:
            aux = new JPEG();
            
            break;
            
            case 2:
            aux = new LZSS();
            break;
            
            case 3:
            aux = new LZ78();
            break;

            case 4:
            aux = new LZW();
            break;

            default:
            System.out.println("Algorisme no valid");
            System.out.println("Introdueix un numero del 1 al 4 per a triar l'algorisme");
            System.exit(1);
            break;
        }

        
        Descomprimit d = new Descomprimit(path, tamany, aux);

    }
    private static void provaComprimir() throws UnsupportedEncodingException {
        Algorisme aux = new LZW();
        Descomprimit c = new Descomprimit("/exemplepath", 72,aux );
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueixi el path de un arxiu");
        String path = sc.next();
        //comprimeix
        Object[] sortida;
        byte[] entrada= adaptaEntrada(path);
        sortida =c.comprimir(entrada);
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
}
