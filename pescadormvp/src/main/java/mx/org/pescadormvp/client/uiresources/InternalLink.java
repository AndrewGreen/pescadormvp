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
import mx.org.pescadormvp.client.uiresources.ActivateInternalLinkEvent.HasActivateInternalLinkHandlers;

public interface InternalLink extends HasActivateInternalLinkHandlers {

	public abstract void setPlace(PescadorMVPPlace place);

	public abstract PescadorMVPPlace getPlace();

	public String getAlternatePresentationText();

	public void setAlternatePresentationText(String alternatePresentationText);
}
