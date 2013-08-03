/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

/**
 * A class that has regions designated by a subinterface of {@link ForRegionTag}
 * and can provide {@link DynamicContainer}s for them.
 */
public interface HasRegions {

	/**
	 * Get the {@link DynamicContainer} for the region specified. 
	 */
	public DynamicContainer getContainer(Class<? extends ForRegionTag> regionTag);
}
