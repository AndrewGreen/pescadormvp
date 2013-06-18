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


public interface RegionManager {

	public HasRegions getRegionsWidget();
	
	public void setRegionsWidget(HasRegions regionsWidget);
	
	public Set<Class<? extends ForRegionTag>> getRegions();
}