package fr.sorbonne_u.components.dht.components;

import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.ports.NodeClientIbp;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeManagementIbp;
import fr.sorbonne_u.components.dht.ports.NodeOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

public class Node extends AbstractComponent{
	protected String pred;
	protected String succ;
	protected int predInd;
	protected int succInd;
	protected int index;
	protected NodeOutboundPort nObpPred ;
	protected NodeOutboundPort nObpSucc ;
	protected NodeOutboundPort nObpStab ;
	protected NodeInboundPort nIbp ;
	protected NodeManagementIbp nMIbp ;
	protected NodeClientIbp nClientIbp ;
	protected String adminRIPURI ;
	//protected AdminOutboundPort	adminPort ;

	public Node(String nodeRIPURI, String adminRIPURI, int index) throws Exception
	{
		
		super(nodeRIPURI,1, 1);//� voir combien de threads on va utiliser
		System.out.println("NODE BEIND CREATED...");
		assert	adminRIPURI != null ;
		this.index = index;
		this.adminRIPURI = adminRIPURI ;
		this.addRequiredInterface(AdminRequiredI.class) ;
		this.addRequiredInterface(NodeRequiredI.class) ;
		this.addOfferedInterface(NodeOfferedI.class) ;
		this.addOfferedInterface(NodeManagementI.class) ;
		this.addRequiredInterface(NodeManagementI.class) ;
		this.addOfferedInterface(NodeClientIbp.class) ;
		this.addRequiredInterface(NodeClientIbp.class) ;
		
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
		
		this.nClientIbp = new NodeClientIbp(this) ;
		this.addPort(this.nClientIbp) ;
		this.nClientIbp.publishPort() ;

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
	
	public String getClientInboundPortURI()throws Exception{
		return this.nClientIbp.getPortURI();
	}
	
	public String getInboundPortURI() throws Exception {
		return this.nIbp.getPortURI();
	}

	public NodeOutboundPort getOutboundPort() {
		return this.nObpSucc;
	}

	public void setPred(String inboundPort, int n) throws Exception {
		this.logMessage("setting pred to "+n+"...");
		this.pred = inboundPort;
		this.predInd = n;
		if(nObpPred.connected()) {
			this.doPortDisconnection(this.nObpPred.getPortURI());
		}
		this.doPortConnection(this.nObpPred.getPortURI(), inboundPort, NodeConnector.class.getCanonicalName());
	}

	public void setSucc(String inboundPort, int n) throws Exception {
		this.logMessage("setting succ to "+n+"...");
		this.succ = inboundPort;
		this.succInd = n;
		if(nObpSucc.connected()) {
			this.doPortDisconnection(this.nObpSucc.getPortURI());
		}
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
				}, 3000, 3000 // délai entre la fin d'une exécution et la suivante, à modifier 
				, TimeUnit.MILLISECONDS) ;

	}
	public void stab1() throws Exception {
		if(this.succ != null) {
			this.logMessage("stabilisation start...");
			nObpSucc.stab2(this.nIbp);
		}
		else {
			this.logMessage("no successor defined...");
		}
	}
	public void stab2(NodeInboundPort startNode) throws Exception {
		//this.logMessage("stabilisation 2...");
		if(this.nObpStab.connected())
			this.doPortDisconnection(this.nObpStab.getPortURI());
		this.doPortConnection(this.nObpStab.getPortURI(), startNode.getPortURI(), NodeConnector.class.getCanonicalName());
		//this.logMessage("index : "+this.index+" predInd : " +this.predInd );
		this.nObpStab.stab3(this.predInd, this.index, this.pred);
	}
	
	public void stab3(int predOfSuccInd, int succInd, String predOfSucc) throws Exception {
		if(this.index != predOfSuccInd && predOfSuccInd > this.index && predOfSuccInd < succInd) {
			this.setSucc(predOfSucc, predOfSuccInd);
		}
		
		this.logMessage("stabilisation end... ");
		this.nObpSucc.notifyPred1(this.index, this.nIbp.getPortURI());
		
	}
	public void notifyPred1(int notifierIndex, String notifierIbpURI) throws Exception {
		if(this.pred == null)
			this.setPred(notifierIbpURI, notifierIndex);
		else{
			if(notifierIndex < this.index)
				this.nObpPred.notifyPred2(notifierIndex, notifierIbpURI, this.nIbp.getPortURI());
		}
	}
	public void notifyPred2(int notifierIndex, String notifierIbpURI, String notifiedIbpURI) throws Exception {
		if(this.nObpStab.connected())
			this.doPortDisconnection(this.nObpStab.getPortURI());
		this.doPortConnection(this.nObpStab.getPortURI(), notifiedIbpURI, NodeConnector.class.getCanonicalName());
		this.nObpStab.notifyPred3(notifierIndex, notifierIbpURI, this.index);
	}
	public void notifyPred3(int notifierIndex, String notifierIbpURI, int predInd) throws Exception {
		if(notifierIndex > predInd)
		{
			this.setPred(notifierIbpURI, notifierIndex);
		}
	}
	
	public void addComponent(String componentURI) throws Exception {
		//TODO : + créer la structure dans laquelle on sauvegarde les composants, voire trouver la node ou add le component
	}
	
	public void			execute() throws Exception
	{
		super.execute() ;
		
	
			
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
