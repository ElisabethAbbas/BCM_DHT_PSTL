package fr.sorbonne_u.components.dht.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.connectors.NodeManagementConnector;
import fr.sorbonne_u.components.dht.interfaces.AdminClientI;
import fr.sorbonne_u.components.dht.interfaces.AdminRequiredI;
import fr.sorbonne_u.components.dht.interfaces.NodeManagementI;
import fr.sorbonne_u.components.dht.ports.AdminClientIbp;
import fr.sorbonne_u.components.dht.ports.AdminOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.pre.dcc.connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.pre.dcc.interfaces.DynamicComponentCreationI;
import fr.sorbonne_u.components.pre.dcc.ports.DynamicComponentCreationOutboundPort;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;


public class DynamicAdmin extends		AbstractComponent
{
	protected HashMap<Integer, String[]> uris;//for later, link node index to JVM uri
	protected int size;
	protected int nbNodes;
	protected AdminOutboundPort adminOutboundPort;
	protected ReflectionOutboundPort rop;
	protected AdminClientIbp adminClientIbp;
	protected String[][] ring;
	protected HashMap<Integer, String> nodes;//string correspond au nom de la JVM de la node a creer
	protected HashMap<Integer, DynamicComponentCreationOutboundPort> portsToNodesJVM = new HashMap<Integer, DynamicComponentCreationOutboundPort>();
	protected HashMap<Integer, String> nodesReflectionIbpURIS = new HashMap<Integer, String>();
	
	class HashMapAffiche extends HashMap<Integer, String>{
		public String affiche(List<Integer> fingerInd) {
			String res = "[";
			
			for(Integer k : this.keySet())
				res+=fingerInd.get(k)+" -> "+ ((this.get(k)==null)?"null":"ok") +" ; ";
			
			res+="]";
			
			return res;
		}
		
		public String affiche_i(List<Integer> fingerInd) {
			String res = "[";
			
			for(Integer k : this.keySet())
				res+=""+k+":"+fingerInd.get(k)+" ; ";
			
			res+="]";
			
			return res;
		}
    }
	
	public DynamicAdmin(String AdminInboundPortURI, String adminClientIbpURI, int size, HashMap<Integer, String> nodes) throws Exception//hashMap nodes != de celle de l'admin précédent
	{
		super(AdminInboundPortURI, 1, 1);
		this.size = size;
		this.nodes = nodes;
		
		this.addRequiredInterface(AdminRequiredI.class);
		this.addRequiredInterface(DynamicComponentCreationI.class);
		this.addOfferedInterface(NodeManagementI.class);
		this.addRequiredInterface(AdminClientI.class);
		this.addOfferedInterface(AdminClientI.class);
		
		this.adminClientIbp = new AdminClientIbp(adminClientIbpURI, this);
		this.addPort(this.adminClientIbp);
		this.adminClientIbp.localPublishPort();
		
		this.adminOutboundPort = new AdminOutboundPort(this);
		this.addPort(this.adminOutboundPort);
		this.adminOutboundPort.localPublishPort();
		
		this.tracer.setTitle("Dynamic Admin") ;
		this.tracer.setRelativePosition(1, 0) ;
	}
	
