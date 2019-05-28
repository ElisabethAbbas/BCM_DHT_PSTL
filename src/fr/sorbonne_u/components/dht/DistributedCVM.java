package fr.sorbonne_u.components.dht;

import java.util.HashMap;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
import fr.sorbonne_u.components.dht.components.DynamicAdmin;
import fr.sorbonne_u.components.helpers.CVMDebugModes;


public class				DistributedCVM
extends		AbstractDistributedCVM
{
	public static String ADMIN_RIP_URI = "admin-rip" ;
	public static String ADMIN_CLIENT_RIP_URI = "adminClient-rip" ;
	public static String NODE1_RIP_URI = "node4-rip" ;
	public static String NODE2_RIP_URI = "node1-rip" ;
	public static String NODE3_RIP_URI = "node6-rip" ;
	public static String ADMIN_URI = "jvm-1" ;
	
	public DynamicAdmin dAdmin;
	
	public				DistributedCVM(String[] args, int xLayout, int yLayout)
			throws Exception
			{
				super(args, xLayout, yLayout);
			}
	@Override
	public void			initialise() throws Exception
	{
		// debugging mode configuration; comment and uncomment the line to see
		// the difference
		//AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PUBLIHSING) ;
		//AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING) ;
		//AbstractCVM.DEBUG_MODE.add(CVMDebugModes.COMPONENT_DEPLOYMENT) ;

		super.initialise() ;
		// any other application-specific initialisation must be put here

	}

	@Override
	public void			instantiateAndPublish() throws Exception
	{
		if (thisJVMURI.equals(ADMIN_URI)) {
			HashMap<Integer, String> nodes = new HashMap<Integer, String>();
			
			nodes.put(4,"jvm-1");
			
			nodes.put(1,"jvm-2");		
			
			nodes.put(6,"jvm-3");
			
			this.dAdmin = new DynamicAdmin(ADMIN_RIP_URI, ADMIN_CLIENT_RIP_URI, 15, nodes) ;
			this.deployedComponents.add(this.dAdmin) ;
			this.dAdmin.toggleTracing() ;
			this.dAdmin.toggleLogging() ;
			
		}
		super.instantiateAndPublish();
	}
	
	@Override
	public void			start() throws Exception
	{
		super.start() ;
		if (thisJVMURI.equals(ADMIN_URI)) {
			this.dAdmin.runTask(
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
					}) ;
		}
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
								((DynamicAdmin)this.getOwner()).
														initialize() ;
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}
						}
					}) ;*/
	}

	public static void		main(String[] args)
	{
		try {
			DistributedCVM da  = new DistributedCVM(args, 2, 5) ;
			da.startStandardLifeCycle(200000L) ;
			Thread.sleep(25000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}		
	}
}