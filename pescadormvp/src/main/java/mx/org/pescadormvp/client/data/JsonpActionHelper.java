/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.data;

import com.google.gwt.core.client.JavaScriptObject;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * Implementations of this interface help the {@link DataManager} perfom actions.
 * They construct a URL to call from a specific {@link JsonpAction}, and create a 
 * {@link Result} from the Javascript response.
 * 
 * @author Andrew Green
 *
 * @param <A> The class of action to be handled.
 * @param <R> The class of result to be produced.
 */
public interface JsonpActionHelper<A extends Action<R>, R extends Result> {

	R insantiateResult(JavaScriptObject jsResult);
	
	String getRequestURL(A action);
	
	/**
	 * The parameter to use in the URL to set the Javascript callback function
	 * to be sent with the request.
	 * 
	 * @return The callback parameter, or null if none should be set.
	 */
	String getCallbackParameter();
	
    Class<A> getActionType();
}
