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
import mx.org.pescadormvp.client.internallinks.ActivateInternalLinkEvent;
import mx.org.pescadormvp.client.session.StatePointer;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.IsWidget;

public interface PescadorMVPActivity<
		V extends IsWidget, 
		S extends StatePointer,
		I extends Component<I>>
		extends Activity, ActivateInternalLinkEvent.Handler {

}
