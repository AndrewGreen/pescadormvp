/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.jsonpexample.client.query;

import com.google.gwt.user.client.ui.IsWidget;

public interface OSMMap extends IsWidget {

	void setLatLon(double lat, double lon); 
}
