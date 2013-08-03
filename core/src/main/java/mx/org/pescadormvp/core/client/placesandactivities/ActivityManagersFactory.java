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

/**
 * Internal Pescador MVP use. A factory that creates {@link ActivityManager}s
 * the Guice way. 
 */
public interface ActivityManagersFactory {

	/**
	 * Create an {@link ActivityManager}.
	 */
	ActivityManager create(ActivityMapper mapper);
}
