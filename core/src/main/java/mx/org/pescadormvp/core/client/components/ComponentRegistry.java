/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

import java.util.Set;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;

/**
 * Registry of components.
 * 
 * <p>By keeping this separate from {@link ComponentSetup} we avoid circular
 * dependencies.</p>
 * 
 * @author Andrew Green
 *
 */
public interface ComponentRegistry {

	void addComponents(Component<?>... components);

	void callFinalizeSetup();

	<I extends Component<I>> I getComponent(Class<I> publicInterface);

	PescadorMVPPAVComponent<?, ?> getPAVComponent(String token);

	void setRegions(Set<Class<? extends ForRegionTag>> regions);
}
