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
 * 
 * Some of the code in this file was inspired by code
 * included in GWT, copyright Google, Inc. 2009, distributed under the terms of the
 * Apache License, Version 2.0. Said license is available here:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package mx.org.pescadormvp.client.util;

import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.AbstractSelectionModel;

// TODO check if this class is really necessary, given that SingleSelectionModel
// accepts a key provider. If it is, document why.
public class KeyBasedSingleSelectionModel<T> extends AbstractSelectionModel<T> {

	private Object selectedKey;
	private boolean newSelectedPending;
	private boolean newSelected;
	private Object newSelectedKey = null;

	// no inject here, expect to create a provider for this
	public KeyBasedSingleSelectionModel(ProvidesKey<T> keyProvider) {
		super(keyProvider);
	}

	public boolean isSelectedByKey(Object otherKey) {
		if ((selectedKey == null) || (otherKey== null))
			return false;
		
		return selectedKey.equals(otherKey);
	}
	
	@Override
	public boolean isSelected(T object) {
		if (object == null)
			return false;
		
		return isSelectedByKey(getKey(object));
	}

	public void setSelectedByKey(Object newKey, boolean selected) {
		if (!selected) {
			Object oldKey = newSelectedPending ? newSelectedKey
					: selectedKey;
			if (!equalsOrBothNull(oldKey, newKey)) {
				return;
			}
		}
		newSelectedKey = newKey;
		newSelected = selected;
		newSelectedPending = true;
		scheduleSelectionChangeEvent();
	}
	
	@Override
	public void setSelected(T object, boolean selected) {
		setSelectedByKey(getKey(object), selected);
	}

	@Override
	protected void fireSelectionChangeEvent() {
		if (isEventScheduled()) {
			setEventCancelled(true);
		}
		resolveChanges();
	}

	private boolean equalsOrBothNull(Object a, Object b) {
		return (a == null) ? (b == null) : a.equals(b);
	}
	
	private void resolveChanges() {
		if (!newSelectedPending) {
			return;
		}

		boolean sameKey = equalsOrBothNull(selectedKey, newSelectedKey);
		boolean changed = false;
		if (newSelected) {
			changed = !sameKey;
			selectedKey = newSelectedKey;
		} else if (sameKey) {
			changed = true;
			newSelectedKey = null;
		}

		newSelectedPending = false;

		// Fire a selection change event.
		if (changed) {
			SelectionChangeEvent.fire(this);
		}
	}
}
