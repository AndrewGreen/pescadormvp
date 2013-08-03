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
package mx.org.pescadormvp.core.shared;

import java.io.Serializable;
import java.util.Comparator;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>A class that holds a localeName (as used in URLs or locale cookies) and
 * a localized (display) name for that locale.</p>
 * 
 * <p>Note: here {@link #hashCode} and {@link #equals} only take into account
 * localeName, and not the display name.</p>
 */

@SuppressWarnings("serial")
public class PescadorMVPLocale implements Serializable, IsSerializable {

	private static StandardComparator standardComparator =
			new StandardComparator();
	
	private String localeName;
	private String localizedDisplayName;
	
	// for serialization
	public PescadorMVPLocale() { }
	
	public PescadorMVPLocale(String localeName, String localizedDisplayName) {
		this.localeName = localeName;
		this.localizedDisplayName = localizedDisplayName;
	}

	public static StandardComparator getStandardComparator() {
		return standardComparator;
	}
	
	public String getLocaleName() {
		return localeName;
	}
	
	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}
	
	public String getLocalizedDisplayName() {
		return localizedDisplayName;
	}
	
	public void setLocalizedDisplayName(String localizedDisplayName) {
		this.localizedDisplayName = localizedDisplayName;
	}

	/**
	 * Does not take into account the localizedDisplayName field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((localeName == null) ? 0 : localeName.hashCode());
		return result;
	}

	/**
	 * Does not take into account the localizedDisplayName field.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PescadorMVPLocale))
			return false;
		PescadorMVPLocale other = (PescadorMVPLocale) obj;
		if (localeName == null) {
			if (other.localeName != null)
				return false;
		} else if (!localeName.equals(other.localeName))
			return false;
		return true;
	}
	
	public static class StandardComparator
			implements Comparator<PescadorMVPLocale> {

		@Override
		public int compare(PescadorMVPLocale o1, PescadorMVPLocale o2) {
			return o1.localeName.compareToIgnoreCase(o2.localeName);
		}
	}
}
