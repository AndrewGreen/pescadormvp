/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.data;

import mx.org.pescadormvp.client.components.Component;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataManager extends Component<DataManager> {
	public <A extends Action<R>, R extends Result> void execute(
			A action,
			Class<R> resultClass,
			final AsyncCallback<R> callback);

	void registerActionHelper(JsonpActionHelper<?, ?> helper);
	
}
