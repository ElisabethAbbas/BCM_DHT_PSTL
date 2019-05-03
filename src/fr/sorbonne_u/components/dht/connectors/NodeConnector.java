package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;

public class NodeConnector  extends		AbstractConnector
implements	NodeOfferedI
{

	@Override
	public void setPred(String s, int n) throws Exception {
		//System.out.println("setting pred - connector");
		 ((NodeInboundPort)this.offering).setPred(s,n);
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		((NodeInboundPort)this.offering).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		((NodeInboundPort)this.offering).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		return ((NodeInboundPort)this.offering).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		return ((NodeInboundPort)this.offering).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		return ((NodeInboundPort)this.offering).getIndex();
	}

	@Override
	public void stab1() throws Exception {
		((NodeInboundPort)this.offering).stab1();
	}

	@Override
	public void stab2(NodeInboundPort startNode) throws Exception {
		((NodeInboundPort)this.offering).stab2(startNode);
	}

	@Override
	public void stab3(int succPredInd, int succInd, String predOfSucc) throws Exception {
		((NodeInboundPort)this.offering).stab3(succPredInd, succInd, predOfSucc);
	}

	@Override
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception {
		((NodeInboundPort)this.offering).notifyPred1(notifierIndex, notifierIbpURI);
		
	}

	@Override
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception {
		((NodeInboundPort)this.offering).notifyPred2(notifierIndex, notifierIbpURI, notifiedIbpURI);
		
	}

	@Override
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception {
		((NodeInboundPort)this.offering).notifyPred3(notifierIndex, notifierIbpURI, predInd);
		
	}

	@Override
	public void store(String s) throws Exception {
		((NodeInboundPort)this.offering).store(s);
		
	}

	@Override
	public String retrieve(int id) throws Exception {
		return ((NodeInboundPort)this.offering).retrieve(id);
	}
	
	@Override
	public void findSuccessor(String ClientIbpURI, int id) throws Exception {
		((NodeInboundPort)this.offering).findSuccessor(ClientIbpURI, id);
	}
	
	@Override
	public int closestPrecedingNode(int id) throws Exception {
		return ((NodeInboundPort)this.offering).closestPrecedingNode(id);
	}
	
	@Override
	public void fixFingers() throws Exception {
		((NodeInboundPort)this.offering).fixFingers();
	}
	
	@Override
	public void get(String clientIbpURI, int id) throws Exception{
		((NodeInboundPort)this.offering).get(clientIbpURI,id);
	}
	
	@Override
	public void put(int id, String value) throws Exception {
		((NodeInboundPort)this.offering).put(id, value);
	}

	@Override
	public void connectAndSendToClient(String ClientIbpURI, int id) throws Exception {
		((NodeInboundPort)this.offering).connectAndSendToClient(ClientIbpURI,id);
		
	}
}
