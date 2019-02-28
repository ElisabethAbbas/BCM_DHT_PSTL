package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.DataOfferedI;
import fr.sorbonne_u.components.interfaces.DataOfferedI.DataI;
import fr.sorbonne_u.components.ports.AbstractDataInboundPort;

public class NodeDataInboundPort extends AbstractDataInboundPort{
	private static final long serialVersionUID = 1L;

	public				NodeDataInboundPort(
		String uri,
		ComponentI owner
		) throws Exception{
		super(uri,
			  DataOfferedI.PullI.class,
			  DataOfferedI.PushI.class,
			  owner);
	}

	public NodeDataInboundPort(
		ComponentI owner
		) throws Exception
	{
		super(DataOfferedI.PullI.class,
			  DataOfferedI.PushI.class,
			  owner);
	}

	@Override
	public DataI get() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
