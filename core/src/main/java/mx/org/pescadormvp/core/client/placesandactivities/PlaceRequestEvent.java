/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;


import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * An event indicating that something, somewhere, wants the application
 * to go to the specified place. 
 */
public class PlaceRequestEvent
		extends GwtEvent<PlaceRequestEvent.Handler> {

	public interface Handler extends EventHandler {
		void onPlaceRequest(PlaceRequestEvent event);
	}
	
	public interface HasPlaceRequestHandlers extends HasHandlers {
		HandlerRegistration addPlaceRequestHandler(
				Handler handler);
	}
	
	public static final Type<Handler> TYPE = new Type<Handler>();

	private final PescadorMVPPlace place;
	
	public PlaceRequestEvent(PescadorMVPPlace place) {
		this.place = place;
	}
	
	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Get the place that has been requested. 
	 */
	public PescadorMVPPlace getPlace() {
		return place;
	}
	
	@Override
	protected void dispatch(Handler handler) {
		handler.onPlaceRequest(this);
	}

}
