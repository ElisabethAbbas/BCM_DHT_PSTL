package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NodeClientIbp extends AbstractInboundPort implements NodeClientI{
	private static final long serialVersionUID = 1L;

	public				NodeClientIbp(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeClientI.class, owner);
	}

	public				NodeClientIbp(ComponentI owner)
	throws Exception
	{
		super(NodeClientI.class, owner);
	}

	@Override
	public void addComponent(String componentURI) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).addComponent(componentURI) ;
						return null;
					}
				}) ;
		
	}
}
