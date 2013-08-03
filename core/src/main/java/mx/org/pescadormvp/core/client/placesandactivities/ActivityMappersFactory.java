/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import com.google.gwt.activity.shared.ActivityMapper;

import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;

/**
 * Internal Pescador MVP use. A factory that creates {@link ActivityMapper}s
 * the Guice way. 
 */
public interface ActivityMappersFactory {

	/**
	 * {@link PescadorMVPActivityMapper} for the UI region indicated.
	 */
	PescadorMVPActivityMapper create(
			Class<? extends ForRegionTag> region);
}
