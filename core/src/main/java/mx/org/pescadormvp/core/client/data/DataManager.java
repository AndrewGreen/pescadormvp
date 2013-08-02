/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.data;

import mx.org.pescadormvp.core.client.components.Component;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * <p>
 * Caching, command-pattern data manager. Accepts gwt-dispatch {@link Action}s
 * and returns {@link Result}s. Only the results of actions that implement
 * {@link CacheableAction} will be cached.
 * </p>
 * <p>
 * Note that cache size is limited automatically: once
 * a specified maximum cache size has been reached, the results that have
 * gone the longest without being accessed are automatically purged. See
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveGlobalSetup} for an
 * example of how to set maximum cache size.
 * </p>
 * <p>
 * Partly based on work by David Chandler. See
 * <a href="http://turbomanage.wordpress.com/2010/07/12/caching-batching-dispatcher-for-gwt-dispatch/">
 * this blog post</a>.
 * </p>
 */
@SuppressWarnings("javadoc")
public interface DataManager extends Component<DataManager> {

	/**
	 * Perform an action and execute one of the callback methods to handle
	 * the response.
	 * 
	 * @param action
	 *            The action to be performed.
	 * @param resultClass
	 *            The class of result expected.
	 * @param callback
	 *            The callback that handles the response.
	 */
	public <A extends Action<R>, R extends Result> void execute(
			A action,
			Class<R> resultClass,
			final AsyncCallback<R> callback);

	/**
	 * Clear the cache of results. Call this if cached results may
	 * have become stale. Note that cache size is limited automatically: once
	 * a specified maximum cache size has been reached, the results that have
	 * gone the longest without being accessed are automatically purged.
	 */
	void clearCache();

	/**
	 * Register a {@link JsonpActionHelper} for processing {@link JsonpAction}s.
	 */
	void registerActionHelper(JsonpActionHelper<?, ?> helper);
}
