/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.components.GlobalSetup;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Internal Pescador MVP use. A Guice-enabled {@link ActivityMapper}, associated
 * with a UI region.
 */
public class PescadorMVPActivityMapper implements ActivityMapper {

	private final Class<? extends ForRegionTag> region;
	private final GlobalSetup globalSetup;
	
	@Inject
	PescadorMVPActivityMapper(
			@Assisted Class<? extends ForRegionTag> region,
			GlobalSetup globalSetup) {
		this.region = region;
		this.globalSetup = globalSetup;
	}

	/**
	 * Internal Pescador MVP use. Provide an activity for the specified place,
	 * associated with this {@link PescadorMVPActivityMapper}'s region.
	 */
	@Override
	public Activity getActivity(Place place) {
		if (!(place instanceof PescadorMVPPlace))
			throw new IllegalArgumentException();
		
		return globalSetup
				.getActivityForRegionAndPlace(region, (PescadorMVPPlace) place);
	}
}
