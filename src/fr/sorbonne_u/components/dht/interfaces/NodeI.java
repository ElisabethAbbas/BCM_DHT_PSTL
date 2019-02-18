package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeI extends		OfferedI,
RequiredI
{
	public void setPred(String s);
	public void setSucc(String s);
	public void setIndex(int i);
	public String getPred();
	public String getSucc();
	public int getIndex();
}
