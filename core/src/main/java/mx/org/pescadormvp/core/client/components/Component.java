/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

/**
 * Interface that components must implement.
 * 
 * @param <I>
 *            The public interface that this component offers.
 */
public interface Component<I> {

	/**
	 * Called by {@link GlobalSetup} on components once all components have been
	 * registered. Don't call it yourself, just implement it to do stuff that can
	 * only be done once all components are registered, or leave it empty if
	 * your component has nothing to do at that time.
	 */
	void finalizeSetup();

	/**
	 * The class representing the public interface that this component offers.
	 */
	Class<I> publicInterface();

}
