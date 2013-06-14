/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.internallinks;

import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlace;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ActivateInternalLinkEvent
		extends GwtEvent<ActivateInternalLinkEvent.Handler> {

	public interface Handler extends EventHandler {
		void onActivateInternalLink(ActivateInternalLinkEvent event);
	}
	
	public interface HasActivateInternalLinkHandlers extends HasHandlers {
		HandlerRegistration addActivateInternalLinkHandler(
				Handler handler);
	}
	
	public static final Type<Handler> TYPE = new Type<Handler>();

	private final PescadorMVPPlace place;
	
	public ActivateInternalLinkEvent(PescadorMVPPlace place) {
		this.place = place;
	}
	
	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	public PescadorMVPPlace getPlace() {
		return place;
	}
	
	@Override
	protected void dispatch(Handler handler) {
		handler.onActivateInternalLink(this);
	}

}
