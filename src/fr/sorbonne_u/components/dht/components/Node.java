package fr.sorbonne_u.components.dht.components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.ports.NodeDataOutboundPort;

public class Node extends AbstractComponent{
	protected NodeDataOutboundPort dataOutboundPort;
	protected String pred;
	protected String succ;
	
	public void initialize() throws Exception {
		this.dataOutboundPort = new NodeDataOutboundPort(this);
	}
	
	public NodeDataOutboundPort getDataOutboundPort() {
		return this.dataOutboundPort;
	}
	
	public void setPred(String outboundPort, String inboundPort) throws Exception {
		if(this.dataOutboundPort.getPortURI() == outboundPort) {
			this.pred = inboundPort;
		}
	}
	
	public void setSucc(String outboundPort, String inboundPort) throws Exception {
		if(this.dataOutboundPort.getPortURI() == outboundPort) {
			this.succ = inboundPort;
		}
	}
	
	public Node()
	{
		super(1, 1);//à voir combien de threads on va utiliser
		// TODO Auto-generated constructor stub
	}



}
