/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client;

import mx.org.pescadormvp.core.client.components.ComponentSetup;

import com.google.gwt.inject.client.Ginjector;

/**
 * Framework ginjector, for bootstrapping dependency injection. 
 * @author Andrew Green
 */
public interface PescadorMVPGinjector extends Ginjector {
	public ComponentSetup getComponetSetup();
}
