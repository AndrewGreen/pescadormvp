/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.util.UUID;
import mx.org.pescadormvp.core.shared.PescadorMVPLocale;

import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Session component (implementation). Provides information on the current
 * locale and available locales, as well as methods for going to places, storing
 * session data, and generating unique IDs.
 */
public class SessionImpl implements Session, PlaceChangeEvent.Handler {

	private int placeChangeCounter = 0;
	private final PlaceController placeController;
	private PescadorMVPLocale currentLocale;
	private Map<Class<? extends Component<?>>, SessionData> sessionDataByComponent =
			new HashMap<Class<? extends Component<?>>, SessionData>();

	private final String clientSessionID = UUID.uuid(8);

	private static String DEFAULT_LOCALE_STRING = "default";

	@Inject
	public SessionImpl(EventBus eventBus, PlaceController placeController) {
		eventBus.addHandler(PlaceChangeEvent.TYPE, this);
		this.placeController = placeController;
	}

	public String getClientSessionID() {
		return clientSessionID;
	}

	@Override
	public PescadorMVPLocale currentLocale() {
		String currentLocaleName =
				LocaleInfo.getCurrentLocale().getLocaleName();

		if ((currentLocale == null) ||
				(!currentLocale.getLocaleName().equals(currentLocaleName)))
			setupCurrentLocale(currentLocaleName);

		return currentLocale;
	}

	private void setupCurrentLocale(String currentLocaleName) {
		currentLocale = setupLocale(currentLocaleName);
	}

	private PescadorMVPLocale setupLocale(String localeName) {
		return new PescadorMVPLocale(localeName,
				LocaleInfo.getLocaleNativeDisplayName(localeName));
	}

	@Override
	public List<PescadorMVPLocale> availableLocales() {
		List<PescadorMVPLocale> locales = new ArrayList<PescadorMVPLocale>();

		for (String localeName : LocaleInfo.getAvailableLocaleNames()) {

			// for some reason, "default" appears as a separate locale
			if (localeName.equalsIgnoreCase(DEFAULT_LOCALE_STRING))
				continue;

			PescadorMVPLocale locale = new PescadorMVPLocale(localeName,
					LocaleInfo.getLocaleNativeDisplayName(localeName));

			locales.add(locale);
		}

		Collections.sort(locales, PescadorMVPLocale.getStandardComparator());
		return locales;
	}

	@Override
	public void setSessionData(Class<? extends Component<?>> component,
			SessionData data) {
		sessionDataByComponent.put(component, data);
	}

	@Override
	public SessionData getSessionData(Class<? extends Component<?>> component) {
		return sessionDataByComponent.get(component);
	}

	@Override
	public void goTo(PescadorMVPPlace place) {
		// sometimes to go to a place we reload the whole app
		// (e.g., when going to a place with a different locale)
		if (place.requiresReload())
			Window.Location.replace(place.getURL());
		else
			placeController.goTo(place.asGWTPlace());
	}

	@Override
	public PescadorMVPPlace getWhere() {
		// this cast is safe in the current framework
		return (PescadorMVPPlace) placeController.getWhere();
	}

	@Override
	public void onPlaceChange(PlaceChangeEvent event) {
		placeChangeCounter++;
	}

	@Override
	public int currentStateHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentLocale == null) ? 0 :
				currentLocale.hashCode());
		result = prime * result + placeChangeCounter;
		return result;
	}

	@Override
	public String currentPlaceSessionID() {
		return clientSessionID + placeChangeCounter;
	}

	@Override
	public String getUniqueID() {
		return clientSessionID + UUID.uuid(4);
	}

	@Override
	public void finalizeSetup() {
		// nothing to do
	}

	@Override
	public Class<Session> publicInterface() {
		return Session.class;
	}
}
