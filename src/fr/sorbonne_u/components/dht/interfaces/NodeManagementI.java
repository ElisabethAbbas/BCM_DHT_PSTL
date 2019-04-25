package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeManagementI  extends		 OfferedI, RequiredI
{
	public void setPred(String s, int n) throws Exception;
	public void setSucc(String s, int n)throws Exception;
	public void setIndex(int i)throws Exception;
	public String getPred()throws Exception;
	public String getSucc()throws Exception;
	public String getInboundPortURI()throws Exception;
	public String getClientInboundPortURI()throws Exception;
	public int getIndex()throws Exception;
}
