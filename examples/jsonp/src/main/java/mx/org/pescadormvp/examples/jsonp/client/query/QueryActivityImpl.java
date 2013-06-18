/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import java.util.logging.Level;

import mx.org.pescadormvp.examples.jsonp.client.query.QueryMessages;

import mx.org.pescadormvp.core.client.data.DataManager;
import mx.org.pescadormvp.core.client.internallinks.ActivateInternalLinkEvent;
import mx.org.pescadormvp.core.client.logging.PescadorMVPLogger;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * <p>Implementation of the Query component's only activity, {@link QueryActivity}.
 * (That's the presenter in GWT-MVP-speak.)</p>
 * 
 * <p>This class controls the display (view), through which it interacts with the user. This
 * class also uses PescadorMVP facilities to get data from OpenWeatherMap.</p>
 *  
 * @author Andrew Green
 */
public class QueryActivityImpl 
		extends 
		PescadorMVPPlaceActivityBase<QueryView,QueryPlace,QueryComponent>
		implements QueryActivity {

	// Localized messages for the UI. All static strings sent to the user come
	// from here, making internationalization easy.
	private final QueryMessages messages;
	
	// registration object for the handlers we register
	private HandlerRegistration linkHandlerReg;
	
	// OSMMap
	private OSMMap map;
	
	// Components used
	private QueryComponent queryComponent; // component this is part of
	private DataManager dataManager;
	private PescadorMVPLogger logger;
	
	@Inject
	public QueryActivityImpl(
			@Assisted QueryPlace place,
			QueryMessages messages,
			OSMMap map,
			QueryComponent queryComponent,
			DataManager dataManager,
			PescadorMVPLogger logger) {

		super(place);
		this.messages = messages;
		this.map = map;
		this.queryComponent = queryComponent;
		this.dataManager = dataManager;
		this.logger = logger;
	}

	/**
	 * Called when the activity starts.
	 */
	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {

		// Attach the view to the container for this region of the layout.
		// (In this example there's only one region.)
		final QueryView view = getView(); 
		container.setWidget(view);
		
		// Make sure the view's query area is set up
		if (!view.isQueryAreaRendered()) {

			view.renderQueryArea(
					messages.beforeQueryTextBox(),
					messages.afterQueryTextBox());
		}

		// Register to receive events from the view. The view is the first
		// object to hear about user interactions, and mainly it just passes
		// them on here.
		linkHandlerReg = eventBus.addHandlerToSource(
				ActivateInternalLinkEvent.TYPE,
				view, this);
		
		// Provide the view with a fresh place instance for the next query
		view.setRawQueryPlace(queryComponent.getPlace());
		
		// The Place object provides the data for this activity, which in this
		// case is the name of the place whose latitude and longitude we'll query.
		final String location = getPlace().getLocation();
		
		// If there's no location data, set up the view as "empty"
		if ((location == null) || (location.length() == 0)) {
			view.setTextboxContents("");
			view.renderEmpty();
			
		} else {
			
			// Make sure the text box contains the location string the user asked for
			// We don't sanitize it here, that's for the view to do
			view.setTextboxContents(location);
			
			// Before trying to retrieve the temperature information,
			// set a timer to display a "Loading" message if the request
			// doesn't come back quickly. The view will cancel the timer
			// once any state is rendered.
			view.setLoadingString(messages.loading());
			view.startLoadingTimer();
						
			// Create an action object with the name of the location to query
			GetLatLonAction action = new GetLatLonAction(location);
			
			// Perform the request
			dataManager.execute(action, GetLatLonResult.class,
					new AsyncCallback<GetLatLonResult>() {
	
				@Override
				public void onFailure(Throwable caught) {
					
					// Log the error
					logger.log(Level.WARNING, caught.getLocalizedMessage());
					
					// Notify the user
					view.setErrorStrings(messages.errorCommunicating(),
							messages.tryAgain());
					view.renderError();
				}
		
				@Override
				public void onSuccess(GetLatLonResult result) {
					
					if (result.isValid()) {
						
						double lat = result.getLat();
						double lon = result.getLon();
						
						String latNSStr = lat > 0 ? "N" : "S";
						String latStr = Math.abs(lat) + " " + latNSStr;
						
						String lonEWStr = lon > 0 ? "E" : "W";
						String lonStr = Math.abs(lon) + " " + lonEWStr;
						
						String info = messages.XisAtY(
								result.getDisplayName(), latStr, lonStr);
						
						// Send the info to the view
						view.setLatLonMsg(info);
						
						// Ensure the view has the map it will use
						if (!view.osmMapSet())
							view.setOSMMap(map);
						
						// Set the map to the correct location
						map.setLatLon(lat, lon);
						
						// Tell the view to render
						view.renderLatLon();

					} else {
						
						view.setNoSuchPlaceStrings(
								messages.neverHeardOf(location),
								messages.tryAgain());
						
						view.renderNoSuchPlace();
					}
				}
			});
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();

		// de-register event handling from the view when this event stops
		if (linkHandlerReg != null)
			linkHandlerReg.removeHandler();
	}
	
	@Override
	protected Class<QueryComponent> getComponentClass() {
		return QueryComponent.class;
	}

	@Override
	public Class<QueryPlace> getPlaceClass() {
		return QueryPlace.class;
	}
}
