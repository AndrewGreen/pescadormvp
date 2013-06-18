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
 * <p>Query view. As per MVP, views are only responsible for display logic. In
 * GWT, view objects are long-lived, while activities are disposable.</p>
 * 
 * <p>Usage: set the required info, then call one of the render methods.</p>
 *  
 * @author Andrew Green
 */
public interface QueryView extends
		IsWidget {
	
	void renderQueryArea(String beforeQueryTextBox, String afterQueryTextBox);

	boolean isQueryAreaRendered();

	/**
	 * Does the view have the OSMMap to it will use?
	 */
	boolean osmMapSet();
	
	/**
	 * Set the view's OSMMap. 
	 */
	void setOSMMap(OSMMap map);
	
	void setLatLonMsg(String tempInfoMsg);

	void setNoSuchPlaceStrings(String neverHeardOf, String tryAgain);
	
	void setLoadingString(String loading);
	
	void setErrorStrings(String errorCommunicating, String tryAgain);

	/**
	 * Sets the text to display in the textbox. Unlike other methods that set
	 * text, this one renders immediately.
	 * @param textBoxContents
	 */
	void setTextboxContents(String textBoxContents);
	
	void renderEmpty();
	
	void renderLatLon();
	
	void renderNoSuchPlace();
	
	void renderError();
	
	void startLoadingTimer();

	void setRawQueryPlace(QueryPlace rawQueryPlace);
}
