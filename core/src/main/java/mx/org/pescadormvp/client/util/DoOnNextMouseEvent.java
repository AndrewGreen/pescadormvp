/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
/*
 * Copyright 2011 Instituto de Investigaciones Dr. José María Luis Mora
 * See COPYING.txt and LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2011 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase COPYING.txt y LICENSE.txt para los términos bajo los cuales
 * se permite la redistribución.
 */
package mx.org.pescadormvp.client.util;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

/**
 * Executes a command before the next mouse event, providing
 * coordinates 
 */
public abstract class DoOnNextMouseEvent 
		implements NativePreviewHandler {
	
	HandlerRegistration handlerReg;

	public DoOnNextMouseEvent() {
		handlerReg = Event.addNativePreviewHandler(this);
	}
	
	@Override
	public void onPreviewNativeEvent(NativePreviewEvent event) {
		if ((event.getTypeInt() & Event.MOUSEEVENTS) != 0) {
		
			NativeEvent nativeEvent = event.getNativeEvent();
			int xPos = nativeEvent.getClientX();
			int yPos = nativeEvent.getClientY();
			
			handlerReg.removeHandler();
			
			callback(xPos, yPos);
		}
	}
	
	/**
	 * The code to be executed
	 * 
	 * @param xPos new mouse x-position in the browser window's client area 
	 * @param yPos new mouse y-position in the browser window's client area
	 */
	protected abstract void callback(int xPos, int yPos);
}
