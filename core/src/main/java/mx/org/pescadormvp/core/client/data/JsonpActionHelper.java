/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.data;

import com.google.gwt.core.client.JavaScriptObject;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Implementations of this interface help the {@link DataManager} perform
 * {@link JsonpAction}s. They construct a URL to call using information in a
 * specific {@link JsonpAction}, and create a {@link Result} from the Javascript
 * response.
 * 
 * @param <A>
 *            The class of action to be handled.
 * @param <R>
 *            The class of result to be produced.
 */
public interface JsonpActionHelper<A extends JsonpAction<R>, R extends Result> {

	/**
	 * Instantiate the result from the {@link JavaScriptObject} received from
	 * the server.
	 */
	R insantiateResult(JavaScriptObject jsResult);

	/**
	 * The full URL to call for the action specified.
	 */
	String getRequestURL(A action);

	/**
	 * The callback parameter to send in the request URL. This sets the
	 * Javascript callback function to be called by the returned Javascript.
	 * 
	 * @return The callback parameter, or null if none should be set.
	 */
	String getCallbackParameter();

	/**
	 * The class of action to be handled.
	 */
	Class<A> getActionType();
}
