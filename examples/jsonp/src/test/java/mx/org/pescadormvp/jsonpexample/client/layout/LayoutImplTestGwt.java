package mx.org.pescadormvp.jsonpexample.client.layout;

import java.util.Set;

import mx.org.pescadormvp.client.regionsandcontainers.DynamicContainer;
import mx.org.pescadormvp.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.client.regionsandcontainers.LayoutHelper;
import mx.org.pescadormvp.jsonpexample.client.layout.Layout.Body;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class LayoutImplTestGwt extends GWTTestCase {

	public void testRegions() {
		Set<Class<? extends ForRegionTag>> regions = getFreshLayoutImpl().getRegions();
		assertTrue(regions.contains(Body.class));
		assertEquals(1, regions.size());
	}
	
	public void testAttach() {
		LayoutImpl layoutImpl = getFreshLayoutImpl();
		layoutImpl.attach();
		assertTrue(layoutImpl.isAttached());
	}
	
	public void testGetContainer() {
		LayoutImpl layoutImpl = getFreshLayoutImpl();
		DynamicContainer container = layoutImpl.getContainer(Body.class);
		assertNotNull(container);
	}
	
	private LayoutImpl getFreshLayoutImpl() {
		EventBus eventBus = new SimpleEventBus();
		return new LayoutImpl(new LayoutHelper(eventBus));
	}

	@Override
	public String getModuleName() {
		return "mx.org.pescadormvp.jsonpexample.JSONPExample";
	}
}
