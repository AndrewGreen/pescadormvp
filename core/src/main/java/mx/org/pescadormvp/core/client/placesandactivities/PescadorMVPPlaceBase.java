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
 * <p>A WebClientPlace contains a main token which designates the general
 * type of place, plus a set of property-value pairs that provide
 * further information about the place. Which properties are implemented
 * will vary from depending on the concrete WebClientPlace subclass. The
 * information in the main token and properties is expressed in 
 * the fragment part of the URL.</p>
 * 
 * <p>Use {@link #setNewLocale(PescadorMVPLocale)} to indicate that going
 * to this place should activate a new locale. This will result in a page
 * reload. Don't set this field, or set it to null, to stay in the current
 * locale.</p>
 * 
 */
public abstract class PescadorMVPPlaceBase extends Place implements PescadorMVPPlace {

	private String mainToken;
	private String[] propertyKeys;
	private Map<String, String> properties;
	private PescadorMVPLocale newLocale;
	
	private String presentationText;
	private String historyToken;
	private String URL;
	private boolean requiresReload;

	public PescadorMVPPlaceBase() {
		super();
	}

	/**
	 * Set via {@link PescadorMVPPAVComponentBase}.
	 * 
	 * @param mainToken
	 */
	@Override
	public void setMainToken(String mainToken) {
		this.mainToken = mainToken;
	}

	@Override
	public String getMainToken() {
		return mainToken;
	}

	@Override
	public String[] getPropertyKeys() {
		return propertyKeys;
	}

	protected void setPropertyKeys(String[] propertyKeys) {
		this.propertyKeys = propertyKeys;
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
	 * Called from {@link #setProperties(Map)} for each property set.
	 * Subclasses will check the property being set, and set some aspect of
	 * their state if an appropriate key is used.
	 * 
	 * @param key
	 * @param value
	 */
	protected abstract void processProperty(String key, String value);
	
	/**
	 * Set the text used to produce a user-friendly reference to this place
	 * to display somewhere in the UI. May not be set when not needed.
	 * Local-specific; when the local changes, this must be re-set.
	 * 
	 */
	@Override
	public void setPresentationText(String presentationText) {
		this.presentationText = presentationText;
	}

	/**
	 * Returns presentation text for this locale. Warning: may return null.
	 * 
	 * @return presentation text
	 */
	@Override
	public String getPresentationText() {
		return presentationText;
	}

	/**
	 * Full history token to use in URLs in UI. Convenience field that may or may not
	 * be set in any given case.
	 * 
	 * @return full history token
	 */
	@Override
	public String getHistoryToken() {
		return historyToken;
	}

	@Override
	public void setHistoryToken(String historyToken) {
		this.historyToken = historyToken;
	}

	/**
	 * If set to non-null, then activating this place results in reloading
	 * the whole app in the new locale. Null is used to indicate the same
	 * locale as is currently active, whatever that may be. 
	 */
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
		
		String separator = "";
		StringBuffer sb = new StringBuffer();
		for (String key : properties.keySet()) {
			sb.append(separator + key + ":" + properties.get(key));
			separator = ", ";
		}
		
		return splitName[Math.max(0, splitName.length - 1)] + " | " + 
				sb.toString();
	}
}
