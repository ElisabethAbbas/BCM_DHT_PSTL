package fr.sorbonne_u.components.dht.interfaces;
import java.util.HashMap;
import java.util.List;

import fr.sorbonne_u.components.interfaces.RequiredI;

public interface AdminRequiredI extends	
RequiredI
{
	public void setPred(String s, int n) throws Exception;
	public void setSucc(String s, int n) throws Exception;
	public void setIndex(int i) throws Exception;
	public String getPred() throws Exception;
	public String getSucc() throws Exception;
	public String getInboundPortURI()throws Exception;
	public int getIndex() throws Exception;
	public void setFingers(List<Integer> fingerInd ,HashMap<Integer, String> fingerIbpFromInd) throws Exception ;
}
