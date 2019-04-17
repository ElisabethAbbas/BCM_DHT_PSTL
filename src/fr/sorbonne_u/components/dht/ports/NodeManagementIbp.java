package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NodeManagementIbp extends AbstractInboundPort implements NodeManagementI{
	private static final long serialVersionUID = 1L;

	public				NodeManagementIbp(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeOfferedI.class, owner);
	}

	public				NodeManagementIbp(ComponentI owner)
	throws Exception
	{
		super(NodeOfferedI.class, owner);
	}

	@Override
	public void setPred(String s, int n) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						((Node)this.getOwner()).
									setPred(s,n) ;
						return true;
					}
				}) ;
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
								setSucc(s,n) ;
						return null ;
					}
				}) ;
		
	}

	@Override
	public void setIndex(int i) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						((Node)this.getOwner()).
								setIndex(i) ;
						return true;
					}
				}) ;
		
	}
}
