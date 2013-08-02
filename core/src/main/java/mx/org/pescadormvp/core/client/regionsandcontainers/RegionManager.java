/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import java.util.Set;

/**
 * A class that manages UI regions designated via {@link ForRegionTag}s and
 * implemented with a {@link HasRegions} widget.
 */
public interface RegionManager {

	/**
	 * Get the {@link HasRegions} widget that provides containers for UI regions.
	 */
	public HasRegions getRegionsWidget();

	/**
	 * Set the {@link HasRegions} widget that provides containers for UI regions.
	 */
	public void setRegionsWidget(HasRegions regionsWidget);

	/**
	 * Get the UI regions managed by this {@link RegionManager}. 
	 */
	public Set<Class<? extends ForRegionTag>> getRegions();
}
