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

public interface IsItem extends IsWidget {

	public int getMinWidth();

	public int getMinHeight();

	public void resizeContentWidth(int pxWidth);
	
	public void resizeContentHeight(int pxHeight);
}
