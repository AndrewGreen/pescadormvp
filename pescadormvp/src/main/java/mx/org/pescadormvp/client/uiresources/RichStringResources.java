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

public interface RichStringResources extends ClientBundle {

	public static final RichStringResources INSTANCE =
			GWT.create(RichStringResources.class);
	
	@Source("richstring.css")
	RichStringStyle style();
	
	public interface RichStringStyle extends CssResource {
		String blockNote();
		String note();
		String em();
		String hi();
	}
}
