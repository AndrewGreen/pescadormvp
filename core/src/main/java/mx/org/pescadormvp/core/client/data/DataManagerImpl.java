/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución. Partly based on work by David
 * Chandler. Used with permission. See
 * http://turbomanage.wordpress.com/2010/07/12
 * /caching-batching-dispatcher-for-gwt-dispatch/
 ******************************************************************************/
package mx.org.pescadormvp.core.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import mx.org.pescadormvp.core.client.util.Reflect;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * <p>
 * Caching, command-pattern data manager (implementation).
 * </p>
 * <p>
 * Partly based on work by David Chandler. See
 * <a href="http://turbomanage.wordpress.com/2010/07/12/caching-batching-dispatcher-for-gwt-dispatch/">
 * this blog post</a>.
 * </p>
 */
public class DataManagerImpl implements DataManager {

	private Map<Action<Result>, ArrayList<AsyncCallback<Result>>> pendingCallbacks = new HashMap<Action<Result>, ArrayList<AsyncCallback<Result>>>();
	private Map<Action<Result>, Result> cache;
	private DispatchAsync standardDispatcher;
	private JsonpDispatchAsync jsonpDispatcher;

	private static Integer maxCacheSize;
	private static int DEFAULT_MAX_CACHE_SIZE = 100;

	// find a way to annotate actions as server dispatched
	// server dispatched and cached, or locally handled
	// do away with SiteLayoutManager and create a handler for finding a related
	// top-
	// level element, that will process all the intermediate requests to get a
	// top-
	// level element

	// TODO allow callbacks to be associated with, and only called in, a given
	// app state (as identified by the session component)

	@SuppressWarnings("serial")
	// To remove warning for LinkedHashMap
	@Inject
	public DataManagerImpl(
			DispatchAsync standardDispatcher,
			JsonpDispatchAsync jsonpDispatcher) {

		this.standardDispatcher = standardDispatcher;
		this.jsonpDispatcher = jsonpDispatcher;

		// The cache will be a LinkedHashMap that automatically
		// removes the last entity that was accessed if the cache is larger
		// than a certain size.
		cache = new LinkedHashMap<Action<Result>, Result>(
				DEFAULT_MAX_CACHE_SIZE * 2, (float) 0.75, true) {

			@Override
			protected boolean removeEldestEntry(
					Entry<Action<Result>, Result> eldest) {
				return size() > getMaxCacheSize();
			}
		};
	}

	/**
	 * Set the maximum cache size. See
     * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveGlobalSetup} for an
     * example of how to set maximum cache size using Guice/GIN. 
	 */
	@SuppressWarnings("javadoc")
	@Inject(optional = true)
	public static void setMaxCacheSize(
			@Named("maxDataManagerCacheSize") Integer maxCacheSize) {

		DataManagerImpl.maxCacheSize = maxCacheSize;
	}

	private int getMaxCacheSize() {
		return maxCacheSize != null ? maxCacheSize : DEFAULT_MAX_CACHE_SIZE;
	}

	public <A extends Action<R>, R extends Result> void execute(
			A action,
			Class<R> resultClass,
			final AsyncCallback<R> callback) {

		List<AsyncCallback<Result>> pendingCallbacksForSameAction =
				pendingCallbacks.get(action);
		Result cachedResult = cache.get(action);

		// Check if there are callbacks pending for this action.
		// If so, add this call to a list of pending callbacks and return.
		if (pendingCallbacksForSameAction != null) {

			// This cast should always work, since R extends Result (?)
			@SuppressWarnings("unchecked")
			AsyncCallback<Result> upcastCallback = (AsyncCallback<Result>) callback;
			pendingCallbacksForSameAction.add(upcastCallback);

			if (cachedResult != null)
				throw new RuntimeException(
						"An action should never be both cached and pending at the same time");

			return;
		}

		// Check if there is a cached result for this action. If so, just
		// use it.
		if (cachedResult != null) {
			if (!Reflect.isOfSameClassOrSubclass(resultClass, cachedResult))
				throw new RuntimeException();
			@SuppressWarnings("unchecked")
			R downcastCachedResult = (R) cachedResult;

			callback.onSuccess(downcastCachedResult);

			// If there is no cached result, call the server to get it.
		} else {
			// This cast should always work, since A extends Action<R>,
			// and the R parameter in Action extends Result (?)
			@SuppressWarnings("unchecked")
			final Action<Result> upcastAction = (Action<Result>) action;

			// Don't do cache-related stuff if the action is uncacheable
			final boolean cacheable = action instanceof CacheableAction;

			if (cacheable)
				pendingCallbacks.put(upcastAction,
						new ArrayList<AsyncCallback<Result>>());

			AsyncCallback<R> outerCallback = new AsyncCallback<R>() {

				@Override
				public void onFailure(Throwable caught) {
					// process other pending callbacks
					if (cacheable) {
						ArrayList<AsyncCallback<Result>>
						localPendingCallbacksForSameAction =
								pendingCallbacks.remove(upcastAction);

						for (AsyncCallback<Result> pendingCallback :
						localPendingCallbacksForSameAction)
							pendingCallback.onFailure(caught);
					}

					callback.onFailure(caught);
				}

				@Override
				public void onSuccess(R result) {
					// process other pending callbacks
					if (cacheable) {
						ArrayList<AsyncCallback<Result>>
						localPendingCallbacksForSameAction =
								pendingCallbacks.remove(upcastAction);

						for (AsyncCallback<Result> pendingCallback :
						localPendingCallbacksForSameAction)
							pendingCallback.onSuccess(result);

						cache.put(upcastAction, result);
					}

					callback.onSuccess(result);
				}
			};

			if (action instanceof JsonpAction)
				jsonpDispatcher.execute((JsonpAction<R>) action, outerCallback);
			else
				standardDispatcher.execute(action, outerCallback);
		}
	}

	@Override
	public void registerActionHelper(JsonpActionHelper<?, ?> helper) {
		jsonpDispatcher.registerActionHelper(helper);
	}

	@Override
	public void finalizeSetup() {
		// nothing to do
	}

	@Override
	public Class<DataManager> publicInterface() {
		return DataManager.class;
	}

	@Override
	public void clearCache() {
		cache.clear();
	}
}
