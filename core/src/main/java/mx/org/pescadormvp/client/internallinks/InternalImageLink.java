/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.internallinks;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class InternalImageLink extends InternalLinkBase {

	interface InternalImageLinkUiBinder extends 
		UiBinder<Widget, InternalImageLink> { }
	
	private static InternalImageLinkUiBinder uiBinder =
			GWT.create(InternalImageLinkUiBinder.class);
	
	interface InternalImageLinkStyle extends CssResource {
		String image();
		String imageDropShadow();
		String link();
	}
	
	@UiField Image image;
	@UiField AnchorElement link;
	@UiField InternalImageLinkStyle style;
		
	public InternalImageLink() {
		initWidget(uiBinder.createAndBindUi(this));
		completeSetup();
	}
	
	public void setImageResource(ImageResource imageResource) {
		image.setResource(imageResource);
	}

	public void setImageSrc(String imageSrc) {
		image.setUrl(UriUtils.fromString(imageSrc));
	}
	
	public void setImageWidthPx(int width) {
		image.setWidth(width + "px");
	}
	
	public void setImageHeightPx(int height) {
		image.setHeight(height + "px");
	}
	
	public void setDropShadow(boolean dropShadow) {

		if (dropShadow)
			image.addStyleName(style.imageDropShadow());
		else
			image.removeStyleName(style.imageDropShadow());
	}
	
	@Override
	protected void setHref(String href) {
		link.setHref(href);
	}

	@Override
	protected void setPresentationText(String presentaitonText) {
		link.setTitle(presentaitonText);
	}
}
