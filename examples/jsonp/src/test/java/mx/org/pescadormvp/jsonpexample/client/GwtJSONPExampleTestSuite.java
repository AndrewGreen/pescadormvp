package mx.org.pescadormvp.jsonpexample.client;

import mx.org.pescadormvp.jsonpexample.client.layout.LayoutImplTestGwt;
import mx.org.pescadormvp.jsonpexample.client.query.GetLatLonActionHelperImplTestGwt;

import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;
import junit.framework.TestCase;

public class GwtJSONPExampleTestSuite extends TestCase {

	public static Test suite() {
		
		GWTTestSuite suite = new GWTTestSuite("tests that require GWT browser context");
		suite.addTestSuite(LayoutImplTestGwt.class);
		suite.addTestSuite(GetLatLonActionHelperImplTestGwt.class);
		return suite;
	}
}
