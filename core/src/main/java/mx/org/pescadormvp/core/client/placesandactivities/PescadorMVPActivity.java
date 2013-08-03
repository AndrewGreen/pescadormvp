/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.session.StatePointer;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * <p>
 * An activity in Pescador MVP; interface implemented by all activities in
 * Pescador MVP.
 * </p>
 * <p>
 * Note that this interface is implemented by activities not associated with a
 * place, as well as those associated with one. Activities associated with a
 * place (as part of a {@link PAVComponent}) should implement the subinterface,
 * {@link PescadorMVPPlaceActivity}.
 * </p>
 * 
 * @param <V>
 *            The public interface of view associated with this activity.
 * @param <S>
 *            The {@link StatePointer} class (often a subinterface of
 *            {@link PescadorMVPPlace}) associated with the activation of this
 *            activity.
 * @param <I>
 *            The {@link Component} that this activity is a part of.
 */
public interface PescadorMVPActivity<V extends IsWidget, 
		S extends StatePointer, 
		I extends Component<I>>
		extends Activity, PlaceRequestEvent.Handler {
	
	/**
	 * Set the state pointer associated with the activity. Standard activities
	 * associated with a place won't need to call this.
	 */
	void setStatePointer(S StatePointer);
}
