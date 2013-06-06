/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.data;

import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The interface for the Jsonp dispatcher. 
 */
public interface JsonpDispatchAsync {

	/**
	 * Register a helper for specific types of actions. (The helper itself
	 * specifies which  type of action it's for.)
	 */
	void registerActionHelper(JsonpActionHelper<?, ?> helper);
	
	/**
	 * Perform an action using the specified callback.
	 * 
	 * @param action
	 * @param callback
	 */
	<A extends JsonpAction<R>, R extends Result> void execute(A action, AsyncCallback<R> callback);
}
