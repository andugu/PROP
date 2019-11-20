package main.domini;

import java.io.UnsupportedEncodingException;

public abstract class Algorisme {

	public Algorisme() {}
	public static void main(String[] args){}
	public abstract Object[] comprimir(byte[] input) throws UnsupportedEncodingException;
	public abstract Object[] descomprimir(byte[] input);	
}