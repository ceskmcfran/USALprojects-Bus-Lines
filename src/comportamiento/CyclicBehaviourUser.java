/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package comportamiento;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.*;

import java.io.Serializable;
import java.util.Scanner;

import agents.AgentUsuario1;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;

public class CyclicBehaviourUser extends CyclicBehaviour{

	public CyclicBehaviourUser(Agent agente){
		super(agente);
	}
	
	//Metodo que regula el comportamiento del usuario
	public void action(){
		
		int horaBus;
		
		//Introduce los datos por pantalla
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca el punto de origen: ");
		String origen = sc.nextLine();
		System.out.print("Introduzca el punto de destino: ");
		String destino = sc.nextLine();
		System.out.print("Introduzca la hora en la que sale el bus de la estacion: ");
		String horaSalida = sc.nextLine();
		
		//Si se teclea todo negativo se destruye el agente
		if((origen.equalsIgnoreCase("-1")) && (destino.equalsIgnoreCase("-1")) && (horaSalida.equalsIgnoreCase("-1"))){
			AgentUsuario1 a = (AgentUsuario1)this.myAgent;
			a.doDelete();
		}
		
		DatosRutas test = new DatosRutas();
		test.setOrigen(origen);
		test.setDestino(destino);
		test.setHoraSalida(horaSalida);
		
		//Mandamos el mensaje a ruta con lo que pide el user
		Utils.enviarMensaje(this.myAgent, "control", test);
		
		//Recibimos la hora de llegada del bus a la parada
		ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		horaBus = Integer.parseInt(msg1.getContent());
		
		try{
			//Mandamos la confirmacion de que han llegado los datos
			ACLMessage aclMessage1=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage1.addReceiver(msg1.getSender());
			aclMessage1.setOntology("ontologia");
			aclMessage1.setLanguage(new SLCodec().getName());
			aclMessage1.setContentObject((Serializable)null);
			myAgent.send(aclMessage1);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		//Esperamos a recibir la respuesta
		ACLMessage msg = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

		
		//Guardamos los datos de la ruta
		String salida= (String)msg.getContent();
		
		try{
			//Mandamos la confirmacion de que han llegado los datos
			ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
			
			aclMessage.addReceiver(msg.getSender());
			aclMessage.setOntology("ontologia");
			aclMessage.setLanguage(new SLCodec().getName());
			aclMessage.setContentObject((Serializable)null);
			myAgent.send(aclMessage);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		//Sacamos los datos por pantalla 
		System.out.println("\n\n-----BUS NOCTURNO-----\n\n");
		
		System.out.println("Origen: "+origen);
		System.out.println("Destino: "+destino);
		System.out.println("Hora de salida del bus de la estacion: "+horaSalida);
		System.out.println("Hora de llegada a la parada: "+horaBus);
		System.out.println("\n");
		System.out.println(salida);
		System.out.println("\n");
	}
	
}
