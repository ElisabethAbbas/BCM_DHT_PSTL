package fr.sorbonne_u.components.dht.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;

public class NodeConnector  extends		AbstractConnector
implements	NodeOfferedI
{

	@Override
	public void setPred(String s, int n) throws Exception {
		System.out.println("setting pred - connector");
		 ((NodeOfferedI)this.offering).setPred(s,n);
		
	}

	@Override
	public void setSucc(String s, int n) throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).setSucc(s,n);
	}

	@Override
	public void setIndex(int i) throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).setIndex(i);
	}

	@Override
	public String getPred() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeOfferedI)this.offering).getPred();
	}

	@Override
	public String getSucc() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeOfferedI)this.offering).getSucc();
	}

	@Override
	public int getIndex() throws Exception {
		// TODO Auto-generated method stub
		return ((NodeOfferedI)this.offering).getIndex();
	}

	@Override
	public void stab1() throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).stab1();
	}

	@Override
	public void stab2(NodeInboundPort startNode) throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).stab2(startNode);
	}

	@Override
	public void stab3(String predOfSucc, int succInd) throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).stab3(predOfSucc, succInd);
	}

	@Override
	public void stab4(NodeInboundPort startNode, int succInd, String predOfSucc) throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).stab4(startNode, succInd, predOfSucc);
	}

	@Override
	public void stab5(int succPredInd, int succInd, String predOfSucc) throws Exception {
		// TODO Auto-generated method stub
		((NodeOfferedI)this.offering).stab5(succInd, succInd, predOfSucc);
	}

}
