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
import java.util.NavigableSet;
import java.util.TreeSet;


public class Grafos {

	private final Map<String, Vertice> grafo; //Mapa en el que la clave es un String(nombre del vertice) y el valor un Vertice
	private static int[] horaLinea1 = null;
	private static int[] horaLinea2 = null;
	private static String[] ordenBus1 = null;
	private static String[] ordenBus2 = null;	
	
	/**
	 * Rellena los mapas
	 * @param arcos
	 */
	public Grafos(Arco[] arcos, String[] hora1, String[] hora2, String[] ordenParadas1, String[] ordenParadas2){
		
		horaLinea1 = new int[hora1.length];
		horaLinea2 = new int[hora2.length];
		
		//Guardamos los datos que recibimos
		for(int i=0; i<hora1.length; i++){
			horaLinea1[i] = Integer.parseInt(hora1[i]);
		}
		for(int i=0; i<hora1.length; i++){
			horaLinea2[i] = Integer.parseInt(hora2[i]);
		}
		ordenBus1 = ordenParadas1;
		ordenBus2 = ordenParadas2;
		
		//Creamos un HashMap que almacenara los vertices de los arcos
		grafo = new HashMap<>(arcos.length);
		
		//Con esto se consigue añadir todos los vertices al HashMap
		for(Arco a : arcos){
			
			//Si no esta en el mapa el vertice de origen
			if(!grafo.containsKey(a.getOrigen())){
				//Asocia el nuevo vertice al vertice 
				grafo.put(a.getOrigen(), new Vertice(a.getOrigen()));
				
			}
			//Si no esta en el mapa el vertice de destino
			if(!grafo.containsKey(a.getDestino())){
				
				grafo.put(a.getDestino(), new Vertice(a.getDestino()));
				
			}
		}
		
		//Otra pasada para poner los vertices vecinos
		for(Arco a : arcos){
			
			//Buscamos en el mapa para cada vertice su mapa de vecinos y asociamos su vecino a la distancia
			grafo.get(a.getOrigen()).vecino.put(grafo.get(a.getDestino()), a.getDist());
			
		}
		
	}

	/**
	 * Devuelve la linea del vertice
	 * @param paradaOrigen
	 * @return Linea en la que va el bus
	 */
	public int encontrarLineaBus(String paradaOrigen){
		
		int horaBus;
		
		for(int i=0; i<ordenBus1.length; i++){
			for(int j=0; j<ordenBus2.length; j++){
				if(paradaOrigen.equalsIgnoreCase(ordenBus1[i])){//Si el origen coincide con alguna parada de la linea 1
					for(int k=0; k<ordenBus2.length; k++){//Buscamos coincidencias con la linea 2
						if(paradaOrigen.equalsIgnoreCase(ordenBus2[k])){//Si hay coincidencidas con la linea 2, quiere decir que la parada es una parada comun
							return 0;
						}
					}
					return 1;
				}
				//Si el origen coincide con alguna parada de la linea 2
				else if(paradaOrigen.equalsIgnoreCase(ordenBus2[j])){
					for(int k=0; k<ordenBus1.length; k++){//Buscamos coincidencias con la linea 2
						if(paradaOrigen.equalsIgnoreCase(ordenBus1[k])){//Si hay coincidencidas con la linea 2, quiere decir que la parada es una parada comun
							return 0;
						}
					}
					return 2;
				}
			}
		}
		return -1; //Si no se encuentra devuelve negativo
	}
	
