/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.internallinks;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * An item that may be included in an {@link ItemPanel}. 
 */
public interface IsItem extends IsWidget {

	/**
	 * Internal use. Used by {@link ItemPanel} to determine its minimum size.
	 */
	public int getMinWidth();

	/**
	 * Internal use. Used by {@link ItemPanel} to determine its minimum size.
	 */
	public int getMinHeight();

	/**
	 * Internal use. Used by {@link ItemPanel} to set the size of contained
	 * items.
	 */
	public void resizeContentWidth(int pxWidth);
	
	/**
	 * Internal use. Used by {@link ItemPanel} to set the size of contained
	 * items.
	 */
	public void resizeContentHeight(int pxHeight);
}
