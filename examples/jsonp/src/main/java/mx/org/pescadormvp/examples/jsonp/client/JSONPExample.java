/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * This class is the entry point--where GWT client code starts executing.
 * As always in GWT, this is specified in the corresponding .gwt.xml file.
 * 
 * @author Andrew Green
 */
public class JSONPExample implements EntryPoint{

	@Override
	public void onModuleLoad() {
		
		// This static call on the class we've created in turn
		// calls a static method on the superclass that starts everything 
		// up, including DI, history management and the UI.
		ActiveComponentSetup.startUp();
	}
}
