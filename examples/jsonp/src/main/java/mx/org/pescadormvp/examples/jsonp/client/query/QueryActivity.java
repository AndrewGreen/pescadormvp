/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity;

/**
 * Activity for the Query Component in the
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body} region
 * in the JSONP Example app.
 * It's the only activity the component has.
 */
public interface QueryActivity extends 
	PescadorMVPPlaceActivity<QueryView, QueryPlace, QueryComponent> { }
