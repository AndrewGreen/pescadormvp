/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.regionsandcontainers;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface for widgets (say, views) that have absolutely positioned widgets
 * associated with them but not necessarily contained in them visually.
 * Used by {@link DynamicSimpleLayoutPanel} to inform about absolutely positioned
 * widgets to add to or remove from the UI. 
 */
public interface HasAbsolutelyPositionedWidgets {

	/**
	 * Get the absolutely positioned widgets logically "contained" in this
	 * widget. 
	 */
	public List<Widget> getAbsolutelyPositionedWidgets();
}
