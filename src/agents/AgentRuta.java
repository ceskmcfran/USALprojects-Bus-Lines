/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package agents;

import comportamiento.*;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import utils.Grafos;

public class AgentRuta extends Agent {

	private static final long serialVersionUID = 1L;
	private Grafos mapa = null;
	
	public void setup(){
		
		//Proporcionamos las paginas amarillas en la plataforma con el DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		
		//Describimos el servicio
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Ruta");
		sd.setType("control");//Establecer el tipo del servicio para poder localizarlo cuando haya una busqueda
		sd.addOntologies("ontologia");//Para usar este servicio hay que saber la ontologia
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
		
		addBehaviour(new OSBehaviourRuta(this));
		addBehaviour(new ParallelBehaviourRuta(this));
		
	}

	public Grafos getMapa() {
		return mapa;
	}

	public void setMapa(Grafos mapa) {
		this.mapa = mapa;
	}

	
}
