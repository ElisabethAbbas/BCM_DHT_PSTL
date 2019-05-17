package fr.sorbonne_u.components.dht;

import java.util.HashMap;

import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.components.DynamicAdmin;

public class DynamicCVM extends		AbstractCVM
{
	public static String ADMIN_RIP_URI = "admin-rip" ;
	public static String ADMIN_CLIENT_RIP_URI = "adminClient-rip" ;
	public static String NODE1_RIP_URI = "node4-rip" ;
	public static String NODE2_RIP_URI = "node1-rip" ;
	public static String NODE3_RIP_URI = "node6-rip" ;
	public DynamicAdmin dAdmin;
	
	public				DynamicCVM() throws Exception
	{
		super() ;
	}

	@Override
	public void			deploy() throws Exception
	{
		
		HashMap<Integer, String> nodes = new HashMap<Integer, String>();
		
		nodes.put(4,"");

		nodes.put(1,"");		
		
		nodes.put(6,"");
		
		this.dAdmin = new DynamicAdmin(ADMIN_RIP_URI, ADMIN_CLIENT_RIP_URI, 16, nodes) ;
		//this.dAdmin = new DynamicAdmin(ADMIN_RIP_URI, ADMIN_CLIENT_RIP_URI, 15, nodes) ;
		this.deployedComponents.add(this.dAdmin) ;
		this.dAdmin.toggleTracing() ;
		this.dAdmin.toggleLogging() ;
		super.deploy();
	}
	
	@Override
	public void			start() throws Exception
	{
		super.start() ;

		/*this.dAdmin.runTask(
			new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							((DynamicAdmin)this.getOwner()).
													dynamicDeploy() ;
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}) ;*/
	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#execute()
	 */
	@Override
	public void			execute() throws Exception
	{
		super.execute() ;

		/*this.dAdmin.runTask(
			new AbstractComponent.AbstractTask() {
					@Override
					public void run() {
						try {
							((DynamicAdmin)this.getOwner()).initialize() ;
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				}) ;*/
	}

	public static void		main(String[] args)
	{
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(20000L) ;
			Thread.sleep(20000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}		
	}
}