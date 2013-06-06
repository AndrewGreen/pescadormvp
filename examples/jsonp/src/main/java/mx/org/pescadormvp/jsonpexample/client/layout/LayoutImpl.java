/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
/*
 * Some parts of this file,
 * copyright 2011 Instituto de Investigaciones Dr. José María Luis Mora
 * See COPYING.txt and LICENSE.txt for redistribution conditions.
 * 
 * 
 */
package mx.org.pescadormvp.jsonpexample.client.layout;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mx.org.pescadormvp.client.regionsandcontainers.DynamicContainer;
import mx.org.pescadormvp.client.regionsandcontainers.DynamicSimpleLayoutPanel;
import mx.org.pescadormvp.client.regionsandcontainers.ForRegionTag;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * <p>Implementation of {@link Layout}. Controls global app layout.</p>
 *
 * <p>TODO: parts of this should be refactored into a helper class in 
 * pescadormvp.</p>
 * 
 * @author Andrew Green
 *
 */
public class LayoutImpl extends ResizeComposite implements Layout {

	private SimpleLayoutPanel outer = new SimpleLayoutPanel();
	private DynamicSimpleLayoutPanel body = new DynamicSimpleLayoutPanel();
	private FlowPanel absoluteWidgetPanel;
	
	private final Map<Class<? extends ForRegionTag>, DynamicSimpleLayoutPanel>
			regionTagsAndContainers =
			new HashMap<Class<? extends ForRegionTag>, DynamicSimpleLayoutPanel>();
	
	private EventBus eventBus;
	
	@Inject
	public LayoutImpl(EventBus eventBus) {
		this.eventBus = eventBus;
		
		// set up region(s)
		setupRegion(Body.class, body);

		// panel for absolutely positioned widgets
		absoluteWidgetPanel = new FlowPanel();
		
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
		RootPanel.get().add(absoluteWidgetPanel);
		
		onResize(); // setup panel sizes and call resize on them
	}

	@Override
	public Set<Class<? extends ForRegionTag>> getRegions() {
		return regionTagsAndContainers.keySet();
	}

	@Override
	public DynamicContainer
			getContainer(Class<? extends ForRegionTag> regionTag) {

		DynamicContainer container = regionTagsAndContainers.get(regionTag);
		
		if (container == null)
			throw new IllegalArgumentException("Requested non-existant region");
		
		return container;
	}
	
	private void setupRegion(Class<? extends ForRegionTag> regionTag,
			DynamicSimpleLayoutPanel container) {

		container.setEventBus(eventBus);
		regionTagsAndContainers.put(regionTag, container);
	}

}
