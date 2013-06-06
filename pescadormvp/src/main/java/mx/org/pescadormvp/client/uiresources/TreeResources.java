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
import com.google.gwt.user.cellview.client.CellTree;

public interface TreeResources extends CellTree.Resources {
	public static final TreeResources INSTANCE = 
			GWT.create(TreeResources.class);
	
	@Source("tree.css")
	Style cellTreeStyle();
	
	public interface Style extends CellTree.Style {
		String cellTreeItemHover();
	}
}
