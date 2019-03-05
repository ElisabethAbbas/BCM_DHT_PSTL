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
	public void setPred(String s) {
		// TODO Auto-generated method stub
		((NodeI)this.connector).setPred(s);
		
	}

	@Override
	public void setSucc(String s) {
		// TODO Auto-generated method stub
		((NodeI)this.connector).setSucc(s);
	}

	@Override
	public void setIndex(int i) {
		// TODO Auto-generated method stub
		((NodeI)this.connector).setIndex(i);
	}

	@Override
	public String getPred() {
		// TODO Auto-generated method stub
		((NodeI)this.connector).getPred();
		return null;
	}

	@Override
	public String getSucc() {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).getSucc();

	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return ((NodeI)this.connector).getIndex();

	}
}
