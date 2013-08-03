/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 *  A {@link HasFixedSetOfRegions} that has a method for attaching itself
 *  to the {@link RootLayoutPanel}.
 */
public interface RootHasFixedSetOfRegions extends HasFixedSetOfRegions {

	public void attach();
}
