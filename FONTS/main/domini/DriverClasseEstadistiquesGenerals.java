package main.domini;
import java.util.Scanner;

public class DriverClasseEstadistiquesGenerals {
	//Atributs
	private static EstadistiquesGenerals estgen; 

	//Metodes
	public static void testConstructor() {
		estgen = new EstadistiquesGenerals();
		System.out.println("Ja s'ha creat una instància de la classe EstadistiquesGenerals.");
	}

	public static void testAssignarNovaEstadistica() {
		System.out.println("Indica les dades estadístiques que vols afegir:");
		Scanner in = new Scanner(System.in);

		System.out.print("Escriu el grau com un valor decimal amb coma: ");
		double grau = in.nextDouble();

		System.out.print("Escriu la velocitat com un valor decimal amb coma: ");
		double velocitat = in.nextDouble();

		System.out.print("Escriu el temps com un numero enter, en mil·lisegons: ");
		long temps = in.nextLong();

		System.out.print("Escriu el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
		String nomalgorisme = in.next();

		boolean correcte;
		if(nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
			correcte = true;
		else correcte = false;

		while(!correcte) {
			System.out.print("No és un algorisme correcte. Torna a escriure el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
			nomalgorisme = in.next();
			if(nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
				correcte = true;
		}

		System.out.print("Escriu true, si es tracta d'un fitxer comprimit o false si es tracta d'un arxiu descomprimit: ");
		boolean comprimit = in.nextBoolean();

		estgen.assignarNovaEstadistica(grau, velocitat, temps, nomalgorisme, comprimit);

		System.out.println();
		System.out.println("Ja s'ha assignat una nova estadística amb els valors que has indicat.");
	}

	public static void testGetEstadistiques() {
		System.out.println("Indica les següents dades per a obtenir les estadístiques:");
		Scanner in = new Scanner(System.in);

		System.out.print("Escriu el nom de l'algorisme utilitzat, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
		String nomalgorisme = in.next();

		boolean correcte;
		if(nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
			correcte = true;
		else correcte = false;

		while(!correcte) {
			System.out.print("No és un algorisme correcte. Torna a escriure el nom de l'algorisme, aquest ha de ser LZ78 o LZSS o LZW o JPEG: ");
			nomalgorisme = in.next();
			if(nomalgorisme.equals("LZ78") || nomalgorisme.equals("LZSS") || nomalgorisme.equals("LZW") || nomalgorisme.equals("JPEG"))
				correcte = true;
		}

		System.out.print("Escriu true, si es tracta d'un fitxer comprimit o false si es tracta d'un arxiu descomprimit: ");
		boolean comprimit = in.nextBoolean();

		Object[] estadistiques = estgen.getEstadistiques(nomalgorisme, comprimit);

		double grauMitja = (double) estadistiques[0];
		double velocitatMitjana = (double) estadistiques[1];
		double tempsMitja = (double) estadistiques[2];

		if(comprimit) System.out.println("Els valors de les mitjanes estadístiques de l'algorisme " + nomalgorisme + " i del fitxer comprimit són les següents:");
		else System.out.println("Els valors de les mitjanes estadístiques de l'algorisme " + nomalgorisme + " i del fitxer descomprimit són les següents:");
		System.out.println(grauMitja);
		System.out.println(velocitatMitjana);
		System.out.println(tempsMitja);

		System.out.println("Ja s'han ensenyat els valors de les estadístiques.");
	}

	public static void main(String[] args) {
		System.out.println("Disposes de les següents opcions, però obligatoriament la primera en passar el test ha de ser la numero 1. Escriu l'opció:");
		System.out.println("1. Test Constructor");
		System.out.println("2. Test AssignarNovaEstadistica");
		System.out.println("3. Test GetEstadistiques");
		System.out.println("4. Sortir");

		Scanner in = new Scanner(System.in);
		int i = in.nextInt();

		boolean correcte = false;
		if(i == 4 || i == 1) correcte = true;

		while(!correcte) {
			System.out.println("Recorda que la primera opció que ha de passar el test ha de ser la numero 1.");
			System.out.println("Escriu una altra vegada l'opció: ");
			i = in.nextInt();

			if(i == 1 || i == 4) correcte = true;
		}

		while(i != 4) {
			if(i == 1) 
				testConstructor();
			else if(i == 2)
				testAssignarNovaEstadistica();
			else if(i == 3)
				testGetEstadistiques();
			else 
				System.out.println("Opció no valida");

			System.out.println("Escull una altre opció:");
			i = in.nextInt();
		}
	}
}