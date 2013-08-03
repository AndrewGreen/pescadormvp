/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * A special container that can be told to de-activate or have a minimum height
 * or width, can pass those messages on (probable to a larger layout widget it's
 * a part of), and can be associated with absolutely positioned widgets not
 * visually contained within it.
 */
public interface DynamicContainer extends AcceptsOneWidget, IsWidget,
		RegionStateUpdateRequiredEvent.Handler {
	
	public interface ContainerDeactivation extends IsWidget { }

	void setEventBus(EventBus eventBus);
}
