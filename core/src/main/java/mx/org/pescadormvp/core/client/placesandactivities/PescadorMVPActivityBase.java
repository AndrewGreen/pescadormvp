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
/**
 * Here's some stuff
 * 
 * @author Andrew Green
 *
 * @param <V>
 * @param <S>
 * @param <I>
 */
public abstract class PescadorMVPActivityBase<
		V extends IsWidget,
		S extends StatePointer,
		I extends Component<I>>
		extends AbstractActivity
		implements PescadorMVPActivity<V, S, I> {

	private V view;
	private S statePointer;
	private Session session;

	/**
	 * @param statePointer The instance (often a
	 *  {@link PescadorMVPPlace}) for this instance of the activity.
	 */
	protected PescadorMVPActivityBase(
			S statePointer) {

		this.statePointer = statePointer;
	}

	/**
	 * Method used by GIN to inject some stuff we need. 
	 */
	@Inject
	public void setupStuff(Session session, V view) {
		this.view = view;
		this.session = session;
	}

	/**
	 * Gets the class of the component this is a part of. 
	 */
	protected abstract Class<I> getComponentClass();

	/**
	 * Gets the view that this activity will use. 
	 */
	protected V getView() {
		return view;
	}

	/**
	 * Used only by {@link NullActivity NullActivity}, which does not follow the standard
	 * factory injection procedure used by other {@link PescadorMVPActivity PescadorMVPActivities}.
	 * s.
	 */
	protected void setView(V view) {
		this.view = view;
	}

	/**
	 * Get the {@link StatePointer} (often a {@link PescadorMVPPlace}).  
	 */
	protected S getStatePointer() {
		return statePointer;
	}

	/**
	 * Makes it easy for {@link PescadorMVPActivity PescadorMVPActivities} to respond to
	 * {@link ActivateInternalLinkEvent}s from views.
	 */
	@Override
	public void onActivateInternalLink(ActivateInternalLinkEvent event) {
		goTo(event.getPlace());
	}

	/**
	 * Convenience method that simply calls
	 * {@link Session#goTo(PescadorMVPPlace)}.
	 * 
	 * @param place
	 *            Where to go
	 */
	protected void goTo(PescadorMVPPlace place) {
		session.goTo(place);
	}
}
