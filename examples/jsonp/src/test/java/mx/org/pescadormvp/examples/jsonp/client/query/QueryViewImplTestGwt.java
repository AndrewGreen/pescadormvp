package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.examples.jsonp.client.query.QueryViewImpl;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryViewImpl.QueryViewImplUiBinder;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryViewImpl.Templates;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class QueryViewImplTestGwt extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "mx.org.pescadormvp.examples.jsonp.JSONPExample";
	}
	
	public void testRenderQueryArea() {
		QueryViewImpl view = setupView();
		String beforeQueryTextBox = "Before QTB";
		String afterQueryTextBox = "After QTB";

		assertFalse(view.isQueryAreaRendered());
		
		view.renderQueryArea(beforeQueryTextBox, afterQueryTextBox);
		
		assertEquals(beforeQueryTextBox,
				view.beforeQueryTextBoxSpan.getInnerText());
		
		assertEquals(afterQueryTextBox,
				view.afterQueryTextBoxSpan.getInnerText());
		
		assertEquals(Visibility.VISIBLE.getCssName(),
				view.queryStrip.getStyle().getVisibility());
		
		assertTrue(view.isQueryAreaRendered());
	}
	
	public void testEmpty() {
		QueryViewImpl view = setupView();
		view.renderEmpty();
		
		assertEquals(Visibility.HIDDEN.getCssName(),
				view.messageStrip.getStyle().getVisibility());
	}
	
	public void testLatLon() {
		QueryViewImpl view = setupView();
		String latLonMsg = "Somwhere is at 0 W 0 N";
		
		view.setLatLonMsg(latLonMsg);
		view.renderLatLon();
		
		assertEquals(latLonMsg, view.messageContainer.getInnerText());

		assertEquals(Visibility.VISIBLE.getCssName(),
				view.messageStrip.getStyle().getVisibility());
	}
	
	public void testNoSuchPlace() {
		QueryViewImpl view = setupView();
		String neverHeardOf = "Never heard of";
		String tryAgain = "Try again";
		
		view.setNoSuchPlaceStrings(neverHeardOf, tryAgain);
		view.renderNoSuchPlace();
		
		assertEquals(neverHeardOf,
				((Element) view.messageContainer.getChild(0)).getInnerText());
		assertEquals(tryAgain,
				((Element) view.messageContainer.getChild(1)).getInnerText());

		assertEquals(Visibility.VISIBLE.getCssName(),
				view.messageStrip.getStyle().getVisibility());
	}
	
	public void testLoading() {
		final QueryViewImpl view = setupView();
		final String loadingMsg = "Loading...";
		
		view.setLoadingString(loadingMsg);
		view.startLoadingTimer();
		
		Timer t = new Timer() {
			
			@Override
			public void run() {
				assertEquals(loadingMsg, view.messageContainer.getInnerText());

				assertEquals(Visibility.VISIBLE.getCssName(),
						view.messageStrip.getStyle().getVisibility());
				
				finishTest();
			}
		};
		
		delayTestFinish(QueryViewImpl.WAIT_FOR_LOADING_MESSAGE_MS * 2);
		t.schedule(QueryViewImpl.WAIT_FOR_LOADING_MESSAGE_MS + 200);
	}

	public void testError() {
		QueryViewImpl view = setupView();
		String errorCommunicating = "Misunderstanding with server";
		String tryAgain = "Try again";
		
		view.setErrorStrings(errorCommunicating, tryAgain);
		view.renderError();
		
		assertEquals(errorCommunicating,
				((Element) view.messageContainer.getChild(0)).getInnerText());
		assertEquals(tryAgain,
				((Element) view.messageContainer.getChild(1)).getInnerText());

		assertEquals(Visibility.VISIBLE.getCssName(),
				view.messageStrip.getStyle().getVisibility());
	}
	
	private QueryViewImpl setupView() {
		return new QueryViewImpl(
				new SimpleEventBus(),
				(Templates) GWT.create(Templates.class),
				(QueryViewImplUiBinder) GWT.create(QueryViewImplUiBinder.class));
	}
}
