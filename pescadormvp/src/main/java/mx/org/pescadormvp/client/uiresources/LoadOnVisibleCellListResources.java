/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.uiresources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LoadOnVisibleCellListResources extends ClientBundle {

	public static final LoadOnVisibleCellListResources INSTANCE =
			GWT.create(LoadOnVisibleCellListResources.class);

	@Source("loadOnVisibleCellList.css")
	LoadOnVisibleCellListStyle style();

	public interface LoadOnVisibleCellListStyle extends CssResource {
		String emptyLoadingArea();
	}
}
