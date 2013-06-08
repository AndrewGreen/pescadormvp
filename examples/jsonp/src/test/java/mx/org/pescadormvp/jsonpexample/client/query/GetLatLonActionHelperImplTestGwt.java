package mx.org.pescadormvp.jsonpexample.client.query;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class GetLatLonActionHelperImplTestGwt  extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "mx.org.pescadormvp.jsonpexample.JSONPExample";
	}

	public void testInstantiateValidResult() {
		GetLatLonActionHelperImpl helper = new GetLatLonActionHelperImpl();
		GetLatLonResult result = helper.insantiateResult(getValidJSObject());
		assertTrue(result.isValid());
		assertEquals(result.getDisplayName(), "Somewhere, someplace");
		assertEquals(result.getLat(), 45.5224507);
		assertEquals(result.getLon(), -73.5912827);
	}
	
	public void testInstantiateInvalidResult() {
		GetLatLonActionHelperImpl helper = new GetLatLonActionHelperImpl();
		GetLatLonResult result = helper.insantiateResult(getInvalidJSObject());
		assertFalse(result.isValid());
	}
	
	private final native JavaScriptObject getValidJSObject()  /*-{
		return [
		          {
		              "display_name": "Somewhere, someplace",
		          	  "lat": "45.5224507",
		          	  "lon": "-73.5912827",
		          }
		       ];
	}-*/;
	
	private final native JavaScriptObject getInvalidJSObject()  /*-{
		return [ ];
	}-*/;

}
