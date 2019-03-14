package fr.sorbonne_u.components.dht;

import java.util.HashMap;

import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.components.Admin;
import fr.sorbonne_u.components.dht.components.Node;


public class				CVM
extends		AbstractCVM
{
	public static String ADMIN_RIP_URI = "admin-rip" ;
	public static String NODE1_RIP_URI = "node1-rip" ;
	public static String NODE2_RIP_URI = "node2-rip" ;
	public static String NODE3_RIP_URI = "node3-rip" ;
	
	public				CVM() throws Exception
	{
		super() ;
	}

	@Override
	public void			deploy() throws Exception
	{
		
		HashMap<Integer, String[]> nodes = new HashMap<Integer, String[]>();
		// à faire dans admin
		Node node1 = new Node(NODE1_RIP_URI, ADMIN_RIP_URI) ;
		this.deployedComponents.add(node1) ;
		String[] nodePorts1=new String[2];
		nodePorts1[0]=node1.getInboundPort().getPortURI();
		nodePorts1[1]=node1.getOutboundPort().getPortURI();
		nodes.put(1,nodePorts1);
		System.out.println("node 1 : "+ nodePorts1[0]+","+nodePorts1[0]);
		node1.toggleTracing() ;
		node1.toggleLogging() ;
		
		Node node2 = new Node(NODE2_RIP_URI, ADMIN_RIP_URI) ;
		this.deployedComponents.add(node2) ;
		String[] nodePorts2=new String[2];
		nodePorts2[0]=node2.getInboundPort().getPortURI();
		nodePorts2[1]=node2.getOutboundPort().getPortURI();
		nodes.put(4,nodePorts2);
		System.out.println("node 2 : "+ nodePorts2[0]+","+nodePorts2[0]);
		node2.toggleTracing() ;
		node2.toggleLogging() ;
		
		Node node3 = new Node(NODE3_RIP_URI, ADMIN_RIP_URI) ;
		this.deployedComponents.add(node3) ;
		String[] nodePorts3=new String[2];
		nodePorts3[0]=node3.getInboundPort().getPortURI();
		nodePorts3[1]=node3.getOutboundPort().getPortURI();
		nodes.put(6,nodePorts3);
		System.out.println("node 3 : "+ nodePorts3[0]+","+nodePorts3[0]);
		node3.toggleTracing() ;
		node3.toggleLogging() ;
		
		Admin admin = new Admin(ADMIN_RIP_URI, 15, nodes) ;
		this.deployedComponents.add(admin) ;
		admin.toggleTracing() ;
		admin.toggleLogging() ;

		super.deploy();
	}

	public static void		main(String[] args)
	{
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(60000L) ;
			Thread.sleep(5000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}		
	}
}