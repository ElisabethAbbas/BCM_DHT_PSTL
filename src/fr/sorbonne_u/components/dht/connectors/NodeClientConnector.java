package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;

public class NodeClientConnector extends AbstractConnector implements NodeClientI {

	@Override
	public void addComponent(String componentURI) throws Exception {
		((NodeClientI)this.offering).addComponent(componentURI);
	}
}
