/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mx.org.pescadormvp.core.shared.PescadorMVPLocale;

import com.google.gwt.place.shared.Place;

/**
 * Abstract base class for places in Pescador MVP.
 * {@link PescadorMVPPlace}s have a few extra features that standard GWT
 * {@link Place}s lack. They can always be obtained as a standard GWT {@link Place}
 * via {@link #asGWTPlace()}. See
 * {@link mx.org.pescadormvp.examples.jsonp.client ...examples.jsonp.client} for
 * more information.
 */
@SuppressWarnings("javadoc")
public abstract class PescadorMVPPlaceBase extends Place implements PescadorMVPPlace {

	private final String mainToken;
	private final String[] propertyKeys;
	
	private Map<String, String> properties;
	private PescadorMVPLocale newLocale;
	
	private String presentationText;
	private String historyToken;
	private String URL;
	private boolean requiresReload;

	public PescadorMVPPlaceBase(String mainToken, String[] propertyKeys) {
		this.mainToken = mainToken;
		this.propertyKeys = propertyKeys;
	}

	@Override
	public String getMainToken() {
		return mainToken;
	}

	@Override
	public String[] getPropertyKeys() {
		return propertyKeys;
	}

	@Override
	public void setProperties(Map<String, String> properties) {
		initializeProperties();
		
		for (String key : properties.keySet()) {
			setProperty(key, properties.get(key));
		}
	}
	
	private void initializeProperties() {
		properties = new HashMap<String, String>();
	}
	
	/**
	 * Set the (string-serialized) value of the property associated with the
	 * specified key. Subclasses should call this method to set a value
	 * that is meant to be stored in the URL fragment identifier's key-value
	 * system. This method will call {@link #processProperty(String, String)},
	 * which is where any local instance variables should be set.
	 * 
	 * @param key
	 * @param value
	 */
	protected void setProperty(String key, String value) {
		if (properties == null)
			initializeProperties();
		
		properties.put(key, value);
		processProperty(key, value);
	}
	
	@Override
	public Map<String, String> getProperties() {
		return properties;
	}
	
	/**
	 * Called from {@link #setProperties(Map)} and
	 * {@link #setProperty(String, String)} . Subclasses must implement this
	 * method. In it they should check which property is
	 * being set, and set some aspect of their state depending on the key
	 * used. See {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlaceImpl}
	 * for an example.
	 * 
	 * @param key
	 * @param value
	 */
	protected abstract void processProperty(String key, String value);
	
	@Override
	public void setPresentationText(String presentationText) {
		this.presentationText = presentationText;
	}

	@Override
	public String getPresentationText() {
		return presentationText;
	}

	@Override
	public String getHistoryToken() {
		return historyToken;
	}

	@Override
	public void setHistoryToken(String historyToken) {
		this.historyToken = historyToken;
	}

	@Override
	public PescadorMVPLocale getNewLocale() {
		return newLocale;
	}

	@Override
	public void setNewLocale(PescadorMVPLocale newLocale) {
		this.newLocale = newLocale;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
	public boolean requiresReload() {
		return requiresReload;
	}

	@Override
	public void setRequiresReload(boolean requiresReload) {
		this.requiresReload = requiresReload;
	}

	@Override
	public Place asGWTPlace() {
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mainToken == null) ? 0 : mainToken.hashCode());
		result = prime * result
				+ ((newLocale == null) ? 0 : newLocale.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + Arrays.hashCode(propertyKeys);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PescadorMVPPlaceBase))
			return false;
		PescadorMVPPlaceBase other = (PescadorMVPPlaceBase) obj;
		if (mainToken == null) {
			if (other.mainToken != null)
				return false;
		} else if (!mainToken.equals(other.mainToken))
			return false;
		if (newLocale == null) {
			if (other.newLocale != null)
				return false;
		} else if (!newLocale.equals(other.newLocale))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (!Arrays.equals(propertyKeys, other.propertyKeys))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String[] splitName = this.getClass().getName().split("\\.");
		String name = splitName[Math.max(0, splitName.length - 1)];
		
		if (properties != null) {
			String separator = "";
			StringBuffer sb = new StringBuffer();
			
			for (String key : properties.keySet()) {
				sb.append(separator + key + "=" + properties.get(key));
				separator = ", ";
			}
			
			return name + " " + sb.toString();
			
		} else {
			return name;
		}
	}
}
