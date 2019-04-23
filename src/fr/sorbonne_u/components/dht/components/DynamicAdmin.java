package fr.sorbonne_u.components.dht.components;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.connectors.NodeManagementConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeManagementIbp;
import fr.sorbonne_u.components.examples.ddeployment_cs.interfaces.URIConsumerLaunchI;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.pre.dcc.connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.pre.dcc.interfaces.DynamicComponentCreationI;
import fr.sorbonne_u.components.pre.dcc.ports.DynamicComponentCreationOutboundPort;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;


public class DynamicAdmin extends		AbstractComponent
{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected AdminOutboundPort adminOutboundPort;
	protected String[][] ring;
	protected HashMap<Integer, String> nodes;//string correspond au nom de la JVM de la node a creer
	protected HashMap<Integer, DynamicComponentCreationOutboundPort> portsToNodesJVM = new HashMap<Integer, DynamicComponentCreationOutboundPort>();
	protected HashMap<Integer, String> nodesReflectionIbpURIS = new HashMap<Integer, String>();
	public DynamicAdmin(String AdminInboundPortURI,int size, HashMap<Integer, String> nodes) throws Exception//hashMap nodes != de celle de l'admin précédent
	{
		super(AdminInboundPortURI, 1, 1);
		this.size = size;
		this.nodes = nodes;
		
		this.addRequiredInterface(AdminRequiredI.class);
		this.addRequiredInterface(DynamicComponentCreationI.class);
		this.addOfferedInterface(NodeManagementI.class);
		
		this.adminOutboundPort = new AdminOutboundPort(this);
		this.addPort(this.adminOutboundPort);
		this.adminOutboundPort.localPublishPort();
		
		this.tracer.setTitle("Dynamic Admin") ;
		this.tracer.setRelativePosition(1, 0) ;
	}
	public void initialize() throws Exception {
		
		
		
	}
	public void initialize2() throws Exception {
		ring=new String[size][2];
		for(int i = 0; i < size; i++){
			// /!\ on considere que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		
		//admin reflection outbound port
		this.addRequiredInterface(ReflectionI.class) ;
		ReflectionOutboundPort rop = new ReflectionOutboundPort(this) ;
		this.addPort(rop) ;
		rop.localPublishPort() ;
		
		String [] tmpRingNode = new String[2];
		for(int n : nodesReflectionIbpURIS.keySet()) {
			rop.doConnection(nodesReflectionIbpURIS.get(n), ReflectionConnector.class.getCanonicalName());
			System.out.println("test RIbpURI : " + nodesReflectionIbpURIS.get(n));
			try {
				System.out.println("test 2 : taille " + rop.findPortURIsFromInterface(NodeManagementIbp.class).length);
			}catch (Exception e) {
				throw new ComponentStartException(e) ;
			}
			
			tmpRingNode[0] = rop.findPortURIsFromInterface(NodeManagementIbp.class)[0];
			System.out.println("test 3");
			tmpRingNode[1] = rop.findPortURIsFromInterface(NodeInboundPort.class)[0];
			System.out.println("test 4");
			ring[n] = tmpRingNode;
			rop.doDisconnection();
		}
		
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
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i][0], NodeManagementConnector.class.getCanonicalName());
					
