package fr.sorbonne_u.components.dht.interfaces;

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
	public void stab2(String startNode) throws Exception ;
	public void stab3(int succPredInd, int succInd, String predOfSucc) throws Exception ;
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception ;
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception ;
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception ;
	public void store( String s) throws Exception ;
	public String retrieve( int id) throws Exception ;
	public void findSuccessor(String ClientIbpURI, int id) throws Exception ;
	public int closestPrecedingNode(int id) throws Exception;
	public void fixFingers1() throws Exception;
	public void fixFingers2(String ibpURI) throws Exception;
	public void fixFingers3(String ibpURI, int next) throws Exception;
	public void fixFingers4(String ibpURI, int id) throws Exception;
	public void fixFingers5(String ibpURI, int indice) throws Exception;
	public void get(String clientIbpURI, String id) throws Exception;
	public void put(String id, String value) throws Exception;
	public void connectAndSendToClient(String ClientIbpURI, int id) throws Exception;
	public void initiateUpdateSuccessorList() throws Exception ;
	public void updateSuccessorList(String askingNodeIbpURI, int successorsToVisit) throws Exception ;
	public void receiveUpdateSuccessorList(String succIbpURI, int succIndex) throws Exception ;
	public void fixFing1() throws Exception;
	public void fixFing2(String senderIbp, int lastId, int id, int indice) throws Exception;
	public void updateFing(int indice, int res, String resUri) throws Exception;
}