/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import org.gwtopenmaps.openlayers.client.MapWidget;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A small wrapper for the OpenLayers {@link MapWidget}. It's been kept separate from
 * the view and the activity because it contains a bit of logic related to map
 * rendering that doesn't fit in either.
 */
public interface OSMMap extends IsWidget {

	void setLatLon(double lat, double lon); 
}
