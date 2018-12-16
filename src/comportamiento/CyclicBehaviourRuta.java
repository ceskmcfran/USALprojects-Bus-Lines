/**
 * Copyright © 2016 by
 * Francisco Blázquez Matías 70919093L
 * Jorge Lorenzo Torres 70901632S
 * 
 * ALL RIGHTS RESEREVED
 */

package comportamiento;

import java.io.Serializable;

import agents.AgentRuta;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.*;

public class CyclicBehaviourRuta extends CyclicBehaviour{

	/*public CyclicBehaviourRuta(Agent agente){
		super(agente);
	}*/
	
	public void action(){

		String salida = "";
		String[] vSalida;
		int horaBus;
		
		//Esperamos a recibir el mensaje del user
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
				

		//Creamos un objeto que guarde los datos necesarios para el calculo de rutas
		DatosRutas dt = new DatosRutas();

		try{
			//Guardamos los datos en el objeto
			dt = ((DatosRutas)msg.getContentObject());
			
			//Creamos un acceso al agente
			AgentRuta a = (AgentRuta)this.myAgent;
			
			//Si la hora introducida por el usuario no existe en el horario
			if(-1 == a.getMapa().busAqui(Integer.parseInt(dt.getHoraSalida()), dt.getOrigen(), dt.getDestino())){
				salida = "La hora de salida introducida no existe en el horario";

				//Mandamos el mensaje de error
				ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
				
				aclMessage.addReceiver(msg.getSender());
				aclMessage.setOntology("ontologia");
				aclMessage.setLanguage(new SLCodec().getName());
				//aclMessage.setContentObject((Serializable)error);
				aclMessage.setContent(salida);
				myAgent.send(aclMessage); 
			}
			else{
				
				//Guardamos lo que tarda en llegar el bus
				horaBus = a.getMapa().busAqui(Integer.parseInt(dt.getHoraSalida()), dt.getOrigen(), dt.getDestino());
				
				//Enviamos lo que tarda en llegar el bus
				ACLMessage aclMessage1=new ACLMessage(ACLMessage.REQUEST);
				
				aclMessage1.addReceiver(msg.getSender());
				aclMessage1.setOntology("ontologia");
				aclMessage1.setLanguage(new SLCodec().getName());
				aclMessage1.setContent(String.valueOf(horaBus));
				myAgent.send(aclMessage1);
				
				//Esperamos a la contestacion
				ACLMessage msg2 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
				
				
				//Si la parada empieza en el 11 y cambia de linea
				if((dt.getOrigen().equals("11")) && (Integer.parseInt(dt.getDestino()) > 4)){
				
					//Calculamos la ruta
					a.getMapa().caminoMinimo(dt.getOrigen());
					vSalida = a.getMapa().imprimirCamino(dt.getDestino(), horaBus);
					
					//Guardamos el contenido del vector en un String
					for(int i=0; i<vSalida.length;i++){
						salida = salida + vSalida[i];
					}
					
					//Mandamos el mensaje con la ruta
					ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
					
					aclMessage.addReceiver(msg.getSender());
					aclMessage.setOntology("ontologia");
					aclMessage.setLanguage(new SLCodec().getName());
					aclMessage.setContent(salida);
					myAgent.send(aclMessage);
					
					//Esperamos a recibir la confirmacion
					ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
					
				}
				//Si la parada empieza en el 12 y cambia de linea
				else if((dt.getOrigen().equals("12")) && (Integer.parseInt(dt.getDestino()) > 9)){
					
					//Calculamos la ruta
					a.getMapa().caminoMinimo(dt.getOrigen());
					vSalida = a.getMapa().imprimirCamino(dt.getDestino(), horaBus);
					
					//Guardamos el contenido del vector en un String
					for(int i=0; i<vSalida.length;i++){
						salida = salida + vSalida[i];
					}
					
					//Mandamos el mensaje con la ruta
					ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
					
					aclMessage.addReceiver(msg.getSender());
					aclMessage.setOntology("ontologia");
					aclMessage.setLanguage(new SLCodec().getName());
					aclMessage.setContent(salida);
					myAgent.send(aclMessage);
					
					//Esperamos a recibir la confirmacion
					ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
					
				}
				
				//Si la parada de destino es la misma o anterior a la de origen
				else if((Integer.parseInt(dt.getOrigen())) >= (Integer.parseInt(dt.getDestino()))){
					
					salida= "La parada de destino es la misma o anterior a la parada de origen";
					
					//Mandamos el mensaje de error
					ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
					
					aclMessage.addReceiver(msg2.getSender());
					aclMessage.setOntology("ontologia");
					aclMessage.setLanguage(new SLCodec().getName());
					aclMessage.setContent(salida);
					myAgent.send(aclMessage);
				}
				else{
					
					//Calculamos la ruta
					a.getMapa().caminoMinimo(dt.getOrigen());
					vSalida = a.getMapa().imprimirCamino(dt.getDestino(), horaBus);
					
					//Guardamos el contenido del vector en un String
					for(int i=0; i<vSalida.length;i++){
						salida = salida + vSalida[i];
					}
					
					//Mandamos el mensaje con la ruta
					ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
					
					aclMessage.addReceiver(msg.getSender());
					aclMessage.setOntology("ontologia");
					aclMessage.setLanguage(new SLCodec().getName());
					aclMessage.setContent(salida);
					myAgent.send(aclMessage);
					
					//Esperamos a recibir la confirmacion
					ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
					
				}
			}
		} catch(Exception e){
			
			e.printStackTrace();
			
		}

	}
	
}
