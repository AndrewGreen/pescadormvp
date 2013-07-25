/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client.query;


import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import mx.org.pescadormvp.core.client.data.DataManager;
import mx.org.pescadormvp.core.client.placesandactivities.ActivitiesFactory;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponentBase;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceMapper;
import mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body;

/**
 * General setup for the query component. Sets dependency injections, provides
 * a place as the default place for the app. There is some boilerplate here that could
 * eventually be avoided via annotations and code generation.
 *  
 * @author Andrew Green
 */
public class QueryComponentImpl extends PescadorMVPPAVComponentBase< 
		QueryComponent,
		QueryPlace>
		implements
		QueryComponent
	{

	private QueryPlace defaultPlace;

	@Inject
	public QueryComponentImpl(
			QueryPlaceProvider queryPlaceProvider,
			ActivitiesFactory<QueryPlace, QueryActivity> activitiesFactory,
			GetLatLonActionHelper actionHelper,
			DataManager dataManager) {
		
		// send the place provider to the superclass
		super(queryPlaceProvider);

		// Here we establish that this component has an activity for the
		// {@link Body} screen region (in this app, that's the only region).
		addRegionAndActivitiesFactory(Body.class, activitiesFactory);

		// register JSONP action helper for getting lat-lon data
		dataManager.registerActionHelper(actionHelper);
	}
	
	/**
	 * GIN (DI) bindings.
	 *  
	 * @author Andrew Green
	 *
	 */
	public static class QueryGinModule extends AbstractGinModule {

		@Override
		protected void configure() {
			// bind the enclosing class
			bind(QueryComponent.class).
					to(QueryComponentImpl.class).in(Singleton.class);

			// bind place provider
			bind(QueryPlaceProvider.class).in(Singleton.class);
			
			// create activities factory
			install(new GinFactoryModuleBuilder().implement(
					QueryActivity.class, QueryActivityImpl.class)
					.build(
					new TypeLiteral<
					ActivitiesFactory<QueryPlace, QueryActivity>>(){}));
			
			// bind view
			bind(QueryView.class).to(QueryViewImpl.class)
					.in(Singleton.class);
			
			// bind jsonp action helper
			bind(GetLatLonActionHelper.class)
					.to(GetLatLonActionHelperImpl.class)
					.in(Singleton.class);
			
			// bind OSMMap
			bind(OSMMap.class).to(OSMMapImpl.class).in(Singleton.class);
		}
	}

	public static class QueryPlaceProvider
		extends PescadorMVPRawPlaceProvider<QueryPlace> {
		
		@Override
		public QueryPlace get() {
			return new QueryPlaceImpl();
		}
	}

	/**
	 * Provides a raw (not fully setup) place for use as default place.
	 * The place will be fully setup by {@link PescadorMVPPlaceMapper}.
	 */
	@Override
	public PescadorMVPPlace getRawDefaultPlace() {
		if (defaultPlace == null)
			defaultPlace = getPlace();

		return defaultPlace;
	}

	@Override
	public Class<QueryComponent> publicInterface() {
		return QueryComponent.class;
	}
}
