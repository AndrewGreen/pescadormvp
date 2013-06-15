/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.data;

import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * Keeps track of {@link JsonpActionHelper}s used for performing specific
 * {@link JsonpAction}s, sends requests using GWT's JSONP facilities, and
 * uses the helpers to construct URLs and {@link Result}s. 
 */
public class JsonpDispatchAsyncImpl implements JsonpDispatchAsync {

	private JsonpRequestBuilder requestBuilder;
	
	private Map<Class<? extends Action<?>>, JsonpActionHelper<?,?>>
			helpersByActionType =
			new HashMap<Class<? extends Action<?>>, JsonpActionHelper<?,?>>();
	
	@Inject
	public JsonpDispatchAsyncImpl(JsonpRequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}
	
	@Override
	public <A extends JsonpAction<R>, R extends Result> void execute(A action,
			final AsyncCallback<R> originalCallback) {

		// Get the right helper
		@SuppressWarnings("unchecked")
		final JsonpActionHelper<A, R> actionHelper = 
				(JsonpActionHelper<A, R>) helpersByActionType.get(action.getClass());
		
		// Use the helper to get the URL using information from the action
		String url = actionHelper.getRequestURL(action);

		// If the helper specifies a callback parameter, set it
		String callbackParam = actionHelper.getCallbackParameter();
		if (callbackParam != null)
			requestBuilder.setCallbackParam(callbackParam);
		
		// Send the callback
		requestBuilder.requestObject(url,
				new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				originalCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(JavaScriptObject jsResult) {
				
				R result = actionHelper.insantiateResult(jsResult);
				originalCallback.onSuccess(result);
			}
		});
	}

	@Override
	public void registerActionHelper(JsonpActionHelper<?, ?> helper) {
		helpersByActionType.put(helper.getActionType(), helper);
	}
}
