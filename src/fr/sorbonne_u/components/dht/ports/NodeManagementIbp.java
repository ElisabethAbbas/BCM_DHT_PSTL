package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NodeManagementIbp extends AbstractInboundPort implements NodeManagementI{
	private static final long serialVersionUID = 1L;

	public				NodeManagementIbp(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeManagementI.class, owner);
	}

	public				NodeManagementIbp(ComponentI owner)
	throws Exception
	{
		super(NodeManagementI.class, owner);
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
	@Override
	public String getPred() throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<String>() {
					@Override
					public String call() throws Exception {
						return ((Node)this.getOwner()).
								getPred() ;
					}
				}) ;
	}

	@Override
	public String getSucc() throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<String>() {
					@Override
					public String call() throws Exception {
						return ((Node)this.getOwner()).
								getSucc() ;
					}
				}) ;
	}

	@Override
	public int getIndex() throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Integer>() {
					@Override
					public Integer call() throws Exception {
						return ((Node)this.getOwner()).
								getIndex() ;
					}
				}) ;
	}

	@Override
	public String getInboundPortURI() throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<String>() {
					@Override
					public String call() throws Exception {
						return ((Node)this.getOwner()).
								getInboundPortURI() ;
					}
				}) ;
	}

	@Override
	public String getClientInboundPortURI() throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<String>() {
					@Override
					public String call() throws Exception {
						return ((Node)this.getOwner()).
								getClientInboundPortURI() ;
					}
				}) ;
	}
}

