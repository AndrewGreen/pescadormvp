/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.internallinks;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**  
 * General CSS and image resources for item widgets.
 */
public interface ItemResources extends ClientBundle {
	public static final ItemResources INSTANCE = 
			GWT.create(ItemResources.class);
	
	@Source("item.css")
	public ItemStyle style();
	
	@Source("checkmark.png")
	public ImageResource checkmarkImage();
	
	@Source("clear.gif")
	public ImageResource clearImage();

	/**
	 * General CSS resources for item widgets.
	 */
	public interface ItemStyle extends CssResource {
		String itemContainer();
		String textArea();
		String image();
	}
}
