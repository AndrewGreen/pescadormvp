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
import mx.org.pescadormvp.core.client.placesandactivities.PAVComponentBase;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceMapper;
import mx.org.pescadormvp.core.client.placesandactivities.RawPlaceFactory;
import mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body;

/**
 * General setup for the query component. Sets dependency injections, provides
 * a place as the default place for the app. There is some boilerplate here that could
 * eventually be avoided via annotations and code generation.
 *  
 * @author Andrew Green
 */
public class QueryComponentImpl extends PAVComponentBase< 
		QueryComponent,
		QueryPlace>
		implements
		QueryComponent
	{

	private QueryPlace defaultPlace;

	@Inject
	public QueryComponentImpl(
			ActivitiesFactory<QueryPlace, QueryActivity> activitiesFactory,
			GetLatLonActionHelper actionHelper,
			DataManager dataManager) {
		
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
			// Bind the component interface to its implementation
			// (which is the enclosing class).
			// This is something all components will do.
			bind(QueryComponent.class).
					to(QueryComponentImpl.class).in(Singleton.class);

			// Create the place factory.
			// All place-activity-view components will do this.
			install(new GinFactoryModuleBuilder().implement(
					QueryPlace.class, QueryPlaceImpl.class)
					.build(
					new TypeLiteral<
					RawPlaceFactory<QueryPlace>>(){}));

			// The next two bindings are for the activity and the view.
			// Place-activity-view components will have bindings like these
			// for each activity and view they define.
			// (This component has only one activity and one view.)
			
			// Create the activities factory.
			install(new GinFactoryModuleBuilder().implement(
					QueryActivity.class, QueryActivityImpl.class)
					.build(
					new TypeLiteral<
					ActivitiesFactory<QueryPlace, QueryActivity>>(){}));
			
			// Bind the view.
			bind(QueryView.class).to(QueryViewImpl.class)
					.in(Singleton.class);
			
			// The remaining bindings are specific to this component.
			// Other place-activity-view components may or may not have
			// additional bindings like these, depending on what they do.
			
			// Bind JSONP action helper.
			// Any component that sets up JSONP requests will do something
			// like this.
			bind(GetLatLonActionHelper.class)
					.to(GetLatLonActionHelperImpl.class)
					.in(Singleton.class);
			
			// Bind OSMMap widget.
			bind(OSMMap.class).to(OSMMapImpl.class).in(Singleton.class);
		}
	}

	/**
	 * Provides a raw (not fully setup) place for use as default place.
	 * The place will be fully setup by {@link PescadorMVPPlaceMapper}.
	 */
	@Override
	public PescadorMVPPlace getRawDefaultPlace() {
		if (defaultPlace == null)
			defaultPlace = getRawPlace();

		return defaultPlace;
	}

	@Override
	public Class<QueryComponent> publicInterface() {
		return QueryComponent.class;
	}
}
