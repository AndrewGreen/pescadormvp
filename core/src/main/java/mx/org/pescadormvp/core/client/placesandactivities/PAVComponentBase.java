/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.core.client.session.Session;
import mx.org.pescadormvp.core.client.session.SessionData;

/**
 * Provides a means for linking a place class to activity and view classes 
 * for parts of the UI (regions), using factories to get the concrete
 * implementations for these classes  
 *
 * @param <P> Place class
 */
public abstract class PAVComponentBase<
		// public interface offered as a component
		I extends PAVComponent<I,P>,
		
		// place class we're binding to
		P extends PescadorMVPPlace				
		
		> implements PAVComponent<I,P> {

	private final Map<Class<? extends ForRegionTag>, 
			ActivitiesFactory<?,?> > regionsAndActivitiesFactories =
			new HashMap<Class<? extends ForRegionTag>, 
			ActivitiesFactory<?,?> >();

	private RawPlaceFactory<P> placeFactory;
	
	private Session session;
	
	/**
	 * Use method injection to get basic stuff, to keep subclasses'
	 * constructors simpler.
	 */
	@Inject
	public void setBasicComponents(
			Session session,
			RawPlaceFactory<P> placeFactory) {
		
		this.session = session;
		this.placeFactory = placeFactory;
	}
	
	/**
	 * Override this method if the component has to do something
	 * after all components have been loaded.
	 * 
	 */
	@Override
	public void finalizeSetup() {
		// usually we'll have nothing to do; in any cases, subclasses can
		// override
	}
	
	@Override
	public Set<Class<? extends ForRegionTag>> handlesRegions() {
		return regionsAndActivitiesFactories.keySet();
	}

	protected void addRegionAndActivitiesFactory(
			Class<? extends ForRegionTag> region,
			ActivitiesFactory<?,?> activitiesFactory) {
		
		regionsAndActivitiesFactories.put(region, activitiesFactory);
	}
	
	@Override
	public P getRawPlace() {
		P place = placeFactory.create();
		return place;
	}
	
	@Override
	public <A extends PescadorMVPPlaceActivity<?,P,?>>
			A getActivity(Class<? extends ForRegionTag> region, P place) {

		ActivitiesFactory<?,?> activitiesFactory = 
				regionsAndActivitiesFactories.get(region);
		
		if (activitiesFactory == null)
			return null;

		// GWT reflection doesn't provide for finding implemented interfaces
		@SuppressWarnings("unchecked")
		A activity = ((ActivitiesFactory<P,A>) activitiesFactory)
				.create();
		
		// TODO figure out how to set this via DI
		activity.setPlace(place);

		return activity;
	}
	
	// TODO use generics to avoid cast or unchecked warnings...
	@SuppressWarnings("unchecked")
	@Override
	public <S extends SessionData> S ensureSessionData() {
		S sessionData = (S) session.getSessionData(publicInterface());
		
		if (sessionData == null) {
			sessionData = (S) createSessionData();
			session.setSessionData(publicInterface(), sessionData);
		}
		
		return sessionData;
	}
	
	/**
	 * Override this method if the component requires session data
	 * 
	 * @return Session data for this component
	 */
	protected SessionData createSessionData() {
		return null;
	}
}
