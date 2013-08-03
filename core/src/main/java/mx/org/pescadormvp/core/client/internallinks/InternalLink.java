/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.internallinks;

import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.placesandactivities.PlaceRequestEvent.HasPlaceRequestHandlers;

/**
 * An application-internal link, with data provided by a
 * {@link PescadorMVPPlace}.
 */
public interface InternalLink extends HasPlaceRequestHandlers {

	/**
	 * Set the {@link PescadorMVPPlace} the link points to, including a native
	 * hyperlink's URL and presentation text, if appropriate.
	 */
	public abstract void setPlace(PescadorMVPPlace place);

	/**
	 * Get the place the link points to.
	 */
	public abstract PescadorMVPPlace getPlace();

	/**
	 * Get the alternate presentation text (to use instead of that provided by the specified
	 * {@link PescadorMVPPlace} object).
	 */
	public String getAlternatePresentationText();

	/**
	 * Set the alternate presentation text (to use instead of that provided by the specified
	 * {@link PescadorMVPPlace} object).
	 */
	public void setAlternatePresentationText(String alternatePresentationText);
}
