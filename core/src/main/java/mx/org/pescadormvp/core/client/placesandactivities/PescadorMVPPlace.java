/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import java.util.Map;

import com.google.gwt.place.shared.Place;

import mx.org.pescadormvp.core.client.session.StatePointer;
import mx.org.pescadormvp.core.shared.PescadorMVPLocale;

/**
 * A place in Pescador MVP. Has a few extra features that standard GWT
 * {@link Place}s lack. Can always be obtained as a standard GWT {@link Place}
 * via {@link #asGWTPlace()}. See
 * {@link mx.org.pescadormvp.examples.jsonp.client ...examples.jsonp.client} for
 * more information.
 */
@SuppressWarnings("javadoc")
public interface PescadorMVPPlace extends StatePointer {

	/**
	 * Internal Pescador MVP use. Get the main token associated with this place.
	 */
	String getMainToken();

	/**
	 * Internal Pescador MVP use. Get the property keys associated with this
	 * place.
	 */
	String[] getPropertyKeys();

	/**
	 * Set all the place's properties.
	 */
	void setProperties(Map<String, String> properties);

	/**
	 * Internal Pescador MVP use. Get all the place's properties.
	 */
	Map<String, String> getProperties();

	/**
	 * Set the text used to produce a user-friendly reference to this place to
	 * display somewhere in the UI. If not used, there is no need to set this.
	 */
	void setPresentationText(String presentationText);

	/**
	 * Returns presentation text. Warning: may return null.
	 */
	String getPresentationText();

	/**
	 * Full history token to use in URLs. Convenience field that may or may not
	 * be set in any given case. To ensure it's set, call
	 * {@link PescadorMVPPlaceMapper#setupURLInfo(PescadorMVPPlace)
	 * setupURLInfo(PescadorMVPPlace)} with this place.
	 * 
	 * @return full history token
	 */
	String getHistoryToken();

	/**
	 * Internal Pescador MVP use. Set the full history token. Normally this
	 * should be done by calling {@link PescadorMVPPlaceMapper#setupURLInfo(PescadorMVPPlace)
	 * setupURLInfo(PescadorMVPPlace)}.  
	 */
	void setHistoryToken(String historyToken);

	/**
	 * If set to non-null, then going to this place (via standard Pescador MVP
	 * facilities) results in reloading the whole app in the new locale. Don't
	 * set this (or set it to null) to indicate the same locale as is currently
	 * active, whatever that may be.
	 */
	void setNewLocale(PescadorMVPLocale newLocale);
	
	/**
	 * Returns a locale if this place is expected to change the application
	 * to a new local. Returns null if the place requires no locale change. 
	 */
	PescadorMVPLocale getNewLocale();

	/**
	 * Get the URL associated with this place; only available if
	 * {@link PescadorMVPPlaceMapper#setupURLInfo(PescadorMVPPlace)
	 * setupURLInfo(PescadorMVPPlace)} has been called with this place.
	 */
	String getURL();

	/**
	 * Internal Pescador MVP use. Sets the place's URL. Normally this should be
	 * done by calling
	 * {@link PescadorMVPPlaceMapper#setupURLInfo(PescadorMVPPlace)
	 * setupURLInfo(PescadorMVPPlace)}.
	 */
	void setURL(String URL);

	/**
	 * Internal Pescador MVP use. Tells the framework that we need to reload the
	 * app to go to this place (required to go to a different locale).
	 */
	boolean requiresReload();

	/**
	 * Internal Pescador MVP use. Set whether the place requires a reload of the
	 * whole app.
	 */
	void setRequiresReload(boolean requiresReload);
	
	/**
	 * Gets this place object, cast as a GWT {@link Place}. 
	 */
	Place asGWTPlace();
}
