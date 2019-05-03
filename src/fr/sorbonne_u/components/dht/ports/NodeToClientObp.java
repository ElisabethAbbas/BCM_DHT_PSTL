package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.connectors.NodeToClientConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class NodeToClientObp extends AbstractOutboundPort implements NodeClientI{
	private static final long serialVersionUID = 1L;

	public				NodeToClientObp(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeRequiredI.class, owner);
	}

	public				NodeToClientObp(ComponentI owner)
	throws Exception
	{
		super(NodeClientI.class, owner);
	}

	@Override
	public void put(int id, String s) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get(String clientIbpURI, int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reveiveResultOfGet(String result) throws Exception {
		((NodeToClientConnector)this.connector).reveiveResultOfGet( result);
		
	}
}
