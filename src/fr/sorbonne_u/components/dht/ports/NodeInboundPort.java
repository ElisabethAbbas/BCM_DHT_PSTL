package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NodeInboundPort extends AbstractInboundPort implements NodeI{
	private static final long serialVersionUID = 1L;

	public				NodeInboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeI.class, owner);
	}

	public				NodeInboundPort(ComponentI owner)
	throws Exception
	{
		super(NodeI.class, owner);
	}

	@Override
	public void setPred(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSucc(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIndex(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPred() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSucc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
}
