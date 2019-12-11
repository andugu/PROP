package main.domini;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import  java.util.ArrayList;


public class LZ78 extends Algorisme {

    public LZ78(){

    }

    public Object[] comprimir(byte[] contingut) throws UnsupportedEncodingException {
        long time_start = System.currentTimeMillis();
        char [] text = new String(contingut).toCharArray();
        //for (int i = 0; i< text.length; ++i){
            //System.out.println("text[i] :"+ (text[i]));
        //}
        HashMap<String, Integer> Diccionari = new HashMap<String, Integer>();
        ArrayList<Integer> Llista1 = new ArrayList<Integer>();
        ArrayList<Character> Llista2 = new ArrayList<Character>();
        int llargada = text.length;
        int contador = 1;
        int index_mes_gran = 0;
        for (int i = 0; i < llargada;  ++i ){
            String part = "";
            boolean trobat = false;
            int auxiliar = 0;
            String auxiliar_part = "";
            while(!trobat) {
                if (i >= llargada) {
                    auxiliar_part = "-1";
                    trobat = true;
                }
                else {
                    part += text[i];
                    auxiliar_part = part;
                    if (Diccionari.containsKey(part)) { //si existeix ens guardem el seu auxiliar (valor a guardar a vectors) i avancem i
                        auxiliar = Diccionari.get(part);
                        ++i;
                    } else {//si no existeix el guardem
                        Diccionari.put(part, contador);
                        ++contador; //index de cada entrada al diccionari.
                        trobat = true;
                    }
                }
            }
            //guardem als vectors el (per exemple) (0,a) on (auxiliar, ultim char de part) (llista1,llista2)
            Llista1.add(auxiliar);
            if (auxiliar > index_mes_gran) index_mes_gran = auxiliar;
            if (auxiliar_part == "-1"){
                Llista2.add(' ');
            }
            else {
                char h = auxiliar_part.toCharArray()[auxiliar_part.length()-1];//lultim char
                //System.out.println("afegim: " + h);
                Llista2.add((char)(h));//&0xff
            }
        }
        //System.out.println("ep" + Llista2.get(3));
        //System.out.println("ArrayList1: " + Llista1);
        //System.out.println("ArrayList2: " + Llista2);

        byte[] resultat = converteix_a_text(Llista1, Llista2, index_mes_gran);
        long time_end = System.currentTimeMillis();
        long temps_comprimir =  time_end - time_start;
        //System.out.println("Temps en comprimir: "+ ( time_end - time_start ) + " mil·lisegons");
        Object[] retornada = new Object[2];
        retornada[0] = resultat;
        retornada[1] = temps_comprimir;
        return retornada;
        //return resultat;
    }

    private static byte[] converteix_a_text(ArrayList<Integer> Llista1, ArrayList<Character> Llista2, int a) throws UnsupportedEncodingException {
        int n = Llista1.size();
        int num_bytes = 0;
        boolean assignat = false;
        int limit = 1;
        while(!assignat){
            ++num_bytes;
            limit = limit * 256;
            if (a < limit){
                assignat = true;
            }
        }
        //codificar amb num_bytes el numero i un byte pel caracter.
        int mida_total = (num_bytes+1)*n;
        byte[] resultat = new byte[mida_total+1];
        resultat[mida_total] =(byte) (num_bytes & 0xFF);
        for (int i = 0; i < n; i = ++i){
            int numero = Llista1.get(i);
            int limit_for = limit/256;
            for (int k = 0; k < num_bytes; ++k){ //posem el numero
                int cocient, residu;
                cocient = numero / limit_for;
                residu = numero%limit_for;
                byte auxiliar = (byte)cocient;
                //System.out.println("afegirem el numero: " + auxiliar );
                resultat[((num_bytes+1)*i)+k]  = auxiliar;
                numero = residu;
                if (limit_for != 1) {
                    limit_for = limit_for / 256;
                }
            }
            byte[] bytes = new byte[1];
            //String s = new String(String.valueOf(Llista2.get(i)));
            char s = Llista2.get(i);
            //System.out.println("s es: " + (s));
            //byte[] auxiliar_caracter = s.getBytes(StandardCharsets.UTF_8);
            byte auxiliar_caracter1 =(byte)(((byte)s )& 0xFF);
            //byte auxiliar_caracter1 = (byte)(auxiliar_caracter[auxiliar_caracter.length-1]&0xff);
            //System.out.println("afegirem el caracter: " + (auxiliar_caracter1&0xff));
            resultat[((num_bytes+1)*i)+num_bytes] =(byte) (auxiliar_caracter1&0xff);//posem el caracter
            //System.out.println("Estem a la i: " + i );
        }
        //for (int i = 0; i < mida_total+1; ++i){
            //System.out.println(resultat[i]&0xFF);
        //}
        return resultat;
    }


    public Object[] descomprimir(byte[] text) {
        long time_start = System.currentTimeMillis();
        HashMap<Integer, String> Diccionari = new HashMap<>();
        int contador = 1;
        int n = text.length;
        int mida_numeros = text[n-1];
        String resultat = "";
        int mida_bucle = n/(mida_numeros+1);
        int limit = 1;
        for (int i = 0; i < mida_numeros-1; ++i){
            limit = limit * 256;
        }
        for (int i = 0; i < mida_bucle; ++i){
            int limit_auxiliar = limit;
            int numero = 0;
            for (int j = 0; j < mida_numeros; ++j){
                int auxiliar_num = (int) text[(i*(mida_numeros+1))+j];
                numero = numero + (auxiliar_num * limit_auxiliar);
                limit_auxiliar = limit_auxiliar/256;
            }
            char caracter =(char) (text[(i*(mida_numeros+1))+(mida_numeros)] & 0xff);//&0xff
            //System.out.println("el caracter es: " + (caracter&0xff));
            String auxiliar = "";
            if (numero == 0){
                auxiliar += caracter;
            }
            else{
                auxiliar = Diccionari.get(numero);
                auxiliar += caracter;
            }
            //System.out.println("auxiliar es: " + auxiliar);
            resultat += auxiliar;
            Diccionari.put(contador, auxiliar);
            ++contador;
        }
        byte[] a = new byte[resultat.length()];
        a = resultat.getBytes(StandardCharsets.UTF_8);
        Object[] retornada = new Object[2];
        //System.out.println("El resultat final es: "+ resultat);
        retornada[0] = a;
        long time_end = System.currentTimeMillis();
        long temps_descomprimir =  time_end - time_start;
        //System.out.println("Temps en descompirmir: "+ ( time_end - time_start ) + " mil·lisegons");
        retornada[1] = temps_descomprimir;
        return retornada;
        //return a;
    }
}