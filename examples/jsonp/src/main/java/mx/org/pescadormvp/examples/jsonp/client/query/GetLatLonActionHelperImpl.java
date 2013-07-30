/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.safehtml.shared.UriUtils;

/**
 * Helps perform {@link GetLatLonAction}s.
 */
public class GetLatLonActionHelperImpl 
		implements GetLatLonActionHelper {

	// This class is meant to be used only with this site, and with a specific
	// version of the API, so it's OK to hardcode these here.
	final static String BASE_URL = "http://nominatim.openstreetmap.org/search?format=json&limit=1&q=";
	final static String CALLBACK_PARAM ="json_callback";

	/**
	 * Create the {@link Result} from the received {@link JavaScriptObject}.
	 */
	@Override
	public GetLatLonResult insantiateResult(JavaScriptObject jsResult) {

		LatLonInfo latLonInfo = jsResult.cast();
		GetLatLonResult result = new GetLatLonResult();
		
		result.setValid(latLonInfo.getCount() > 0);
		
		if (result.isValid()) {
			result.setDisplayName(latLonInfo.getDisplayName());
			result.setLat(Double.valueOf(latLonInfo.getLat()));
			result.setLon(Double.valueOf(latLonInfo.getLon()));
		}

		return result;
	}

	@Override
	public String getRequestURL(GetLatLonAction action) {
		return UriUtils.sanitizeUri(BASE_URL + action.getLocation());
	}

	@Override
	public String getCallbackParameter() {
		return CALLBACK_PARAM;
	}
	
	@Override
	public Class<GetLatLonAction> getActionType() {
		return GetLatLonAction.class;
	}
	
	/**
	 * Overlay Java type for accessing contents of Javascript object
	 * returned over JSONP.
	 * 
	 * @author Andrew Green
	 */
	// TODO more error handling if JS object is malformed
	static private class LatLonInfo extends JavaScriptObject {

		protected LatLonInfo() {}
		
		public final native int getCount() /*-{
			return this.length;
		}-*/;
		
		public final native String getDisplayName() /*-{
			return this[0].display_name;
		}-*/;
		
		public final native String getLat() /*-{
			return this[0].lat;
		}-*/;
		
		public final native String getLon() /*-{
			return this[0].lon;
		}-*/;
	}
}
