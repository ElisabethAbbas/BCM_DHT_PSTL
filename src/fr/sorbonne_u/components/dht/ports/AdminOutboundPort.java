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
	public void setPred(String s, int n) throws Exception {
		System.out.println("setting pred - admin outbound port");
		((NodeConnector)this.connector).setPred(s,n);
	}
	@Override
	public void setSucc(String s, int n) throws Exception {
		((NodeConnector)this.connector).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		((NodeConnector)this.connector).setIndex(i);
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
