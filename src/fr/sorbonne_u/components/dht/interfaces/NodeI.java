package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeI extends		OfferedI,
RequiredI
{
	public boolean setPred(String s) throws Exception;
	public boolean setSucc(String s)throws Exception;
	public boolean setIndex(int i)throws Exception;
	public String getPred()throws Exception;
	public String getSucc()throws Exception;
	public int getIndex()throws Exception;
}
