package fr.sorbonne_u.components.dht.components;

import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeDataOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeOutboundPort;
import fr.sorbonne_u.components.examples.pingpong.components.PingPongPlayer;
import fr.sorbonne_u.components.examples.pingpong.interfaces.PingPongI;
import fr.sorbonne_u.components.examples.pingpong.ports.PingPongOutboundPort;
import fr.sorbonne_u.components.examples.reflection.connectors.MyServiceConnector;
import fr.sorbonne_u.components.examples.reflection.interfaces.MyServiceI;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;

public class Node extends AbstractComponent{
	protected String pred;
	protected String succ;
	protected int index;
	protected NodeOutboundPort nObpPred ;
	protected NodeOutboundPort nObpSucc ;
	protected NodeInboundPort nIbp ;
	protected String adminRIPURI ;
	protected AdminOutboundPort	adminPort ;

	public Node(String nodeRIPURI, String adminRIPURI) throws Exception
	{
		super(nodeRIPURI,1, 1);//� voir combien de threads on va utiliser
		assert	adminRIPURI != null ;

		this.adminRIPURI = adminRIPURI ;

		this.addRequiredInterface(AdminRequiredI.class) ;
		this.addRequiredInterface(NodeRequiredI.class) ;
		this.addOfferedInterface(NodeOfferedI.class) ;
		
		this.nObpPred = new NodeOutboundPort(this) ;
		this.addPort(this.nObpPred) ;
		this.nObpPred.localPublishPort() ;

		this.nObpSucc = new NodeOutboundPort(this) ;
		this.addPort(this.nObpSucc) ;
		this.nObpSucc.localPublishPort() ;

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

	public NodeOutboundPort getOutboundPort() {//TODO
		return this.nObpSucc;//TODO
	}

	public void setPred(String inboundPort, int n) throws Exception {
		this.logMessage("setting pred to "+n+"...");
		this.pred = inboundPort;

		//this.doPortConnection(this.nObpPred.getPortURI(), inboundPort, NodeConnector.class.getCanonicalName());
	}

	public void setSucc(String inboundPort, int n) throws Exception {
		this.logMessage("setting succ to "+n+"...");
		this.succ = inboundPort;
		this.doPortConnection(this.nObpSucc.getPortURI(), inboundPort, NodeConnector.class.getCanonicalName());

	}
	public void connect(String port) throws Exception {//not used
		//this.doPortConnection(this.nObp.getPortURI(), port, NodeConnector.class.getCanonicalName());
	}
	public void setIndex(int index)throws Exception {
		this.index = index;
	}
	public String getPred() {
		return pred;
	}
	public String getSucc() {
		return succ;
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

		if(index==1)
			Thread.sleep(5000L);

		this.traceMessage("La node "+index+" fait la stabilisation.\n") ;
		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							((Node)this.getOwner()).
							nObpSucc.execute() ;
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				},
				100L, TimeUnit.MILLISECONDS) ;
	}
	
	public void stabilisation(){//called periodically --> stabilisation
		String uriPred = nObpSucc.getPred();
		if((v!=this)&&((v.getIndex()>index)&&(v.getIndex()<succ.getIndex()))){//remplacer v!=this par this.equals(v)
			succ=v;
		}
		succ.notifyPred(this);
	}
	public void notifyPred(DHT_node n){
		if((pred==null)||((n.getIndex()<index)&&(n.getIndex()>pred.getIndex()))){
			pred=n;
		}
	}*/


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
		this.nObpPred.doDisconnection() ;
		this.nObpSucc.doDisconnection() ;
		this.nIbp.doDisconnection();
		
		super.finalise();
	}

	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			this.nObpPred.unpublishPort();
			this.nObpSucc.unpublishPort() ;
			this.adminPort.unpublishPort();
			this.nIbp.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
	}
}
