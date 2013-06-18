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
package mx.org.pescadormvp.core.client.util;

import com.google.gwt.dom.client.Element;

public class DOMUtils {

	public static int getElementWidth(Element el) {
		return Math.max(el.getClientWidth(), 
				Math.max(el.getScrollWidth(), el.getOffsetWidth()));
	}

	public static int getElementHeight(Element el) {
		return Math.max(el.getClientHeight(), 
				Math.max(el.getScrollHeight(), el.getOffsetHeight()));
	}
}
