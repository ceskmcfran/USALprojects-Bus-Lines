/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package comportamiento;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.Utils;

public class OSBehaviourLinea1 extends OneShotBehaviour {

	public OSBehaviourLinea1(Agent agente){
		super(agente);
	}
	
	@Override
	public void action() {

		String ordenParadas[] = {"1","2","3","4","5","6","7","8","9","10"};
		String horasBuses[] = { "1", "2", "15", "20", "30"};
		List<List<String>> nodo = new ArrayList<List<String>>();//ArrayList donde meter toda la informacion del grafo de la linea1
		
		insertarArista(nodo, "1","2","2");;
		insertarArista(nodo, "2","3","3");
		insertarArista(nodo, "3","4","2");
		insertarArista(nodo, "4","5","7");
		insertarArista(nodo, "5","6","1");
		insertarArista(nodo, "6","7","4");
		insertarArista(nodo, "7","8","3");
		insertarArista(nodo, "8","9","2");
		insertarArista(nodo, "9","10","1");
		
		//Mandamos el grafo a ruta
		Utils.enviarMensajePorOnt(this.myAgent, "control", nodo, "ontologia1");

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
