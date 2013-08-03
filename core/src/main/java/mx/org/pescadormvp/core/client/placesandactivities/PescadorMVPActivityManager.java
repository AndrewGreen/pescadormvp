/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Internal Pescador MVP use. Guice-enabled {@link ActivityManager}. 
 */
public class PescadorMVPActivityManager extends ActivityManager {

	@Inject
	public PescadorMVPActivityManager(
			@Assisted ActivityMapper mapper,
			EventBus eventBus) {
		super(mapper, eventBus);
	}
}
