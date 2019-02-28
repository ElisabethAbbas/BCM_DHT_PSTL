package fr.sorbonne_u.components.dht.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.ports.AdminDataOutboundPort;
import fr.sorbonne_u.components.examples.pingpong.components.PingPongPlayer;
import fr.sorbonne_u.components.examples.pingpong.ports.PingPongOutboundPort;

public class Admin extends AbstractComponent{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected AdminDataOutboundPort adminDataOutboundPort;
	protected String[][] ring;//ring[0][0]->inbound port de la node 0, ring[0][1]->outbound port de la node 0
	
	public void initialize (int size, HashMap<Integer, String[]> nodes) throws Exception{//constructeur avec uris des JVM et index desires dans la DHT, pour l'instant noms et index des nodes
		this.adminDataOutboundPort = new AdminDataOutboundPort(this) ;
		this.size = size;
		nbNodes = 0;
		ring=new String[size][2];
		for(int i = 0; i < size; i++){
			// /!\ on considère que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		uris = nodes;
		for (int ind : nodes.keySet()) {
			// /!\ on considère que size est une puissance de 2, sinon arrondir au-dessus.
			ring[ind] = nodes.get(ind); 
			nbNodes++;
		}
		//initialisation ses bons liens de pred et succ
		ArrayList<Integer> sortedIndex = new ArrayList<Integer>(nodes.keySet());
		Collections.sort(sortedIndex);
		String[] first = null;
		String[] tmp = null;
		for(int i = 0; i < ring.length; i++){
			if(ring[i] != null){
				if(first == null){
					first = ring[i];
					tmp = ring[i];
				}
				else{
					this.doPortConnection(ring[i][1], tmp[0], NodeConnector.class.getCanonicalName());//utiliser un twoWayConnector?
					this.doPortConnection(ring[i][0], tmp[1], NodeConnector.class.getCanonicalName());
					this.runTask(
							new AbstractComponent.AbstractTask() {
								@Override
								public void run() {
									try {
										Thread.sleep(500) ;
										((Admin)this.getOwner()).
										adminDataOutboundPort.setPred(ring[i][1], tmp[0]) ;//appeler sur le dataInboundPort de ring[i] ?
									} catch (Exception e) {
										throw new RuntimeException(e) ;
									}
								}
							}) ;
					
					this.runTask(
							new AbstractComponent.AbstractTask() {
								@Override
								public void run() {
									try {
										Thread.sleep(500) ;
										((Admin)this.getOwner()).
										adminDataOutboundPort.setSucc(tmp[1], ring[i][0]) ;//appeler sur le dataInboundPort de tmp? ?
									} catch (Exception e) {
										throw new RuntimeException(e) ;
									}
								}
							}) ;
					
					//ring[i].setPred(tmp);// TODO ring et tmp sont des uri, appeler setPred et setSucc sur ces ports avec runTask()
					//tmp.setSucc(ring[i]);
					tmp = ring[i];
				}
			}
		}
		if((first != null)&&(first != tmp)){//on exclut le  cas où 0 ou 1 seule node
			this.doPortConnection(first[1], tmp[0], NodeConnector.class.getCanonicalName());
			this.doPortConnection(first[0], tmp[1], NodeConnector.class.getCanonicalName());
			this.runTask(
					new AbstractComponent.AbstractTask() {
						@Override
						public void run() {
							try {
								Thread.sleep(500) ;
								((Admin)this.getOwner()).
								adminDataOutboundPort.setPred(first[1], tmp[0]) ;//appeler sur le dataInboundPort de first ?
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}
						}
					}) ;
			
			this.runTask(
					new AbstractComponent.AbstractTask() {
						@Override
						public void run() {
							try {
								Thread.sleep(500) ;
								((Admin)this.getOwner()).
								adminDataOutboundPort.setSucc(tmp[1], first[0]) ;//appeler sur le dataInboundPort de tmp? ?
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}
						}
					}) ;
			//first.setPred(tmp);// TODO :ring et tmp sont des uri, appeler setPred et setSucc sur ces ports
			//tmp.setSucc(first);
		}
		
		//fingerTable, TODO
		
		/*for(int i = 0; i < ring.length; i++) {
			if(ring[i] != null) {
				FingerTable.initialize(this, ring[i]);
			}
		}*/
	}

	public Admin() 
	{
		super(1, 0);
	}


}
