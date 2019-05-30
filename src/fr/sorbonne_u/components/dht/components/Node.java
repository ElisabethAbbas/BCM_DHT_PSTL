package fr.sorbonne_u.components.dht.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.dht.connectors.NodeConnector;
import fr.sorbonne_u.components.dht.connectors.NodeToClientConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeClientI;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.interfaces.NodeOfferedI;
import fr.sorbonne_u.components.dht.interfaces.NodeRequiredI;
import fr.sorbonne_u.components.dht.ports.NodeClientIbp;
import fr.sorbonne_u.components.dht.ports.NodeInboundPort;
import fr.sorbonne_u.components.dht.ports.NodeManagementIbp;
import fr.sorbonne_u.components.dht.ports.NodeOutboundPort;
import fr.sorbonne_u.components.dht.ports.NodeToClientObp;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

public class Node extends AbstractComponent{
	protected String pred;
	protected String succ;
	protected int predInd;
	protected int succInd;
	protected int index;
	protected int next;
	protected NodeToClientObp towardsClient;
	protected NodeOutboundPort nObpPred ;
	protected NodeOutboundPort nObpSucc ;
	protected NodeOutboundPort nObpStab ;
	protected NodeOutboundPort nObpFingers ;
	protected NodeInboundPort nIbp ;
	protected NodeManagementIbp nMIbp ;
	protected NodeClientIbp nClientIbp ;
	protected String adminRIPURI ;
	protected Map<Integer, String> components ;
	protected List<Integer> fingerInd ;
	protected HashMap<Integer, String> fingerIbpFromInd;
	protected HashMap<Integer, String> successorsList;
	protected int size;
	
	//protected AdminOutboundPort	adminPort ;

	public Node(String nodeRIPURI, String adminRIPURI, int index, int size) throws Exception
	{
		super(nodeRIPURI, 1, 1);
		assert	adminRIPURI != null ;
		this.index = index;
		this.next=0;
		this.predInd=-1;
		this.succInd=-1;

		this.adminRIPURI = adminRIPURI ;
		this.addRequiredInterface(AdminRequiredI.class) ;
		this.addRequiredInterface(NodeRequiredI.class) ;
		this.addOfferedInterface(NodeOfferedI.class) ;
		this.addOfferedInterface(NodeManagementI.class) ;
		this.addRequiredInterface(NodeManagementI.class) ;
		this.addOfferedInterface(NodeClientI.class) ;
		this.addRequiredInterface(NodeClientI.class) ;

		this.nObpPred = new NodeOutboundPort(this) ;
		this.addPort(this.nObpPred) ;
		this.nObpPred.localPublishPort() ;

		this.nObpSucc = new NodeOutboundPort(this) ;
		this.addPort(this.nObpSucc) ;
		this.nObpSucc.localPublishPort() ;

		this.towardsClient = new NodeToClientObp(this) ;
		this.addPort(this.towardsClient) ;
		this.towardsClient.localPublishPort() ;

		this.nObpStab = new NodeOutboundPort(this) ;
		this.addPort(this.nObpStab) ;
		this.nObpStab.localPublishPort() ;

		this.nIbp = new NodeInboundPort(this) ;
		this.addPort(this.nIbp) ;
		this.nIbp.publishPort() ;
		System.out.println("test : "+ this.nIbp.getPortURI());

		this.nMIbp = new NodeManagementIbp(this) ;
		this.addPort(this.nMIbp) ;
		this.nMIbp.publishPort() ;

		this.nClientIbp = new NodeClientIbp(this) ;
		this.addPort(this.nClientIbp) ;
		this.nClientIbp.publishPort() ;

		this.components = new HashMap<Integer, String>();
		this.size = size;

		this.tracer.setTitle("Node "+nodeRIPURI) ;
		this.tracer.setRelativePosition(1, 1) ;

		this.nObpFingers = new NodeOutboundPort(this) ;
		this.addPort(this.nObpFingers) ;
		this.nObpFingers.localPublishPort() ;

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

	public void setFingers(List<Integer> fingerInd ,HashMap<Integer, String> fingerIbpFromInd) throws Exception {
		this.fingerInd = fingerInd;
		this.fingerIbpFromInd = fingerIbpFromInd;
	}

	public void setPred(String inboundPort, int n) throws Exception {
		this.logMessage("setting pred to "+n+"...");
		this.pred = inboundPort;
		this.predInd = n;
		if(nObpPred.connected()) {
			this.doPortDisconnection(this.nObpPred.getPortURI());
		}
		this.doPortConnection(this.nObpPred.getPortURI(), inboundPort, NodeConnector.class.getCanonicalName());
		if(nObpPred.connected()) {
			this.logMessage("connexion successfull");
		}
		else {
			this.logMessage("connexion not successfull");
		}
	}

	public void setSucc(String inboundPort, int n) throws Exception {
		this.logMessage("setting succ to "+n+"...");
		this.succ = inboundPort;
		this.succInd = n;
		if(nObpSucc.connected()) {
			this.doPortDisconnection(this.nObpSucc.getPortURI());
		}
		this.doPortConnection(this.nObpSucc.getPortURI(), inboundPort, NodeConnector.class.getCanonicalName());
		if(nObpSucc.connected()) {
			this.logMessage("connexion successfull");
		}
		else {
			this.logMessage("connexion not successfull");
		}
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
				}, 10000, 3000 // délai entre la fin d'une exécution et la suivante, à modifier 
				, TimeUnit.MILLISECONDS) ;

