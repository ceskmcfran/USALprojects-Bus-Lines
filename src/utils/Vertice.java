/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package utils;

import java.util.HashMap;
import java.util.Map;

public class Vertice implements Comparable<Vertice>{

	final private String nombre;
	private int dist = Integer.MAX_VALUE; //Distancia pretende ser infinita
	private Vertice anterior = null;
	public final Map<Vertice, Integer> vecino = new HashMap<>(); //Contendra un vertice(que sera vecino) y la distancia	
	static int sumador=0;
	private static String[] vectorSalida = {"","","","","","","","","","","","","","",""};


	/**
	 * Constructor
	 * @param nombre
	 */
	public Vertice(String nombre){

		this.nombre = nombre;
		
	}

	/**
	 * Metodo que implementa la interfaz
	 */
	@Override
	public int compareTo(Vertice otro) {
		
		if(dist == otro.dist){
			return nombre.compareToIgnoreCase(otro.nombre);
		}
		
		return Integer.compare(dist, otro.dist);
	}
	
	/**
	 * Devuelve un String con la ruta a partir de un inicio, hasta un final y en una hora de salida
	 * @param horaSalida
	 */
	public String[] imprimirCaminoToString(int horaSalida){
		
		int peso=0;
		//Si es el mismo nodo imprime el nombre
		if(this == this.anterior){
			peso = this.dist + horaSalida;
			vectorSalida[sumador] = "Parada: "+this.nombre+"      Hora: "+ String.valueOf(horaSalida)+"\n";
			sumador++;
		}
		//Si no hay nodo anterior
		else if(this.anterior == null){
			System.err.printf("%s(ilocalizable)\n", this.nombre);
		}
		//En los demás casos llama recursivamente a esta funcion para el nodo anterior e imprime el actual con la distancia
		else{
			
			this.anterior.imprimirCaminoToString(horaSalida);
			peso = this.dist + horaSalida;
			vectorSalida[sumador] = "Parada: "+this.nombre+"      Hora: "+ String.valueOf(peso)+"\n";
			sumador++;

		}

		return vectorSalida;
	}
	
	/**
	 * Actualiza el tiempo desde que sale el bus hasta que llega a la parada del usuario
	 * @param horaSalida
	 * @return
	 */
	public int caminoHastaUser(int horaSalida){
		
		int trayectoBus = 0;
		int temp=0;
		
		//Si es el mismo nodo imprime el nombre
		if(this == this.anterior){
			temp = this.dist + horaSalida;
		}
		//Si no hay nodo anterior
		else if(this.anterior == null){
			System.out.printf("%s(ilocalizable)\n", this.nombre);
		}
		//En los demás casos llama recursivamente a esta funcion para el nodo anterior e imprime el actual con la distancia
		else{
			trayectoBus = this.anterior.caminoHastaUser(horaSalida);
			
			temp = this.dist + horaSalida;
			trayectoBus = temp;
		}
		
		return trayectoBus;
		
	}
	
	public void reiniciar(){
		sumador = 0;
		for(int i=0; i<vectorSalida.length; i++){
			vectorSalida[i] = "";
		}
	}
	
	public String getNombre() {
		return nombre;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Vertice getAnterior() {
		return anterior;
	}

	public void setAnterior(Vertice anterior) {
		this.anterior = anterior;
	}
	
	
	
}
