package fr.sorbonne_u.components.dht.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

public class Admin extends AbstractComponent{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected AdminOutboundPort adminOutboundPort;
	protected String[][] ring;//ring[0][0]->inbound port de la node 0, ring[0][1]->outbound port de la node 0
	protected HashMap<Integer, String[]> nodes;
	public Admin(String AdminInboundPortURI,int size, HashMap<Integer, String[]> nodes) throws Exception
	{
		super(AdminInboundPortURI, 1, 1);
		this.size = size;
		this.nodes = nodes;
		/*this.addOfferedInterface(MyServiceI.class) ;
		AdminInboundPort ip = new AdminInboundPort(this) ;
		this.addPort(ip) ;
		ip.publishPort() ;*/
		
		this.addRequiredInterface(NodeRequiredI.class);
		this.addOfferedInterface(AdminOfferedI.class);
		
		this.adminOutboundPort = new AdminOutboundPort(this);
		this.addPort(this.adminOutboundPort);
		this.adminOutboundPort.localPublishPort();
		this.tracer.setTitle("Admin") ;
		this.tracer.setRelativePosition(1, 0) ;
	}
	
	public void initialize () throws Exception{//constructeur avec uris des JVM et index desires dans la DHT, pour l'instant noms et index des nodes
		this.logMessage("initialization...");
		
		nbNodes = 0;
		ring=new String[size][2];
		for(int i = 0; i < size; i++){
			// /!\ on considere que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		uris = nodes;
		for (int ind : nodes.keySet()) {
			// /!\ on considere que size est une puissance de 2, sinon arrondir au-dessus.
			ring[ind] = nodes.get(ind); 
			nbNodes++;
		}
		//initialisation ses bons liens de pred et succ
		ArrayList<Integer> sortedIndex = new ArrayList<Integer>(nodes.keySet());
		Collections.sort(sortedIndex);
		String[] first = null;
		String[] tmp = null;
		int tmpi = 0;
		int firsti=0;
		for(int i = 0; i < ring.length; i++){
			if(ring[i] != null){
				if(first == null){
					first = ring[i];
					firsti=i;
					tmp = ring[i];
					tmpi = i;
				}
				else{
					this.logMessage("connecting Outb->Inb admin - node : " + i +"...");
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i][0], NodeConnector.class.getCanonicalName());
					
					this.logMessage("settingPred node : " + i +"...");
					this.adminOutboundPort.setPred(tmp[0], tmpi);
					
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					this.logMessage("connecting Outb->Inb admin - node : " + tmpi +"...");
					this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeConnector.class.getCanonicalName());
					
					this.logMessage("settingSucc node : " + tmpi +"...");
					this.adminOutboundPort.setSucc(ring[i][0],i);
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					
					tmp = ring[i];
					tmpi = i;
				}
			}
		}
		if((first != null)&&(first != tmp)){//pour ne pas faire le cas ou 1 seule node
			this.doPortConnection(this.adminOutboundPort.getPortURI(), first[0], NodeConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setPred(tmp[0],tmpi);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setSucc(first[0],firsti);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
		}
		this.logMessage("initialized !");
		//fingerTable, TODO
		
		/*for(int i = 0; i < ring.length; i++) {
			if(ring[i] != null) {
				FingerTable.initialize(this, ring[i]);
			}
		}*/
	}
	
	public void			start() throws ComponentStartException
	{
		super.start() ;
		this.logMessage("starting Admin component.") ;
		// Schedule the first service method invocation in one second.
		this.scheduleTask(
			new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						((Admin)this.getOwner()).initialize() ;
					} catch (Exception e) {
						throw new RuntimeException(e) ;
					}
				}
			},
			1000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void			finalise() throws Exception
	{
		/*PortI[] p = this.findPortsFromInterface(AdminI.class) ;
		p[0].unpublishPort() ;*/
		this.doPortDisconnection(this.adminOutboundPort.getPortURI());
		
		super.finalise();
	}
	
	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			this.adminOutboundPort.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
	}
}
