package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeRequiredI extends	RequiredI
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
	public void stab3(String predOfSucc, int succInd) throws Exception ;
	public void stab4(NodeInboundPort startNode, int succInd, String predOfSucc) throws Exception ;
	public void stab5(int succPredInd, int succInd, String predOfSucc) throws Exception ;
}
