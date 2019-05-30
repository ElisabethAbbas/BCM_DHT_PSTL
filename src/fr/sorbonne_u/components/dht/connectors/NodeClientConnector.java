package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;

public class NodeClientConnector extends AbstractConnector implements NodeClientI {

	@Override
	public void put(String id, String s) throws Exception {
		((NodeClientI)this.offering).put(id, s);
	}

	@Override
	public void get(String clientIbpURI, String id) throws Exception {
		((NodeClientI)this.offering).get(clientIbpURI, id);
	}

	@Override
	public void receiveResultOfGet(String result) throws Exception {
		// not used
		
	}
}
