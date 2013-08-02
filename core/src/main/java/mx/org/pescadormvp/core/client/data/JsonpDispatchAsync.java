/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.data;

import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Internal Pescador MVP use. The Jsonp dispatcher: leeps track of {@link JsonpActionHelper}s used for performing specific
 * {@link JsonpAction}s, sends requests using GWT's JSONP facilities, and
 * uses the helpers to construct URLs and {@link Result}s. 
 */
public interface JsonpDispatchAsync {

	/**
	 * Register a helper for actions of a certain type. (The helper itself
	 * specifies the type of action it's for.)
	 */
	void registerActionHelper(JsonpActionHelper<?, ?> helper);
	
	/**
	 * Perform an action using the specified callback.
	 */
	<A extends JsonpAction<R>, R extends Result> void execute(A action, AsyncCallback<R> callback);
}
