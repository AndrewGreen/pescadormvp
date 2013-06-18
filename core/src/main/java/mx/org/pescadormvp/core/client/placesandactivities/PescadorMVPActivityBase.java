/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.internallinks.ActivateInternalLinkEvent;
import mx.org.pescadormvp.core.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.core.client.session.Session;
import mx.org.pescadormvp.core.client.session.StatePointer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

public abstract class PescadorMVPActivityBase<
		V extends IsWidget,
		S extends StatePointer,
		I extends Component<I>>
		extends AbstractActivity
		implements PescadorMVPActivity<V, S, I> {

	private V view;
	private S statePointer;
	private Session session;

	// The state pointer (often a place) is gotten by subclasses via
	// assisted inject.
	public PescadorMVPActivityBase(
			S statePointer) {

		this.statePointer = statePointer;
	}

	@Inject
	public void setupStuff(Session session, V view) {
		this.view = view;
		this.session = session;
	}

	protected abstract Class<I> getComponentClass();

	protected V getView() {
		return view;
	}

	/**
	 * Used only by {@link NullActivity}, which does not follow the standard
	 * factory injection procedure used by other {@link PescadorMVPPlaceActivity}
	 * s.
	 */
	protected void setView(V view) {
		this.view = view;
	}

	protected S getStatePointer() {
		return statePointer;
	}

	/**
	 * Makes it easy for {@link PescadorMVPPlaceActivityBase}s to respond to
	 * {@link ActivateInternalLinkEvent}s from views.
	 */
	@Override
	public void onActivateInternalLink(ActivateInternalLinkEvent event) {
		goTo(event.getPlace());
	}

	/**
	 * Convenience method that simply calls
	 * {@link Session#goTo(PescadorMVPPlace)}
	 * 
	 * @param place
	 *            Where to go
	 */
	protected void goTo(PescadorMVPPlace place) {
		session.goTo(place);
	}
}
