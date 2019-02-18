package fr.sorbonne_u.components.dht.interfaces;

import java.util.HashMap;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface AdminI extends		OfferedI,
RequiredI
{
	public void initialize(HashMap<Integer, String> nodes);//maybe rethink parameters
	public String[] getRing();
	public void join(String s);
}
