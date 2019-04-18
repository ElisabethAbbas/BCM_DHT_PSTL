package fr.sorbonne_u.components.dht.components;

import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeManagementIbp;
import fr.sorbonne_u.components.dht.ports.NodeOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

public class Node extends AbstractComponent{
	protected String pred;
	protected String succ;
	protected int index;
	protected NodeOutboundPort nObpPred ;
	protected NodeOutboundPort nObpSucc ;
	protected NodeOutboundPort nObpStab ;
	protected NodeInboundPort nIbp ;
	protected NodeManagementIbp nMIbp ;
	protected String adminRIPURI ;
	//protected AdminOutboundPort	adminPort ;

	public Node(String nodeRIPURI, String adminRIPURI) throws Exception
	{
		super(nodeRIPURI,1, 1);//� voir combien de threads on va utiliser
		assert	adminRIPURI != null ;

		this.adminRIPURI = adminRIPURI ;

		this.addRequiredInterface(AdminRequiredI.class) ;
		this.addRequiredInterface(NodeRequiredI.class) ;
		this.addOfferedInterface(NodeOfferedI.class) ;
		this.addOfferedInterface(NodeManagementI.class) ;
		
		this.nObpPred = new NodeOutboundPort(this) ;
		this.addPort(this.nObpPred) ;
		this.nObpPred.localPublishPort() ;

		this.nObpSucc = new NodeOutboundPort(this) ;
		this.addPort(this.nObpSucc) ;
		this.nObpSucc.localPublishPort() ;
		
		this.nObpStab = new NodeOutboundPort(this) ;
		this.addPort(this.nObpStab) ;
		this.nObpStab.localPublishPort() ;

		this.nIbp = new NodeInboundPort(this) ;
		this.addPort(this.nIbp) ;
		this.nIbp.publishPort() ;
		
		this.nMIbp = new NodeManagementIbp(this) ;
		this.addPort(this.nMIbp) ;
		this.nMIbp.publishPort() ;

		this.tracer.setTitle("Node "+nodeRIPURI) ;
		this.tracer.setRelativePosition(1, 1) ;

		/*this.addRequiredInterface(AdminI.class) ;
		this.adminPort = new AdminOutboundPort(this) ;
		this.addPort(this.adminPort) ;
		this.adminPort.publishPort() ;*/
	}

	public void initialize() throws Exception {
	}

	public NodeManagementIbp getInboundPort() {
		return this.nMIbp;
	}

	public NodeOutboundPort getOutboundPort() {//TODO
		return this.nObpSucc;//TODO
	}

	public void setPred(String inboundPort, int n) throws Exception {
		this.logMessage("setting pred to "+n+"...");
		this.pred = inboundPort;

		this.doPortConnection(this.nObpPred.getPortURI(), inboundPort, NodeConnector.class.getCanonicalName());
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
	public NodeOutboundPort getNObpPred() {
		return nObpPred;
	}	
	public NodeOutboundPort getNObpSucc() {
		return nObpSucc;
	}


	public void			start() throws ComponentStartException
	{
		this.logMessage("starting node component.") ;
		super.start();
	}
	public void stab1() throws Exception {
		nObpSucc.stab2(this.nIbp);
	}
	public void stab2(NodeInboundPort startNode) throws Exception {
		this.doPortConnection(this.nObpStab.getPortURI(), startNode.getPortURI(), NodeConnector.class.getCanonicalName());
		this.nObpStab.stab3(this.pred, index);
		this.doPortDisconnection(this.nObpStab.getPortURI());
	}
	public void stab3(String predOfSucc, int succInd) throws Exception {
		this.doPortConnection(this.nObpStab.getPortURI(), predOfSucc, NodeConnector.class.getCanonicalName());
		this.nObpStab.stab4(this.nIbp, succInd, predOfSucc);
		this.doPortDisconnection(this.nObpStab.getPortURI());
	}
	public void stab4(NodeInboundPort startNode, int succInd, String predOfSucc) throws Exception {
		this.doPortConnection(this.nObpStab.getPortURI(), startNode.getPortURI(), NodeConnector.class.getCanonicalName());
		this.nObpStab.stab5(index, succInd, predOfSucc);
		this.doPortDisconnection(this.nObpStab.getPortURI());
	}
	public void stab5(int succPredInd, int succInd, String predOfSucc) throws Exception {
		if(index != succPredInd && succPredInd > index && succPredInd < succInd) {
			this.doPortDisconnection(this.nObpSucc.getPortURI());
			this.doPortConnection(this.nObpSucc.getPortURI(), predOfSucc, NodeConnector.class.getCanonicalName());
			this.succ = predOfSucc;
			//this.nObpSucc.notifyPred(this.nIbp);
		}
	}
	
	/*
	public void			execute() throws Exception
	{
		super.execute() ;
		
		//  exécution de la stabilisation toutes les 3 secondes
		this.scheduleTaskWithFixedDelay(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							((Node)this.getOwner()).stab1();
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 0, 3000, // délai entre la fin d'une exécution et la suivante, à modifier 
				, TimeUnit.MILLISECONDS) ;

		this.traceMessage("La node "+index+" fait la stabilisation.\n") ;
	}


	public void stab1() {
		// rajouter le doPortConnexion et déconnexion !!

		// rajouter le doPortConnexion !!
		this.doPortConnection(
				this.nObp.getPortURI(),
				this.adminRIPURI,
				ReflectionConnector.class.getCanonicalName());

		
		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							((Node)this.getOwner()).nObpSucc.stab2((Node)this.getOwner());
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 0, 
				TimeUnit.MILLISECONDS) ;
	}
	
	public void stab2(Node n) {
		// rajouter le doPortConnexion et déconnexion  !!

		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							NodeOutboundPort nobp = ((Node)this.getOwner()).nObpPred;
							if(nobp!=null) // ?? ou inutile? 
								nobp.stab3(n);
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 0, 
				TimeUnit.MILLISECONDS) ;
	}

	public void stab3(Node n) {
		// rajouter le doPortConnexion et déconnexion !!

		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							if(((Node)this.getOwner()).getIndex()>n.getIndex())
								n.nObpSucc.stab4((Node)this.getOwner());
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 0,  
				TimeUnit.MILLISECONDS) ;
	}
	
	public void stab4(Node succ) {
		// rajouter le doPortConnexion et déconnexion  !!

		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							if(((Node)this.getOwner()).getIndex()<succ.getIndex())
								((Node)this.getOwner()).setSucc(, n);
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 0,  
				TimeUnit.MILLISECONDS) ;
	}*/

	
/*	public void stabilisation(){//called periodically --> stabilisation
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
		// déconnexion des outboundPort : 
		//this.adminPort.doDisconnection() ;
		this.nObpPred.doDisconnection() ;
		this.nObpSucc.doDisconnection() ;
		//this.nObpStab.doDisconnection() ;
		
		super.finalise();
	}

	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			//this.adminPort.unpublishPort();
			this.nObpPred.unpublishPort();
			this.nObpSucc.unpublishPort() ;
			this.nIbp.unpublishPort();
			this.nMIbp.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
	}
}
