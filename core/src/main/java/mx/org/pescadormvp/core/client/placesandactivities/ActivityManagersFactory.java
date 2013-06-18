/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;

public interface ActivityManagersFactory {

	ActivityManager create(ActivityMapper mapper);
}
