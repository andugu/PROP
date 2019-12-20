package main.domini;

public class Fitxer {
    //Atributs
    protected String path;
    protected int tamany;
    protected Algorisme algorisme;


    //Metodes
    public Fitxer(String path, int tamany, Algorisme algorisme) {
        this.path = path;
        this.tamany = tamany;
        this.algorisme = algorisme;
    }

    public Object[] calcularEstadistiques(int tamanyNou, long temps){

        Object[] ret = new Object[2];

        double grau = (double)tamanyNou * (double)100;
        grau = grau/(double)this.tamany;

        if(tamany == 0) grau = 0.0;

        double velocitat = (double)this.tamany/(double)temps;
        ret[0] = grau;
        ret[1] = velocitat;
        return ret;
    }
}