		this.logMessage("starting fixFingers().") ;
		// exécution de fixFingers() toutes les 3 secondes
		this.scheduleTaskWithFixedDelay(		
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							//System.out.println("start - index : "+((Node)this.getOwner()).getIndex());
							//System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
							((Node)this.getOwner()).fixFingers1();
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 4000, 10000 // délai entre la fin d'une exécution et la suivante, à modifier 
				, TimeUnit.MILLISECONDS) ;

	}
	public void stab1() throws Exception {
		checkSucc();
		checkPred();
		if(this.succ != null) {
			nObpSucc.stab2(this.nIbp.getPortURI());
			this.logMessage("stabilisation started... ");
		}
		else {
			this.logMessage("no successor defined...");
		}
	}
	public void stab2(String startNode) throws Exception {
		//this.logMessage("stabilisation 2...");
		if(this.nObpStab.connected())
			this.doPortDisconnection(this.nObpStab.getPortURI());
		this.doPortConnection(this.nObpStab.getPortURI(), startNode, NodeConnector.class.getCanonicalName());
		//this.logMessage("index : "+this.index+" predInd : " +this.predInd );
		this.nObpStab.stab3(this.predInd, this.index, this.pred);

	}

	public void stab3(int predOfSuccInd, int succInd, String predOfSucc) throws Exception {
		
		if(this.index != predOfSuccInd && predOfSuccInd > this.index && predOfSuccInd < succInd) {
			this.setSucc(predOfSucc, predOfSuccInd);
		}

		this.logMessage("stabilisation end... ");
		this.nObpSucc.notifyPred1(this.index, this.nIbp.getPortURI());
		this.nObpSucc.initiateUpdateSuccessorList();
		
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
			for(int ind : components.keySet()) {
				if(notifierIndex >= ind) {
					nObpPred.store(components.get(ind));
					components.remove(ind);
				}
			}
		}
		
	}

	public void initiateUpdateSuccessorList() throws Exception {
		synchronized(this) {
			successorsList = new HashMap<Integer, String>();
			nObpSucc.updateSuccessorList(this.nIbp.getPortURI(),(int)Math.floor(Math.log(size)));
		}
	}

	public void updateSuccessorList(String askingNodeIbpURI, int successorsToVisit) throws Exception {
		if(successorsToVisit > 0)
			nObpSucc.updateSuccessorList(askingNodeIbpURI, successorsToVisit - 1);

		if(this.nObpStab.connected())
			this.doPortDisconnection(this.nObpStab.getPortURI());
		this.doPortConnection(this.nObpStab.getPortURI(), askingNodeIbpURI, NodeConnector.class.getCanonicalName());
		this.nObpStab.receiveUpdateSuccessorList(this.nIbp.getPortURI(), this.index);
	}

	public void receiveUpdateSuccessorList(String succIbpURI, int succIndex) throws Exception {
		if(succIndex != this.succInd)
			successorsList.put(succIndex, succIbpURI);
	}

	public void checkPred() throws Exception {
		if(!nObpPred.connected()) {
			this.logMessage("pred failed in node " + this.index);
			this.pred = null;
			this.predInd = -1;
		}
	}

	public void checkSucc() throws Exception {
		if (!nObpSucc.connected()) {
			this.logMessage("succ failed in node " + this.index);
			succInd = -1;
			setNewSuccessor();
		}
	}

	public void nodeJoined() throws Exception {
		initiateUpdateSuccessorList();
	}

	public void setNewSuccessor() throws Exception {
		if(successorsList.size() > 0) {
			ArrayList<Integer> successorsIndexes = new ArrayList<Integer>(successorsList.keySet());
			Collections.sort(successorsIndexes);
			int tmp_index = 0;
			while(tmp_index < successorsIndexes.size() && successorsIndexes.get(tmp_index) < this.index)
				tmp_index++;
			if(tmp_index == successorsIndexes.size())
				setSucc(successorsList.get(successorsIndexes.get(0)), successorsIndexes.get(0));
			else
				setSucc(successorsList.get(successorsIndexes.get(tmp_index)), successorsIndexes.get(tmp_index));
		}
	}

	public int hashFunction(String s) {
		return s.hashCode()%size;
	}

	public void store( String s) throws Exception {
		components.put(hashFunction(s), s);
	}

	public String retrieve( int id) throws Exception {//not used
		if(components.containsKey(id))
			return components.get(id);
		else
			return null;
	}

	public void put(int id, String value) throws Exception {
		if (predInd != -1 && id > predInd && predInd <= index) 
			store(value);
		else if (id > index && id <= succInd) {
			try {
				nObpSucc.store(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else { // forward the query around the circle
			//nObpSucc.put(id, value);
			if(this.nObpStab.connected())
				this.doPortDisconnection(this.nObpStab.getPortURI());
			this.doPortConnection(this.nObpStab.getPortURI(), fingerIbpFromInd.get(fingerInd.indexOf(closestPrecedingNode(id))), NodeConnector.class.getCanonicalName());
			this.nObpStab.put(id,  value);
		}
	}

	public void get(String ClientIbpURI, int id) throws Exception {
		if (predInd != -1 && id > predInd && predInd <= index) 
			connectAndSendToClient( ClientIbpURI, id);
		else if (id > index && id <= succInd) {
			try {
				nObpSucc.connectAndSendToClient( ClientIbpURI, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else { // forward the query around the circle
			//nObpSucc.get(ClientIbpURI, id);
			if(this.nObpStab.connected())
				this.doPortDisconnection(this.nObpStab.getPortURI());
			this.doPortConnection(this.nObpStab.getPortURI(), fingerIbpFromInd.get(fingerInd.indexOf(closestPrecedingNode(id))), NodeConnector.class.getCanonicalName());
			nObpStab.get(ClientIbpURI, id);
		}
	}

	public void connectAndSendToClient(String ClientIbpURI, int id) throws Exception {
		if(components.containsKey(id)) {
			if(this.towardsClient.connected())
				this.doPortDisconnection(this.towardsClient.getPortURI());
			this.doPortConnection(this.towardsClient.getPortURI(), ClientIbpURI, NodeToClientConnector.class.getCanonicalName());
			this.towardsClient.receiveResultOfGet(components.get(id));
		}
	}

	public void			execute() throws Exception
	{
		super.execute() ;
	}

	// lookup
	public void findSuccessor(String ClientIbpURI, int id) throws Exception {
		synchronized(this) {
			
			// initialisation des variables de tests
			int indextmp, idtmp, succIndtmp;
						
			if(predInd>index) {
				if (predInd>id)
					idtmp=id+size;
				else
					idtmp=id;
				indextmp=index+size;
				succIndtmp=succInd+size;

			}
			else {
				indextmp=index;
				
				if(id<index)
					idtmp=id+size;
				else
					idtmp=id;

				if(succInd<index)
					succIndtmp=succInd+size;
				else
					succIndtmp=succInd;
			}
			if (predInd != -1 && idtmp > predInd && idtmp <= indextmp)
				connectAndSendToClient( ClientIbpURI, id);
			else if (idtmp > indextmp && idtmp <= succIndtmp) {
			try {
					if(this.nObpSucc.connected()) 
						this.doPortDisconnection(this.nObpSucc.getPortURI());
					this.doPortConnection(this.nObpSucc.getPortURI(), fingerIbpFromInd.get(fingerInd.indexOf(closestPrecedingNode(id))), NodeConnector.class.getCanonicalName());
					nObpSucc.connectAndSendToClient( ClientIbpURI, id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else { // forward the query around the circle
				if(this.nObpStab.connected())
					this.doPortDisconnection(this.nObpStab.getPortURI());
				this.doPortConnection(this.nObpStab.getPortURI(), fingerIbpFromInd.get(fingerInd.indexOf(closestPrecedingNode(id))), NodeConnector.class.getCanonicalName());
				this.nObpStab.findSuccessor(ClientIbpURI, id);
			}
		}
	}
	
	public int closestPrecedingNode(int id) {
		for(int i = fingerInd.size() - 1 ; i >= 0 ; i--) { 
			/*System.out.print(i+"="+fingerInd.get(i)+" ");
			System.out.flush();*/

			if(id > index){
				if ((fingerInd.get(i) > index && fingerInd.get(i) < id))
					return fingerInd.get(i);
			}
			else if(id < index){
				if ((fingerInd.get(i) > index && fingerInd.get(i) < id+size) 
						|| (fingerInd.get(i) > index-size && fingerInd.get(i) < id))
					return fingerInd.get(i);
			}
		}
		
		//System.out.println();
		return index;
	}
	
	// finger
	public void fixFing1() throws Exception{
		this.logMessage("fixFing1()") ;
		next = next+1;
		if (next > fingerInd.size()-1) 
			next = 0;
		
		int id = (this.index + (int)Math.pow(2, next))%size;
		
		if(id == this.index) {
			fingerInd.set(next, this.index);
			fingerIbpFromInd.put(this.index,  this.nIbp.getPortURI());
		}
		
		else {
			this.nObpSucc.fixFing2(this.nIbp.getPortURI(), this.index, id, next);
		}
	}
	
	public void fixFing2(String senderIbp, int lastId, int id, int indice) throws Exception{
		if((id > lastId) && (id < this.index)) {
			if(this.nObpStab.connected())
				this.doPortDisconnection(this.nObpStab.getPortURI());
			this.doPortConnection(this.nObpStab.getPortURI(), senderIbp, NodeConnector.class.getCanonicalName());
			this.nObpStab.updateFing(indice, this.index, this.nIbp.getPortURI());
		}else {
			this.nObpSucc.fixFing2(senderIbp, this.index, id, indice);
		}
	}
	
	public void updateFing(int indice, int res, String resUri) throws Exception{
		this.logMessage("updating fing " + indice + " succ to : "+res) ;
		fingerInd.set(indice, res);
		fingerIbpFromInd.put(res, resUri);
	}

	public void fixFingers1() {
		synchronized(this) {
			// initialisation des variables de tests
			int indextmp, idtmp, succIndtmp;
						
			this.logMessage("fixFingers1()") ;
			
			next = next+1;
			if (next > fingerInd.size()-1) 
				next = 0;

			int id = (index + (1<<(next)))%size;

			if(predInd>index) {
				if (predInd>id)
					idtmp=id+size;
				else
					idtmp=id;
				indextmp=index+size;
				succIndtmp=succInd+size;

			}
			else {
				indextmp=index;
				
				if(id<index)
					idtmp=id+size;
				else
					idtmp=id;

				if(succInd<index)
					succIndtmp=succInd+size;
				else
					succIndtmp=succInd;
			}
			if ((predInd != -1 && idtmp > predInd && idtmp <= indextmp)) {
				fingerInd.set(next, index);
				if(fingerIbpFromInd.containsKey(next)) {
					try {
						fingerIbpFromInd.replace(next, nIbp.getPortURI());
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println("On en est au noeud "+index + " on a mis à jour next: " +next);
					System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
				}
				else {
					try {
						fingerIbpFromInd.put(next, nIbp.getPortURI());
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println("On en est au noeud "+index + " on a mis à jour next: " +next);
					System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
				}
			}
			else if (idtmp > indextmp && idtmp <= succIndtmp) {
				try {
					nObpSucc.fixFingers2(nIbp.getPortURI());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else { // forward the query around the circle	
				try {
					if(this.nObpFingers.connected())
						this.doPortDisconnection(this.nObpFingers.getPortURI());
					int noeud_avant_id=closestPrecedingNode(id);
					System.out.println(index+" (index), id="+id+" on va au closestPrecedingNode : " + noeud_avant_id);
					this.doPortConnection(this.nObpFingers.getPortURI(), fingerIbpFromInd.get(fingerInd.indexOf(noeud_avant_id)), NodeConnector.class.getCanonicalName());
					this.nObpFingers.fixFingers4(nIbp.getPortURI(), id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void fixFingers4(String inbpURI, int id) {
		synchronized(this) {
			this.logMessage("fixFingers4()") ;
			
			// initialisation des variables de tests
			int indextmp, idtmp, succIndtmp;
						
			if(predInd>index) {
				if (predInd>id)
					idtmp=id+size;
				else
					idtmp=id;
				indextmp=index+size;
				succIndtmp=succInd+size;

			}
			else {
				indextmp=index;
				
				if(id<index)
					idtmp=id+size;
				else
					idtmp=id;

				if(succInd<index)
					succIndtmp=succInd+size;
				else
					succIndtmp=succInd;
			}			
			
			if (predInd != -1 && id > predInd && id <= indextmp) {	
				try {
					if(this.nObpFingers.connected())
						this.doPortDisconnection(this.nObpFingers.getPortURI());
					this.doPortConnection(this.nObpFingers.getPortURI(), inbpURI, NodeConnector.class.getCanonicalName());
	
					nObpFingers.fixFingers5(nIbp.getPortURI(), index);
	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			else if (idtmp > indextmp && idtmp <= succIndtmp) {
				try {
					nObpSucc.fixFingers2(inbpURI);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else { // forward the query around the circle	
				try {
					if(this.nObpFingers.connected())
						this.doPortDisconnection(this.nObpFingers.getPortURI());
					int noeud_avant_id=closestPrecedingNode(id);
					System.out.println(index+" (index), id="+id+" on va au closestPrecedingNode : " + noeud_avant_id);
					this.doPortConnection(this.nObpFingers.getPortURI(), fingerIbpFromInd.get(fingerInd.indexOf(noeud_avant_id)), NodeConnector.class.getCanonicalName());
					this.nObpFingers.fixFingers4(inbpURI, id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void fixFingers5(String inbpURI, int index) {
		synchronized(this) {
			this.logMessage("fixFingers5()") ;
			fingerInd.set(next, index);
			if(fingerIbpFromInd.containsKey(next)) {
				try {
					fingerIbpFromInd.replace(next, inbpURI);
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("On en est au noeud "+index + " on a mis à jour next: " +next);
				System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
			}
			else {
				try {
					fingerIbpFromInd.put(next, inbpURI);
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("On en est au noeud "+index + " on a mis à jour next: " +next);
				System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
			}
		}
	}
	
	public void fixFingers2(String inbpURI) {
		synchronized(this) {
			this.logMessage("fixFingers2()") ;
			try {
				if(this.nObpFingers.connected())
					this.doPortDisconnection(nObpFingers.getPortURI());
				this.doPortConnection(this.nObpFingers.getPortURI(), inbpURI, NodeConnector.class.getCanonicalName());

				nObpFingers.fixFingers3(nIbp.getPortURI(), index);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fixFingers3(String inbpURI, int indice) {
		synchronized(this) {
			this.logMessage("fixFingers3()") ;
			fingerInd.set(next, indice);
			if(fingerIbpFromInd.containsKey(next)) {
				try {
					fingerIbpFromInd.replace(next, inbpURI);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("On en est au noeud "+index + " on a mis à jour next: " +next);
				System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
			}
			else {
				try {
					fingerIbpFromInd.put(next, inbpURI);
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("On en est au noeud "+index + " on a mis à jour next: " +next);
				System.out.println(((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
			}
		}
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
		// déconnexion des outboundPort : 
		//this.adminPort.doDisconnection() ;
		if(this.nObpPred.connected())
			this.nObpPred.doDisconnection() ;
		if(this.nObpSucc.connected())
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
