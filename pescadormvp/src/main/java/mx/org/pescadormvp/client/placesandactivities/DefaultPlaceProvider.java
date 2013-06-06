/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlace;

/**
 * Provides a default place, and may set up its presentation text depending on the
 * current locale. Not a provider in Guice/Gin terms.
 * 
 */
public interface DefaultPlaceProvider {

	public PescadorMVPPlace getDefaultPlace();
}
