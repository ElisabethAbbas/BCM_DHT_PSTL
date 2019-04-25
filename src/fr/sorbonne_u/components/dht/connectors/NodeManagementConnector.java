package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;

public class NodeManagementConnector extends		AbstractConnector
implements	NodeManagementI
{

	@Override
	public void setPred(String s, int n) throws Exception {
		//System.out.println("setting pred - connector");
		 ((NodeManagementI)this.offering).setPred(s,n);
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		((NodeManagementI)this.offering).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		((NodeManagementI)this.offering).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		return ((NodeManagementI)this.offering).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		return ((NodeManagementI)this.offering).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		return ((NodeManagementI)this.offering).getIndex();
	}

	@Override
	public String getInboundPortURI() throws Exception {
		return ((NodeManagementI)this.offering).getInboundPortURI();
	}

	@Override
	public String getClientInboundPortURI() throws Exception {
		return ((NodeManagementI)this.offering).getClientInboundPortURI();
	}
}
