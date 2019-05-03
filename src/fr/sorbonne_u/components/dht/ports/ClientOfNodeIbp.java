package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.ClientI;
import fr.sorbonne_u.components.dht.interfaces.ClientOfNodeMustImplement;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class ClientOfNodeIbp extends AbstractInboundPort implements ClientOfNodeMustImplement{
	private static final long serialVersionUID = 1L;

	public				ClientOfNodeIbp(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeClientI.class, owner);
	}

	public				ClientOfNodeIbp(ComponentI owner)
	throws Exception
	{
		super(ClientOfNodeIbp.class, owner);
	}

	@Override
	public void reveiveResultOfGet(String result) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((ClientI)this.getOwner()).reveiveResultOfGet(result) ;
						return null;
					}
				}) ;
		
	}
}
