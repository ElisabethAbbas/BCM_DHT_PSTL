package fr.sorbonne_u.components.dht.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface AdminClientI extends		 OfferedI, RequiredI
{
	public String getRingNodeInMyJVM(String JVMURI) throws Exception;
}
