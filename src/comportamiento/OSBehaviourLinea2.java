/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package comportamiento;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.Utils;

public class OSBehaviourLinea2 extends OneShotBehaviour {

	public OSBehaviourLinea2(Agent agente){
		super(agente);
	}
	
	@Override
	public void action() {

		String ordenParadas[] = {"11","4","12","9","13"};
		String horasBuses[] = {"1", "7", "15", "32", "35"};
		List<List<String>> nodo = new ArrayList<List<String>>();//ArrayList donde meter toda la informacion del grafo de la linea1
		
		insertarArista(nodo, "11","4","3");
		insertarArista(nodo, "4","12","4");
		insertarArista(nodo, "12","9","3");
		insertarArista(nodo, "9","13","2");
		
		//Mandamos el grafo a ruta
		Utils.enviarMensajePorOnt(this.myAgent, "control", nodo, "ontologia2");

		//Esperamos a recibir el mensaje de la ruta
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		//Enviamos las horas de la linea
		Utils.enviarMensaje(myAgent, "control", horasBuses);

		//Esperamos a recibir el mensaje de la ruta
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

		//Enviamos el orden de las paradas de la linea
		Utils.enviarMensaje(myAgent, "control", ordenParadas);

		//Esperamos a recibir el mensaje de la ruta
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

	}

	private void insertarArista(List<List<String>> nodo, String origen, String destino, String peso){
		
		List<String> listaNodo = new ArrayList<String>();
		listaNodo.add(origen);
		listaNodo.add(destino);
		listaNodo.add(peso);
		nodo.add(listaNodo);
		
	}

	
}
