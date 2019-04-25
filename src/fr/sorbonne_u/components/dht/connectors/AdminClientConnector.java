package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminClientI;

public class AdminClientConnector extends AbstractConnector implements AdminClientI{

	@Override
	public String getRingNodeInMyJVM(String JVMURI) throws Exception {
		return ((AdminClientI)this.offering).getRingNodeInMyJVM(JVMURI);
	}

}
