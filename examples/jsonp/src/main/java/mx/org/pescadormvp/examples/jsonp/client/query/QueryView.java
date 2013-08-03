/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * <p>
 * Query View, with various renderable states, to be controlled by the activity.
 * </p>
 * <p>
 * The renderable states are:
 * </p>
 * <ol>
 * <li><i>Empty</i>&nbsp;&nbsp;&nbsp;In this state, no messages or information
 * are shown.</li>
 * <li><i>Loading</i>&nbsp;&nbsp;&nbsp;The view displays a message saying that
 * the requested information is loading.</li>
 * <li><i>Error</i>&nbsp;&nbsp;&nbsp;The view displays a message explaining that
 * there was an error communicating with the server.</li>
 * <li><i>Lat-lon</i>&nbsp;&nbsp;&nbsp;The view displays a place's latitude and
 * longitude, and a map.</li>
 * <li><i>No such place</i>&nbsp;&nbsp;&nbsp;The view displays a message
 * explaining that no place was found with the name entered.</li>
 * </ol>
 * <p>
 */
public interface QueryView extends
		IsWidget {
	
	void renderQueryArea(String beforeQueryTextBox, String afterQueryTextBox);

	/**
	 * Indicates whether the query area has been rendered or not. 
	 */
	boolean isQueryAreaRendered();

	/**
	 * Does the view have the OSMMap it will use?
	 */
	boolean osmMapSet();
	
	/**
	 * Set the view's OSMMap. 
	 */
	void setOSMMap(OSMMap map);
	
	/**
	 * Sets latitude and longitude message.
	 */
	void setLatLonMsg(String latLonMsg);

	/**
	 * Strings to display when no matching place has been found.
	 */
	void setNoSuchPlaceStrings(String neverHeardOf, String tryAgain);
	
	/**
	 * Set string for loading message. 
	 */
	void setLoadingString(String loading);
	
	/**
	 * Set strings to display for a server or network error. 
	 */
	void setErrorStrings(String errorCommunicating, String tryAgain);

	/**
	 * Sets the text to display in the text box. Unlike other methods that set
	 * text, this one renders immediately.
	 */
	void setTextboxContents(String textBoxContents);
	
	/**
	 * Render an empty view (no messages).
	 */
	void renderEmpty();
	
	void renderLatLon();
	
	void renderNoSuchPlace();
	
	void renderError();
	
	/**
	 * Start a timer; if no other state has been rendered by a certain time,
	 * display a message saying that the requested information is loading.
	 */
	void scheduleLoadingMessage();

	/**
	 * Cancel the loading timer and, if the loading message is being displayed,
	 * the related animation. 
	 */
	void cancelLoadingMessage();

	/**
	 * Set the place object, with no data yet, needed to prepare the next query.
	 */
	void setRawQueryPlace(QueryPlace rawQueryPlace);
}
