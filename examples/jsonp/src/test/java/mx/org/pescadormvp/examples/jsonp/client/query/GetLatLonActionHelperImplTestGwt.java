package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonActionHelperImpl;
import mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonResult;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class GetLatLonActionHelperImplTestGwt extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "mx.org.pescadormvp.examples.jsonp.JSONPExample";
	}

	public void testInstantiateResultWithData() {
		GetLatLonActionHelperImpl helper = new GetLatLonActionHelperImpl();
		GetLatLonResult result = helper.insantiateResult(getJSObjectWihData());
		assertTrue(result.hasData());
		assertEquals(result.getDisplayName(), "Somewhere, someplace");
		assertEquals(result.getLat(), 45.5224507);
		assertEquals(result.getLon(), -73.5912827);
	}
	
	public void testInstantiateResultWithoutData() {
		GetLatLonActionHelperImpl helper = new GetLatLonActionHelperImpl();
		GetLatLonResult result = helper.insantiateResult(getJSObjectWithoutData());
		assertFalse(result.hasData());
	}
	
	private final native JavaScriptObject getJSObjectWihData()  /*-{
		return [
		          {
		              "display_name": "Somewhere, someplace",
		          	  "lat": "45.5224507",
		          	  "lon": "-73.5912827",
		          }
		       ];
	}-*/;
	
	private final native JavaScriptObject getJSObjectWithoutData()  /*-{
		return [ ];
	}-*/;
}
