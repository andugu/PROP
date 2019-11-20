package main.domini;
import java.io.*;
import java.util.Scanner;

public class DriverClasseFitxer{

	public static void main(String[] args) {
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
                    provaCalcularEstadistiques();    
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

private static void provaCalcularEstadistiques(){
        Algorisme alg = new LZW();//per exemple
	 	Fitxer fitxer = new Fitxer("/pathexemple/",78,alg);//exemple ini
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueix un tamany nou (mida de larray de bytes)");
        int tamany = sc.nextInt();
        System.out.println("Introdueix un temps en milisegons nou");
        Long temps = sc.nextLong();

        Object[] sortida = fitxer.calcularEstadistiques(tamany,temps);
        System.out.println("Grau de compresio es "+ sortida[0]);
        System.out.println("La velocitat es de "+sortida[1] +" bytes per milisegons");


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
        Algorisme aux = new LZW();
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
        }

		
        Fitxer n = new Fitxer(path, tamany, aux);
    }
    }
