/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase;
import mx.org.pescadormvp.core.client.regionsandcontainers.DynamicContainer.ContainerDeactivation;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class NullPanelTools {

	/**
	 * Stub view to be used with null activities (when a region of the
	 * screen has no activity and is hidden).
	 * 
	 *  Although this class extends {@link Widget}, it implements no actual 
	 *  UI functionality.
	 */
	public static class NullView extends Widget implements ContainerDeactivation {

		@Override
		public Widget asWidget() {
			return this;
		}
	}
	
	/**
	 * Placeholder. Abstract so we don't have to implement stuff. 
	 */
	public static abstract class NullComponent 
			implements Component<NullComponent> { }
	
	/**
	 * Stub activity that simply deactivates its corresponding window area 
	 */
	public static class NullActivity 
		extends PescadorMVPPlaceActivityBase<NullView, PescadorMVPPlace, NullComponent> {

		private static final NullView nullView = new NullView();
		
		public NullActivity() {
			super(null);
			setView(nullView);
		}

		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			/* Rightly, we should check that panel is in fact an instance of
			 * DynamicContainer, but because GWT actually sends us a wrapper class
			 * instead of our actual container class, this is impossible.
			 */
			NullView view = getView();
			panel.setWidget(view);
		}

		@Override
		protected Class<NullComponent> getComponentClass() {
			return NullComponent.class;
		}

		@Override
		public Class<PescadorMVPPlace> getPlaceClass() {
			return PescadorMVPPlace.class;
		}
	}
}
