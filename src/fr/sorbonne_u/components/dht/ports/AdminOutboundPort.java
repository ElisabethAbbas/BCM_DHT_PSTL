package fr.sorbonne_u.components.dht.ports;

import java.util.HashMap;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class AdminOutboundPort extends AbstractOutboundPort implements AdminI{
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

	public void setPred(String s) {
		((NodeConnector)this.connector).setPred(s);
	}
	public void setSucc(String s) {
		((NodeConnector)this.connector).setSucc(s);
	}
	
	@Override
	public void initialize(HashMap<Integer, String> nodes) {
		// TODO Auto-generated method stub
		((AdminI)this.connector).initialize(nodes) ;
	}

	@Override
	public String[] getRing() {
		// TODO Auto-generated method stub
		return ((AdminI)this.connector).getRing();

	}

	@Override
	public void join(String s) {
		// TODO Auto-generated method stub
		((AdminI)this.connector).join(s);
		
	}

	
}
