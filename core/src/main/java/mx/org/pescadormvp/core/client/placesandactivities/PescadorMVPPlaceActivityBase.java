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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Abstract base class for {@link PescadorMVPPlaceActivity
 * PescadorMVPPlaceActivities}. Subclasses should implement the
 * {@link Activity#start start(...)} method, and attach the view widget to the
 * appropriate container there.
 * 
 * @param <V>
 *            The public interface of view associated with this activity.
 * @param <P>
 *            The {@link PescadorMVPPlace} class associated with the activation
 *            of this activity.
 * @param <I>
 *            The {@link Component} that this activity is a part of.
 */
public abstract class
	PescadorMVPPlaceActivityBase<
			V extends IsWidget,
			P extends PescadorMVPPlace,
			I extends Component<I>> 
	extends PescadorMVPActivityBase<V,P,I>
	implements PescadorMVPPlaceActivity<V,P,I> {

	/**
	 * Get the current {@link PescadorMVPPlace} object that brought us here.
	 */
	protected P getPlace() {
		return getStatePointer();
	}
	
	@Override
	public void setPlace(P place) {
		setStatePointer(place);
	}
}