					this.logMessage("settingPred node : " + i +"...");
					this.adminOutboundPort.setPred(tmp[1], tmpi);
					
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					this.logMessage("connecting Outb->Inb admin - node : " + tmpi +"...");
					this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeManagementConnector.class.getCanonicalName());
					
					this.logMessage("settingSucc node : " + tmpi +"...");
					this.adminOutboundPort.setSucc(ring[i][1],i);
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					
					tmp = ring[i];
					tmpi = i;
				}
			}
		}
		if((first != null)&&(first != tmp)){//pour ne pas faire le cas ou 1 seule node
			this.doPortConnection(this.adminOutboundPort.getPortURI(), first[0], NodeManagementConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setPred(tmp[1],tmpi);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeManagementConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setSucc(first[1],firsti);
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

	public void			dynamicDeploy() throws Exception
	{
		//call the dynamic component creator of each node to create the nodes
		String tmpReflectionIbpURI;
		System.out.println("starting node creation...");
		for(int n : portsToNodesJVM.keySet()) {
			System.out.println("creating node : " + n);
			tmpReflectionIbpURI = portsToNodesJVM.get(n).createComponent(Node.class.getCanonicalName(), new Object[]{"node" + n +"-rip", "admin-rip", n});
			nodesReflectionIbpURIS.put(n, tmpReflectionIbpURI);
			
		}	
		ring=new String[size][2];
		for(int i = 0; i < size; i++){
			// /!\ on considere que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		
		//admin reflection outbound port
		this.addRequiredInterface(ReflectionI.class) ;
		ReflectionOutboundPort rop = new ReflectionOutboundPort(this) ;
		this.addPort(rop) ;
		rop.localPublishPort() ;
		
		
		for(int n : nodesReflectionIbpURIS.keySet()) {
			String [] tmpRingNode = new String[2];
			rop.doConnection(nodesReflectionIbpURIS.get(n), ReflectionConnector.class.getCanonicalName());
			System.out.println("test RIbpURI : " + nodesReflectionIbpURIS.get(n));
			try {
				tmpRingNode[0] = rop.findPortURIsFromInterface(NodeManagementI.class)[0];
				rop.toggleTracing();
				this.doPortConnection(this.adminOutboundPort.getPortURI(), tmpRingNode[0], NodeManagementConnector.class.getCanonicalName());
				tmpRingNode[1] = this.adminOutboundPort.getInboundPortURI();
				this.doPortDisconnection(this.adminOutboundPort.getPortURI());
				
				//tmpRingNode[1] = rop.findPortURIsFromInterface(NodeInboundPort.class)[0];
			}catch (Exception e) {
				throw new Exception(e) ;
			}
			
			System.out.println("test 3");
			//tmpRingNode[1] = rop.findPortURIsFromInterface(NodeInboundPort.class)[0];
			System.out.println("test 4");
			
			ring[n] = tmpRingNode;
			rop.doDisconnection();
		}
		
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
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i][0], NodeManagementConnector.class.getCanonicalName());
					
					this.logMessage("settingPred node : " + i +"...");
					this.adminOutboundPort.setPred(tmp[1], tmpi);
					
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					this.logMessage("connecting Outb->Inb admin - node : " + tmpi +"...");
					this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeManagementConnector.class.getCanonicalName());
					
					this.logMessage("settingSucc node : " + tmpi +"...");
					this.adminOutboundPort.setSucc(ring[i][1],i);
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					
					tmp = ring[i];
					tmpi = i;
				}
			}
		}
		if((first != null)&&(first != tmp)){//pour ne pas faire le cas ou 1 seule node
			this.doPortConnection(this.adminOutboundPort.getPortURI(), first[0], NodeManagementConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setPred(tmp[1],tmpi);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeManagementConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setSucc(first[1],firsti);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
		}
		this.logMessage("initialized !");
	}
	
	@Override
	public void			start() throws ComponentStartException
	{
		
		try {
			System.out.println("starting dccObp creation...");
			nbNodes = 0;
			DynamicComponentCreationOutboundPort tmpCObp;
			for(int n : nodes.keySet()) {
				System.out.println("starting dccObp creation : " + n);
				tmpCObp = new DynamicComponentCreationOutboundPort(this);
				portsToNodesJVM.put(n, tmpCObp);
				this.addPort(tmpCObp);
				tmpCObp.localPublishPort();
				tmpCObp.doConnection(nodes.get(n) + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX, DynamicComponentCreationConnector.class.getCanonicalName());
			
			}
			
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}
		
		super.start() ; 
	}
	
	// TODO start, finalize, shutDown...
}
