/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Inject;

/**
 * Subclassing allows the use of injection without a provider class 
 *
 */
public class PescadorMVPPlaceHistoryHandler extends PlaceHistoryHandler {

	@Inject
	public PescadorMVPPlaceHistoryHandler(PescadorMVPPlaceMapper mapper) {
		super(mapper);
	}
}
