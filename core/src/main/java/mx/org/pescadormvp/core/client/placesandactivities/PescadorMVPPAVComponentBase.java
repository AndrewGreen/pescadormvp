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

import com.google.inject.Provider;

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
public abstract class PescadorMVPPAVComponentBase<
		// public interface offered as a component
		I extends PescadorMVPPAVComponent<I,P>,
		
		// place class we're binding to
		P extends PescadorMVPPlace				
		
		> implements PescadorMVPPAVComponent<I,P> {

	private final Class<I> publicInterface;
	private final String mainToken;
	private final Map<Class<? extends ForRegionTag>, 
			ActivitiesFactory<?,?> > regionsAndActivitiesFactories =
			new HashMap<Class<? extends ForRegionTag>, 
			ActivitiesFactory<?,?> >();

	private final PescadorMVPPlaceProvider<P> placeProvider;
	
	private Session session;
	
	// no @Inject, injection used in the extending class's constructor
	public PescadorMVPPAVComponentBase(
			String mainToken,
			Class<I> publicInterface,
			PescadorMVPPlaceProvider<P> placeProvider,
			Session session) {
		
		this.publicInterface = publicInterface;
		this.mainToken = mainToken;
		this.placeProvider = placeProvider;
		this.session = session;
	}

	@Override
	public String getMainToken() {
		return mainToken;
	}

	public Class<I> publicInterface() {
		return publicInterface;
	}

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
	public P getPlace() {
		P place = placeProvider.get();
		place.setMainToken(mainToken);
		return place;
	}
	
	@Override
	public <PS extends P, 
			A extends PescadorMVPPlaceActivity<?,?,?>>
			A getActivity(Class<? extends ForRegionTag> region, P place) {

		ActivitiesFactory<?,?> activitiesFactory = 
				regionsAndActivitiesFactories.get(region);
		
		if (activitiesFactory == null)
			return null;

		// GWT reflection doesn't provide for finding implemented interfaces
		@SuppressWarnings("unchecked")
		A activity = ((ActivitiesFactory<PS,A>) activitiesFactory)
				.create((PS) place);

		return activity;
	}
	
	public abstract static class PescadorMVPPlaceProvider<P extends PescadorMVPPlace> 
		implements Provider<P> { }
	
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
	
	protected abstract SessionData createSessionData();
}
