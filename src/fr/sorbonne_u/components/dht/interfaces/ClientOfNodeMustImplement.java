package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface ClientOfNodeMustImplement extends OfferedI, RequiredI{
	public void reveiveResultOfGet(String result) throws Exception;

}