	public void join(int index, String JVMURI) throws Exception {
		
		if(ring[index] != null)
			this.logMessage("cant join node " + index + " : node already exists !") ;
		else {
			nodes.put(index,  JVMURI);
			//initializing fingerTable
			List<Integer> fingerInd ;
			HashMap<Integer, String> fingerIbpFromInd;
			int fingerIndex;
			
			fingerInd = new ArrayList<Integer>();
			fingerIbpFromInd = new HashMapAffiche();//new HashMap<Integer, String>();
			fingerIndex = 0;
			while(Math.pow(2, fingerIndex) < size) {
				int tmpFingerInd = 0;
				while(ring[(int) (index + Math.pow(2, fingerIndex) + tmpFingerInd) % size] == null) {
					tmpFingerInd++;
					
				}
				
				int succOf2Pow = (int) (index + Math.pow(2, fingerIndex) + tmpFingerInd) % size;
				fingerInd.add(fingerIndex,succOf2Pow);
				fingerIbpFromInd.put(fingerIndex, ring[succOf2Pow][1]);
				fingerIndex++;
			}
			System.out.println("admin -> node "+index+" : finger size : "+fingerInd.size());
			try {
				System.out.println("starting dccObp creation : " + index);
				DynamicComponentCreationOutboundPort tmpCObp = new DynamicComponentCreationOutboundPort(this);
				portsToNodesJVM.put(index, tmpCObp);
				this.addPort(tmpCObp);
				tmpCObp.localPublishPort();
				tmpCObp.doConnection(nodes.get(index) + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX, DynamicComponentCreationConnector.class.getCanonicalName());
			} catch (Exception e) {
				throw new Exception(e) ;
			}
			
			this.logMessage("creating node : " + index);
			String tmpReflectionIbpURI = portsToNodesJVM.get(index).createComponent(Node.class.getCanonicalName(), new Object[]{"node" + index +"-rip", "admin-rip", index, size});
			nodesReflectionIbpURIS.put(index, tmpReflectionIbpURI);
			String [] tmpRingNode = new String[2];
			
			rop.doConnection(nodesReflectionIbpURIS.get(index), ReflectionConnector.class.getCanonicalName());
			try {
				tmpRingNode[0] = rop.findPortURIsFromInterface(NodeManagementI.class)[0];
				rop.toggleTracing();
				this.doPortConnection(this.adminOutboundPort.getPortURI(), tmpRingNode[0], NodeManagementConnector.class.getCanonicalName());
				tmpRingNode[1] = this.adminOutboundPort.getInboundPortURI();
				this.doPortDisconnection(this.adminOutboundPort.getPortURI());
				
				//tmpRingNode[1] = rop.findPortURIsFromInterface(NodeInboundPort.class)[0];
			}catch (Exception e) {
				throw new Exception(e) ;
			}
			ring[index] = tmpRingNode;
			rop.doDisconnection();
			//TODO : SYNCHRONISATION SUR LE ROP reflection outbound port !!
			
			int cptFindSucc = (index + 1)%this.size;
			while(ring[cptFindSucc] == null){
				cptFindSucc = (cptFindSucc + 1)%this.size;
			}
			if(cptFindSucc != index) {
				this.logMessage("connecting Outb->Inb admin - new joined node : " + index +"...");
				this.doPortConnection(this.adminOutboundPort.getPortURI(), tmpRingNode[0], NodeManagementConnector.class.getCanonicalName());
				this.logMessage("settingSucc node : " + index +"...");
				this.adminOutboundPort.setSucc(ring[cptFindSucc][1], cptFindSucc);
				this.adminOutboundPort.setFingers(fingerInd, fingerIbpFromInd);
				this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			}
			
			for(int i = 0; i < ring.length; i++){
				if(ring[i] != null && i != index){
					
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i][0], NodeManagementConnector.class.getCanonicalName());
					this.adminOutboundPort.nodeJoined();
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
				}
			}
			
