package fr.sorbonne_u.components.dht.ports;

import fr.sorbonne_u.components.ComponentI;
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
	public void stab2(String startNode) throws Exception {
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

	@Override
	public void store(String s) throws Exception {
		((NodeConnector)this.connector).store(s);
		
	}

	@Override
	public String retrieve(int id) throws Exception {
		return ((NodeConnector)this.connector).retrieve(id);
	}
	
	@Override
	public void findSuccessor(String ClientIbpURI, int id) throws Exception {
		((NodeConnector)this.connector).findSuccessor(ClientIbpURI, id);
	}
	
	@Override
	public int closestPrecedingNode(int id) throws Exception {
		return ((NodeConnector)this.connector).closestPrecedingNode(id);
	}
	
	@Override
	public void fixFingers1() throws Exception {
		((NodeConnector)this.connector).fixFingers1();
	}
	
	@Override
	public void fixFingers2(String ibpURI) throws Exception {
		((NodeConnector)this.connector).fixFingers2(ibpURI);
	}
	
	@Override
	public void fixFingers3(String ibpURI, int next) throws Exception {
		((NodeConnector)this.connector).fixFingers3(ibpURI, next);
	}
	
	@Override
	public void fixFingers4(String ibpURI, int id) throws Exception {
		((NodeConnector)this.connector).fixFingers4(ibpURI, id);
	}
	
	
	@Override
	public void fixFingers5(String ibpURI, int index) throws Exception {
		((NodeConnector)this.connector).fixFingers5(ibpURI, index);
	}
	
	@Override
	public void get(String clientIbpURI, String id) throws Exception {
		((NodeConnector)this.connector).get(clientIbpURI,id);
	}
	
	@Override
	public void put(String id, String value) throws Exception {
		((NodeConnector)this.connector).put(id, value);
	}

	@Override
	public void connectAndSendToClient(String ClientIbpURI, int id) throws Exception {
		((NodeConnector)this.connector).connectAndSendToClient( ClientIbpURI, id);
		
	}

	@Override
	public void updateSuccessorList(String askingNodeIbpURI, int successorsToVisit) throws Exception {
		((NodeConnector)this.connector).updateSuccessorList( askingNodeIbpURI,  successorsToVisit);
		
	}

	@Override
	public void receiveUpdateSuccessorList(String succIbpURI, int succIndex) throws Exception {
		((NodeConnector)this.connector).receiveUpdateSuccessorList( succIbpURI,  succIndex);
		
	}

	@Override
	public void initiateUpdateSuccessorList() throws Exception {
		((NodeConnector)this.connector).initiateUpdateSuccessorList();
		
	}

	@Override
	public void fixFing1() throws Exception {
		((NodeConnector)this.connector).fixFing1();
		
	}

	@Override
	public void fixFing2(String senderIbp, int lastId, int id, int indice) throws Exception {
		((NodeConnector)this.connector).fixFing2(senderIbp, lastId, id, indice);
		
	}

	@Override
	public void updateFing(int indice, int res, String resUri) throws Exception {
		((NodeConnector)this.connector).updateFing(indice, res, resUri);
	}
}
