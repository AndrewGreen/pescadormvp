/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.jsonpexample.client.layout;

import java.util.Set;

import mx.org.pescadormvp.client.regionsandcontainers.DynamicContainer;
import mx.org.pescadormvp.client.regionsandcontainers.DynamicSimpleLayoutPanel;
import mx.org.pescadormvp.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.client.regionsandcontainers.LayoutHelper;

import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.inject.Inject;

/**
 * <p>Implementation of {@link Layout}. Controls global app layout.</p>
 *
 * @author Andrew Green
 *
 */
public class LayoutImpl extends ResizeComposite implements Layout {

	private SimpleLayoutPanel outer = new SimpleLayoutPanel();
	private DynamicSimpleLayoutPanel body = new DynamicSimpleLayoutPanel();
	
	private LayoutHelper layoutHelper;
	
	@Inject
	public LayoutImpl(LayoutHelper layoutHelper) {
		this.layoutHelper = layoutHelper;
		
		// set up region(s)
		layoutHelper.setupRegion(Body.class, body);

		// take up the whole viewport
		body.setWidth("100%");
		body.setHeight("100%");
		outer.setWidth("100%");
		outer.setHeight("100%");
		
		outer.add(body);
		
		// required call for all children of GWT's Composite widget
		initWidget(outer);
	}
	
	@Override
	public void attach() {
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.add(this);
		onResize(); // make sure everything's the right size
	}

	@Override
	public Set<Class<? extends ForRegionTag>> getRegions() {
		return layoutHelper.getRegions();
	}

	@Override
	public DynamicContainer
			getContainer(Class<? extends ForRegionTag> regionTag) {
		return layoutHelper.getContainer(regionTag);
	}
}
