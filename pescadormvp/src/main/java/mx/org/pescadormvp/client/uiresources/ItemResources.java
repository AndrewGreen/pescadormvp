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
import com.google.gwt.resources.client.ImageResource;

/**  
 * General resources for item widgets. Not instantiated explicitly, see GWT
 * docs for how to get a handle on the created instance.
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

	public interface ItemStyle extends CssResource {
		String itemContainer();
		String textArea();
		String image();
	}
}
