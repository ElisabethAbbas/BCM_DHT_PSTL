package fr.sorbonne_u.components.dht.components;

import java.util.HashMap;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.pre.dcc.connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.pre.dcc.ports.DynamicComponentCreationOutboundPort;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.ports.ReflectionInboundPort;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;

public class DynamicAdmin extends		AbstractComponent
{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected AdminOutboundPort adminOutboundPort;
	protected String[][] ring;//ring[0][0]->inbound port de la node 0, ring[0][1]->outbound port de la node 0
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
		DynamicComponentCreationOutboundPort tmp;
		for(int n : nodes.keySet()) {
			tmp = new DynamicComponentCreationOutboundPort(this);
			portsToNodesJVM.put(n, tmp);
			this.addPort(tmp);
			tmp.localPublishPort();
			tmp.doConnection(nodes.get(n) + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX, DynamicComponentCreationConnector.class.getCanonicalName());
		}
		
		//call the dynamic component creator of each node to create the nodes
		String tmpReflectionIbpURI;
		for(int n : portsToNodesJVM.keySet()) {
			tmpReflectionIbpURI = portsToNodesJVM.get(n).createComponent(Node.class.getCanonicalName(), new Object[]{"node" + n +"-rip", "admin-rip"});
			nodesReflectionIbpURIS.put(n, tmpReflectionIbpURI);
		}
		
		//admin reflection outbound port
		ReflectionOutboundPort rop = new ReflectionOutboundPort(this) ;
		this.addPort(rop) ;
		rop.localPublishPort() ;
		for(int n : nodesReflectionIbpURIS.keySet()) {
			rop.doConnection(nodesReflectionIbpURIS.get(n), ReflectionConnector.class.getCanonicalName());
			
			//TODO : port connection for the node n
			//rop.doPortConnection
			
			rop.doDisconnection();
		
		}
			
			
		//connect the nodes
	}
	
	// TODO start, finalize, shutDown...
}
