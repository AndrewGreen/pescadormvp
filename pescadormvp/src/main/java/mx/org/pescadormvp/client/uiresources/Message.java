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
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Message extends Composite {

	interface MessageUiBinder extends 
		UiBinder<Widget, Message> { }
	
	private static MessageUiBinder uiBinder =
			GWT.create(MessageUiBinder.class);

	@UiField HTMLPanel outerPanel;
	@UiField SpanElement textSpan;
	
	public Message(String text) {
		initWidget(uiBinder.createAndBindUi(this));

		setText(text);
	}
	
	public void setText(String text) {
		
		textSpan.setInnerHTML(SafeHtmlUtils.htmlEscape(text));
	}
}
