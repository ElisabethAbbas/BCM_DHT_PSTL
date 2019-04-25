package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface NodeClientI extends		 OfferedI, RequiredI
{
	public void addComponent(String componentURI) throws Exception;
}
