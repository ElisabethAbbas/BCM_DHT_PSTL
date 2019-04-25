package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.DynamicAdmin;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.AdminClientI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class AdminClientIbp extends AbstractInboundPort implements AdminClientI{
	private static final long serialVersionUID = 1L;

	public				AdminClientIbp(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, AdminClientI.class, owner);
	}

	public				AdminClientIbp(ComponentI owner)
	throws Exception
	{
		super(AdminClientI.class, owner);
	}

	

	@Override
	public String getRingNodeInMyJVM(String JVMURI) throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<String>() {
					@Override
					public String call() throws Exception {
						return ((DynamicAdmin)this.getOwner()).getRingNodeInMyJVM(JVMURI) ;
					}
				}) ;
	}
}
