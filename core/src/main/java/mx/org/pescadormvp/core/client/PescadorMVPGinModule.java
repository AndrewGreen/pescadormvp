/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client;

import mx.org.pescadormvp.core.client.components.ComponentRegistry;
import mx.org.pescadormvp.core.client.components.ComponentRegistryImpl;
import mx.org.pescadormvp.core.client.data.DataManager;
import mx.org.pescadormvp.core.client.data.DataManagerImpl;
import mx.org.pescadormvp.core.client.data.JsonpDispatchAsync;
import mx.org.pescadormvp.core.client.data.JsonpDispatchAsyncImpl;
import mx.org.pescadormvp.core.client.logging.PescadorMVPLogger;
import mx.org.pescadormvp.core.client.logging.PescadorMVPLoggerImpl;
import mx.org.pescadormvp.core.client.placesandactivities.ActivityManagersFactory;
import mx.org.pescadormvp.core.client.placesandactivities.ActivityMappersFactory;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityManager;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityMapper;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceController;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceHistoryHandler;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceMapper;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceMapperImpl;
import mx.org.pescadormvp.core.client.regionsandcontainers.LayoutHelper;
import mx.org.pescadormvp.core.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.core.client.session.Session;
import mx.org.pescadormvp.core.client.session.SessionImpl;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Specifies DI bindings for framework facilities.
 * 
 * @author Andrew Green
 *
 */
public class PescadorMVPGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		// component registry
		bind(ComponentRegistry.class)
				.to(ComponentRegistryImpl.class).in(Singleton.class);
		
		// event bus
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

		// place control and mapping
		bind(PlaceController.class).to(PescadorMVPPlaceController.class).
				in(Singleton.class);
		bind(PescadorMVPPlaceMapper.class).to(PescadorMVPPlaceMapperImpl.class).
				in(Singleton.class);
		bind(PlaceHistoryHandler.class).to(PescadorMVPPlaceHistoryHandler.class).
				in(Singleton.class);
		
		// factories for activity mappers and activity managers
		install(new GinFactoryModuleBuilder()
				.implement(ActivityMapper.class, PescadorMVPActivityMapper.class)
				.build(ActivityMappersFactory.class));
		
		install(new GinFactoryModuleBuilder()
				.implement(ActivityManager.class, PescadorMVPActivityManager.class)
				.build(ActivityManagersFactory.class));
		
		// layout helper
		bind(LayoutHelper.class).in(Singleton.class);
		
		// session
		bind(Session.class).to(SessionImpl.class).in(Singleton.class);
		
		// data management
		bind(DataManager.class).to(DataManagerImpl.class).in(Singleton.class);

		bind(JsonpDispatchAsync.class).to(JsonpDispatchAsyncImpl.class)
				.in(Singleton.class);
		
		bind(JsonpRequestBuilder.class).in(Singleton.class);
		
		// logging
		bind(PescadorMVPLogger.class).to(PescadorMVPLoggerImpl.class).
				in(Singleton.class);
	}
	
	// Null activity used to deactivate regions when required.
	// Created in a provides method to avoid the method injection in the
	// superclass, which in this case is not needed and would create a
	// circular dependency.
	@Provides
	NullActivity provideNullActivity() {
		return new NullActivity();
	}
}
