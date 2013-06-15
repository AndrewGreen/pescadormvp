/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.regionsandcontainers;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * <p>
 * Widget containers whose {@link AcceptsOneWidget#setWidget} method has extra
 * functionality: when the widget is set to an instance of
 * {@link ContainerDeactivation}, any real widgets are removed from the
 * container, and the container is hidden or otherwise disposed in some way
 * within the current UI context. In the Pescador
 * implementation, this means the container is hidden.
 * </p>
 * <p>
 * Implementations so far set commands for activation and deactivation, and for
 * adding/removing absolutely-placed widgets related to the container. (So far,
 * these are widgets that appear visually within the container, but for
 * implementation reasons, are actually not within it in the DOM hierarchy.)
 * </p>
 */
public interface DynamicContainer extends AcceptsOneWidget, IsWidget,
		RegionStateUpdateRequiredEvent.Handler {
	
	public interface ContainerDeactivation extends IsWidget { }

	void setEventBus(EventBus eventBus);
}
