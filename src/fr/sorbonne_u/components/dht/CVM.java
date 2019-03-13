package fr.sorbonne_u.components.dht;

import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.components.Admin;
import fr.sorbonne_u.components.dht.components.Node;


public class				CVM
extends		AbstractCVM
{
	public static String ADMIN_RIP_URI = "admin-rip" ;
	
	public				CVM() throws Exception
	{
		super() ;
	}

	@Override
	public void			deploy() throws Exception
	{
		Admin admin = new Admin(ADMIN_RIP_URI) ;
		this.addDeployedComponent(admin) ;

		// à faire dans admin
		Node node = new Node(ADMIN_RIP_URI) ;
		this.addDeployedComponent(node) ;

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