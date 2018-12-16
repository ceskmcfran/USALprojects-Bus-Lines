/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package comportamiento;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

public class ParallelBehaviourRuta extends ParallelBehaviour{

	CyclicBehaviourRuta b1;
	CyclicBehaviourRuta b2;
	CyclicBehaviourRuta b3;
	CyclicBehaviourRuta b4;
	CyclicBehaviourRuta b5;
	
	public ParallelBehaviourRuta(Agent agente){
		
		super();
		
		//Creamos la factory de los comportamientos
		ThreadedBehaviourFactory threadedBehaviourFactory;
		
		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		b1 = new CyclicBehaviourRuta();
		addSubBehaviour(threadedBehaviourFactory.wrap(b1));
		
		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		b2 = new CyclicBehaviourRuta();
		addSubBehaviour(threadedBehaviourFactory.wrap(b2));

		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		b3 = new CyclicBehaviourRuta();
		addSubBehaviour(threadedBehaviourFactory.wrap(b3));
		
		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		b4 = new CyclicBehaviourRuta();
		addSubBehaviour(threadedBehaviourFactory.wrap(b4));
		
		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		b5 = new CyclicBehaviourRuta();
		addSubBehaviour(threadedBehaviourFactory.wrap(b5));
		
		
	}
	
}
