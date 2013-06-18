package mx.org.pescadormvp.examples.jsonp.client;

import mx.org.pescadormvp.examples.jsonp.client.layout.LayoutImplTestGwt;
import mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonActionHelperImplTestGwt;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryViewImplTestGwt;

import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;
import junit.framework.TestCase;

public class GwtJSONPExampleTestSuite extends TestCase {

	public static Test suite() {
		
		GWTTestSuite suite = new GWTTestSuite("tests that require GWT browser context");
		suite.addTestSuite(LayoutImplTestGwt.class);
		suite.addTestSuite(GetLatLonActionHelperImplTestGwt.class);
		suite.addTestSuite(QueryViewImplTestGwt.class);
		return suite;
	}
}
