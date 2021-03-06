/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Contains the result of a call to the OpenStreetMap for latitude
 * and longitude information about a location.
 */
public class GetLatLonResult implements Result {

	private boolean hasData;
	private String displayName;
	private double lat;
	private double lon;

	/** 
	 * @return Has temperature information been returned for somewhere?
	 */
	public boolean hasData() {
		return hasData;
	}
	
	void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	String getDisplayName() {
		return displayName;
	}

	void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	double getLat() {
		return lat;
	}

	void setLat(double lat) {
		this.lat = lat;
	}

	double getLon() {
		return lon;
	}

	void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (hasData ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GetLatLonResult))
			return false;
		GetLatLonResult other = (GetLatLonResult) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (hasData != other.hasData)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetLongLatResult [" + hasData + ","
				+ displayName + "," + lat + "," + lon + "]";
	}
}
	