			//let node stabilisation do the rest... ( = connecting to the nodes)
		}
	}
	
	public void			dynamicDeploy() throws Exception
	{
		//call the dynamic component creator of each node to create the nodes
		String tmpReflectionIbpURI;
		System.out.println("starting node creation...");
		for(int n : portsToNodesJVM.keySet()) {
			this.logMessage("creating node : " + n);
			tmpReflectionIbpURI = portsToNodesJVM.get(n).createComponent(Node.class.getCanonicalName(), new Object[]{"node" + n +"-rip", "admin-rip", n, size});
			nodesReflectionIbpURIS.put(n, tmpReflectionIbpURI);
			
		}
		ring=new String[size][2];
		for(int i = 0; i < size; i++){
			// /!\ on considere que size est une puissance de 2, sinon arrondir au-dessus.
			ring[i] = null; 
		}
		
		//admin reflection outbound port
		this.addRequiredInterface(ReflectionI.class) ;
		this.rop = new ReflectionOutboundPort(this) ;
		this.addPort(rop) ;
		this.rop.localPublishPort() ;
		
		for(int n : nodesReflectionIbpURIS.keySet()) {
			String [] tmpRingNode = new String[2];
			rop.doConnection(nodesReflectionIbpURIS.get(n), ReflectionConnector.class.getCanonicalName());
			try {
				tmpRingNode[0] = rop.findPortURIsFromInterface(NodeManagementI.class)[0];
				rop.toggleTracing();
				this.doPortConnection(this.adminOutboundPort.getPortURI(), tmpRingNode[0], NodeManagementConnector.class.getCanonicalName());
				tmpRingNode[1] = this.adminOutboundPort.getInboundPortURI();
				this.doPortDisconnection(this.adminOutboundPort.getPortURI());
				
				//tmpRingNode[1] = rop.findPortURIsFromInterface(NodeInboundPort.class)[0];
			}catch (Exception e) {
				throw new Exception(e) ;
			}			
			ring[n] = tmpRingNode;
			rop.doDisconnection();
		}
		List<Integer> fingerInd =null;
		HashMap<Integer, String> fingerIbpFromInd=null;
		int fingerIndex;
		
		String[] first = null;
		String[] tmp = null;
		int tmpi = 0;
		int firsti=0;
		for(int i = 0; i < ring.length; i++){
			if(ring[i] != null){
				if(first == null){
					first = ring[i];
					firsti=i;
					tmp = ring[i];
					tmpi = i;
				}
				else{
					//initializing fingerTable
					fingerInd = new ArrayList<Integer>();
					fingerIbpFromInd = new HashMapAffiche();//new HashMap<Integer, String>();
					fingerIndex = 0;
					while(Math.pow(2, fingerIndex) < size) {
						int tmpFingerInd = 0;
						while(ring[(int) (i + Math.pow(2, fingerIndex) + tmpFingerInd) % size] == null) {
							tmpFingerInd++;
							
						}
						
						int succOf2Pow = (int) (i + Math.pow(2, fingerIndex) + tmpFingerInd) % size;
						fingerInd.add(fingerIndex,succOf2Pow);
						fingerIbpFromInd.put(fingerIndex, ring[succOf2Pow][1]);
						fingerIndex++;
					}
					System.out.println("admin -> node "+i+" : finger size : "+fingerInd.size());
					
					this.logMessage("connecting Outb->Inb admin - node : " + i +"...");
					this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[i][0], NodeManagementConnector.class.getCanonicalName());
					this.logMessage("settingPred node : " + i +"...");
					this.adminOutboundPort.setPred(tmp[1], tmpi);
					this.adminOutboundPort.setFingers(fingerInd, fingerIbpFromInd);
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					
					this.logMessage("connecting Outb->Inb admin - node : " + tmpi +"...");
					
					this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeManagementConnector.class.getCanonicalName());
					this.logMessage("settingSucc node : " + tmpi +"...");
					this.adminOutboundPort.setSucc(ring[i][1],i);
					this.doPortDisconnection(this.adminOutboundPort.getPortURI());
					
					tmp = ring[i];
					tmpi = i;
					System.out.println("à l'init : "+((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
				}
			}
		}
		
		if((first != null)&&(first != tmp)){//pour ne pas faire le cas ou 1 seule node
			//initializing fingerTable
			fingerInd = new ArrayList<Integer>();
			fingerIbpFromInd = /*new HashMap<Integer, String>();*/ new HashMapAffiche();
			fingerIndex = 0;
			while(Math.pow(2, fingerIndex) < size) {
				int tmpFingerInd = 0;
				while(ring[(int) (firsti + Math.pow(2, fingerIndex) + tmpFingerInd) % size] == null) {
					tmpFingerInd++;
					
				}
				
				int succOf2Pow = (int) (firsti + Math.pow(2, fingerIndex) + tmpFingerInd) % size;
				fingerInd.add(fingerIndex,succOf2Pow);
				fingerIbpFromInd.put(fingerIndex, ring[succOf2Pow][1]);
				fingerIndex++;
			}
			System.out.println("admin -> node "+firsti+" : finger size : "+fingerInd.size());
			System.out.println("à l'init : "+((DynamicAdmin.HashMapAffiche)fingerIbpFromInd).affiche_i(fingerInd));
			this.doPortConnection(this.adminOutboundPort.getPortURI(), first[0], NodeManagementConnector.class.getCanonicalName());
			this.adminOutboundPort.setPred(tmp[1],tmpi);
			this.adminOutboundPort.setFingers(fingerInd, fingerIbpFromInd);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			
			this.doPortConnection(this.adminOutboundPort.getPortURI(), tmp[0], NodeManagementConnector.class.getCanonicalName());
			this.adminOutboundPort.setSucc(first[1],firsti);
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
		}
		this.logMessage("initialized !");
	}
	
	@Override
	public void			start() throws ComponentStartException
	{
		
		try {
			System.out.println("starting dccObp creation...");
			nbNodes = 0;
			DynamicComponentCreationOutboundPort tmpCObp;
			for(int n : nodes.keySet()) {
				System.out.println("starting dccObp creation : " + n);
				tmpCObp = new DynamicComponentCreationOutboundPort(this);
				portsToNodesJVM.put(n, tmpCObp);
				this.addPort(tmpCObp);
				tmpCObp.localPublishPort();
				tmpCObp.doConnection(nodes.get(n) + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX, DynamicComponentCreationConnector.class.getCanonicalName());
			
			}
			
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}
		
		super.start() ; 
				
		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							((DynamicAdmin)this.getOwner()).join(2, "");
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}, 1500 // délai avant d'exécuter le join du noeud 2
				, TimeUnit.MILLISECONDS) ;
	}
	
	public String getRingNodeInMyJVM(String JVMURI) throws Exception {
		if(nodes.containsValue(JVMURI)) {
			int nodeIndex = -1;
			for( int n : nodes.keySet()) {
				if(nodes.get(n) == JVMURI)
					nodeIndex = n;
			}
			String tmpURI;
			this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[nodeIndex][0], NodeManagementConnector.class.getCanonicalName());
			tmpURI = this.adminOutboundPort.getInboundPortURI();
			this.doPortDisconnection(this.adminOutboundPort.getPortURI());
			return tmpURI;
		}
		else {
			int cpt = 0;
			List<Integer> availableIndexes = new ArrayList<Integer>();
			while(cpt < size)
				if(ring[cpt] == null)
					availableIndexes.add(cpt);
				cpt++;
			if(availableIndexes.size() == 0) 
				this.logMessage("ring is full, and there is no node on your JVM : " + JVMURI);
			else {
				Collections.shuffle(availableIndexes);
				String tmpURI;
				join(availableIndexes.get(0), JVMURI);
				this.doPortConnection(this.adminOutboundPort.getPortURI(), ring[cpt][0], NodeManagementConnector.class.getCanonicalName());
				tmpURI = this.adminOutboundPort.getClientPortURI();
				this.doPortDisconnection(this.adminOutboundPort.getPortURI());
				return tmpURI;
			}
		}
		
		return null;
	}
	
	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void			finalise() throws Exception
	{
		for(DynamicComponentCreationOutboundPort reflectionPort : portsToNodesJVM.values()) {
			if (reflectionPort.connected()) {
				reflectionPort.doDisconnection() ;
			}
		}
		if(this.adminOutboundPort.connected())
			this.adminOutboundPort.doDisconnection();
		if(this.rop.connected())
			this.rop.doDisconnection();
			
		super.finalise() ;
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			for(DynamicComponentCreationOutboundPort reflectionPort : portsToNodesJVM.values()) {
				reflectionPort.unpublishPort() ;
			}
			this.rop.unpublishPort() ;
			this.adminOutboundPort.unpublishPort() ;
			this.adminClientIbp.unpublishPort() ;
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}

		super.shutdown();
	}

}
