package mx.org.pescadormvp.client.components;

import java.util.Set;

import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPAVComponent;
import mx.org.pescadormvp.client.regionsandcontainers.ForRegionTag;

public interface ComponentRegistry {

	void addComponents(Component<?>... components);

	void callFinalizeSetup();

	<I extends Component<I>> I getComponent(Class<I> publicInterface);

	PescadorMVPPAVComponent<?, ?> getPAVComponent(String token);

	void setRegions(Set<Class<? extends ForRegionTag>> regions);
}
