/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import com.google.gwt.activity.shared.AbstractActivity;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity;

/**
 * Activity for query. In this case, it's the only activity the app has. Implementations
 * of this interface are normally instances of GWT's {@link AbstractActivity}. This
 * interface and related classes build on GWT's MPV facilities.
 * 
 * @author Andrew Green
 */
public interface QueryActivity extends 
	PescadorMVPPlaceActivity<QueryView, QueryPlace, QueryComponent> { }
