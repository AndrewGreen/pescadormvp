/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.jsonpexample.client.query;

import mx.org.pescadormvp.client.placesandactivities.RawDefaultPlaceProvider;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPAVComponent;

/**
 * The interface representing the general Query component. If we wanted this component
 * to provide methods to other components, they would go here.
 * 
 * @author Andrew Green
 *
 */
public interface QueryComponent 
		extends PescadorMVPPAVComponent<
		QueryComponent, 
		QueryPlace>,
		RawDefaultPlaceProvider { }
