package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class NodeOutboundPort extends AbstractOutboundPort implements NodeRequiredI{
	private static final long serialVersionUID = 1L;

	public				NodeOutboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeRequiredI.class, owner);
	}

	public				NodeOutboundPort(ComponentI owner)
	throws Exception
	{
		super(NodeRequiredI.class, owner);
	}

	@Override
	public void setPred(String s, int n) throws Exception {
		// TODO Auto-generated method stub
		((NodeRequiredI)this.connector).setPred(s,n);
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		// TODO Auto-generated method stub
		((NodeRequiredI)this.connector).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		// TODO Auto-generated method stub
		((NodeRequiredI)this.connector).setIndex(i);
	}
	
	/*@Override
	public void connect(String port) throws Exception {
		// TODO Auto-generated method stub
		((NodeRequiredI)this.connector).connect(port);
	}*/
	
	@Override
	public String getPred() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeRequiredI)this.connector).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeRequiredI)this.connector).getSucc();

	}

	@Override
	public int getIndex() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeRequiredI)this.connector).getIndex();

	}
}
