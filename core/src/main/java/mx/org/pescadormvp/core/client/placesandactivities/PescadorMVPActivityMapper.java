/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.components.ComponentSetup;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PescadorMVPActivityMapper implements ActivityMapper {

	private final Class<? extends ForRegionTag> region;
	private final ComponentSetup componentSetup;
	
	@Inject
	PescadorMVPActivityMapper(
			@Assisted Class<? extends ForRegionTag> region,
			ComponentSetup componentSetup) {
		this.region = region;
		this.componentSetup = componentSetup;
	}

	@Override
	public Activity getActivity(Place place) {
		if (!(place instanceof PescadorMVPPlace))
			throw new IllegalArgumentException();
		
		return componentSetup
				.getActivityForRegionAndPlace(region, (PescadorMVPPlace) place);
	}
}
