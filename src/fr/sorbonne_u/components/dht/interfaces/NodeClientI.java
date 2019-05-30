package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeClientI extends		 OfferedI, RequiredI
{
	public void put( String id, String s) throws Exception ;
	public void get(String clientIbpURI, String id) throws Exception ;
	public void receiveResultOfGet(String result) throws Exception;
}
