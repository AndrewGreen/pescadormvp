/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.internallinks;

import mx.org.pescadormvp.core.client.util.DOMUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * An item in a menu that leads to a place in the application, is a
 * native hyperlink, and may have a checkmark beside it. 
 */
public class InternalCheckableItemLink extends InternalLinkBase
		implements IsItem {

	interface InternalCheckableItemLinkUiBinder extends 
		UiBinder<Widget, InternalCheckableItemLink> { }
	
	private static InternalCheckableItemLinkUiBinder uiBinder =
			GWT.create(InternalCheckableItemLinkUiBinder.class);
	
	private static final int ITEM_CONTAINER_PADDING = 3; // in pixels
	private static final int IMAGE_PADDING = 2; // in pixels
	
	@UiField AnchorElement link;
	@UiField DivElement itemContainer;
	@UiField Image image;
	@UiField SpanElement textArea;

	private ItemResources itemResources;
	
	private int minWidth;
	private int minHeight;
	
	public InternalCheckableItemLink() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// some style elements are set programatically here:
		Style itemContainerStyle = itemContainer.getStyle();
		itemContainerStyle.setPosition(Position.RELATIVE);
		itemContainerStyle.setPadding(ITEM_CONTAINER_PADDING, Unit.PX);
		
		Style textAreaStyle = textArea.getStyle();
		textAreaStyle.setPosition(Position.ABSOLUTE);
		textAreaStyle.setLeft(
				itemResources.checkmarkImage().getWidth() +
				(IMAGE_PADDING * 2),
				Unit.PX);
		
		setImageStyle();
		
		completeSetup();
	}
	
	@UiFactory
	public ItemResources getItemResources() {
		if (itemResources == null) {
			itemResources = ItemResources.INSTANCE;
			itemResources.style().ensureInjected();
		}
		
		return itemResources;
	}

	private void setImageStyle() {
		Style imageStyle = image.getElement().getStyle();
		imageStyle.setPosition(Position.ABSOLUTE);
		imageStyle.setPadding(IMAGE_PADDING, Unit.PX);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		setMinSizes();
	}
	
	@Override
	protected void setHref(String href) {
		link.setHref(href);
		setMinSizes();
	}

	@Override
	protected void setPresentationText(String presentaitonText) {
		textArea.setInnerHTML(SafeHtmlUtils.htmlEscape(presentaitonText));
		setMinSizes();
	}

	/**
	 * Set whether or not there should be a checkmark next to the item. 
	 */
	public void setChecked(boolean checked) {
		ImageResource checkmarkImage = itemResources.checkmarkImage(); 
		
		if (checked) {
			image.setResource(checkmarkImage);
			
		} else {
			image.setResource(itemResources.clearImage());
			image.setWidth(checkmarkImage.getWidth() + "px");
			image.setHeight(checkmarkImage.getHeight() + "px");
		}
		
		setImageStyle();

		setMinSizes();
	}
	
	@Override
	public int getMinWidth() {
		return minWidth;
	}

	@Override
	public int getMinHeight() {
		return minHeight;
	}

	/**
	 * Does not actually change sizes, but sets data that is used by the widget
	 * this is attached to.
	 */
	private void setMinSizes() {
		if (!isAttached())
			return;
		
		ImageResource checkmarkImage = itemResources.checkmarkImage();
		
		minHeight = Math.max(
				DOMUtils.getElementHeight(textArea), 
				checkmarkImage.getHeight() + (IMAGE_PADDING * 2)) +
				(ITEM_CONTAINER_PADDING * 2);
		
		minWidth = checkmarkImage.getWidth() +
				(IMAGE_PADDING * 2) +
				DOMUtils.getElementWidth(textArea) +
				(ITEM_CONTAINER_PADDING * 2);
	}

	@Override
	public void resizeContentWidth(int pxWidth) {
		int itemContainerWidth = pxWidth - (ITEM_CONTAINER_PADDING * 2);
		itemContainer.getStyle().setWidth(itemContainerWidth, Unit.PX);
	}
	
	@Override
	public void resizeContentHeight(int pxHeight) {
		int itemContainerHeight = pxHeight - (ITEM_CONTAINER_PADDING * 2);
		itemContainer.getStyle().setHeight(itemContainerHeight, Unit.PX);		
	}
}
