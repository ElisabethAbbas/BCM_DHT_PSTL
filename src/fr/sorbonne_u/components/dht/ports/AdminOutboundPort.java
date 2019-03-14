package fr.sorbonne_u.components.dht.ports;

import java.util.HashMap;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminI;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class AdminOutboundPort extends AbstractOutboundPort implements AdminRequiredI{
	private static final long serialVersionUID = 1L;

	public				AdminOutboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, AdminI.class, owner);
	}

	public				AdminOutboundPort(ComponentI owner)
	throws Exception
	{
		super(AdminI.class, owner);
	}
	@Override
	public boolean setPred(String s) throws Exception {
		return ((NodeConnector)this.connector).setPred(s);
	}
	@Override
	public boolean setSucc(String s) throws Exception {
		return ((NodeConnector)this.connector).setSucc(s);
	}

	@Override
	public boolean setIndex(int i) throws Exception {
		return ((NodeConnector)this.connector).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		return ((NodeConnector)this.connector).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		return ((NodeConnector)this.connector).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		return ((NodeConnector)this.connector).getIndex();
	}
	

	
	

	
}
