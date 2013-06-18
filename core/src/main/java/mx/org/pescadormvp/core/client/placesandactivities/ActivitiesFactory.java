/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.session.StatePointer;

public interface ActivitiesFactory 
		<S extends StatePointer,
		A extends PescadorMVPActivity<?,?,?>> {
	 
	A create(S statePointer);
}
