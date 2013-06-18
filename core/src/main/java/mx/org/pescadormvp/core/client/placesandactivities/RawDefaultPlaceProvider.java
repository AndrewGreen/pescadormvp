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
 * Provides a raw default place, though does not set it up fully. 
 * Not a provider in Guice/Gin terms. Should only be used directly
 * by {@link PescadorMVPPlaceMapper}, which is the real provider of fully
 * setup default places for the framework.
 * 
 */
public interface RawDefaultPlaceProvider {

	public PescadorMVPPlace getRawDefaultPlace();
}
