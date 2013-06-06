/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.jsonpexample.client.query;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.layer.OSM;

import com.google.gwt.user.client.ui.Composite;

/**
 * The MapWidget class provided by OpenLayers is a widget, but using setting
 * it up involves tasks that are beyond the scope of our view. So here is a little
 * class that encapsulates the logic for the map setup and manipulation.
 *  
 * @author Andrew Green
 */
public class OSMMapImpl extends Composite implements OSMMap {

	private Projection DEFAULT_PROJECTION = new Projection("EPSG:4326");

	private MapWidget mapWidget;
	private Map map;
	private MapOptions defaultMapOptions = new MapOptions();

	public OSMMapImpl() {
		mapWidget = new MapWidget("100%", "100%", defaultMapOptions);
		OSM osm = OSM.Mapnik("Mapnik");
		osm.setIsBaseLayer(true);
		map = mapWidget.getMap();
		map.addLayer(osm);

		initWidget(mapWidget);
	}

	@Override
	public void setLatLon(double lat, double lon) {
		LonLat lonLat = new LonLat(lon, lat);
		lonLat.transform(DEFAULT_PROJECTION.getProjectionCode(), map.getProjection());
		map.setCenter(lonLat, 12);
	}
}
