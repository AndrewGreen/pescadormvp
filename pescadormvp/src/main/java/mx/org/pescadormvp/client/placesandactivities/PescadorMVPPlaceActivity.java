/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import mx.org.pescadormvp.client.components.Component;

import com.google.gwt.user.client.ui.IsWidget;

public interface PescadorMVPPlaceActivity<
		V extends IsWidget, 
		P extends PescadorMVPPlace,
		I extends Component<I>>
		extends PescadorMVPActivity<V, P, I> {

	/**
	 * Used for checking casts of generic types 
	 */
	public Class<P> getPlaceClass();
	
}
