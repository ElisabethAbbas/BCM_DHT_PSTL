package fr.sorbonne_u.components.dht.ports;

import java.util.HashMap;

import fr.sorbonne_u.components.ComponentI;
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

	@Override
	public void initialize(HashMap<Integer, String> nodes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getRing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void join(String s) {
		// TODO Auto-generated method stub
		
	}
}
