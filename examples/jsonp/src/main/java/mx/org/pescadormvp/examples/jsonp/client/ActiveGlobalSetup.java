/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
/*
 * Some parts of this file:
 * 
 * Copyright 2011 Instituto de Investigaciones Dr. José María Luis Mora
 * See COPYING.txt and LICENSE.txt for redistribution conditions.
 *
 */
package mx.org.pescadormvp.examples.jsonp.client;

import net.customware.gwt.dispatch.client.gin.StandardDispatchModule;
import mx.org.pescadormvp.core.client.PescadorMVPGinModule;
import mx.org.pescadormvp.core.client.PescadorMVPGinjector;
import mx.org.pescadormvp.core.client.components.GlobalSetup;
import mx.org.pescadormvp.core.client.placesandactivities.RawDefaultPlaceProvider;
import mx.org.pescadormvp.core.client.regionsandcontainers.RootHasFixedSetOfRegions;
import mx.org.pescadormvp.examples.jsonp.client.layout.Layout;
import mx.org.pescadormvp.examples.jsonp.client.layout.LayoutImpl;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * <p>This class, together with its inner classes and interfaces, provides a full
 * configuration of the modular framework. It specifies which components are
 * active, activates {@link GinModule}s that specify dependency injections,
 * and specifies a provider for the root widget for the viewport.</p> 
 * 
 * <p>Perhaps at a later date this boilerplate could be reduced using annotations
 * and code generation.<p>
 * 
 * @author Andrew Green
 *
 */
public class ActiveGlobalSetup extends GlobalSetup {

	/**
	 * This method calls the superclass's static startup method, providing
	 * the {@link PescadorMVPGinjector} to use, and
	 * in this case, requesting the injection of some JS, too.
	 */
	public static void startUp() {
		
		PescadorMVPGinjectorHolder ginjectorHolder = 
				new PescadorMVPGinjectorHolder() {

			@Override
			public PescadorMVPGinjector getPescadorMVPGinjector() {
				return GWT.create(ActiveGlobalSetupGinjector.class);
			}
		};
		
		// Inject external Javascript libraries for OpenLayers.
		// The framework will ensure that these are loaded and available
		// before the framework actually starts up.
		// Also send an object to display a loading timer while scripts load.
		GlobalSetup.loadJSthenStartUp(
				ginjectorHolder,
				new InitialLoadingTimer(),
				true,
				"JSONPExample/js/gwt-openlayers/util.js",
				"http://www.openlayers.org/api/OpenLayers.js",
				"http://www.openstreetmap.org/openlayers/OpenStreetMap.js");
	}
	
	/**
	 * Activate all necessary {@link GinModule}s for DI in the components to use,
	 * as well as for basic infrastructure.
	 */
	@GinModules({
		// Global bindings for this app that are not part of any component
		ActiveGlobalSetupGinModule.class,
		
		// Bindings for query component, which provides a place, an activity
		// and a view. In a larger app, there would be many of these.
		QueryGinModule.class,
		
		// Modules required by the framework
		PescadorMVPGinModule.class,
		StandardDispatchModule.class
	})
	public interface ActiveGlobalSetupGinjector extends PescadorMVPGinjector {}

	/**
	 *  <p>Note: components that rely on automatic generation of internationalized
	 *  {@link Messages} by Maven must also set up the appropriate configuration in the
	 *  pom.xml.</p>
	 */
	@Inject
	public ActiveGlobalSetup(
			
			// Query component, which provides a place, an activity
			// and a view. In a larger app, there would be many of these.
			QueryComponent queryComponent
			
			) {

		// Register components
		addComponents(queryComponent);
	}
	
	/**
	 * Local GIN module, with general bindings for this app 
	 * that are not part of the larger framework.
	 * 
	 */
	public static class ActiveGlobalSetupGinModule extends AbstractGinModule {
		@Override
		protected void configure() {
			
			// global setup
			bind(GlobalSetup.class).to(ActiveGlobalSetup.class)
					.in(Singleton.class);

			// global layout widget
			bind(Layout.class).to(LayoutImpl.class).in(Singleton.class);
			
			// tell the framework that this is our global layout widget 
			// Here, it's important that we bind the Layout interface,
			// not the implementing class--otherwise, we'll get two instances
			// of the implementing class.
			bind(RootHasFixedSetOfRegions.class).to(Layout.class)
					.in(Singleton.class);
			
			// tell the framework that this is our default place provider
			// Here, it's important that we bind the QueryComponent interface,
			// for the same reason.
			bind(RawDefaultPlaceProvider.class)
					.to(QueryComponent.class).in(Singleton.class);
		}

		// If you need to set application-wide configuration values, you can
		// inject them the Guice way here. 
		
		// As an example, here we set the maximum cache size for the data manager.
		// (This particular configuration is not usually necessary--the 
		// data manager's default maximum cache size is probably fine for most
		// cases.)
		
		// Have a look at DataMangerImpl to see how to set up config
		// value injection like this. Note that in the global
		// PescadorMVPGinModule, static injection is requested for DataMangerImpl.
		// This is necessary for the value to be injected in the static method
		// that receives it.
		@Provides
		@Named("maxDataManagerCacheSize")
		Integer providesMaxDataManagerCacheSize() {
			return 50;
		}
	}
}
