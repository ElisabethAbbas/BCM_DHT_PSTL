package fr.sorbonne_u.components.dht.ports;

import java.util.HashMap;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.AdminOfferedI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class AdminInboundPort extends AbstractInboundPort implements AdminOfferedI{
	private static final long serialVersionUID = 1L;

	public				AdminInboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, AdminOfferedI.class, owner);
	}

	public				AdminInboundPort(ComponentI owner)
	throws Exception
	{
		super(AdminOfferedI.class, owner);
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
