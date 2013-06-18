/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.logging;

import java.util.logging.Level;

import mx.org.pescadormvp.core.client.components.Component;

public interface PescadorMVPLogger extends Component<PescadorMVPLogger> {

	public void log (Level level, String text);
}
