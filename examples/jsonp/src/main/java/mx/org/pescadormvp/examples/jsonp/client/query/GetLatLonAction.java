/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;


import mx.org.pescadormvp.core.client.data.JsonpAction;
import mx.org.pescadormvp.core.client.data.CacheableAction;

/**
 * Action (following the Command pattern) for requesting temperature
 * information from OpenWeatherMap.
 *  
 * @author Andrew Green
 */
public class GetLatLonAction implements JsonpAction<GetLatLonResult>, CacheableAction {

//	// This class is meant to be used only with this site 
//	final static String BASE_URL = "http://api.openweathermap.org/data/2.5/find?q=";
	
	private final String location;
	
	/**
	 * @param location The string describing the location to request temperature
	 * information about.
	 */
	public GetLatLonAction(String location) {
		this.location = location;
	}

	String getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GetLatLonAction))
			return false;
		GetLatLonAction other = (GetLatLonAction) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetTempAction[" + location + "]";
	}
}
