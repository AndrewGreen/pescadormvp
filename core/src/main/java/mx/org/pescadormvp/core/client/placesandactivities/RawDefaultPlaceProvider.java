/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;

/**
 * <p>
 * Provides a raw default place, though does not set it up fully. Not a provider
 * in Guice/Gin terms.
 * </p>
 * <p>
 * Don't call the method specified here yourself. It'll be only be used by
 * {@link PescadorMVPPlaceMapper}, which is the real provider of fully setup
 * default places for the framework.
 * </p>
 * <p>
 * Instead, implement this interface on the {@link PAVComponent} that you wish
 * to associated with your application's default place, and then bind that
 * component's interface to this interface in your global setup. See
 * {@link mx.org.pescadormvp.examples.jsonp.client ...examples.jsonp.client} for
 * a full example.
 * </p>
 */
@SuppressWarnings("javadoc")
public interface RawDefaultPlaceProvider {

	/**
	 * Implement this method on the {@link PAVComponent} that you wish to
	 * associated with your application's default place, but don't call it
	 * yourself. See {@link mx.org.pescadormvp.examples.jsonp.client
	 * ...examples.jsonp.client} for a full example.
	 */
	public PescadorMVPPlace getRawDefaultPlace();
}
