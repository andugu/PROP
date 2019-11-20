package main.domini;

public class EstadistiquesGenerals {
	//Atributs
	private Object[] estgen;

	//Metodes
	public EstadistiquesGenerals() {
		estgen = new Object[32];
		for(int i = 0; i < estgen.length; i += 4) {
			estgen[i] = 0.0; 	//grau double
			estgen[i+1] = 0.0; 	//velocitat double
			estgen[i+2] = 0.0;	//temps double
			estgen[i+3] = 0;	//numeroElements int
		}
	}

	private void assignarNovaEstadisticai(double grau, double velocitat, long temps, int i) {
		double grauMitja = (double) estgen[i];
		double velocitatMitjana = (double) estgen[i+1];
		double tempsMitja = (double) estgen[i+2];
		int numeroElements = (int) estgen[i+3];

		estgen[i] = (grauMitja * numeroElements + grau) / (double)(numeroElements + 1);
		estgen[i+1] = (velocitatMitjana * numeroElements + velocitat) / (double) (numeroElements + 1);
		estgen[i+2] = (tempsMitja * numeroElements + (double) temps) / (double) (numeroElements + 1);
		estgen[i+3] = (++numeroElements);
	}

	public void assignarNovaEstadistica(double grau, double velocitat, long temps, String nomalgorisme, boolean comprimit) {
		if(comprimit) {
			if(nomalgorisme == "LZ78")
				assignarNovaEstadisticai(grau, velocitat, temps, 0);
			else if(nomalgorisme == "LZSS")
				assignarNovaEstadisticai(grau, velocitat, temps, 4);
			else if(nomalgorisme == "LZW")
				assignarNovaEstadisticai(grau, velocitat, temps, 8);
			else
				assignarNovaEstadisticai(grau, velocitat, temps, 12);
		}
		else {
			if(nomalgorisme == "LZ78")
				assignarNovaEstadisticai(grau, velocitat, temps, 16);
			else if(nomalgorisme == "LZSS")
				assignarNovaEstadisticai(grau, velocitat, temps, 20);
			else if(nomalgorisme == "LZW")
				assignarNovaEstadisticai(grau, velocitat, temps, 24);
			else
				assignarNovaEstadisticai(grau, velocitat, temps, 28);
		}
	}

	private Object[] getEstadistiquesi(int i) {
		Object[] estadistiques = new Object[3];

		estadistiques[0] = estgen[i];
		estadistiques[1] = estgen[i+1];
		estadistiques[2] = estgen[i+2];

		return estadistiques;
	}

	public Object[] getEstadistiques(String nomalgorisme, boolean comprimit) {
		Object[] estadistiques;
		if(comprimit) {
			if(nomalgorisme == "LZ78")
				estadistiques = getEstadistiquesi(0);
			else if(nomalgorisme == "LZSS")
				estadistiques = getEstadistiquesi(4);
			else if(nomalgorisme == "LZW")
				estadistiques = getEstadistiquesi(8);
			else
				estadistiques = getEstadistiquesi(12);
		}
		else {
			if(nomalgorisme == "LZ78")
				estadistiques = getEstadistiquesi(16);
			else if(nomalgorisme == "LZSS")
				estadistiques = getEstadistiquesi(20);
			else if(nomalgorisme == "LZW")
				estadistiques = getEstadistiquesi(24);
			else
				estadistiques = getEstadistiquesi(28);
		}

		return estadistiques;
	}
}