package main.domini;

public class Comprimit extends Fitxer{
	//Metodes
	public Comprimit(String path, int tamany, Algorisme algorisme) {
	    super(path, tamany, algorisme);
	}

	public Object[] descomprimir (byte[] contingut) {

		Object[] contingutIestadistiques = new Object[4];

		Object[] contingutItemps = algorisme.descomprimir(contingut);

		long temps = (long) contingutItemps[1];
		byte[] contingut_descomprimit = (byte[]) contingutItemps[0];
		int tamanyNou = contingut_descomprimit.length;

		if(temps == 0) temps = (long) 0.5;

		Object[] estadistiques = calcularEstadistiques(tamanyNou,temps);
		
		contingutIestadistiques[0] = contingutItemps[0]; //contingutcomprimit
		contingutIestadistiques[1] = estadistiques[0]; //grau de les estadistiques
		contingutIestadistiques[2] = estadistiques[1]; //velocitat de les estadistiques
		contingutIestadistiques[3] = contingutItemps[1]; //temps de les estadisitiques.

		return contingutIestadistiques;
	}
}