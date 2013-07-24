/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceBase;

/**
 * Implementation for our only place, which only has one key/value property to
 * serialize to the URL fragment identifier. (Specifically, the location whose
 * temperature we're querying.)
 *  
 * @author Andrew Green
 */
public class QueryPlaceImpl extends PescadorMVPPlaceBase implements QueryPlace {

	private static final String MAIN_TOKEN = "query";
	private static final String LOCATION_KEY = "l";
	
	private String location;
	
	public QueryPlaceImpl() {
		super(MAIN_TOKEN, new String[] { LOCATION_KEY });
	}
	
	@Override
	protected void processProperty(String key, String value) {
		if (key.compareTo(LOCATION_KEY) == 0) {
			location = value;
		} 
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		setProperty(LOCATION_KEY, location);
	}
}
