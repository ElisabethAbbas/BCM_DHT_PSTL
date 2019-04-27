package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeClientI extends		 OfferedI, RequiredI
{
	public void put( int id, String s) throws Exception ;
	public String get( int id) throws Exception ;
}
