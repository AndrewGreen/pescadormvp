/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.session;

import java.util.List;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.shared.PescadorMVPLocale;

/**
 * Session component. Provides information on the current locale and available
 * locales, as well as methods for going to places, storing session data, and
 * generating unique IDs.
 */
public interface Session extends Component<Session>{

	/**
	 * Get the application's current locale. 
	 */
	PescadorMVPLocale currentLocale();
	
	/**
	 * Get the locales available for the application. 
	 */
	List<PescadorMVPLocale> availableLocales();
	
	/**
	 * Returns an integer that represents the current state, taking into
	 * account place change events and locale.
	 * 
	 * @return integer representing current state
	 */
	int currentStateHash();

	/**
	 * Go to the place specified. 
	 */
	void goTo(PescadorMVPPlace place);

	/**
	 * Get the current place. 
	 */
	PescadorMVPPlace getWhere();

	/**
	 * Set session data for the component specified. 
	 */
	void setSessionData(Class<? extends Component<?>> component, SessionData data);

	/**
	 * Get session data for the component specified. 
	 */
	SessionData getSessionData(Class<? extends Component<?>> component);

	/**
	 * Get a string that will be unique for the current session and place.
	 * Includes current place count and current session ID.
	 *  
	 * @return unique string
	 */
	String currentPlaceSessionID();
	
	/**
	 * Get a string that will probably be unique. Useful when we need a unique
	 * ID that can't be linked to a place in the current session's history.
	 * Includes current session ID.
	 * 
	 * @return unique string
	 */
	String getUniqueID();
}
