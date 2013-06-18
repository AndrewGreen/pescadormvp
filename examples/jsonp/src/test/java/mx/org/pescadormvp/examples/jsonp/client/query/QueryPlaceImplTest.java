package mx.org.pescadormvp.examples.jsonp.client.query;

import static org.junit.Assert.*;

import mx.org.pescadormvp.examples.jsonp.client.query.QueryPlaceImpl;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class QueryPlaceImplTest {

	@Inject QueryPlaceImpl queryPlaceImpl;
	
	@Test
	public void testLocation() {
		String testLocation = "Test location";
		queryPlaceImpl.setLocation(testLocation);
		assertEquals(testLocation, queryPlaceImpl.getLocation());
	}
}
