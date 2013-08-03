/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.layer.OSM;

import com.google.gwt.user.client.ui.Composite;

/**
 * A small wrapper for the OpenLayers {@link MapWidget} (implementation). It's been kept separate from
 * the view and the activity because it contains a bit of logic related to map
 * rendering that doesn't fit in either.
 */
public class OSMMapImpl extends Composite implements OSMMap {

	private Projection defaultProjection;

	private MapWidget mapWidget;
	private Map map;
	private MapOptions defaultMapOptions = new MapOptions();

	public OSMMapImpl() {
		mapWidget = new MapWidget("100%", "100%", defaultMapOptions);
		OSM osm = OSM.Mapnik("Mapnik");
		osm.setIsBaseLayer(true);
		map = mapWidget.getMap();
		map.addLayer(osm);
		defaultProjection = new Projection("EPSG:4326");

		initWidget(mapWidget);
	}

	@Override
	public void setLatLon(double lat, double lon) {
		LonLat lonLat = new LonLat(lon, lat);
		lonLat.transform(defaultProjection.getProjectionCode(), map.getProjection());
		map.setCenter(lonLat, 12);
	}
}
