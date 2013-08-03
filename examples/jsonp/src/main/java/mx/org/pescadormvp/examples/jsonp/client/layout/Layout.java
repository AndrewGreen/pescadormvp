/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.layout;

import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.core.client.regionsandcontainers.RootHasFixedSetOfRegions;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

/**
 * The global layout system for JSONP Example. In this app, there is only one viewport
 * region, but in larger apps there are usually many.
 */
public interface Layout extends RequiresResize, ProvidesResize,
		RootHasFixedSetOfRegions {

	/**
	 * A tag interface designating the only region in the JSONP Example app.
	 * 
	 * @author Andrew Green
	 */
	public interface Body extends ForRegionTag { }
	
}
