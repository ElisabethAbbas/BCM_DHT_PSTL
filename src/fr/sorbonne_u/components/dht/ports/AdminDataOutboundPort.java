package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.DataRequiredI;
import fr.sorbonne_u.components.interfaces.DataRequiredI.DataI;
import fr.sorbonne_u.components.ports.AbstractDataOutboundPort;

public class AdminDataOutboundPort extends AbstractDataOutboundPort{
	private static final long serialVersionUID = 1L;

	public				AdminDataOutboundPort(
		String uri,
		ComponentI owner) throws Exception {
		super(uri,
			  DataRequiredI.PullI.class,
			  DataRequiredI.PushI.class,
			  owner);
	}

	public				AdminDataOutboundPort(
		ComponentI owner
		) throws Exception
	{
		super(DataRequiredI.PullI.class,
			  DataRequiredI.PushI.class,
			  owner);
	}

	@Override
	public void receive(DataI d) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setPred(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setSucc(String string) {
		// TODO Auto-generated method stub
		
	}

}
