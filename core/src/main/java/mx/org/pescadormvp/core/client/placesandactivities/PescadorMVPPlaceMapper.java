/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.components.Component;

import com.google.gwt.place.shared.PlaceHistoryMapper;

/**
 * A {@link PlaceHistoryMapper} for {@link PescadorMVPPlace}s. Implemented as a
 * {@link Component}.
 */
public interface PescadorMVPPlaceMapper extends PlaceHistoryMapper, 
		Component<PescadorMVPPlaceMapper> {

	/**
	 * Set up extra info in the place object provided. This will correctly set
	 * the places's URL, history token and reload requirement flag, taking into
	 * account the place's properties and whether or not it sets a new
	 * locale.
	 */
	public void setupURLInfo(PescadorMVPPlace place);
	
	/**
	 * Get a copy of a place. Does not copy or set up URL
	 * info.
	 */
	public <P extends PescadorMVPPlace> P copyPlaceInto(
			P originalPlace,
			P newPlace);
	
	/**
	 * Internal Pescador MVP use. Get the place's history token. To set up a
	 * {@link PescadorMVPPlace}'s history token, call
	 * {@link PescadorMVPPlaceMapper#setupURLInfo(PescadorMVPPlace)
	 * setupURLInfo(PescadorMVPPlace)}.
	 */
	public String getToken(PescadorMVPPlace place);
	
	/**
	 * Get the application's default place. Will have URL and history token
	 * info already set up. 
	 */
	PescadorMVPPlace defaultPlace();
}
