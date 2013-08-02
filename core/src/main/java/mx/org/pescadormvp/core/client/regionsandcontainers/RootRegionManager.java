/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

/**
 * A class that manages UI regions designated via {@link ForRegionTag}s and
 * implemented with a {@link RootHasFixedSetOfRegions} widget.
 */
public interface RootRegionManager extends
		RegionManager {

	/**
	 * Internal Pescador MVP use. Set the {@link RootHasFixedSetOfRegions} widget that provides containers
	 * for UI regions. Such a widget is normally used for global UI layout
	 * in Pescador MVP.
	 */
	public RootHasFixedSetOfRegions getRootRegionsWidget();

	/**
	 * Internal Pescador MVP use. Set the {@link RootHasFixedSetOfRegions} widget that provides containers
	 * for UI regions. Such a widget is normally used for global UI layout
	 * in Pescador MVP.
	 */
	public void setRootRegionsWidget(RootHasFixedSetOfRegions regionsWidget);
}