	/**
	 * Metodo que ejecuta rutas en el que se proporcionan los datos especificos al usuario
	 * @param horaSalida
	 * @param paradaOrigen
	 * @param paradaDestino
	 */
	public int busAqui(int horaSalida, String paradaOrigen, String paradaDestino){
		
		int llegadaBus = -1; //Se inicializa a -1 porque si no cambia(no encuentra la hora) devuelve este numero como codigo de error
		int linea = encontrarLineaBus(paradaOrigen);

		//Dependiendo de la linea en la que este la parada y el tiempo introducido calculamos el tiempo que tarda el bus en llegar
		switch(linea){
		
			case 0: //Si es una parada comun
				
				boolean encontrado = false; //Flag para saber si la hora no esta en el vector e imprimir el error
				
				break1: //Etiqueta para la salida de los bucles anidados
				for(int i=0; i<horaLinea1.length; i++){
					for(int j=0; j<horaLinea2.length; j++){
						//Si esta en el horario de la linea 1
						if(horaSalida == horaLinea1[i]){
							for(int k=0; k<horaLinea2.length; k++){
								//Si tambien esta en el horario de la otra linea
								if(horaSalida == horaLinea2[k]){
									
									//Guarda el tiempo que tarda el bus en llegar a la parada de cada linea
									int temp1, temp2;
									caminoMinimo(ordenBus1[0]);
									temp1 = caminoBus(paradaOrigen, horaSalida);
									caminoMinimo(ordenBus2[0]);
									temp2 = caminoBus(paradaOrigen, horaSalida);
									encontrado = true;
									
									//Guardamos el tiempo en el que el bus tarda menos en llegar a la parada del usuario
									if(temp1 >= temp2){
										llegadaBus = temp2;
									}
									else{
										llegadaBus = temp1;
									}
									break break1;
								}
							}
							
							//Calculamos el camino del bus hasta la parada
							caminoMinimo(ordenBus1[0]);
							llegadaBus = caminoBus(paradaOrigen, horaSalida);
							encontrado = true;
							break break1;
							
						}
						else if(horaSalida == horaLinea2[j]){
							
							for(int k=0; k<horaLinea1.length; k++){
								//Si tambien esta en el horario de la otra linea
								if(horaSalida == horaLinea1[k]){
									
									//Guarda el tiempo que tarda el bus en llegar a la parada de cada linea
									int temp1, temp2;
									caminoMinimo(ordenBus1[0]);
									temp1 = caminoBus(paradaOrigen, horaSalida);
									caminoMinimo(ordenBus2[0]);
									temp2 = caminoBus(paradaOrigen, horaSalida);
									encontrado = true;
									
									//Guardamos el tiempo en el que el bus tarda menos en llegar a la parada del usuario
									if(temp1 >= temp2){
										llegadaBus = temp2;
									}
									else{
										llegadaBus = temp1;
									}
									break break1;
								}
							}
							
							//Calculamos el camino del bus hasta la parada
							caminoMinimo(ordenBus2[0]);
							llegadaBus = caminoBus(paradaOrigen, horaSalida);
							encontrado = true;
							break break1;
						}
					}
				}
				//Si no lo ha encontrado imprimimos el error
				if(encontrado == false){
					System.err.println("Hora no encontrada\n");
				}
				break;
		
			case 1: //Si es la linea 1
				
				boolean encontrado1 = false; //Flag para saber si la hora no esta en el vector e imprimir el error
				
				//Recorremos buscando la linea a la que sale el bus de la parada
				for(int i=0; i<horaLinea1.length; i++){
					//Si la hora introducida coincide con alguna del horario
					if(horaSalida == horaLinea1[i]){
					
						//Calculamos el camino del bus hasta la parada
						caminoMinimo(ordenBus1[0]);
						llegadaBus = caminoBus(paradaOrigen, horaSalida);
						encontrado1 = true;
						break;
					}
				}
				//Si no lo ha encontrado imprimimos el error
				if(encontrado1 == false){
					System.err.println("Hora no encontrada\n");
				}
				break;
				
			case 2: //Si es la linea 2
				
				boolean encontrado2 = false; //Flag para saber si la hora no esta en el vector e imprimir el error
				
				//Recorremos buscando la linea a la que sale el bus de la parada
				for(int i=0; i<horaLinea2.length; i++){
					//Si la hora introducida coincide con alguna del horario
					if(horaSalida == horaLinea2[i]){
						
						//Calculamos el camino del bus hasta la parada
						caminoMinimo(ordenBus2[0]);
						llegadaBus = caminoBus(paradaOrigen, horaSalida);
						encontrado2 = true;
						break;
					}
				}
				//Si no lo ha encontrado imprimimos el error
				if(encontrado2 == false){
					System.err.println("Hora no encontrada\n");
				}
				break;
		
			default:
				System.err.println("La parada no existe en ninguna linea\n");
				break;
		}
		
		if(llegadaBus == 0){
			llegadaBus = horaSalida;
		}
		return llegadaBus;
	}
	
	/**
	 * Realiza el camino minimo desde un vertice de origen
	 * @param nombreOrigen
	 */
	public void caminoMinimo(String nombreOrigen){
		
		if(!grafo.containsKey(nombreOrigen)){
			System.err.printf("El grafo no contiene el vertice de entrada %s\n", nombreOrigen);
			return;
		}
		
		//Guardamos el vertice que cogemos del mapa
		final Vertice fuente = grafo.get(nombreOrigen);
		
		//Creamos un objeto TreeSet para ordenar
		NavigableSet<Vertice> q = new TreeSet<>();
		
		//Organizacion de vertices
		for(Vertice v : grafo.values()){
			
			v.setAnterior(v == fuente ? fuente : null);
			v.setDist(v == fuente ? 0 : Integer.MAX_VALUE);
			q.add(v);
			
		}
		
		dijkstra(q);
		
	}
	
	/**
	 * Algoritmo dijkstra usando un monticulo binario
	 * @param q (cola de prioridad)
	 */
	private void dijkstra(final NavigableSet<Vertice> q){
		
		Vertice u, v;
		while(!q.isEmpty()){
			
			u = q.pollFirst(); //El vertice con la distancia mas corta (La primera iteracion devolvera la fuente)
			if (u.getDist() == Integer.MAX_VALUE){
				break;
			}
			
			//Mira las distancias de los vecinos
			for(Map.Entry<Vertice, Integer> m : u.vecino.entrySet()){
				//Guardamos la clave del vecino en esta iteracion
				v = m.getKey();				
				
				final int distanciaAlt = u.getDist() + m.getValue();
				//Si la distancia del vecino es mas corta
				if(distanciaAlt < v.getDist()){
					q.remove(v);
					v.setDist(distanciaAlt);
					v.setAnterior(u);
					q.add(v);
				}
				
			}
			
		}
		
	}

	/**
	 * Imprime el camino minimo hasta un vertice
	 * @param nombreDestino
	 */
	public String[] imprimirCamino(String nombreDestino, int horaSalida){
		
		String[] retorno;
		
		grafo.get(nombreDestino).reiniciar();
		if(!grafo.containsKey(nombreDestino)){
			System.err.printf("El grafo no contiene el vertice de destino %s\n", nombreDestino);
			return null;
		}
		
		retorno = grafo.get(nombreDestino).imprimirCaminoToString(horaSalida);
		
		return retorno;
		
	}
	
	/** Imprime el camino minimo hasta un vertice del bus
	 * @param nombreDestino
	 */
	public int caminoBus(String nombreDestino, int horaSalida){
		
		int pasaBus;
		
		if(!grafo.containsKey(nombreDestino)){
			System.err.printf("El grafo no contiene el vertice de destino %s\n", nombreDestino);
			return -1;
		}
		
		pasaBus = grafo.get(nombreDestino).caminoHastaUser(horaSalida);
		return pasaBus;
		
	}
}
