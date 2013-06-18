/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.data;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * A tag interface for indicating that an action should be performed with JSONP.
 * (Note: JSONP actions also require a {@link JsonpActionHelper}, which must be registered with 
 * the {@link DataManager}.
 * 
 * @author Andrew Green
 *
 * @param <R> The class of result associated with this action.
 */
public interface JsonpAction<R extends Result> extends Action<R>{
	
}
