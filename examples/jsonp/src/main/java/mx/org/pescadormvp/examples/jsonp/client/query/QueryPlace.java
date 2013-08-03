/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;

/**
 * The place associated with the {@link QueryComponent}. Contains the name of
 * the location whose latitude and longitude data the user wishes to query.
 */
public interface QueryPlace extends PescadorMVPPlace {
	
	/**
	 * Get the name of the location to query. 
	 */
	String getLocation();
	
	/**
	 * Set the name of the location to query.
	 */
	void setLocation(String location);

}
