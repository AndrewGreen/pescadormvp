/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.uiresources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.ImageResourceRenderer;

/**
 * Creates a sanitized HTML string for internal links. 
 */
public class InternalLinkHTMLRenderer {

	interface Templates extends SafeHtmlTemplates {
		@Template(
				"<a href=\"{0}\" >" +
				"<div style=\"position: absolute\">{1}</div>" +
				"<div style=\"margin-left: {2}px; min-height: {3}px\">{4}</div>" +
				"</a>")
		SafeHtml linkWRenderedImg(String href, SafeHtml imageResource, 
				String imgAreaWidth, String imgAreaHeight, String text);

		@Template(
				"<a href=\"{0}\" >" +
				"<div style=\"position: absolute\">{1}</div>" +
				"<div style=\"margin-left: {2}px; min-height: {3}px\">{4}</div>" +
				"</a>")
		SafeHtml linkWRenderedImg(String href, SafeHtml imageResource,
				String imgAreaWidth, String imgAreaHeight, SafeHtml text);
		
		@Template("<a href=\"{0}\" >{1}</a>")
		SafeHtml link(String href, String text);
		
		@Template("<a href=\"{0}\" >{1}</a>")
		SafeHtml link(String href, SafeHtml text);
	}
	
	private static final Templates templates = GWT.create(Templates.class);
	
	private static final int IMAGE_TEXT_SPACING = 3;  // in px
	
	private ImageResource imageResource;
	private String presentationText;
	private SafeHtml presentationSafeHTML;
	private String href;

	public void setMainImageResource(ImageResource mainImageResource) {
		this.imageResource = mainImageResource;
	}
	
	public void setPresentationText(String presentationText) {
		this.presentationText = presentationText;
		presentationSafeHTML = null;
	}

	public void setPresentationText(SafeHtml presentationSafeHTML) {
		this.presentationSafeHTML = presentationSafeHTML;
		presentationText = null;
	}

	public void setHref(String href) {
		this.href = UriUtils.sanitizeUri(href);
	}

	public SafeHtml render() {
		if (presentationText == null)
			presentationText = "";
		
		if (href == null)
			href = "#";
		
		if (imageResource == null) {
			if (presentationSafeHTML ==  null) 
				return templates.link(href, presentationText);
			else
				return templates.link(href, presentationSafeHTML);

		} else {
			if (presentationSafeHTML ==  null) {
				return templates.linkWRenderedImg(
						href, 
						new ImageResourceRenderer().render(imageResource), 
						(imageResource.getWidth() + IMAGE_TEXT_SPACING) + "",
						imageResource.getHeight() + "",
						presentationText);	

			} else {
				return templates.linkWRenderedImg(
						href, 
						new ImageResourceRenderer().render(imageResource), 
						(imageResource.getWidth() + IMAGE_TEXT_SPACING) + "",
						imageResource.getHeight() + "",
						presentationSafeHTML);
				
				
			}
		}
	}
}
