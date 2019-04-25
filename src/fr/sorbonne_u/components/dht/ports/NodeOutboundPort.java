package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.connectors.*;
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
		((NodeConnector)this.connector).setPred(s,n);
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		((NodeConnector)this.connector).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		((NodeConnector)this.connector).setIndex(i);
	}
	
	/*@Override
	public void connect(String port) throws Exception {
		// TODO Auto-generated method stub
		((NodeRequiredI)this.connector).connect(port);
	}*/
	
	@Override
	public String getPred() throws Exception {
		return ((NodeConnector)this.connector).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		return ((NodeConnector)this.connector).getSucc();

	}

	@Override
	public int getIndex() throws Exception {
		return ((NodeConnector)this.connector).getIndex();

	}

	@Override
	public void stab1() throws Exception {
		((NodeConnector)this.connector).stab1();
	}

	@Override
	public void stab2(NodeInboundPort startNode) throws Exception {
		((NodeConnector)this.connector).stab2(startNode);
	}

	@Override
	public void stab3(int succPredInd, int succInd, String predOfSucc) throws Exception {
		((NodeConnector)this.connector).stab3(succPredInd, succInd, predOfSucc);
	}
	
	@Override
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception {
		((NodeConnector)this.connector).notifyPred1(notifierIndex, notifierIbpURI);
		
	}

	@Override
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception {
		((NodeConnector)this.connector).notifyPred2(notifierIndex, notifierIbpURI, notifiedIbpURI);
		
	}

	@Override
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception {
		((NodeConnector)this.connector).notifyPred3(notifierIndex, notifierIbpURI, predInd);
		
	}
	
	/*@Override
	public void stab1() throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).getNObpSucc().stab2((Node) this.getOwner());
						return null ;
					}
				}) ;
		
	}
	
	@Override
	public void stab2(Node n) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).getNObpPred().stab3(n);
						return null ;
					}
				}) ;
		
	}
	
	@Override
	public void stab3(Node n) throws Exception {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Node)this.getOwner()).getNObpPred(); // ???????? à modifier
						return null ;
					}
				}) ;
		
	}

	public void stab4(Node n) {
		this.getOwner().handleRequestAsync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
					 // ?????? à remplir
						return null ;
					}
				}) ;
	}*/
}
