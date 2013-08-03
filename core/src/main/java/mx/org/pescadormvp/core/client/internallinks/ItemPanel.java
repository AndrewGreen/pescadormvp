/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.internallinks;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel widget that can contain items. Used to create menu-like widgets
 * whose items are native hyperlinks. 
 */
public class ItemPanel extends Composite {
	
	private final FlowPanel flowPanel = new FlowPanel();

	public ItemPanel() {
		initWidget(flowPanel);
	}
	
	/**
	 * Add an item. 
	 */
	public void add(IsItem i) {
		flowPanel.add(i);
	}

	/**
	 * Get the item at the specified index. 
	 */
	public IsItem getItem(int index) {
		return (IsItem) flowPanel.getWidget(index);
	}

	/**
	 * Clear the panel.
	 */
	public void clear() {
		flowPanel.clear();
	}

	/**
	 * Get the number of items in the panel. 
	 */
	public int getItemCount() {
		return flowPanel.getWidgetCount();
	}
	
	/**
	 * Set up the dimensions of the panel and the items it contains based
	 * on the minimum space required by all the items.
	 */
	public void setupSizes() {
		// find item dimensions
		int itemCount = getItemCount();
		int requiredItemWidth = 0;
		int requiredItemHeight = 0;
		
		for (int i = 0; i < itemCount; i++) {
			IsItem item = getItem(i);
			int minWidth = item.getMinWidth();
			int minHeight = item.getMinHeight();
			
			if (requiredItemWidth < minWidth)
				requiredItemWidth = minWidth;
			
			if (requiredItemHeight < minHeight)
				requiredItemHeight = minHeight;
		}
		
		
		// set item dimensions
		for (int i = 0; i < itemCount; i++) {

			IsItem item = getItem(i);
			Widget itemWidget = item.asWidget();
			
			item.resizeContentWidth(requiredItemWidth);
			item.resizeContentHeight(requiredItemHeight);
			
			itemWidget.setWidth(requiredItemWidth + "px");
			itemWidget.setHeight(requiredItemHeight + "px");
		}
		
		setWidth(requiredItemWidth + "px");
		setHeight((requiredItemHeight * itemCount) + "px");
	}
}
