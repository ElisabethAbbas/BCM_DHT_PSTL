package fr.sorbonne_u.components.dht;

import java.util.HashMap;

import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.dht.components.Admin;
import fr.sorbonne_u.components.dht.components.Node;

//-----------------------------------------------------------------------------
/**
* The class <code>CVM</code> deploys and run the ping pong example on a
* single JVM.
*
* <p><strong>Description</strong></p>
* 
* <p><strong>Invariant</strong></p>
* 
* <pre>
* invariant		true
* </pre>
* 
* <p>Created on : 2018-03-14</p>
* 
* @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
*/
public class				CVM
extends		AbstractCVM
{
	/** URI of the inbound port of node 1.						*/ 
	// Node ou Integer (correspondant au numéro de la node) ?
	public static HashMap<Integer, String> NODE_INBOUD_PORT_URIS ;
	
	/** URI of the inbound port of admin.						*/
	public final static String ADMIN_INBOUND_PORT_URI = "adminibpURI" ;
	
	/** URI of the outbound port of the node 1.						*/
	public static HashMap<Integer, String> NODE_OUTBOUD_PORT_URIS;
	
	/** URI of the outbound port of the admin.						*/
	public final static String	ADMIN_OUTBOUND_PORT_URI =
														"admindobpURI" ;
	
	public				CVM() throws Exception
	{
		super() ;
	}

	@Override
	public void			deploy() throws Exception
	{
		// --------------------------------------------------------------------
		// Creation phase
		// --------------------------------------------------------------------
		
		// Admin.
		Admin admin = new Admin() ; // passage d'uri(s) en paramètres?
		this.addDeployedComponent(admin) ;
		admin.toggleTracing() ;

		// Nodes.
		int i=0;
		while(i++< 8/*NUMBER_OF_NODES_WANTED_AT_THE_BEGINNING*/) {	
			Node node =	new Node() ; // passage d'uri(s) en paramètres?
			this.addDeployedComponent(node) ;
			node.toggleTracing() ;
		}

		// --------------------------------------------------------------------
		// Deployment done
		// --------------------------------------------------------------------

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