/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.components.Component;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * An activity associated with a place in Pescador MVP; interface implemented by
 * all activities that are part of {@link PAVComponent}s in Pescador MVP.
 * 
 * @param <V>
 *            The public interface of view associated with this activity.
 * @param <P>
 *            The {@link PescadorMVPPlace} class associated with the activation
 *            of this activity.
 * @param <I>
 *            The {@link Component} that this activity is a part of.
 */
public interface PescadorMVPPlaceActivity<
		V extends IsWidget, 
		P extends PescadorMVPPlace,
		I extends Component<I>>
		extends PescadorMVPActivity<V, P, I> {

	/**
	 * The place class associated with the activation of this activity. Place
	 * activities must implement this; it's used for checking casts of generic
	 * types.
	 */
	public Class<P> getPlaceClass();
	
	/**
	 * Internal Pescador MVP use. Set the place to be used by this activity. 
	 */
	void setPlace(P place);
	
}
