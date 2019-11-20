package main.domini;
import java.util.ArrayList;
import java.util.ListIterator;

public class LZSS extends Algorisme{
	//Atributs
	private static int midaSB = 4096;   //mida màxima del Search Buffer
	private static int midaLAB = 18;  //16+2 de la mínima coincidencia   //mida màxima del Lookahead Buffer
	
	//Metodes
	public LZSS() {

	}

	private static byte [] fusionToChar(int offset, int length) {
		offset -= 1; //Ho faig perque el valor màxim és 4096 i per codificar-ho amb 12 bits el valor màxim és 4095
		length -= 3; //Ho faig perquè el minim de coincidencia és 3 per tant, no es codifiquen i a l'hora de descomprimir després se li suma 3

		byte[] offsetIlength = new byte[2];

		offsetIlength[0] = (byte) (offset >>> 4);
		offsetIlength[1] = (byte) ((offset << 4) & 0x0FF | length);

		return offsetIlength;
	}

	private static Integer[] descomposarChar(byte[] offsetLength) {
		int a = ((int) offsetLength[0] << 4) & 0x0FF0;
		int b = ((int) offsetLength[1] >>> 4) & 0x0F;

		int offset = ( a | b);
		int length = (offsetLength[1] & 0x0F);

		offset += 1; //Ho faig perque el valor màxim és 4096 i per codificar-ho amb 12 bits el valor màxim és 4095
		length += 3; //Ho faig perquè el minim de coincidencia valida és 3 i aquests a l'hora de comprimir no es codifiquen

		Integer[] offsetIlength = new Integer[2];

		offsetIlength[0] = offset;
		offsetIlength[1] = length;

		return offsetIlength;
	}

	private static Object[] comprovaSB(byte[] text, int p, ListIterator<Byte> it) {
		int cont = it.nextIndex();	//L'utilitzo per després calcular l'offset
		boolean fi = true;

		while(it.hasNext() && fi) {
			if(it.next() != text[p]) ++cont;
			else fi = false;  
		}

		if(fi) {
			Object[] offsetIlength = new Object[2];
			offsetIlength[0] = 0;
			offsetIlength[1] = 0;
			return offsetIlength;
		}

		int length = 1;
		int offset = midaSB - cont; 

		int posaux = p+1;
		boolean coincideix = true;
		while(it.hasNext() && coincideix && posaux < text.length && length < midaLAB) {
			byte aux = it.next();

			if(aux == text[posaux]) {
				++length;
				++posaux;
			}
			else {
				coincideix = false;
				it.previous();
			}
		}

		Object[] offsetIlength = new Object[2];
		offsetIlength[0] = offset;
		offsetIlength[1] = length;

		return offsetIlength;
	}

	public Object[] comprimir(byte[] contingutEnBytes) {
		long time_start = System.currentTimeMillis();

		ArrayList<Byte> SearchBuffer = new ArrayList<>(midaSB);

		int p = 0;
		ArrayList<Byte> llistacontingutsortida = new ArrayList<>();

		while(p < contingutEnBytes.length) {
			int offset = 0; 
			int length = 0;
			boolean coincideixenMolts = false;

			if(SearchBuffer.contains(contingutEnBytes[p])) {
				ListIterator<Byte> it = SearchBuffer.listIterator();

				while(it.hasNext()) {
					Object[] offsetIlength = comprovaSB(contingutEnBytes, p, it);

					if((int) offsetIlength[1] >= length && (int) offsetIlength[0] >= (int) offsetIlength[1]) {
						offset = (int) offsetIlength[0];
						length = (int) offsetIlength[1];
					} 
				}

				if(length > 2)
					coincideixenMolts = true;
			}

			if(coincideixenMolts) {
				//Afegim al SearchBuffer els caracters que hem codificat
				for (int i = 0; i < length; ++i) {
					SearchBuffer.add(contingutEnBytes[p]);
					++p;
				}

				//Treiem el nombre necessari de caracters del SearchBuffer per a complir la midaSB maxima
				for (int i = SearchBuffer.size(); i > midaSB; --i)
					SearchBuffer.remove(0);

				byte flag = 1;
				llistacontingutsortida.add(flag);

				byte[] offsetIlength = fusionToChar(offset,length);
				llistacontingutsortida.add(offsetIlength[0]);
				llistacontingutsortida.add(offsetIlength[1]);

			}
			else {
				byte flag = 0;
				llistacontingutsortida.add(flag);
				llistacontingutsortida.add(contingutEnBytes[p]);

				//Afegim al SearchBuffer el caracter que hem codificat
				SearchBuffer.add(contingutEnBytes[p]);

				//Treiem un caracter del SearchBuffer per a complir la midaSB maxima
				if (SearchBuffer.size() > midaSB) 
					SearchBuffer.remove(0);
				
				++p;
			}
		}

		byte[] contingutsortida = new byte[llistacontingutsortida.size()];

		ListIterator<Byte> it = llistacontingutsortida.listIterator();

		for(int i = 0; i < contingutsortida.length && it.hasNext(); ++i)
			contingutsortida[i] = it.next();

		long time_end = System.currentTimeMillis();
		long temps = time_end - time_start;

		Object[] contingutItemps = new Object[2];

		contingutItemps[0] = contingutsortida;
		contingutItemps[1] = temps;

		return contingutItemps;
	}

