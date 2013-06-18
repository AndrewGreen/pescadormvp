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

// TODO perhaps move to a real reflection framework like gwt-ent?
public class Reflect {
	
	/**
	 * Partial replacement for the isInstance method, unavailable in GWT
	 * 
	 * @param clazz
	 *            The class to check against
	 * @param o
	 *            The object we're checking
	 * @return true of the object is of the class {@code clazz} or a subclass
	 *         thereof, false otherwise
	 */
	public static boolean isOfSameClassOrSubclass(Class<?> clazz, Object o) {
		Class<?> oClass = o.getClass();
		do {
			if (oClass.getName().compareTo(clazz.getName()) == 0)
				return true;
			
			oClass = oClass.getSuperclass();
		} while (oClass != null);
		
		return false;
	}
	
}
