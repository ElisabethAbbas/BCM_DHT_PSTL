package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.dht.components.Node;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeOfferedI extends OfferedI, RequiredI
{
	public void setPred(String s, int n) throws Exception;
	public void setSucc(String s, int n)throws Exception;
	public void setIndex(int i)throws Exception;
	//public void connect(String port) throws Exception;
	public String getPred()throws Exception;
	public String getSucc()throws Exception;
	public int getIndex()throws Exception;
	public void stab1() throws Exception ;
	public void stab2(NodeInboundPort startNode) throws Exception ;
	public void stab3(int succPredInd, int succInd, String predOfSucc) throws Exception ;
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception ;
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception ;
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception ;
	public void store( String s) throws Exception ;
	public String retrieve( int id) throws Exception ;
	public Node findSuccessor(int id) throws Exception;
	public Node closestPrecedingNode(int id) throws Exception;
	public void fixFingers() throws Exception;
	public String get(int id) throws Exception;
	public void put(int id, String value) throws Exception;
	public void fixFingers1() throws Exception;
}