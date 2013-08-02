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
import mx.org.pescadormvp.core.client.session.StatePointer;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Interface implemented by all activities in PescadorMVP.
 * 
 * @author Andrew Green
 *
 * @param <V> The view class associated with this activity. 
 * @param <S> The {@link StatePointer} class associated with the activation of this activity. 
 * @param <I> The {@link Component} that this activity is a part of.
 */
public interface PescadorMVPActivity<
		V extends IsWidget, 
		S extends StatePointer,
		I extends Component<I>>
		extends Activity, PlaceRequestEvent.Handler {

	
	void setStatePointer(S StatePointer);
}
