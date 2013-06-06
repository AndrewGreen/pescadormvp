/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.jsonpexample.client;

import mx.org.pescadormvp.client.PescadorMVPGinjector;
import mx.org.pescadormvp.jsonpexample.client.ActiveComponentSetup.ActiveSetupGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;

/**
 * This class is the entry point--where GWT client code starts executing.
 * As always in GWT, this is specified in the corresponding .gwt.xml file.
 * 
 * @author Andrew Green
 */
public class JSONPExample implements EntryPoint{

	@Override
	public void onModuleLoad() {

		// Get the Ginjector to boot up dependency injection
		PescadorMVPGinjector ginjector =
				GWT.create(ActiveSetupGinjector.class);
		
		// The Ginjector provides the active ComponentSetup, which
		// we use to start the app. This will initialize the UI
		// and go to the default place.
		ginjector.getComponetSetup().start();
	}
}
