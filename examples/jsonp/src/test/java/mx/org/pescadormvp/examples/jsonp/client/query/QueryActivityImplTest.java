package mx.org.pescadormvp.examples.jsonp.client.query;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.containsString;

import java.util.logging.Level;

import mx.org.pescadormvp.core.client.data.DataManager;
import mx.org.pescadormvp.core.client.data.JsonpActionHelper;
import mx.org.pescadormvp.core.client.logging.PescadorMVPLogger;
import mx.org.pescadormvp.core.client.placesandactivities.ActivitiesFactory;
import mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonResult;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryActivity;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryActivityImpl;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryView;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryMessages;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * This class performs various tests on {@link QueryActivityImpl}.
 *  
 * @author Andrew Green
 */
@RunWith(JukitoRunner.class)
public class QueryActivityImplTest {

	// This will get factory for getting the QueryActivityImpls we want to test
	@Inject ActivitiesFactory<QueryPlace, QueryActivity> activitiesFactory;
	
	// These will get automocked by Mockito/Jukito
	@Inject AcceptsOneWidget panel;
	@Inject EventBus eventBus;
	@Inject QueryView queryView;
	@Inject PescadorMVPLogger logger;
	@Inject QueryMessages messages;
	
	// Hand-crafted mock of DataManager
	@Inject private DataManager dataManager;
	
	// Here are some mocks we set with Mockito
	private QueryPlace mockedPlaceWithData;
	private QueryPlace mockedPlaceEmpty;
	private GetLatLonResult validResult;
	private GetLatLonResult invalidResult;
	
	// constant strings for tests
	private static final String MOCK_SERVER_ERROR_STR = "Test of OnFailure";
	private static final String MOCK_LOCATION_STR = "Somewhere";
	private static final String MOCK_DISPLAY_NAME_STR = "Somewhere, someplace";
	private static final double MOCK_LATITUDE = 45.5224507;
	private static final double MOCK_LONGITUDE = -73.5912827;

	/**
	 * Handmade mock of DataManager. Set succeed to true for mock server calls
	 * to succeed, or false for them to fail. Set result for the desired
	 * mock result.
	 */
	public static class MockDataManager implements DataManager {

		private boolean succeed;
		private GetLatLonResult result;
		
		void setSucceed(boolean succeed) {
			this.succeed = succeed;
		}
		
		void setResult(GetLatLonResult result) {
			this.result = result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <A extends Action<R>, R extends Result> void execute(A action,
				Class<R> resultClass, AsyncCallback<R> callback) {
			
			if (succeed)
				callback.onSuccess((R) result);
			else
				callback.onFailure(new RuntimeException(MOCK_SERVER_ERROR_STR));
		}

		// The following methods are stubs, not used here.
		@Override
		public void finalizeSetup() { }

		@Override
		public Class<DataManager> publicInterface() { return null; }

		@Override
		public void registerActionHelper(JsonpActionHelper<?, ?> helper) { }

		@Override
		public void clearCache() { }
	}
	
	/**
	 * Most bindings are automocked by Jukito. Here are some that we
	 * set up manually.  
	 */
	public static class Module extends JukitoModule {

		@Override
		protected void configureTest() {

			// create activities factory
			install(new FactoryModuleBuilder().implement(
					QueryActivity.class, QueryActivityImpl.class)
					.build(
					new TypeLiteral<
					ActivitiesFactory<QueryPlace, QueryActivity>>(){}));
			
			bind(DataManager.class).to(MockDataManager.class).in(Singleton.class);
		}
	}

	/**
	 * Use Mockito to specify behavior of mocked places and results.
	 */
	@Before
	public void setupMocks() {
		// Two mock places, one that's empty and another with location data
		mockedPlaceWithData = mock(QueryPlace.class);
		mockedPlaceEmpty = mock(QueryPlace.class);
		
		when(mockedPlaceWithData.getLocation()).thenReturn(MOCK_LOCATION_STR);
		when(mockedPlaceEmpty.getLocation()).thenReturn("");
		
		// set up mock results here
		validResult = mock(GetLatLonResult.class);
		when(validResult.isValid()).thenReturn(true);
		when(validResult.getDisplayName()).thenReturn(MOCK_DISPLAY_NAME_STR);
		when(validResult.getLat()).thenReturn(MOCK_LATITUDE);
		when(validResult.getLon()).thenReturn(MOCK_LONGITUDE);

		invalidResult = mock(GetLatLonResult.class);
		when(invalidResult.isValid()).thenReturn(false);
	}

