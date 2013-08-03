/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import java.util.Set;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.components.GlobalSetup;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.core.client.session.SessionData;

/**
 * A place-activity-view component. A special kind of component that associates
 * a place class with activity classes, view classes and regions of the UI.
 * 
 * @param <I>
 *            The component's public interface.
 * @param <P>
 *            The place class associated with this component.
 */
public interface PAVComponent<
		// public interface offered as a component
		I extends PAVComponent<I, P>,

		// place we're binding to
		P extends PescadorMVPPlace>

		extends Component<I> {

	/**
	 * Get a new place of the class associated with this {@link PAVComponent}.
	 * This place normally won't have any extra info, URL, presentation text or
	 * any other stuff yet.
	 */
	P getRawPlace();

	/**
	 * Get the set of regions that are associated with
	 * {@link PescadorMVPPlaceActivity PescadorMVPPlaceActivities} and views for
	 * this this {@link PAVComponent}.
	 */
	Set<Class<? extends ForRegionTag>> handlesRegions();

	/**
	 * Internal Pescador MVP use. Get a new activity for the region specified, and provide it with the
	 * place specified.
	 * 
	 * @param region
	 *            The UI region for which you want a new activity.
	 * @param place
	 *            The place that this activity will use (accessible from the
	 *            within the activity via
	 *            {@link PescadorMVPPlaceActivityBase#getPlace() getPlace()}).
	 * @return The new activity.
	 */
	<A extends PescadorMVPPlaceActivity<?, P, ?>>
			A getActivity(Class<? extends ForRegionTag> region, P place);

	/**
	 * Get this {@link PAVComponent}'s {@link SessionData}. (And if it hasn't
	 * been created yet, create it first.)
	 */
	<S extends SessionData> S ensureSessionData();

	/**
	 * Called by {@link GlobalSetup} on components once all components have been
	 * registered. Don't call it yourself, just implement it to do stuff that
	 * can only be done once all components are registered, or leave it empty if
	 * your component has nothing to do at that time. Note:
	 * {@link PAVComponentBase} provides a default empty implementation.
	 */
	@Override
	void finalizeSetup();
}
