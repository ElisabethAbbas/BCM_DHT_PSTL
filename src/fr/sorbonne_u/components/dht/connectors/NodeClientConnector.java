package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;

public class NodeClientConnector extends AbstractConnector implements NodeClientI {

	@Override
	public void put(int id, String s) throws Exception {
		((NodeClientI)this.offering).put(id, s);
	}

	@Override
	public String get(int id) throws Exception {
		return ((NodeClientI)this.offering).get(id);
	}
}
