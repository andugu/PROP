package main.domini;

import java.io.UnsupportedEncodingException;

public class Descomprimit extends Fitxer{

    public Descomprimit(String path, int tamany, Algorisme algorisme){
        super(path,tamany,algorisme);
    }
    
    public Object[] comprimir (byte[] contingut) throws UnsupportedEncodingException {
        Object[] contingutItemps  = algorisme.comprimir(contingut);

        byte[] contingut_descomprimit = (byte[]) contingutItemps[0];
        int tamanyNou = contingut_descomprimit.length;
        long temps = (long) contingutItemps[1];

        if(temps == 0) temps = 1;

        Object[] grauIvelocitat = calcularEstadistiques(tamanyNou, temps);
        Object[] ret = new Object[4];
        
        ret[0] = contingutItemps[0];//nou contingut
        ret[1] = grauIvelocitat[0];//grau
        ret[2] = grauIvelocitat[1];//velocitat
        ret[3] = temps;//temps
        return ret;


    }
    


}