	public Object[] descomprimir(byte[] contingutEnBytes) {
		long time_start = System.currentTimeMillis();

		ArrayList<Byte> SearchBuffer = new ArrayList<>(midaSB);

		int p = 0;
		int offset;
		int length;
		ArrayList<Byte> llistacontingutsortida = new ArrayList<>();

		while(p < contingutEnBytes.length) {
			if(contingutEnBytes[p] == (byte) 0) {
				++p;
				llistacontingutsortida.add(contingutEnBytes[p]);

				//Afegim al SearchBuffer el caracter que hem descodificat
				SearchBuffer.add(contingutEnBytes[p]);
				++p;

				//Treiem un caracter del SearchBuffer per a complir la midaSB maxima
				if(SearchBuffer.size() > midaSB)
					SearchBuffer.remove(0);
			}
			else {
				byte[] offIlen = new byte[2];
				offIlen[0] = contingutEnBytes[++p]; //byte 7 bits d'offset
				offIlen[1] = contingutEnBytes[++p]; //byte 4 bits d'offset i 3 bits de length

				Integer[] offsetIlength = descomposarChar(offIlen);
				offset = offsetIlength[0];
				length = offsetIlength[1];

				++p;

				ListIterator<Byte> it = SearchBuffer.listIterator();

				int posit = midaSB - offset;

				//corregir: mirar de fer-ho amb la funció get(indexllista)
				while(posit != 0 && it.hasNext()) {
					it.next();
					--posit;
				}

				ArrayList<Byte> coincidencia = new ArrayList<>();
				for(int i = 0; i < length && it.hasNext(); ++i) {
					byte seguent = it.next();
					llistacontingutsortida.add(seguent);
					coincidencia.add(seguent);
				}

				ListIterator<Byte> itcoincidencia = coincidencia.listIterator();
				for(int i = 0; i < coincidencia.size() && itcoincidencia.hasNext(); ++i) {
					//Afegim al SearchBuffer el caracter que hem descodificat
					SearchBuffer.add(itcoincidencia.next());

					//Treiem un caracter del SearchBuffer per a complir la midaSB maxima
					if(SearchBuffer.size() > midaSB)
					SearchBuffer.remove(0);
				}
			}
		}

		byte[] contingutsortida = new byte[llistacontingutsortida.size()];

		ListIterator<Byte> it = llistacontingutsortida.listIterator();

		for(int i = 0; i < contingutsortida.length && it.hasNext(); ++i)
			contingutsortida[i] = it.next();

		long time_end =  System.currentTimeMillis();
		long temps = time_end - time_start;

		Object[] contingutItemps = new Object[2];

		contingutItemps[0] = contingutsortida;
		contingutItemps[1] = temps;

		return contingutItemps;
	}
}