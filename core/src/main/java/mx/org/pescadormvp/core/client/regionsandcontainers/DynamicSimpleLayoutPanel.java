/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import java.util.List;


import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * <p>
 * An implementation of {@link DynamicContainer}.
 * </p>
 * <p>
 * This widget can be told to de-activate or have a minimum height or width, can
 * pass those messages on (probable to a larger layout widget it's a part of),
 * and can be associated with absolutely positioned widgets not visually
 * contained within it.
 * </p>
 */
public class DynamicSimpleLayoutPanel extends SimpleLayoutPanel
	implements DynamicContainer, IsRegion {
	
	private IsWidget lastWidget;
	private Class<? extends ForRegionTag> regionTag;
	private EventBus eventBus;
	private HandlerRegistration handlerReg;
	
	public Class<? extends ForRegionTag> getRegionTag() {
		return regionTag;
	}

	public void setRegionTag(Class<? extends ForRegionTag> regionTag) {
		this.regionTag = regionTag;
	}

	// can't inject this; class can't use injection because it's instantiated
	// via uiBinder
	/**
	 * Don't forget to call this after creating one of these!
	 * 
	 * @param eventBus
	 */
	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public void setWidget(IsWidget w) {

		// remove absolute widgets of previous contained widget
		if (lastWidget instanceof HasAbsolutelyPositionedWidgets) {
			HasAbsolutelyPositionedWidgets lastWidgetWithAbs =
					(HasAbsolutelyPositionedWidgets) lastWidget;

			eventBus.fireEventFromSource(
					new RegionStateUpdateRequiredEvent(
					null, null,
					lastWidgetWithAbs.getAbsolutelyPositionedWidgets(),
					null, null),
					this);

		}
		
		// unregister handling of update required space events from last widget
		if (handlerReg != null) {
			handlerReg.removeHandler();
			handlerReg = null;
		}
			
		// deactivate if we have a deactivation widget
		if (w instanceof ContainerDeactivation) { // this checks for null, too
			super.setWidget(null);
			
			eventBus.fireEventFromSource(
					new RegionStateUpdateRequiredEvent(
					false, null, null, null, null),
					this);
			
		} else {
			
			// otherwise activate and add absolute widgets of new widget
			
			List<Widget> widgetsToAdd;

			if (w instanceof HasAbsolutelyPositionedWidgets)
				widgetsToAdd = ((HasAbsolutelyPositionedWidgets) w).
						getAbsolutelyPositionedWidgets();
			else
				widgetsToAdd = null;
			
			eventBus.fireEventFromSource(
					new RegionStateUpdateRequiredEvent(
					true, widgetsToAdd, null, null, null),
					this);
			

			// register for update required space events from current widget
			// check that we're not getting a null widget first
			if (w != null)
				handlerReg = eventBus.addHandlerToSource(
							RegionStateUpdateRequiredEvent.TYPE, w, this);
			
			super.setWidget(w);
		}
				
		lastWidget = w;
	}

	@Override
	public void onRegionStateUpdateRequired(RegionStateUpdateRequiredEvent event) {
		// The widget we hold has told us it needs certain conditions
		// Fire off an event from this class, with the same info;
		// we expect that it'll be heard by the HasRegions that manages
		// this container.
		
		eventBus.fireEventFromSource(
				new RegionStateUpdateRequiredEvent(
				event.getActivate(),
				event.getAddAbsPosWidgets(),
				event.getRemoveAbsPosWidgets(),
				event.getRequiredWidth(),
				event.getRequiredHeight()),
				this);
	}
}