	/**
	 * Test that the query area is not rendered if it's already set up.
	 */
	@Test
	public void testQueryAreaSetUp() {
		when(queryView.isQueryAreaRendered()).thenReturn(true);
		startTestActivity(true, true, true);
		verify(queryView,  never()).renderQueryArea(anyString(), anyString());
	}

	/**
	 * Test that the query area is rendered if it's not already set up.
	 */
	@Test
	public void testQueryAreaNotSetUp() {
		when(queryView.isQueryAreaRendered()).thenReturn(false);
		startTestActivity(true, true, true);
		verify(queryView).renderQueryArea(anyString(), anyString());
	}

	/**
	 * Test that the view is rendered as empty is the place doesn't specify a
	 * location to query.
	 */
	@Test
	public void testRenderEmpty() {
		startTestActivity(false, true, true);
		
		// verify that the view is rendered empty when there is no location data
		verify(queryView).renderEmpty();
	}
	
	/**
	 * When the place has data and a valid result is obtained from the server:
	 * test the loading timer has been started, that {@link QueryMessages} is used to
	 * build a string using data from that result, that information is rendered
	 * into the view, and that everything is done in the correct order.
	 */
	@Test
	public void testRenderLatLon() {
		startTestActivity(true, true, true);
		
		// test message info sent
		ArgumentCaptor<String> latInfo = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> lonInfo = ArgumentCaptor.forClass(String.class);
		
		InOrder inOrderMessages = inOrder(queryView, messages);
		
		// verify messages are set, sent to view, and rendered in the correct order,
		// and that messages have the correct contents
		inOrderMessages.verify(messages).XisAtY(eq(MOCK_DISPLAY_NAME_STR), latInfo.capture(),
				lonInfo.capture());
		inOrderMessages.verify(queryView).setLatLonMsg(anyString());
		inOrderMessages.verify(queryView).renderLatLon();
		
		assertThat(latInfo.getValue(), containsString(Math.abs(MOCK_LATITUDE) + ""));
		assertThat(lonInfo.getValue(), containsString(Math.abs(MOCK_LONGITUDE) + ""));
	
		// verify that loading timer is called before location is rendered
		InOrder inOrderView= inOrder(queryView);
		inOrderView.verify(queryView).scheduleLoadingMessage();
		inOrderView.verify(queryView).renderLatLon();
	}

	/**
	 * When the place has data and an invalid result is obtained from the server:
	 * test that the loading timer has been started and that the view is correctly
	 * rendered.
	 */
	@Test
	public void testNoSuchPlace() {
		startTestActivity(true, true, false);

		InOrder inOrder = inOrder(queryView);
		inOrder.verify(queryView).scheduleLoadingMessage();
		inOrder.verify(queryView).renderNoSuchPlace();
	}
	
	/**
	 * When the place has data but the server returns an error:
	 * test that the loading timer has been started, that the view is correctly
	 * rendered, and that the error is logged.
	 */
	@Test
	public void testServerError() {
		startTestActivity(true, false, true);
		
		// Verify that the activity starts the loading timer, 
		// sets an error state in the view, and logs the error
		InOrder inOrder = inOrder(queryView);
		inOrder.verify(queryView).scheduleLoadingMessage();
		inOrder.verify(queryView).renderError();
		
		verify(logger).log(isA(Level.class), contains(MOCK_SERVER_ERROR_STR));
	}
	
	/**
	 * Private convenience method for starting a test activity with different
	 * conditions. 
	 */
	private QueryActivity startTestActivity(
			boolean placeHasData,
			boolean succeed,
			boolean resultIsValid) {

		QueryPlace mockedPlace = placeHasData ? mockedPlaceWithData : mockedPlaceEmpty;
		QueryActivity activity = activitiesFactory.create(mockedPlace);

		MockDataManager mockDataManager = (MockDataManager) dataManager;
		mockDataManager.setSucceed(succeed);
		GetLatLonResult result = resultIsValid ? validResult : invalidResult; 
		mockDataManager.setResult(result);
		
		activity.start(panel, eventBus);
		return activity;
	}
}
