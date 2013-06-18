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
 * PescadorMVP activities extend this. They should override the start
 * method, and
 * attach the view widget to the appropriate container there.
 *
 */
public abstract class
	PescadorMVPPlaceActivityBase<
			V extends IsWidget,
			P extends PescadorMVPPlace,
			I extends Component<I>> 
	extends PescadorMVPActivityBase<V,P,I>
	implements PescadorMVPPlaceActivity<V,P,I> {

	
	// The place is gotten by subclasses via
	// assisted inject.
	public PescadorMVPPlaceActivityBase(
			P place) {
		super(place);
	}

	protected P getPlace() {
		return getStatePointer();
	}
}
