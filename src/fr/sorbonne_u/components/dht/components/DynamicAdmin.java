package fr.sorbonne_u.components.dht.components;

import java.util.HashMap;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.pre.dcc.connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.pre.dcc.ports.DynamicComponentCreationOutboundPort;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;

public class DynamicAdmin extends		AbstractComponent
{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected AdminOutboundPort adminOutboundPort;
	protected String[] ring;
	protected HashMap<Integer, String> nodes;//string correspond au nom de la JVM de la node a creer
	protected HashMap<Integer, DynamicComponentCreationOutboundPort> portsToNodesJVM;
	protected HashMap<Integer, String> nodesReflectionIbpURIS;
	public DynamicAdmin(String AdminInboundPortURI,int size, HashMap<Integer, String> nodes) throws Exception//hashMap nodes != de celle de l'admin précédent
	{
		super(AdminInboundPortURI, 1, 1);
		this.size = size;
		this.nodes = nodes;
		
		this.addRequiredInterface(AdminRequiredI.class);
		this.addOfferedInterface(NodeOfferedI.class);
		
		this.adminOutboundPort = new AdminOutboundPort(this);
		this.addPort(this.adminOutboundPort);
		this.adminOutboundPort.localPublishPort();
		this.tracer.setTitle("Admin") ;
		this.tracer.setRelativePosition(1, 0) ;
	}
	public void initialize() throws Exception {
		this.logMessage("initialization...");
		nbNodes = 0;
		DynamicComponentCreationOutboundPort tmpCObp;
		for(int n : nodes.keySet()) {
			tmpCObp = new DynamicComponentCreationOutboundPort(this);
			portsToNodesJVM.put(n, tmpCObp);
			this.addPort(tmpCObp);
			tmpCObp.localPublishPort();
			tmpCObp.doConnection(nodes.get(n) + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX, DynamicComponentCreationConnector.class.getCanonicalName());
		}
		
		//call the dynamic component creator of each node to create the nodes
		String tmpReflectionIbpURI;
		for(int n : portsToNodesJVM.keySet()) {
			tmpReflectionIbpURI = portsToNodesJVM.get(n).createComponent(Node.class.getCanonicalName(), new Object[]{"node" + n +"-rip", "admin-rip"});
			nodesReflectionIbpURIS.put(n, tmpReflectionIbpURI);
		}
		
		ring=new String[size];
		for(int i = 0; i < size; i++){
			// /!\ on considere que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		
		//admin reflection outbound port
		ReflectionOutboundPort rop = new ReflectionOutboundPort(this) ;
		this.addPort(rop) ;
		rop.localPublishPort() ;
		

		for(int n : nodesReflectionIbpURIS.keySet()) {
			rop.doConnection(nodesReflectionIbpURIS.get(n), ReflectionConnector.class.getCanonicalName());
			ring[n] = rop.findPortURIsFromInterface(NodeInboundPort.class)[0];
			rop.doDisconnection();
		}
		
		
		String first = null;
		String tmp = null;
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
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i], NodeConnector.class.getCanonicalName());
					
					this.logMessage("settingPred node : " + i +"...");
					this.adminOutboundPort.setPred(tmp, tmpi);
					
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					this.logMessage("connecting Outb->Inb admin - node : " + tmpi +"...");
					this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp, NodeConnector.class.getCanonicalName());
					
					this.logMessage("settingSucc node : " + tmpi +"...");
					this.adminOutboundPort.setSucc(ring[i],i);
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					
					tmp = ring[i];
					tmpi = i;
				}
			}
		}
		if((first != null)&&(first != tmp)){//pour ne pas faire le cas ou 1 seule node
			this.doPortConnection(this.adminOutboundPort.getPortURI(), first, NodeConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setPred(tmp,tmpi);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp, NodeConnector.class.getCanonicalName());
			
			this.adminOutboundPort.setSucc(first,firsti);
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
		
			
	
	// TODO start, finalize, shutDown...
}
