/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import java.util.HashMap;
import java.util.Map;

import mx.org.pescadormvp.core.client.components.ComponentRegistry;
import mx.org.pescadormvp.core.shared.PescadorMVPLocale;

import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window.Location;
import com.google.inject.Inject;

/**
 * An extended {@link PlaceHistoryMapper} that works with the PescadorMVP
 * place mechanisms.
 * 
 * @author Andrew Green
 */
public class PescadorMVPPlaceMapperImpl implements PescadorMVPPlaceMapper {

	private static String MAIN_TOKEN_SEPARATOR = ";";
	private static String KV_PAIR_SEPARATOR = "/";
	private static String KV_SEPARATOR = "=";
	
	private ComponentRegistry componentRegistry;
	private RawDefaultPlaceProvider defaultPlaceProvider;
	
	@Inject
	protected PescadorMVPPlaceMapperImpl(
			ComponentRegistry componentRegistry,
			RawDefaultPlaceProvider defaultPlaceProvider) { 
		
		this.componentRegistry = componentRegistry;
		this.defaultPlaceProvider = defaultPlaceProvider;
	}
	
	@Override
	public Place getPlace(String fullToken) {
		String[] tokenParts = fullToken.split(MAIN_TOKEN_SEPARATOR);
		
		PAVComponent<?, ?> pavComponent =
				componentRegistry.getPAVComponent(tokenParts[0]);

		// if we've got a bad token, we get the default place
		PescadorMVPPlace place;
		if (pavComponent != null)
			place = pavComponent.getRawPlace();
		else
			place = defaultPlaceProvider.getRawDefaultPlace();
		
		if (tokenParts.length > 1) {
			Map<String, String> properties = getKVMap(tokenParts[1]);
			place.setProperties(properties);
		}
		
		return place.asGWTPlace();
	}

	/**
	 * A method for getting a copy of a any place. Does not copy or set up
	 * URL info.
	 * 
	 */
	@Override
	public <P extends PescadorMVPPlace> P copyPlaceInto(
			P originalPlace,
			P newPlace) {
		
		newPlace.setProperties(originalPlace.getProperties());
		newPlace.setNewLocale(originalPlace.getNewLocale());
		newPlace.setPresentationText(originalPlace.getPresentationText());
		
		return newPlace;
	}
	
	@Override
	public String getToken(Place place) {
		if (!(place instanceof PescadorMVPPlace))
			throw new IllegalArgumentException();
		
		return getToken((PescadorMVPPlace) place);
	}
	
	@Override
	public String getToken(PescadorMVPPlace place) {

		Map<String, String> properties = place.getProperties();

		if ((properties == null) || (properties.keySet().size() == 0))
			return place.getMainToken();
		
		String propertiesString = makeKVString(properties,
				place.getPropertyKeys());
		
		return place.getMainToken() + 
				(propertiesString == null ?
						"" : MAIN_TOKEN_SEPARATOR + propertiesString);
	}
	
	@Override
	public void setupURLInfo(PescadorMVPPlace place) {

		String historyTokenFromPlaceObj = place.getHistoryToken();
		String historyToken = 
				historyTokenFromPlaceObj == null ?
				getToken(place) :
				historyTokenFromPlaceObj;
		
		place.setHistoryToken(historyToken);
				
		PescadorMVPLocale newLocale = place.getNewLocale();
		if (newLocale == null) {
			place.setURL("#" + historyToken);
			
			// instructs Session not to reload the whole app when going here
			place.setRequiresReload(false);
			
		} else {
			String queryParam = LocaleInfo.getLocaleQueryParam();
		    UrlBuilder builder = Location.createUrlBuilder();
		    builder.setParameter(queryParam, newLocale.getLocaleName());
		    builder.setHash(historyToken);
		    place.setURL(builder.buildString());
		    
		    // in this case, do reload the whole app when going here
		    place.setRequiresReload(true);
		    
		}
	}
	
	private Map<String, String> getKVMap(String token) { 
		String[] pairs = token.split(KV_PAIR_SEPARATOR);
		Map<String, String> propValueMap = new HashMap<String, String>();
		
		for (String kvPair : pairs) {
			String[] kv = kvPair.split(KV_SEPARATOR);
			
			if (kv.length != 2) {
				// TODO: log this problem somewhere, or do something here
				continue;
			}
			
			propValueMap.put(kv[0], kv[1]);
		}
		return propValueMap;
	}
	
	private String makeKVString(Map<String, String> map, String[] orderedKeys) {
		
		int size = map.keySet().size();
		if (size == 0)
			return null;
		
		String[] pairs = new String[orderedKeys.length];
		int ctr = 0;
		for (String key : orderedKeys) {
			String value = map.get(key);
			if (value != null)
				pairs[ctr++] = key + KV_SEPARATOR + map.get(key);
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			String pair = pairs[i];
			if (pair != null) {
				if (sb.length() > 0)
					sb.append(KV_PAIR_SEPARATOR);
				sb.append(pair);
			}
		}			
		
		return sb.length() > 0 ? sb.toString() : null;
	}

	@Override
	public void finalizeSetup() {
		// nothing to do
	}
	
	@Override
	public Class<PescadorMVPPlaceMapper> publicInterface() {
		return PescadorMVPPlaceMapper.class;
	}
	
	@Override
	public PescadorMVPPlace defaultPlace() {
		
		PescadorMVPPlace place = defaultPlaceProvider.getRawDefaultPlace();
		setupURLInfo(place);
		
		// For some reason, it seems necessary to set this to an empty string
		// rather than the real history token. This allows links to the default
		// place in the UI (generated from this very object) to have no
		// history token at all. If they have their normal history token,
		// then clicking on them adds an extra, unwanted entry in the 
		// browser history.
		place.setHistoryToken("");
		
		return place;
	}
}
