/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import java.util.Set;

/**
 * A class that not only {@link HasRegions}, but that has a fixed set of them,
 * and can tell you about them.
 */
public interface HasFixedSetOfRegions extends HasRegions {

	/**
	 * The set of regions. 
	 */
	public Set<Class<? extends ForRegionTag>> getRegions();
}
