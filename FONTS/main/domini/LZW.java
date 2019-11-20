package main.domini;
import java.util.ArrayList;
import java.util.HashMap;

public class LZW extends Algorisme {


    public LZW(){};

    private int final_diccionari_inicial = 9000;

   

    public Object[] comprimir(byte[] entradaBytes) {
        long time_start = System.currentTimeMillis();

        String entrada = new String(entradaBytes);
        HashMap < String, Integer > diccionari = new HashMap < String, Integer > ();
        
        //ini diccionari
        for (int i = 0; i <= final_diccionari_inicial; ++i) {
            
            diccionari.put("" + (char) i, i);
        }
        int final_local = final_diccionari_inicial;
        ArrayList < Integer > sortida2 = new ArrayList < Integer > ();

        
        String s_aux = new String(entrada.substring(0, 1));
        int maxim_consultat = 0;
        for (int i = 1; i < entrada.length(); ++i) {
                
            char simbol = entrada.charAt(i);
            int temporal_aux = diccionari.get(simbol+"");
            
            if(((temporal_aux)<final_diccionari_inicial) && temporal_aux > maxim_consultat) maxim_consultat = temporal_aux;

            if (diccionari.containsKey(s_aux + simbol)) {

                s_aux = s_aux + simbol;

            } 
            else {
               
                sortida2.add(diccionari.get(s_aux));
                diccionari.put(s_aux + simbol, ++final_local);
                s_aux = simbol + "";
            }

        }
        
        sortida2.add(diccionari.get(s_aux));
       
        
        
        byte[] sortida4 = new byte[5*sortida2.size()];//per si tots fossin de 3
        int contaaa = 0;
        for (int i=0; i < sortida2.size(); i++)
        {
 
           if(sortida2.get(i).intValue()>= 65536){//cas especial de 3 bytes
                byte[] auxespecial = intTObyte_especial3(sortida2.get(i).intValue());
                byte[] zero_aux = toBytes(0);
                
                sortida4[contaaa] = zero_aux[0];      //introdueixo 2 bytes amb 0 per indicar que haure de llegir 3
                sortida4[++contaaa]=zero_aux[1];      //bytes
                ++contaaa;
                
                sortida4[contaaa] = auxespecial[0];
                sortida4[++contaaa] = auxespecial[1];
                sortida4[++contaaa] = auxespecial[2];
                ++contaaa;

            }

           else {
                byte[] aux = toBytes(sortida2.get(i).intValue());

                sortida4[contaaa] = aux[0];
                sortida4[++contaaa] = aux[1];
                ++contaaa;
            }
        }

        byte[] sortida5 = new byte[contaaa];
        for(int i=0; i<sortida5.length; ++i){
            
            sortida5[i] = sortida4[i];
        }

        long time_end = System.currentTimeMillis();
        long tempsTotal = time_end - time_start;
        Object[] ret = {sortida5,tempsTotal};
        return ret;
      
    
    }
    


    public Object[] descomprimir(byte[] novaEntrada) {
        long time_start = System.currentTimeMillis();
        int[] entrada = new int[(novaEntrada.length)/2];//com a maxim sabem que tindra la meitat de posicions que nova entrada amb bytes
       
        int contador = 0;
        int k = 0;

        for(k=0; contador<novaEntrada.length; ++k) {

            byte[] aux = {novaEntrada[contador], novaEntrada[++contador]};
            ++contador;
            int nou_enter = toInt(aux);
            if(nou_enter==0) {//vol dir que ara haurem de llegir un int que ocupa 3 bytes
                byte[] aux_especial_de3 = {novaEntrada[contador], novaEntrada[++contador], novaEntrada[++contador]};
                ++contador;
                int visualitzar_aux = bytetoInt_especial3(aux_especial_de3);
                entrada[k] = bytetoInt_especial3(aux_especial_de3);
            }

            else {// int normal 
               
                entrada[k] = nou_enter;
            }
        }

        ArrayList < String > diccionari = new ArrayList < String > ();
        //ini diccionari
        for (int i = 0; i <= final_diccionari_inicial; ++i) {

            diccionari.add("" + (char) i);
        }

        int final_local = final_diccionari_inicial;


        String sortida = new String("");
        Integer viejo = entrada[0];
        sortida += diccionari.get(viejo);
        String s = diccionari.get(viejo);
        String c = s.substring(0, 1);
        
            for (int i = 1; i < k; ++i) {
            
            Integer nou = entrada[i];
            
            if (nou > final_local) {
                s = diccionari.get(viejo);
                s = s + c;
            } 
            else {
                s = diccionari.get(nou);
            }

            sortida += s;
            c = s.substring(0, 1);
            diccionari.add(diccionari.get(viejo) + c);
            ++final_local;
            viejo = nou;
        } 
        
       
        byte[] sortida2 = sortida.getBytes();
        long time_end = System.currentTimeMillis();
        long tempsTotal = time_end - time_start;
        Object[] ret = {sortida2,tempsTotal};
        return ret;
    }

   private  static byte[] toBytes(int a) {

        int aux = a;
        aux = aux<<32;
        aux = aux>>>32;
        byte baixos = (byte)aux;

        aux = a;
        aux = aux<<16;
        aux = aux>>>24;
        byte alts = (byte)aux;

        byte[] ret = {baixos,alts};
        return ret;

    }

   private static int toInt(byte arr[]) {
        int b;
        int aux;
        byte alts = arr[1];
        byte baixos = arr[0];
        if((int)alts < 0) aux = (int)alts & 0xff;
        else aux = (int)alts;
        aux =  aux <<8;
        if((int)baixos<0) aux = aux | (0xff & (int)baixos);
        else aux = aux | (int)baixos;
        b=aux;

        return b;

    }
    
    private static int bytetoInt_especial3(byte[] arr) {

        int b;
            int aux;
            byte molt_alts = arr[2];
            byte alts = arr[1];
            byte baixos = arr[0];
            if((int)molt_alts<0) aux = (int)molt_alts & 0xff;
            else aux = (int)molt_alts;
            aux = aux<<8;
    
    
            if((int)alts < 0) aux =aux | (int)alts & 0xff;
            else aux =aux|(int)alts;
            aux =  aux <<8;
            if((int)baixos<0) aux = aux | (0xff & (int)baixos);
            else aux = aux | (int)baixos;
            b=aux;
    
            return b;
    
    }

    private static byte[] intTObyte_especial3(int a){
          int aux = a;
            aux = aux<<24;
            aux = aux>>>24;
            byte baixos = (byte)aux;
    
            aux = a;
            aux = aux<<16;
            aux = aux>>>24;
            byte alts = (byte)aux;
    
    
            aux=a;
            aux = aux<<8;
            aux = aux>>>24;
            byte molt_alts = (byte)aux;
    
            byte[] ret = {baixos,alts, molt_alts};
            return ret;
        }

}