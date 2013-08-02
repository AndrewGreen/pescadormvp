/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

import java.util.Set;

import mx.org.pescadormvp.core.client.placesandactivities.PAVComponent;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;

/**
 * Internal Pescador MVP use. Registry of components. Pescador MVP
 * applications shouldn't need to access this directly.
 */
public interface ComponentRegistry {

	/**
	 * Add components to the registry.
	 * 
	 * @param components
	 *            The components to be added.
	 */
	void addComponents(Component<?>... components);

	/**
	 * Call {@link Component#finalizeSetup() finalizeSetup()} on all components
	 * so they can do stuff that can only be done once all components have been
	 * registered.
	 */
	void callFinalizeSetup();

	/**
	 * Get the component that has this public interface.
	 * 
	 * @param publicInterface
	 *            The public interface of the component being requested.
	 * @return The component requested, or <code>null</code> if none was found
	 *         for this interface.
	 */
	<I extends Component<I>> I getComponent(Class<I> publicInterface);

	/**
	 * Get the place-activity-view (PAV) component associated with this main
	 * token, as defined in the {@link PescadorMVPPlace Place} associated with
	 * the component. (The main token is the first part of history tokens/URL
	 * fragment identifiers.)
	 * 
	 * @param token
	 *            The main token for the PAV component being requested.
	 * @return The PAV component requested, or <code>null</code> if none was
	 *         found for this main token.
	 */
	PAVComponent<?, ?> getPAVComponent(String token);

	/**
	 * Establish the regions available in the UI.
	 * 
	 * @param regions
	 *            A set containing the tag interfaces that refer to regions
	 *            available in the UI.
	 */
	void setRegions(Set<Class<? extends ForRegionTag>> regions);
}
