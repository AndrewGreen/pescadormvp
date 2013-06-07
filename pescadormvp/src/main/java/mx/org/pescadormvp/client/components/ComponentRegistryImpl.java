package mx.org.pescadormvp.client.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPAVComponent;
import mx.org.pescadormvp.client.regionsandcontainers.ForRegionTag;

public class ComponentRegistryImpl implements ComponentRegistry {

	private Map<String, PescadorMVPPAVComponent<?,?>> mainTokenIndex =
			new HashMap<String, PescadorMVPPAVComponent<?,?>>();
	
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
	public PescadorMVPPAVComponent<?,?> getPAVComponent(String token) {
		return mainTokenIndex.get(token);
	}
	
	@Override
	public void setRegions(Set<Class<? extends ForRegionTag>> regions) {
		this.regions = regions;
	}
	
	private void addComponent(Component<?> component) {
		componentIndex.put(component.publicInterface(), component);
		
		if (component instanceof PescadorMVPPAVComponent<?,?>) {
			PescadorMVPPAVComponent<?,?> pavComponent =
					(PescadorMVPPAVComponent<?,?>) component;
			
			mainTokenIndex.put(pavComponent.getMainToken(), pavComponent);
			
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
