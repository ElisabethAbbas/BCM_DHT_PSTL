package fr.sorbonne_u.components.dht.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.ports.AdminDataOutboundPort;
import fr.sorbonne_u.components.dht.ports.AdminInboundPort;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeDataInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeDataOutboundPort;
import fr.sorbonne_u.components.examples.pingpong.components.PingPongPlayer;
import fr.sorbonne_u.components.examples.pingpong.ports.PingPongOutboundPort;
import fr.sorbonne_u.components.examples.reflection.interfaces.MyServiceI;
import fr.sorbonne_u.components.examples.reflection.ports.MyServiceInboundPort;
import fr.sorbonne_u.components.ports.PortI;

public class Admin extends AbstractComponent{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected String arg1;//pour passer en param�tres dans les innerClass
	protected String arg2;
	protected AdminOutboundPort adminOutboundPort;
	protected String[][] ring;//ring[0][0]->inbound port de la node 0, ring[0][1]->outbound port de la node 0

	public Admin(String AdminInboundPortURI) throws Exception
	{
		super(AdminInboundPortURI, 1, 0);
		this.addOfferedInterface(MyServiceI.class) ;
		AdminInboundPort ip = new AdminInboundPort(this) ;
		this.addPort(ip) ;
		ip.publishPort() ;
	}
	
	public void initialize (int size, HashMap<Integer, String[]> nodes) throws Exception{//constructeur avec uris des JVM et index desires dans la DHT, pour l'instant noms et index des nodes
		this.adminOutboundPort = new AdminOutboundPort(this) ;
		this.addPort(this.adminOutboundPort);
		this.adminOutboundPort.localPublishPort();
		this.size = size;
		nbNodes = 0;
		ring=new String[size][2];
		for(int i = 0; i < size; i++){
			// /!\ on consid�re que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		uris = nodes;
		for (int ind : nodes.keySet()) {
			// /!\ on consid�re que size est une puissance de 2, sinon arrondir au-dessus.
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
					this.doPortConnection(ring[i][1], tmp[0], NodeConnector.class.getCanonicalName());//utiliser un connecteur diff�rent pour diff�rencier les relation de pred et succ? ou twoWay?
					this.doPortConnection(ring[i][0], tmp[1], NodeConnector.class.getCanonicalName());
					
					arg1 = ring[i][1];
					arg2 = tmp[0];
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i][1], NodeConnector.class.getCanonicalName());
					this.runTask(
							new AbstractComponent.AbstractTask() {
								@Override
								public void run() {
									try {
										Thread.sleep(500) ;
										((Admin)this.getOwner()).
										adminOutboundPort.setPred(arg2) ;
									} catch (Exception e) {
										throw new RuntimeException(e) ;
									}
								}
							}) ;
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					arg1 = tmp[1];
					arg2 = ring[i][0];
					this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[1], NodeConnector.class.getCanonicalName());
					this.runTask(
							new AbstractComponent.AbstractTask() {
								@Override
								public void run() {
									try {
										Thread.sleep(500) ;
										((Admin)this.getOwner()).
										adminOutboundPort.setSucc(arg2) ;
									} catch (Exception e) {
										throw new RuntimeException(e) ;
									}
								}
							}) ;
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					//ring[i].setPred(tmp);// TODO ring et tmp sont des uri, appeler setPred et setSucc sur ces ports avec runTask()
					//tmp.setSucc(ring[i]);
					tmp = ring[i];
				}
			}
		}
		if((first != null)&&(first != tmp)){//on exclut le  cas o� 0 ou 1 seule node
			this.doPortConnection(first[1], tmp[0], NodeConnector.class.getCanonicalName());//utiliser un connecteur diff�rent pour diff�rencier les relation de pred et succ? ou twoWay?
			this.doPortConnection(first[0], tmp[1], NodeConnector.class.getCanonicalName());
			
			arg1 = first[1];
			arg2 = tmp[0];
			this.doPortConnection(this.adminOutboundPort.getPortURI(), first[1], NodeConnector.class.getCanonicalName());
			this.runTask(
					new AbstractComponent.AbstractTask() {
						@Override
						public void run() {
							try {
								Thread.sleep(500) ;
								((Admin)this.getOwner()).
								adminOutboundPort.setPred(arg2) ;
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}
						}
					}) ;
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			arg1 = tmp[1];
			arg2 = first[0];
			this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[1], NodeConnector.class.getCanonicalName());
			this.runTask(
					new AbstractComponent.AbstractTask() {
						@Override
						public void run() {
							try {
								Thread.sleep(500) ;
								((Admin)this.getOwner()).
								adminOutboundPort.setSucc(arg2) ;
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}
						}
					}) ;
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			//first.setPred(tmp);// ring et tmp sont des uri, appeler setPred et setSucc sur ces ports
			//tmp.setSucc(first);
		}
		
		//fingerTable, TODO
		
		/*for(int i = 0; i < ring.length; i++) {
			if(ring[i] != null) {
				FingerTable.initialize(this, ring[i]);
			}
		}*/
	}
	
	@Override
	public void			finalise() throws Exception
	{
		PortI[] p = this.findPortsFromInterface(MyServiceI.class) ;
		p[0].unpublishPort() ;

		super.finalise();
	}

}
