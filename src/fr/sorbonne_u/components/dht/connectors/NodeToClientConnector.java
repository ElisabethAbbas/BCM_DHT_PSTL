package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.ClientOfNodeMustImplement;

public class NodeToClientConnector extends		AbstractConnector
implements	ClientOfNodeMustImplement {

	@Override
	public void reveiveResultOfGet(String result) throws Exception {
		((ClientOfNodeMustImplement)this.offering).reveiveResultOfGet(result);
	}
}
