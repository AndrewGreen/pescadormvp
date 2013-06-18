/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.session;

import java.util.List;

import mx.org.pescadormvp.client.components.Component;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.shared.PescadorMVPLocale;

public interface Session extends Component<Session>{

	PescadorMVPLocale currentLocale();
	
	PescadorMVPLocale fallbackLocale();
	
	List<PescadorMVPLocale> availableLocales();
	
	/**
	 * Returns an integer that represents the current state, taking into
	 * account place change events and locale.
	 * 
	 * @return integer representing current state
	 */
	int currentStateHash();

	void goTo(PescadorMVPPlace place);

	PescadorMVPPlace getWhere();

	void setSessionData(Class<? extends Component<?>> component, SessionData data);

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
