/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.session;

import java.io.Serializable;

/**
 * Data related to the current session. If a component needs to store session
 * data, it should store it in a class
 * that implements an extension of this interface. That will give it access
 * to convenience methods for session data provided by the {@link Session}
 * component.
 */
public interface SessionData extends Serializable {

}
