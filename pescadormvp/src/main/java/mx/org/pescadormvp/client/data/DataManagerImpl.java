/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import mx.org.pescadormvp.client.components.ComponentSetup;
import mx.org.pescadormvp.client.util.Reflect;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

public class DataManagerImpl implements DataManager {

	private Map<Action<Result>, ArrayList<AsyncCallback<Result>>> pendingCallbacks 
		= new HashMap<Action<Result>, ArrayList<AsyncCallback<Result>>>();
	private Map<Action<Result>, Result> cache = new HashMap<Action<Result>, Result>();
	private DispatchAsync standardDispatcher;
	private JsonpDispatchAsync jsonpDispatcher;

	// will be used once we associate callbacks with app state
	@SuppressWarnings("unused")
	private ComponentSetup componentSetup;
	
	// find a way to annotate actions as server dispatched
	// server dispatched and cached, or locally handled
	// do away with SiteLayoutManager and create a handler for finding a related top-
	// level element, that will process all the intermediate requests to get a top-
	// level element
	
	// TODO allow callbacks to be associated with, and only called in, a given
	// app state (as identified by the session component)
	
	@Inject
	public DataManagerImpl(
			DispatchAsync standardDispatcher,
			JsonpDispatchAsync jsnopDispatcher) {
		
		this.standardDispatcher = standardDispatcher;
		this.jsonpDispatcher = jsnopDispatcher;
	}
	
	public <A extends Action<R>, R extends Result> void execute(
			A action,
			Class<R> resultClass,
			final AsyncCallback<R> callback) {
		
		List<AsyncCallback<Result>> pendingCallbacksForSameAction =
				pendingCallbacks.get(action);
		Result cachedResult = cache.get(action);
		
		if (pendingCallbacksForSameAction != null) {

			 // This cast should always work, since R extends Result (?)
			@SuppressWarnings("unchecked")
			AsyncCallback<Result> upcastCallback 
					= (AsyncCallback<Result>) callback;
			pendingCallbacksForSameAction.add(upcastCallback);

			if (cachedResult != null)
				throw new RuntimeException("An action should never be both cached and pending at the same time");
				
			return;
		}
		
		if (cachedResult != null) {
			if (!Reflect.isOfSameClassOrSubclass(resultClass, cachedResult))
				throw new RuntimeException();
			@SuppressWarnings("unchecked")
			R downcastCachedResult = (R) cachedResult;
			
			callback.onSuccess(downcastCachedResult);
		
		} else {
			// This cast should always work, since A extends Action<R>,
			// and the R parameter in Action extends Result (?) 
			@SuppressWarnings("unchecked")
			final Action<Result> upcastAction = (Action<Result>) action; 
			
			pendingCallbacks.put(upcastAction, 
					new ArrayList<AsyncCallback<Result>>());
			
			AsyncCallback<R> outerCallback = new AsyncCallback<R>() {

				@Override
				public void onFailure(Throwable caught) {
					// process other pending callbacks
					ArrayList<AsyncCallback<Result>> localPendingCallbacksForSameAction =
							pendingCallbacks.remove(upcastAction);
					
					for (AsyncCallback<Result> pendingCallback : 
						localPendingCallbacksForSameAction)
						pendingCallback.onFailure(caught);
							
					callback.onFailure(caught);
				}

				@Override
				public void onSuccess(R result) {
					// process other pending callbacks
					ArrayList<AsyncCallback<Result>> localPendingCallbacksForSameAction =
							pendingCallbacks.remove(upcastAction);
					
					for (AsyncCallback<Result> pendingCallback : 
						localPendingCallbacksForSameAction)
						pendingCallback.onSuccess(result);
					
					cache.put(upcastAction, result);
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
	public void setComponentSetup(ComponentSetup componentSetup) {
		this.componentSetup = componentSetup;
	}

	/**
	 * Register an action helper for a specific JSONP action class.
	 */
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
}
