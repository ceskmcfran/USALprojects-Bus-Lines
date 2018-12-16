/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package utils;

public class Arco {

	private final String origen, destino; //Vertices del arco
	private final int dist; //distancia del arco
	
	public Arco(String origen, String destino, int dist){
		
		this.origen = origen;
		this.destino = destino;
		this.dist = dist;
		
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public int getDist() {
		return dist;
	}


	
}
