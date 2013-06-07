/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.regionsandcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Helper class for standard layout/region/container actions normally used
 * in classes that manage global app layout.
 * 
 * @author Andrew Green
 *
 */
public class LayoutHelper {

	private final Map<Class<? extends ForRegionTag>, DynamicSimpleLayoutPanel>
			regionTagsAndContainers =
			new HashMap<Class<? extends ForRegionTag>, DynamicSimpleLayoutPanel>();

	private EventBus eventBus;

	@Inject
	public LayoutHelper(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	public Set<Class<? extends ForRegionTag>> getRegions() {
		return regionTagsAndContainers.keySet();
	}

	public DynamicContainer
			getContainer(Class<? extends ForRegionTag> regionTag) {

		DynamicContainer container = regionTagsAndContainers.get(regionTag);
		
		if (container == null)
			throw new IllegalArgumentException("Requested non-existant region");
		
		return container;
	}
	
	public void setupRegion(Class<? extends ForRegionTag> regionTag,
			DynamicSimpleLayoutPanel container) {

		container.setEventBus(eventBus);
		regionTagsAndContainers.put(regionTag, container);
	}
}
