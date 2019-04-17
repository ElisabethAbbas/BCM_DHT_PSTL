package fr.sorbonne_u.components.dht;

import java.util.HashMap;

import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.components.Admin;
import fr.sorbonne_u.components.dht.components.DynamicAdmin;
import fr.sorbonne_u.components.dht.components.Node;

public class DynamicCVM extends		AbstractCVM
{
	public static String ADMIN_RIP_URI = "admin-rip" ;
	public static String NODE1_RIP_URI = "node4-rip" ;
	public static String NODE2_RIP_URI = "node1-rip" ;
	public static String NODE3_RIP_URI = "node6-rip" ;
	
	public				DynamicCVM() throws Exception
	{
		super() ;
	}

	@Override
	public void			deploy() throws Exception
	{
		
		HashMap<Integer, String> nodes = new HashMap<Integer, String>();
		// à faire dans admin
		
		nodes.put(4,"");

		nodes.put(1,"");		
		
		nodes.put(6,"");
		
		DynamicAdmin dAdmin = new DynamicAdmin(ADMIN_RIP_URI, 15, nodes) ;
		this.deployedComponents.add(dAdmin) ;
		dAdmin.toggleTracing() ;
		dAdmin.toggleLogging() ;

		super.deploy();
	}

	public static void		main(String[] args)
	{
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(5000L) ;
			Thread.sleep(5000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}		
	}
}