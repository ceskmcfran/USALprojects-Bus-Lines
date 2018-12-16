/**
 * Copyright � 2016 by
 * Francisco Bl�zquez Mat�as 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package agents;

import comportamiento.CyclicBehaviourUser;
import jade.core.Agent;

public class AgentUsuario1 extends Agent {
	
	private static final long serialVersionUID = 1L;
	
	protected void setup(){
		
		//A�adimos el comportamiento al agente usuario
		addBehaviour(new CyclicBehaviourUser(this));
		
	}
	
	protected void takeDown(){
		System.out.println("Agente usuario "+getAID().getName()+" terminado");
		
	}
	
}
