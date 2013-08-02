/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

/**
 * Interface that components must implement.
 * 
 * @param <I> The public interface that this component offers.
 */
public interface Component<I> {
	
	/**
	 * Do stuff that can only be done once all components have been loaded.
	 */
	void finalizeSetup();
	
	/**
	 *  The class representing the public interface that this component offers.
	 */
	Class<I> publicInterface();

}
