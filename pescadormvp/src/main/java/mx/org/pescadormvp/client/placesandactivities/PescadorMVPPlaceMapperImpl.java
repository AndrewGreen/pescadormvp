/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import java.util.HashMap;
import java.util.Map;

import mx.org.pescadormvp.client.components.ComponentSetup;
import mx.org.pescadormvp.shared.PescadorMVPLocale;

import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window.Location;
import com.google.inject.Inject;

public class PescadorMVPPlaceMapperImpl implements PescadorMVPPlaceMapper {

	private ComponentSetup componentSetup;
	private static String MAIN_TOKEN_SEPARATOR = ";";
	private static String KV_PAIR_SEPARATOR = "/";
	private static String KV_SEPARATOR = "=";
	
	@Inject
	protected PescadorMVPPlaceMapperImpl() { }
	
	/**
	 * Called from {@link ComponentSetup} after it has been injected there.
	 * (This avoids circular injection problems.)
	 * 
	 * @param componentSetup
	 */
	@Override
	public void setComponentSetup(ComponentSetup componentSetup) {
		this.componentSetup = componentSetup;
	}

	@Override
	public Place getPlace(String fullToken) {
		String[] tokenParts = fullToken.split(MAIN_TOKEN_SEPARATOR);
		
		// if we've got a bad token, we get the default place
		PescadorMVPPlace place = componentSetup.getPlace(tokenParts[0]);
		
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
s	 */
	@Override
	public <P extends PescadorMVPPlace> P copyPlaceInto(
			P originalPlace,
			P newPlace) {
		
		newPlace.setMainToken(originalPlace.getMainToken());
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

		if (properties == null)
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
}
