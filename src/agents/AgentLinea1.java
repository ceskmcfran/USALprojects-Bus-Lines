/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package agents;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import comportamiento.*;

public class AgentLinea1 extends Agent {

	private static final long serialVersionUID = 1L;

	protected void setup(){
		
		//Proporcionamos las paginas amarillas en la plataforma con el DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		
		//Describimos el servicio
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Linea1");
		sd.setType("linea1");//Establecer el tipo del servicio para poder localizarlo cuando haya una busqueda
		sd.addOntologies("ontologia1");//Para usar este servicio hay que saber la ontologia
		sd.addLanguages(new SLCodec().getName());//Para usar este servicio hay que saber hablar el lenguage FIPA-SL
		
		//Añadimos el servicio descrito
		dfd.addServices(sd);
		
		try
		{
			// Registro de los servicios:
			DFService.register(this, dfd);
		}
		catch(FIPAException e)
		{
			System.err.println("Agente " + getLocalName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		
		addBehaviour(new OSBehaviourLinea1(this));
		
	}

	
}
