package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NodeInboundPort extends AbstractInboundPort implements NodeOfferedI{
	private static final long serialVersionUID = 1L;

	public				NodeInboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, NodeOfferedI.class, owner);
	}

	public				NodeInboundPort(ComponentI owner)
	throws Exception
	{
		super(NodeOfferedI.class, owner);
	}

	@Override
	public void setPred(String s, int n) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
									setPred(s,n) ;
						return null;
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
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
								setIndex(i) ;
						return null;
					}
				}) ;
		
	}
	
	/*@Override
	public void connect(String port) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						((Node)this.getOwner()).
								connect(port) ;
						return true;
					}
				}) ;
		
	}*/

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
	public void stab1() throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).stab1() ;
						return null;
					}
				}) ;
	}

	@Override
	public void stab2(NodeInboundPort startNode) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).stab2(startNode) ;
						return null;
					}
				}) ;
	}

	@Override
	public void stab3(String predOfSucc, int succInd) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).stab3(predOfSucc, succInd) ;
						return null;
					}
				}) ;
	}

	@Override
	public void stab4(NodeInboundPort startNode, int succInd, String predOfSucc) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).stab4(startNode, succInd, predOfSucc) ;
						return null;
					}
				}) ;
	}

	@Override
	public void stab5(int succPredInd, int succInd, String predOfSucc) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).stab5(succPredInd, succInd, predOfSucc) ;
						return null;
					}
				}) ;
	}
}
