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
import mx.org.pescadormvp.core.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.core.client.session.Session;
import mx.org.pescadormvp.core.client.session.StatePointer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

/**
 * <p>
 * Abstract base class for {@link PescadorMVPActivity PescadorMVPActivities}.
 * </p>
 * <p>
 * Note that this abstract class is extended by activities not associated with a
 * place, as well as those associated with one. Activities associated with a
 * place (as part of a {@link PAVComponent}) should extend the subclass,
 * {@link PescadorMVPPlaceActivityBase}.
 * </p>
 * 
 * @param <V>
 *            The public interface of view associated with this activity.
 * @param <S>
 *            The {@link StatePointer} class (often a subinterface of
 *            {@link PescadorMVPPlace}) associated with the activation of
 *            this activity.
 * @param <I>
 *            The {@link Component} that this activity is a part of.
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

	// TODO find a way of doing this via DI without making it visible in
	// the constructors of concrete subclasses.
	@Override
	public void setStatePointer(S statePointer) { 
		this.statePointer = statePointer;
	}
	
	/**
	 * Internal Pescador MVP use. Method used by GIN to inject some stuff we need. 
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
	 * Internal Pescador MVP use. Used only by {@link NullActivity NullActivity}
	 * , which does not follow the standard factory injection procedure used by
	 * other {@link PescadorMVPActivity PescadorMVPActivities}.
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
	 * Handles {@link PlaceRequestEvent}s. Placing this method in {@link PescadorMVPActivityBase}
	 * makes it easy for {@link PescadorMVPActivity PescadorMVPActivities} to
	 * respond to {@link PlaceRequestEvent}s from views.
	 */
	@Override
	public void onPlaceRequest(PlaceRequestEvent event) {
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
