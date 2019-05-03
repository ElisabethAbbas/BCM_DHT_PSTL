package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.connectors.NodeManagementConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class AdminOutboundPort extends AbstractOutboundPort implements AdminRequiredI{
	private static final long serialVersionUID = 1L;

	public				AdminOutboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, AdminRequiredI.class, owner);
	}

	public				AdminOutboundPort(ComponentI owner)
	throws Exception
	{
		super(AdminRequiredI.class, owner);
	}
	
	@Override
	public void setPred(String s, int n) throws Exception {
		((NodeManagementConnector)this.connector).setPred(s,n);
	}
	@Override
	public void setSucc(String s, int n) throws Exception {
		((NodeManagementConnector)this.connector).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		((NodeManagementConnector)this.connector).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		return ((NodeManagementConnector)this.connector).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		return ((NodeManagementConnector)this.connector).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		return ((NodeManagementConnector)this.connector).getIndex();
	}

	@Override
	public String getInboundPortURI() throws Exception {
		return ((NodeManagementConnector)this.connector).getInboundPortURI();
	}
	
}
