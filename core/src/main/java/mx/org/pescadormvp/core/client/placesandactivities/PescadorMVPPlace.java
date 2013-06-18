/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import java.util.Map;

import com.google.gwt.place.shared.Place;

import mx.org.pescadormvp.client.session.StatePointer;
import mx.org.pescadormvp.shared.PescadorMVPLocale;

public interface PescadorMVPPlace extends StatePointer {

	/**
	 * Set via {@link PescadorMVPPAVComponentBase}.
	 * 
	 * @param mainToken
	 */
	void setMainToken(String mainToken);

	String getMainToken();

	String[] getPropertyKeys();

	void setProperties(Map<String, String> properties);

	Map<String, String> getProperties();

	/**
	 * Set the text used to produce a user-friendly reference to this place
	 * to display somewhere in the UI. May not be set when not needed.
	 * Local-specific; when the local changes, this must be re-set.
	 * 
	 */
	void setPresentationText(String presentationText);

	/**
	 * Returns presentation text for this locale. Warning: may return null.
	 * 
	 * @return presentation text
	 */
	String getPresentationText();

	/**
	 * Full history token to use in URLs in UI. Convenience field that may or may not
	 * be set in any given case.
	 * 
	 * @return full history token
	 */
	String getHistoryToken();

	void setHistoryToken(String historyToken);

	/**
	 * If set to non-null, then activating this place results in reloading
	 * the whole app in the new locale. Null is used to indicate the same
	 * locale as is currently active, whatever that may be. 
	 */
	PescadorMVPLocale getNewLocale();

	void setNewLocale(PescadorMVPLocale newLocale);

	String getURL();

	void setURL(String uRL);

	boolean requiresReload();

	void setRequiresReload(boolean requiresReload);
	
	public Place asGWTPlace();

}
