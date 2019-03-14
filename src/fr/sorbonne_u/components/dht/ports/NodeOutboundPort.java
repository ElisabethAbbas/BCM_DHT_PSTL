package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class NodeOutboundPort extends AbstractOutboundPort implements NodeI{
	private static final long serialVersionUID = 1L;

	public				NodeOutboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeI.class, owner);
	}

	public				NodeOutboundPort(ComponentI owner)
	throws Exception
	{
		super(NodeI.class, owner);
	}

	@Override
	public boolean setPred(String s) throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).setPred(s);
		
	}

	@Override
	public boolean setSucc(String s) throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).setSucc(s);
	}

	@Override
	public boolean setIndex(int i) throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).getSucc();

	}

	@Override
	public int getIndex() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).getIndex();

	}
}
