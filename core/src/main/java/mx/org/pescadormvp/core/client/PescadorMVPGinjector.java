/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client;

import mx.org.pescadormvp.core.client.components.GlobalSetup;

import com.google.gwt.inject.client.Ginjector;

/**
 * {@link Ginjector} for bootstrapping dependency injection. Normally
 * applications will define a {@link Ginjector} that extends this one. 
 * @author Andrew Green
 */
public interface PescadorMVPGinjector extends Ginjector {
	/**
	 * Returns an instance of a subclass of {@link GlobalSetup} as
	 * bound in a module associated with this {@link Ginjector}.
	 * This is used internally in the static startup methods of
	 * {@link GlobalSetup}.
	 */
	public GlobalSetup getGlobalSetup();
}
