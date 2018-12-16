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

import agents.AgentRuta;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.*;

public class OSBehaviourRuta extends OneShotBehaviour {
	
	public OSBehaviourRuta(Agent agente){
		super(agente);
	}
	
	@Override
	public void action() {
		
		Grafos g = null;
		
		List<List<String>> linea1 = null; //Objeto para guardar el grafo de la primera linea
		List<List<String>> linea2 = null; //Objeto para guardar el grafo de la segunda linea
		String[] horaLinea1 = null;
		String[] horaLinea2 = null;
		String[] ordenBus1 = null;
		String[] ordenBus2 = null;

		
		//Esperamos al grafo de la primera linea
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.MatchOntology("ontologia1"));

		try {
			
			//Guardamos el grafo que ha mandado la primera linea
			linea1 = ((List<List<String>>)msg.getContentObject());
			
			//Mandamos el mensaje de confirmacion de linea1
			ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage.addReceiver(msg.getSender());
			aclMessage.setOntology("ontologia1");
			aclMessage.setLanguage(new SLCodec().getName());
			aclMessage.setContentObject((Serializable)null);
			myAgent.send(aclMessage); 
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		//Esperamos horario de la primera linea
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		try {
			
			//Guardamos el horario que ha mandado la primera linea
			horaLinea1 = ((String[])msg.getContentObject());
			
			//Mandamos el mensaje de confirmacion de linea1
			ACLMessage aclMessage1=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage1.addReceiver(msg.getSender());
			aclMessage1.setOntology("ontologia1");
			aclMessage1.setLanguage(new SLCodec().getName());
			aclMessage1.setContentObject((Serializable)null);
			myAgent.send(aclMessage1); 
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		//Esperamos al orden de buses de la primera linea
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		try {
			
			//Guardamos al orden de buses que ha mandado la primera linea
			ordenBus1 = ((String[])msg.getContentObject());
			
			//Mandamos el mensaje de confirmacion de linea1
			ACLMessage aclMessage2=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage2.addReceiver(msg.getSender());
			aclMessage2.setOntology("ontologia1");
			aclMessage2.setLanguage(new SLCodec().getName());
			aclMessage2.setContentObject((Serializable)null);
			myAgent.send(aclMessage2); 
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
/***************************************SEGUNDA LINEA RECIBIDA**************************************************/
		
		//Esperamos al grafo de la segunda linea
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchOntology("ontologia2"));

		try {
			
			//Guardamos el grafo que ha mandado la segunda linea
			linea2 = ((List<List<String>>)msg.getContentObject());
			
			//Mandamos el mensaje de confirmacion de linea2
			ACLMessage aclMessage3=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage3.addReceiver(msg.getSender());
			aclMessage3.setOntology("ontologia2");
			aclMessage3.setLanguage(new SLCodec().getName());
			aclMessage3.setContentObject((Serializable)null);
			myAgent.send(aclMessage3); 
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		//Esperamos horario de la segunda linea
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		try {
			
			//Guardamos el horario que ha mandado la segunda linea
			horaLinea2 = ((String[])msg.getContentObject());
			
			//Mandamos el mensaje de confirmacion de linea2
			ACLMessage aclMessage4=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage4.addReceiver(msg.getSender());
			aclMessage4.setOntology("ontologia2");
			aclMessage4.setLanguage(new SLCodec().getName());
			aclMessage4.setContentObject((Serializable)null);
			myAgent.send(aclMessage4); 
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	
		//Esperamos al orden de buses de la segunda linea
		msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		try {
			
			//Guardamos al orden de buses que ha mandado la segunda linea
			ordenBus2 = ((String[])msg.getContentObject());
			
			//Mandamos el mensaje de confirmacion de linea2
			ACLMessage aclMessage5=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage5.addReceiver(msg.getSender());
			aclMessage5.setOntology("ontologia2");
			aclMessage5.setLanguage(new SLCodec().getName());
			aclMessage5.setContentObject((Serializable)null);
			myAgent.send(aclMessage5); 
		
		} catch (Exception e) {

			e.printStackTrace();
		}

		
/***************************************IMPLEMENTACION DEL GRAFO CONJUNTO**************************************************/
		
		
		List<Arco> listaArcos = new ArrayList<Arco>();
		Arco aux = null;

		//Guardamos las aristas de la linea 1
		for(int i=0; i<linea1.size(); i++){

			aux = new Arco(linea1.get(i).get(0), linea1.get(i).get(1), Integer.parseInt(linea1.get(i).get(2)));
			listaArcos.add(aux);
			
		}
		//Guardamos las aristas de la linea 2
		for(int i=0; i<linea2.size(); i++){

			aux = new Arco(linea2.get(i).get(0), linea2.get(i).get(1), Integer.parseInt(linea2.get(i).get(2)));
			listaArcos.add(aux);
			
		}
		//Guardamos el arrayList en una constante para pasarsela a Grafos
		final Arco[] RUTAS = {
		
			listaArcos.get(0),
			listaArcos.get(1),
			listaArcos.get(2),
			listaArcos.get(3),
			listaArcos.get(4),
			listaArcos.get(5),
			listaArcos.get(6),
			listaArcos.get(7),
			listaArcos.get(8),
			listaArcos.get(9),
			listaArcos.get(10),
			listaArcos.get(11),
			listaArcos.get(12),
			
		};
		
		//Creamos el objeto con la informacion de las lineas para pasarsela al AgentRuta
		g = new Grafos(RUTAS, horaLinea1, horaLinea2, ordenBus1, ordenBus2);
		//Le pasamos al agente el grafo
		AgentRuta p = (AgentRuta)this.myAgent;
		p.setMapa(g);
		
	}

}

























