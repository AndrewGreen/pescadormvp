/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.regionsandcontainers;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;

public class RegionStateUpdateRequiredEvent extends
		GwtEvent<RegionStateUpdateRequiredEvent.Handler> {

	public interface Handler extends EventHandler {
		void onRegionStateUpdateRequired(RegionStateUpdateRequiredEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final Boolean activate;
	private final List<Widget> addAbsPosWidgets;
	private final List<Widget> removeAbsPosWidgets;
	private final Integer requiredWidth;
	private final Integer requiredHeight;
	
	public RegionStateUpdateRequiredEvent(
			Boolean activate,
			List<Widget> addAbsPosWidgets,
			List<Widget> removeAbsPosWidgets,
			Integer requiredWidth,
			Integer requiredHeight) {
		this.activate = activate;
		this.addAbsPosWidgets = addAbsPosWidgets;
		this.removeAbsPosWidgets = removeAbsPosWidgets;
		this.requiredWidth = requiredWidth;
		this.requiredHeight = requiredHeight;
	}

	public Integer getRequiredWidth() {
		return requiredWidth;
	}

	public Integer getRequiredHeight() {
		return requiredHeight;
	}

	public Boolean getActivate() {
		return activate;
	}
	
	public List<Widget> getAddAbsPosWidgets() {
		return addAbsPosWidgets;
	}

	public List<Widget> getRemoveAbsPosWidgets() {
		return removeAbsPosWidgets;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onRegionStateUpdateRequired(this);
	}
}
