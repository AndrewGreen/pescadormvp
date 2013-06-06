/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.uiresources;

import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;

/**
 * Logic for creating a link to somewhere in the app, based on a
 * {@link PescadorMVPPlace}. Sanitizes input using {@link SafeHtmlUtils}.
 */
public abstract class InternalLinkBase extends Composite 
		implements InternalLink {

	private static HyperlinkImpl hyperlinkImpl = GWT.create(HyperlinkImpl.class);

	private PescadorMVPPlace place;
	private String alternatePresentationText;
	private boolean enabled = true;
	
	/**
	 * It is expected that this method will be called from a subclass's
	 * constructor, after {@link #initWidget(Widget)}
	 * has been called.
	 */
	protected void completeSetup() {
		// depending on how this is implemented, more events may actually be sunk
		sinkEvents(Event.ONCLICK | Event.ONMOUSEDOWN);
	}
	
	@Override
	public void setPlace(PescadorMVPPlace place) {
		this.place = place;
		setHref(UriUtils.sanitizeUri((place.getURL())));
		
		if (alternatePresentationText == null) {
			// there may be cases, perhaps due to malicious scripts, or
			// otherwise, in which a place's presentationText is null.
			// escaping presentation text is subclass's responsibility
			String presentationText = place.getPresentationText();
			if (presentationText != null)
				setPresentationText(presentationText); 
		}
	}
	
	@Override
	public PescadorMVPPlace getPlace() {
		return place;
	}
	
	@Override
	public String getAlternatePresentationText() {
		return alternatePresentationText;
	}

	@Override
	public void setAlternatePresentationText(String alternatePresentationText) {
		this.alternatePresentationText = alternatePresentationText;
		setPresentationText(SafeHtmlUtils.htmlEscape(alternatePresentationText));
	}
	
	protected boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * URI should be sanitized <em>before</em> it is sent to this method.
	 * 
	 * @param presentaitonText
	 */
	protected abstract void setHref(String href);
	
	/**
	 * Text might <em>NOT</em> be sanitized it is sent to this method.
	 * Sanitizing is implementing class's responsibility.
	 * 
	 * @param presentationText
	 */
	protected abstract void setPresentationText(String presentationText);

	@Override
	public HandlerRegistration addActivateInternalLinkHandler(
			ActivateInternalLinkEvent.Handler handler) {
		return addHandler(handler, ActivateInternalLinkEvent.TYPE);
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		if ((DOM.eventGetType(event) == Event.ONMOUSEDOWN)) {

			// prevents dragging 
			DOM.eventPreventDefault(event);
			
			// In some implementations (namely subclasses of CustomButton)
			// calling the super method clobbers keyboard info received in
			// subsequent click events. I don't know why (bug?), so this should
			// be considered a workaround.
			return;
		}
		
		if (DOM.eventGetType(event) == Event.ONCLICK) {
			// handle all click events ourselves
			if (enabled) 
				handleClick(event);
			
		} else {
			// other cases, take the default action
			super.onBrowserEvent(event);
		}
	}
	
	/**
	 * We could simply let the browser handle all clicks on internal links.
	 * That would work, but it's faster to handle the links and place changes
	 * directly. But for control-clicks and other non-standard clicks, we need
	 * to let the browser handle things.
	 * 
	 * This class uses {@link HyperlinkImpl} to make the distinction.
	 * (Thus using part of the internals from GWT's {@link Hyperlink}
	 * implementation.)
	 */
	protected void handleClick(Event event) {
		if (hyperlinkImpl.handleAsClick(event)) {
			DOM.eventPreventDefault(event);
			fireEvent(new ActivateInternalLinkEvent(place));
		}		
	}
}
