package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.components.Node;
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
	public void stab3(int succPredInd, int succInd, String predOfSucc) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).stab3(succPredInd, succInd, predOfSucc) ;
						return null;
					}
				}) ;
	}

	@Override
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).notifyPred1(notifierIndex,notifierIbpURI) ;
						return null;
					}
				}) ;
	}

	@Override
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).notifyPred2(notifierIndex,notifierIbpURI, notifiedIbpURI) ;
						return null;
					}
				}) ;
	}

	@Override
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).notifyPred3(notifierIndex,notifierIbpURI, predInd) ;
						return null;
					}
				}) ;
	}

	@Override
	public void store(String s) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
									store(s) ;
						return null;
					}
				}) ;
	}

	@Override
	public String retrieve(int id) throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<String>() {
					@Override
					public String call() throws Exception {
						return ((Node)this.getOwner()).
								retrieve(id) ;
					}
				}) ;
	}
	
	@Override
	public void findSuccessor(String ClientIbpURI, int id) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).findSuccessor(ClientIbpURI, id) ;
						return null;
					}
				});
	}
	
	@Override
	public int closestPrecedingNode(int id) throws Exception {
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Integer>() {
					@Override
					public Integer call() throws Exception {
						return ((Node)this.getOwner()).closestPrecedingNode(id) ;
					}
				}) ;
	}
	
	@Override
	public void fixFingers() throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).fixFingers() ;
						return null;
					}
				}) ;
	}
	
	@Override
	public void get(String clientIbpURI, int id) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).get(clientIbpURI, id) ;
						return null;
					}
				}) ;
	}
	
	@Override
	public void put(int id, String value) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).put(id, value) ;
						return null;
					}
				}) ;
	}

	@Override
	public void connectAndSendToClient(String ClientIbpURI, int id) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
									connectAndSendToClient( ClientIbpURI,  id);
						return null;
					}
				}) ;
	}

	@Override
	public void updateSuccessorList(String askingNodeIbpURI, int successorsToVisit) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
						updateSuccessorList( askingNodeIbpURI,  successorsToVisit);
						return null;
					}
				}) ;
	}

	@Override
	public void receiveUpdateSuccessorList(String succIbpURI, int succIndex) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
						receiveUpdateSuccessorList( succIbpURI,  succIndex) ;
						return null;
					}
				}) ;
	}

	@Override
	public void initiateUpdateSuccessorList() throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).
						initiateUpdateSuccessorList();
						return null;
					}
				}) ;
		
	}
					
}
