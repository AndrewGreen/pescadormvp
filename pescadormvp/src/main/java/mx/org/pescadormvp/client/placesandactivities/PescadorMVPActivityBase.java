/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.placesandactivities;

import mx.org.pescadormvp.client.components.Component;
import mx.org.pescadormvp.client.components.ComponentSetup;
import mx.org.pescadormvp.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.client.session.Session;
import mx.org.pescadormvp.client.session.StatePointer;
import mx.org.pescadormvp.client.uiresources.ActivateInternalLinkEvent;

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
	private ComponentSetup componentSetup;
	private I componentThisIsPartOf;

	// The place is gotten by subclasses via
	// assisted inject.
	public PescadorMVPActivityBase(
			S statePointer) {

		this.statePointer = statePointer;
	}

	@Inject
	public void setupStuff(
			ComponentSetup componentSetup,
			V view) {
		this.componentSetup = componentSetup;
		this.view = view;

		// For some reason, if we inject this parameter here instead
		// of getting it this way, we end up with extra instances
		// of the component in question.
		this.componentThisIsPartOf = componentSetup
				.getComponent(getComponentClass());
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

	protected ComponentSetup getComponentSetup() {
		return componentSetup;
	}

	protected I getComponentThisIsPartOf() {
		return componentThisIsPartOf;
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
	 * {@link Session#goTo(WebClientPlaceBase)}.
	 * 
	 * @param place
	 *            Where to go
	 */
	protected void goTo(PescadorMVPPlace place) {
		Session session = getComponentSetup().getComponent(Session.class);
		session.goTo(place);
	}
}
