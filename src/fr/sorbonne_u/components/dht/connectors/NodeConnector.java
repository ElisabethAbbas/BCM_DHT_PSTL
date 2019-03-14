package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeI;

public class NodeConnector  extends		AbstractConnector
implements	NodeI
{

	@Override
	public boolean setPred(String s) throws Exception {
		 return ((NodeI)this.offering).setPred(s);
		
	}

	@Override
	public boolean setSucc(String s) throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).setSucc(s);
	}

	@Override
	public boolean setIndex(int i) throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeI)this.offering).getIndex();
	}

}
