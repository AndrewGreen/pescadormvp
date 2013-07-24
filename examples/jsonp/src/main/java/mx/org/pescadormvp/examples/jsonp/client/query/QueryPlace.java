/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;

/**
 * <p>Interface representing the Query place (in GWT's MVP scheme),
 * in this app the only place we have.</p>
 * 
 * <p>As with standard GWT MVP, places have a representation as fragment identifier
 * of the URL. PescadorMVP places, while retaining compatibility, are more flexible
 * with regard to the information they contain and serialize in the fragment identifier.</p>
 * 
 * @author Andrew Green
 *
 */
public interface QueryPlace extends PescadorMVPPlace {
	
	String getLocation();
	
	void setLocation(String location);

}
