/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
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

	/**
	 * This method is called by the superclass whenever a property needs to be
	 * set.
	 */
	@Override
	protected void processProperty(String key, String value) {

		// Tell the superclass about the main token and the only
		// key.
		if (key.compareTo(LOCATION_KEY) == 0) {
			location = value;
		}
	}

	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * Here, instead of setting an instance variable directly, we tell the
	 * superclass to follow its standard procedure for setting a property. The
	 * superclass will call {@link #processProperty(String, String)}, which is
	 * where our instance variable will finally be set. This allows the framework
	 * to serialize and deserialize key-value pairs to and from the
	 * URL fragment identifier.
	 */
	@Override
	public void setLocation(String location) {

		setProperty(LOCATION_KEY, location);
	}
}
