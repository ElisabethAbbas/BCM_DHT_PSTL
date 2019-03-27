package fr.sorbonne_u.components.dht.connectors;

import java.util.HashMap;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminOfferedI;

public class AdminConnector extends		AbstractConnector
implements	AdminOfferedI
{

	@Override
	public void initialize(HashMap<Integer, String> nodes) {
		((AdminOfferedI)this.offering).initialize(nodes);
	}

	@Override
	public String[] getRing() {
		return ((AdminOfferedI)this.offering).getRing();
	}

	@Override
	public void join(String s) {
		((AdminOfferedI)this.offering).join(s);	
	}

}
