/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mx.org.pescadormvp.core.client.placesandactivities.PAVComponent;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;

/**
 * Internal Pescador MVP use. Registry of components (implementation). Pescador MVP
 * applications shouldn't need to access this class directly.
 */
public class ComponentRegistryImpl implements ComponentRegistry {

	private Map<String, PAVComponent<?,?>> mainTokenIndex =
			new HashMap<String, PAVComponent<?,?>>();
	
	private Map<Class<?>, Component<?>> componentIndex = 
			new HashMap<Class<?>, Component<?>>();

	private Set<Class<? extends ForRegionTag>> regions;
	
	@Override
	public void addComponents(
			Component<?>... components) {
		for (Component<?> component : components)
			addComponent(component);
	}
	
	@Override
	public void callFinalizeSetup() {
		for (Component<?> component : componentIndex.values()) {
			component.finalizeSetup();
		}
	}

	@Override
	public <I extends Component<I>> I getComponent(Class<I> publicInterface) {
		Component<?> uncastComponent =
				componentIndex.get(publicInterface);

		// GWT reflection doesn't provide for finding implemented interfaces
		@SuppressWarnings("unchecked")
		I component = (I) uncastComponent;

		return component;
	}
	
	@Override
	public PAVComponent<?,?> getPAVComponent(String token) {
		return mainTokenIndex.get(token);
	}
	
	@Override
	public void setRegions(Set<Class<? extends ForRegionTag>> regions) {
		this.regions = regions;
	}
	
	private void addComponent(Component<?> component) {
		componentIndex.put(component.publicInterface(), component);
		
		if (component instanceof PAVComponent<?,?>) {
			PAVComponent<?,?> pavComponent =
					(PAVComponent<?,?>) component;
			
			// get a throwaway place to find the main token
			PescadorMVPPlace place = pavComponent.getRawPlace();
			String mainToken = place.getMainToken();
			
			mainTokenIndex.put(mainToken, pavComponent);
			
			for (Class<? extends ForRegionTag> region : 
					pavComponent.handlesRegions()) {
				
				if (!regions.contains(region))
					throw new IllegalArgumentException(
							pavComponent + " handles region " + region +
							" which is not managed by " + this);
			}
		}
	}
}
