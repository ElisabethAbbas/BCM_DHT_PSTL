package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeI;

public class NodeConnector  extends		AbstractConnector
implements	NodeI
{

	@Override
	public void setPred(String s) {
		((NodeI)this.offering).setPred(s);
		
	}

	@Override
	public void setSucc(String s) {
		// TODO Auto-generated method stub
		((NodeI)this.offering).setSucc(s);
	}

	@Override
	public void setIndex(int i) {
		// TODO Auto-generated method stub
		((NodeI)this.offering).setIndex(i);
	}

	@Override
	public String getPred() {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).getPred();
	}

	@Override
	public String getSucc() {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).getSucc();
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).getIndex();
	}

}
