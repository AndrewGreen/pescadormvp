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
 * An entity that, for example a {@link DynamicContainer}, is a
 * UI region and provide the tag interface that designates that region. 
 */
public interface IsRegion {

	/**
	 * Get the tag interface that designates this region.  
	 */
	public Class<? extends ForRegionTag> getRegionTag();
}
