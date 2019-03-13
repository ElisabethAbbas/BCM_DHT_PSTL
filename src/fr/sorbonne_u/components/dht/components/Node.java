package fr.sorbonne_u.components.dht.components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.dht.interfaces.AdminI;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeDataOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeOutboundPort;
import fr.sorbonne_u.components.examples.reflection.connectors.MyServiceConnector;
import fr.sorbonne_u.components.examples.reflection.interfaces.MyServiceI;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;

public class Node extends AbstractComponent{
	protected NodeDataOutboundPort dataOutboundPort;
	protected String pred;
	protected String succ;
	protected NodeOutboundPort nObp ;
	protected String adminRIPURI ;
	protected AdminOutboundPort	adminPort ;
	
	public void initialize() throws Exception {
		this.dataOutboundPort = new NodeDataOutboundPort(this);
	}
	
	public NodeDataOutboundPort getDataOutboundPort() {
		return this.dataOutboundPort;
	}
	
	public void setPred(String inboundPort) throws Exception {
		this.pred = inboundPort;
	}
	
	public void setSucc(String inboundPort) throws Exception {
		this.succ = inboundPort;

	}
	
	public Node(String adminRIPURI) throws Exception
	{
		super(1, 1);//� voir combien de threads on va utiliser
		assert	adminRIPURI != null ;

		this.adminRIPURI = adminRIPURI ;
		this.addRequiredInterface(NodeI.class) ;
		this.nObp = new NodeOutboundPort(this) ;
		this.addPort(this.nObp) ;
		this.nObp.publishPort() ;

		this.addRequiredInterface(AdminI.class) ;
		this.adminPort = new AdminOutboundPort(this) ;
		this.addPort(this.adminPort) ;
		this.adminPort.publishPort() ;
	}

	@Override
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
	}

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
