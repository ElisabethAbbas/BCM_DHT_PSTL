package fr.sorbonne_u.components.dht.components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.AdminI;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeDataOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeOutboundPort;
import fr.sorbonne_u.components.examples.pingpong.interfaces.PingPongI;
import fr.sorbonne_u.components.examples.reflection.connectors.MyServiceConnector;
import fr.sorbonne_u.components.examples.reflection.interfaces.MyServiceI;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;

public class Node extends AbstractComponent{
	protected String pred;
	protected String succ;
	protected int index;
	protected NodeOutboundPort nObp ;
	protected NodeInboundPort nIbp ;
	protected String adminRIPURI ;
	protected AdminOutboundPort	adminPort ;
	
	public Node(String nodeRIPURI, String adminRIPURI) throws Exception
	{
		super(nodeRIPURI,1, 1);//� voir combien de threads on va utiliser
		assert	adminRIPURI != null ;

		this.adminRIPURI = adminRIPURI ;
		this.addRequiredInterface(AdminRequiredI.class) ;
		this.addRequiredInterface(PingPongI.class) ;
		this.addOfferedInterface(PingPongI.class) ;
		
		this.nObp = new NodeOutboundPort(this) ;
		this.addPort(this.nObp) ;
		this.nObp.localPublishPort() ;
		
		this.nIbp = new NodeInboundPort(this) ;
		this.addPort(this.nIbp) ;
		this.nIbp.publishPort() ;
		
		
		this.tracer.setTitle("Node "+nodeRIPURI) ;
		this.tracer.setRelativePosition(1, 1) ;

		/*this.addRequiredInterface(AdminI.class) ;
		this.adminPort = new AdminOutboundPort(this) ;
		this.addPort(this.adminPort) ;
		this.adminPort.publishPort() ;*/
	}
	
	public void initialize() throws Exception {
		
	}
	
	public NodeInboundPort getInboundPort() {
		return this.nIbp;
	}
	
	public NodeOutboundPort getOutboundPort() {
		return this.nObp;
	}
	
	public boolean setPred(String inboundPort) throws Exception {
		this.pred = inboundPort;
		return true;
	}
	
	public boolean setSucc(String inboundPort) throws Exception {
		this.succ = inboundPort;
		return true;

	}
	
	public boolean setIndex(int index)throws Exception {
		this.index = index;
		return true;
	}
	public String getPred() {
		return pred;
	}
	public String getSucc() {
		return pred;
	}
	public int getIndex() {
		return index;
	}
	
	public void			start() throws ComponentStartException
	{
		this.logMessage("starting node component.") ;
		super.start();
	}
	/*@Override
	public void			execute() throws Exception
	{
		super.execute() ;

		this.doPortConnection(
					this.nObp.getPortURI(),
					this.adminRIPURI,
					ReflectionConnector.class.getCanonicalName());
		String[] uris =
				((ComponentI) this.nObp).findInboundPortURIsFromInterface(
													AdminI.class) ;
		this.doPortConnection(
					this.adminPort.getPortURI(),
					uris[0],
					MyServiceConnector.class.getCanonicalName()) ;

		System.out.println("-------------------------------------------") ;
		this.adminPort.test() ;
		System.out.println("-------------------------------------------") ;
	}*/

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void			finalise() throws Exception
	{
		this.adminPort.doDisconnection() ;
		this.adminPort.unpublishPort() ;
		this.nObp.doDisconnection() ;
		this.nObp.unpublishPort() ;

		super.finalise();
	}



}
