/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.gwt.logging.client.TextLogFormatter;

public class PescadorMVPLoggerImpl implements PescadorMVPLogger {

	private Logger logger;
	
	private Logger ensureLogger() {
		// customize logger 
		
		if (logger == null) {
			logger = Logger.getLogger("");
			
			Handler[] handlers = logger.getHandlers();
			
			for (Handler h : handlers) {
				h.setFormatter(new TextLogFormatter(false) {
					
					@Override
				    public String format(LogRecord event) {
						return event.getLevel().getName() + "\t" + event.getMessage();
					}
				});
			}
		}
		
		return logger;
	}
	
	@Override
	public Class<PescadorMVPLogger> publicInterface() {
		return PescadorMVPLogger.class;
	}

	@Override
	public void finalizeSetup() {

	}
	
	@Override
	public void log(Level level, String msg) {
		ensureLogger().log(level, msg);
	}
}

