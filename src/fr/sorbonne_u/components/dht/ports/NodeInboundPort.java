package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.examples.basic_cs.components.URIProvider;
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
	public boolean setPred(String s) throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ((Node)this.getOwner()).
									setPred(s) ;
					}
				}) ;
		
	}

	@Override
	public boolean setSucc(String s) throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ((Node)this.getOwner()).
								setSucc(s) ;
					}
				}) ;
		
	}

	@Override
	public boolean setIndex(int i) throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ((Node)this.getOwner()).
								setIndex(i) ;
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
}
