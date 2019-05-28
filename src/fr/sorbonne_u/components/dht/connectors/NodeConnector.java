package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;

public class NodeConnector  extends		AbstractConnector
implements	NodeRequiredI
{

	@Override
	public void setPred(String s, int n) throws Exception {
		//System.out.println("setting pred - connector");
		 ((NodeOfferedI)this.offering).setPred(s,n);
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		((NodeOfferedI)this.offering).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		((NodeOfferedI)this.offering).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		return ((NodeOfferedI)this.offering).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		return ((NodeOfferedI)this.offering).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		return ((NodeOfferedI)this.offering).getIndex();
	}

	@Override
	public void stab1() throws Exception {
		((NodeOfferedI)this.offering).stab1();
	}

	@Override
	public void stab2(String startNode) throws Exception {
		System.out.println("test stab2 connector");
		if(this.offering == null)
			System.out.println("PROBLEM : offering is null");
		((NodeOfferedI)this.offering).stab2(startNode);
	}

	@Override
	public void stab3(int succPredInd, int succInd, String predOfSucc) throws Exception {
		((NodeOfferedI)this.offering).stab3(succPredInd, succInd, predOfSucc);
	}

	@Override
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception {
		((NodeOfferedI)this.offering).notifyPred1(notifierIndex, notifierIbpURI);
		
	}

	@Override
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception {
		((NodeOfferedI)this.offering).notifyPred2(notifierIndex, notifierIbpURI, notifiedIbpURI);
		
	}

	@Override
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception {
		((NodeOfferedI)this.offering).notifyPred3(notifierIndex, notifierIbpURI, predInd);
		
	}

	@Override
	public void store(String s) throws Exception {
		((NodeOfferedI)this.offering).store(s);
		
	}

	@Override
	public String retrieve(int id) throws Exception {
		return ((NodeOfferedI)this.offering).retrieve(id);
	}
	
	@Override
	public void findSuccessor(String ClientIbpURI, int id) throws Exception {
		((NodeOfferedI)this.offering).findSuccessor(ClientIbpURI, id);
	}
	
	@Override
	public int closestPrecedingNode(int id) throws Exception {
		return ((NodeOfferedI)this.offering).closestPrecedingNode(id);
	}
	
	@Override
	public void fixFingers1() throws Exception {
		((NodeOfferedI)this.offering).fixFingers1();
	}
	
	@Override
	public void fixFingers2(String ibpURI) throws Exception {
		((NodeInboundPort)this.offering).fixFingers2(ibpURI);
	}
	
	@Override
	public void fixFingers3(String ibpURI, int next) throws Exception {
		((NodeOfferedI)this.offering).fixFingers3(ibpURI, next);
	}
	
	@Override
	public void fixFingers4(String ibpURI, int id) throws Exception {
		((NodeInboundPort)this.offering).fixFingers4(ibpURI, id);
	}
	
	@Override
	public void fixFingers5(String ibpURI, int indice) throws Exception {
		((NodeInboundPort)this.offering).fixFingers5(ibpURI, indice);
	}
	
	@Override
	public void get(String clientIbpURI, int id) throws Exception{
		((NodeOfferedI)this.offering).get(clientIbpURI,id);
	}
	
	@Override
	public void put(int id, String value) throws Exception {
		((NodeOfferedI)this.offering).put(id, value);
	}

	@Override
	public void connectAndSendToClient(String ClientIbpURI, int id) throws Exception {
		((NodeOfferedI)this.offering).connectAndSendToClient(ClientIbpURI,id);
		
	}

	@Override
	public void updateSuccessorList(String askingNodeIbpURI, int successorsToVisit) throws Exception {
		 ((NodeOfferedI)this.offering).updateSuccessorList( askingNodeIbpURI,  successorsToVisit);
		
	}

	@Override
	public void receiveUpdateSuccessorList(String succIbpURI, int succIndex) throws Exception {
		((NodeOfferedI)this.offering).receiveUpdateSuccessorList( succIbpURI,  succIndex);
		
	}

	@Override
	public void initiateUpdateSuccessorList() throws Exception {
		((NodeOfferedI)this.offering).initiateUpdateSuccessorList();
		
	}

	@Override
	public void fixFing1() throws Exception {
		((NodeOfferedI)this.offering).fixFing1();
	}

	@Override
	public void fixFing2(String senderIbp, int lastId, int id, int indice) throws Exception {
		((NodeOfferedI)this.offering).fixFing2(senderIbp, lastId, id, indice);
	}

	@Override
	public void updateFing(int indice, int res, String resUri) throws Exception {
		((NodeOfferedI)this.offering).updateFing(indice, res, resUri);
		
	}
}
