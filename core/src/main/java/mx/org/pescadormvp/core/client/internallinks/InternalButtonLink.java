/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.internallinks;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class InternalButtonLink extends InternalLinkBase {

	interface InternalButtonLinkUiBinder extends 
			UiBinder<Widget, InternalButtonLink> { }

	private static InternalButtonLinkUiBinder uiBinder =
			GWT.create(InternalButtonLinkUiBinder.class);
	
	interface InternalButtonLinkStyle extends CssResource {
		String link();
		String linkEnabled();
		String linkEnabledHyperStyle();
		String linkDisabled();
		String linkDisabledHyperStyle();
		String htmlPanel();
		String imageDiv();
		String textSpan();
		String image();
	}
	
	private static final double ROUNDED_CORNER_RADIUS = 3; // in pixels
	
	@UiField HTMLPanel htmlPanel;
	@UiField AnchorElement link;
	@UiField DivElement imageDiv;
	@UiField DivElement imagePlacetaker;
	@UiField SpanElement textSpan;
	private Image image;

	@UiField InternalButtonLinkStyle style;
	private boolean hyperStyle;
	
	private boolean showText;
	private boolean showImage;
	private String presentationText;
	private Integer targetHeight;
	private int imageWidth;
	private int imageHeight;
	
	public static String roundedCornerRadius() {
		return ROUNDED_CORNER_RADIUS + "px";
	}
	
	public InternalButtonLink() {
		initWidget(uiBinder.createAndBindUi(this));
		link.getStyle().setProperty("backgroundSize", "100%");
		link.getStyle().setProperty("backgroundPosition", "bottom");
		completeSetup();
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
		setupPresentationText();
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
		setupImage();
	}
	
	public void setTargetHeightPx(Integer targetHeight) {
		this.targetHeight = targetHeight;
		
		if (targetHeight != null) {
			htmlPanel.getElement().getStyle()
					.setProperty("lineHeight", targetHeight, Unit.PX);
			
			link.getStyle().setProperty("backgroundSize", "100% " + targetHeight + "px");
			link.getStyle().setHeight(targetHeight, Unit.PX);
		} else {
			htmlPanel.getElement().getStyle()
					.clearProperty("lineHeight");
		}
			
		setupImage();
	}

	public void setImageResource(ImageResource imageResource) {
		ensureImage();
		imageWidth = imageResource.getWidth();
		imageHeight = imageResource.getHeight();
		image.setResource(imageResource);
		setupImage();
	}

	public void setImageInfo(String imageSrc, int width, int height) {
		ensureImage();

		imageWidth = width;
		image.setWidth(width + "px");
		
		imageHeight = height;
		image.setHeight(height + "px");
		
		image.setUrl(UriUtils.fromString(imageSrc));
		setupImage();
	}
	
	public void setFlushLeft(boolean flushLeft) {
		String radius = 
				flushLeft ? 
				"0px " +  roundedCornerRadius() + 
				" " + roundedCornerRadius() + " 0px" :
				roundedCornerRadius();
		
		link.getStyle().setProperty("borderRadius", radius);
	}

	public void setFlushRight(boolean flushRight) {
		String radius = 
				flushRight ? 
				roundedCornerRadius() + " 0px 0px " + roundedCornerRadius() :
				roundedCornerRadius();

		link.getStyle().setProperty("borderRadius", radius);
	}
	
	public void setHyperStyle(boolean hyperStyle) {
		this.hyperStyle = hyperStyle;
		setupStyles();
	}

	@Override
	protected void setHref(String href) {
		link.setHref(href);
	}

	@Override
	protected void setPresentationText(String presentationText) {
		this.presentationText = presentationText;
		setupPresentationText();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		setupStyles();
	}

	private void setupStyles() {
		if (isEnabled()) {
			link.removeClassName(style.linkDisabled());
			link.removeClassName(style.linkDisabledHyperStyle());
			
			if (hyperStyle) {
				link.addClassName(style.linkEnabledHyperStyle());
				link.removeClassName(style.linkEnabled());
			} else {
				link.addClassName(style.linkEnabled());
				link.removeClassName(style.linkEnabledHyperStyle());
			}
			
		} else {
			link.removeClassName(style.linkEnabled());
			link.removeClassName(style.linkEnabledHyperStyle());

			if (hyperStyle) {
				link.addClassName(style.linkDisabledHyperStyle());
				link.removeClassName(style.linkDisabled());
			} else {
				link.addClassName(style.linkDisabled());
				link.removeClassName(style.linkDisabledHyperStyle());
			}
			
		}
	}
	
	private void setupPresentationText() {
		if (presentationText == null)
			return;
		
		if (showText) {
			
			// TODO This should really be escaped, but for some reason,
			// when this widget is added to a HTMLPanel in HeadContentViewImpl,
			// entities are double-escaped. Figure out why this happens, fix.
			textSpan.setInnerText(presentationText);
			
			link.setTitle("");
		} else {
			link.setTitle(presentationText);
		}
	}
	
	private void ensureImage() {
		if (image == null)
			image = new Image();
	}
	
	private void setupImage() {
		
		if (showImage) {
			// make sure image object exists and is attached 
			ensureImage();
			htmlPanel.add(image, imageDiv);
			
			// placetaker occupies the horizontal space of the image in
			// document flow, allowing the image itself to be placed
			// absolutely (and its 
			imagePlacetaker.setInnerHTML("&nbsp;");
			imagePlacetaker.getStyle().setWidth(imageWidth, Unit.PX);

			// apparently style stuff has to be done *after* image resource
			//  (and other image characteristics?) are set
			
			if (targetHeight != null) {
				int topOffset = 
						Math.round(((float) targetHeight / 2) - 
						((float) imageHeight / 2));
				image.getElement().getStyle().setTop(topOffset, Unit.PX);
			}
			
			image.setStyleName(style.image());
			
		} else if (image != null) {
			htmlPanel.remove(image);
		
		}
	}
}
