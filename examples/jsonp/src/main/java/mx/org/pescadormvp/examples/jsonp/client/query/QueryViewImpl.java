/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;

import mx.org.pescadormvp.core.client.internallinks.ActivateInternalLinkEvent;
import mx.org.pescadormvp.core.client.util.DOMUtils;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.CustomButton;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * <p>Implementation of the query view. See {@link QueryView} for usage.</p>
 * 
 * <p>A view is as also a widget that can be attached to a region of the viewport.
 * This view also uses UiBinder to specify HTML and CSS code declaratively rather than
 * programatically.</p>
 * 
 * <p>This class takes care of logic related to switching styles, showing or hiding
 * elements, setting text. It also receives user interactions and passes them on to the
 * activity for processing.</p>
 *  
 * @author Andrew Green
 */
public class QueryViewImpl extends ResizeComposite implements
		QueryView {

	// Value shared between Java and in CSS
	private static int TEXT_CONTAINER_PADDING_PX = 10;
	
	// Time to wait for a request to come back before showing a loading message
	// Set to default visibility so we can get it in tests
	static int WAIT_FOR_LOADING_MESSAGE_MS = 1500;
	
	// Durations for load throbber
	private static int LOADING_THROBBER_REPEAT_MS = 2000;
	private static int LOADING_THROBBER_THROB_MS = 800;
	
	// We'll use UiBinder for declarative HTML and CSS
	interface QueryViewImplUiBinder extends
			UiBinder<Widget, QueryViewImpl> {
	}

	// Java access to style names from UiBinder
	interface Style extends CssResource {
		String queryStrip();
		String messageStrip();
		String messagePar();
		String textBox();
		String embarassedTextBox();
	}

	// Everything annotated as UiField is defined with UiBinder

	// Instnace of the above interface, for getting the names of CSS classes.
	// (In compiled GWT those names are obfuscated to avoid name collisions.)
	@UiField Style style;
	
	//Panels, containers, colored strips
	@UiField SimpleLayoutPanel outerPanel;
	@UiField DivElement queryStrip;
	@UiField HTMLPanel queryContainer;
	
	// Text box and surrounding elemnets
	@UiField SpanElement beforeQueryTextBoxSpan;
	@UiField SpanElement afterQueryTextBoxSpan;
	
	@UiField SuggestBox suggestBox;
	@UiField CustomButton goButton;
	
	// Message area
	@UiField DivElement messageStrip;
	@UiField DivElement messageContainer;
	
	// Map area
	@UiField SimplePanel mapContainer;
	
	// Decorative Earth image
	@UiField DivElement earthImageContainer;
	@UiField ImageResource earth;
	
	// Map
	private OSMMap map;
	
	// timer for showing loading message after a certain delay
	private Timer loadingTimer; 
	
	// for animating the throbber shown while waiting for data to load
	private LoadingThrob loadingThrob = new LoadingThrob();
	private Timer loadingThobTimer;
	
	// The singleton instance of the global event bus
	private EventBus eventBus;
	
	// Some state info
	boolean queryAreaRendered;
	boolean loaded;
	
	// Data loaded into the view by a QueryActivity
	private String latLonMsg;
	private String neverHeardOf;
	private String loading;
	private String errorCommunicating;
	private String tryAgain;
	
	// An instance of a query place to use for the next query
	private QueryPlace rawQueryPlace;
	
	// A convenience GWT mechanism for building safe HTML
	interface Templates extends SafeHtmlTemplates {
		@Template("<p class=\"{0}\">{1}</p>")
		SafeHtml p(String styleName, String txt);
	}

	private Templates templates;

	public static String getTextContainerPadding() {
		return TEXT_CONTAINER_PADDING_PX + "px";
	}
	
	@Inject
	public QueryViewImpl(
			EventBus eventBus,
			Templates templates,
			final QueryViewImplUiBinder uiBinder) {

		this.eventBus = eventBus;
		this.templates = templates;

		// Firing up the UiBinder mechanism
		initWidget(uiBinder.createAndBindUi(this));
		
		// Handle events from the suggest box and go button.
		suggestBox.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
					doQuery();
			}
		});
		
		goButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				// De-focus the button so that we don't see an ugly
				// outline around it.
				goButton.setFocus(false);
				doQuery();
			}
		});
	}

	/** Fire an event to execute the next query. Setting place data is one of the
	 * <i>only</i> non-display tasks that places are allowed to perform in this
	 * framework.
	 */
	private void doQuery() {
		rawQueryPlace.setLocation(suggestBox.getText());
		eventBus.fireEventFromSource(
				new ActivateInternalLinkEvent(rawQueryPlace), this);
	}

	/**
	 * Receive the place for the next query.
	 */
	@Override
	public void setRawQueryPlace(QueryPlace rawQueryPlace) {
		this.rawQueryPlace = rawQueryPlace;
	}
	
	@Override
	public void onResize() {
		super.onResize();
		fixLayout();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		
		loaded = true;
		fixLayout();
	}

	/**
	 * This is needed to dynamically set some dimensions and placement of UI
	 * elements. It might be possible to avoid this with some very, 
	 * very clever CSS, however...
	 */
	private void fixLayout() {

		// check if we're ready to set dimensions
		if (loaded && queryAreaRendered) {
			
			// A scheduled command executes after the browser has finished doing
			// other pending actions. If we don't do it like this, we don't get
			// the correct measurement of UI elements.
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					
					int w = DOMUtils.getElementWidth(queryContainer.getElement());
					int realW = w + (TEXT_CONTAINER_PADDING_PX * 2);
					
					messageStrip.getStyle().setWidth(w, Unit.PX);
					mapContainer.getElement().getStyle().setLeft(realW, Unit.PX);
					
					int earthImageWidth = earth.getWidth();
					int earthContainerLeft = 
							Math.max(0, realW - earthImageWidth) / 2;
					
					earthImageContainer.getStyle().setLeft(earthContainerLeft, Unit.PX);
					earthImageContainer.getStyle().setBottom(0, Unit.PX);
				}
			});
		}
	}

	@Override
	public boolean isQueryAreaRendered() {
		return queryAreaRendered;
	}
	
	/**
	 * Set info elements surrounding the text box, which remain static.
	 */
	@Override
	public void renderQueryArea(String beforeQueryTextBox,
			String afterQueryTextBox) {

		beforeQueryTextBoxSpan.setInnerSafeHtml(
				SafeHtmlUtils.fromString(beforeQueryTextBox));

		afterQueryTextBoxSpan.setInnerSafeHtml(
				SafeHtmlUtils.fromString(afterQueryTextBox));

		queryStrip.getStyle().setVisibility(Visibility.VISIBLE);
		
		queryAreaRendered = true;
		fixLayout();
	}

	/**
	 * Set the view's OSMMap. 
	 */
	@Override
	public void setOSMMap(OSMMap map) {
		this.map = map;
		mapContainer.add(map);
	}

	/**
	 * Does the view have the OSMMap to it will use?
	 */
	@Override
	public boolean osmMapSet() {
		return map != null;
	}
	
	/**
	 * Sets latitude and longitude and related message
	 */
	@Override
	public void setLatLonMsg(String latLonMsg) {
		this.latLonMsg = latLonMsg;
	}

	/**
	 * Strings for when no matching place has been found.
	 */
	@Override
	public void setNoSuchPlaceStrings(String neverHeardOf,
			String tryAgain) {
		this.neverHeardOf = neverHeardOf;
		this.tryAgain = tryAgain;
	}

	@Override
	public void setLoadingString(String loading) {
		this.loading = loading;
	}

	@Override
	public void setErrorStrings(String errorCommunicating, String tryAgain) {
		this.errorCommunicating = errorCommunicating;
		this.tryAgain = tryAgain;
	}

	@Override
	public void setTextboxContents(String textBoxContents) {
		suggestBox.setText(SafeHtmlUtils.htmlEscape(textBoxContents));
	}
	
	// The following methods use the strings previously sent in to the view,
	// and render it with different types of messages.
	@Override
	public void renderEmpty() {
		commonNonLoadingRender();
		messageStrip.getStyle().setVisibility(Visibility.HIDDEN);
		setTextBoxEmbarassed(false);
	}

	@Override
	public void renderLatLon() {
		commonNonLoadingRender();
		messageStrip.getStyle().setVisibility(Visibility.VISIBLE);
		messageContainer.setInnerSafeHtml(SafeHtmlUtils.fromString(latLonMsg));

		// Note: the map location itself is set directly by the activity,
		// by way of the map object.
		
		setTextBoxEmbarassed(false);
	}

	@Override
	public void renderNoSuchPlace() {
		commonNonLoadingRender();
		messageStrip.getStyle().setVisibility(Visibility.VISIBLE);
		
		messageContainer.setInnerHTML(
				templates.p(style.messagePar(), neverHeardOf).asString() + 
				templates.p(style.messagePar(), tryAgain).asString());

		setTextBoxEmbarassed(true);
	}

	@Override
	public void renderError() {
		commonNonLoadingRender();
		messageStrip.getStyle().setVisibility(Visibility.VISIBLE);

		messageContainer.setInnerHTML(
				templates.p(style.messagePar(), errorCommunicating).asString() + 
				templates.p(style.messagePar(), tryAgain).asString());

		setTextBoxEmbarassed(false);
	}
	
	@Override
	public void startLoadingTimer() {
		loadingTimer = new Timer() {
			
			@Override
			public void run() {
				renderLoading();
			}
		};
		
		loadingTimer.schedule(WAIT_FOR_LOADING_MESSAGE_MS);
	}
		
	private void renderLoading() {		
		messageStrip.getStyle().setVisibility(Visibility.VISIBLE);
		messageContainer.setInnerSafeHtml(SafeHtmlUtils.fromString(loading));

		loadingThobTimer = new Timer() {

			@Override
			public void run() {
				loadingThrob.run(LOADING_THROBBER_THROB_MS);
			}
		};
		
		loadingThobTimer.scheduleRepeating(LOADING_THROBBER_REPEAT_MS);
		
		setTextBoxEmbarassed(false);
	}

	/**
	 * Does standard things for all kinds of states other than loading.
	 */
	private void commonNonLoadingRender() {
		if (loadingTimer != null)
			loadingTimer.cancel();
		
		// If loading stuff if happening, turn it off.
		if (loadingThobTimer != null)
			loadingThobTimer.cancel();
		
		loadingThrob.cancel();
		
		// Get the text box ready for the next input 
		suggestBox.setFocus(true);
	}
	
	/** make the text box coloured or not
	 */
	private void setTextBoxEmbarassed(boolean embarassed) {
		if (embarassed)
			suggestBox.setStylePrimaryName(style.embarassedTextBox());
		else
			suggestBox.setStylePrimaryName(style.textBox());
	}
	
	/** Loading throbber animation
	 */
	private class LoadingThrob extends Animation {

		@Override
		protected void onUpdate(double progress) {
			double msgOpacity = Math.abs(1 - (progress * 2));
			messageContainer.getStyle().setOpacity(msgOpacity);
		}

		@Override
		protected void onComplete() {
			super.onComplete();
			messageContainer.getStyle().setOpacity(1);
		}

		@Override
		protected void onCancel() {
			super.onCancel();
			messageContainer.getStyle().setOpacity(1);
		}
	}
}
