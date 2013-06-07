/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import mx.org.pescadormvp.client.components.Component;

import com.google.gwt.place.shared.PlaceHistoryMapper;
/**
 * An extension to {@link PlaceHistoryMapper}, this interface defines additional
 * methods related to places.
 *  
 * @author Andrew Green
 */
public interface PescadorMVPPlaceMapper extends PlaceHistoryMapper, 
		Component<PescadorMVPPlaceMapper> {

	public void setupURLInfo(PescadorMVPPlace place);
	
	public <P extends PescadorMVPPlace> P copyPlaceInto(
			P originalPlace,
			P newPlace);
	
	public String getToken(PescadorMVPPlace place);

	PescadorMVPPlace defaultPlace();
}
