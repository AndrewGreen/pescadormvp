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
import mx.org.pescadormvp.core.client.components.ComponentRegistry;
import mx.org.pescadormvp.core.client.components.ComponentSetup;
import mx.org.pescadormvp.core.client.placesandactivities.RawDefaultPlaceProvider;
import mx.org.pescadormvp.examples.jsonp.client.layout.Layout;
import mx.org.pescadormvp.examples.jsonp.client.layout.LayoutImpl;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent;
import mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
public class ActiveComponentSetup extends ComponentSetup {

	/**
	 * Activate all necessary {@link GinModule}s for DI in the components to use,
	 * as well as for basic infrastructure.
	 */
	@GinModules({
		// General bindings for this app that are not part of the larger framework
		ActiveSetupGinModule.class,
		
		// Bindings for query component, which provides a place, an activity
		// and a view. In a larger app, there would be many of these.
		QueryGinModule.class,
		
		// Framework
		PescadorMVPGinModule.class,
		StandardDispatchModule.class
	})
	public interface ActiveSetupGinjector extends PescadorMVPGinjector {}

	/**
	 *  <p>Sets specific bindings;
	 *  Backreferences (from components back to {@link ActiveComponentSetup})
	 *  are set as components are received.</p>
	 *  
	 *  <p>Also set the root region provider.</p>
	 *  
	 *  <p>Note: components that rely on automatic generation of internationalized
	 *  {@link Messages} by Maven must also set up the appropriate configuration in the
	 *  pom.xml.</p>
	 */
	@Inject
	public ActiveComponentSetup(
			// Component registry
			ComponentRegistry componentRegistry,
			
			// The global window layout widget
			Layout layout,
			
			// Query component, which provides a place, an activity
			// and a view. In a larger app, there would be many of these.
			QueryComponent queryComponent
			
			) {
		
		super(componentRegistry);

		// We do this before registering components, so that Place-Activity-View
		// components can be checked against the viewport regions available here
		setRootRegionsWidget(layout);
		
		// Register components
		// TODO once multibindings and mapbindings are in Gin, look into using that
		addComponents(queryComponent);
		
		// Inject external Javascript libraries for OpenLayers.
		// The framework will ensure that these are loaded and available
		// before the framework actually starts up.
		injectScripts(
				"JSONPExample/js/gwt-openlayers/util.js",
				"http://www.openlayers.org/api/OpenLayers.js",
				"http://www.openstreetmap.org/openlayers/OpenStreetMap.js");
	}
	
	/**
	 * Local GIN module, with general bindings for this app 
	 * that are not part of the larger framework.
	 * 
	 */
	public static class ActiveSetupGinModule extends AbstractGinModule {
		@Override
		protected void configure() {
			
			// component setup
			bind(ComponentSetup.class).to(ActiveComponentSetup.class)
				.in(Singleton.class);

			// window layout
			bind(Layout.class).to(LayoutImpl.class).in(Singleton.class);
			
			// default place provider
			bind(RawDefaultPlaceProvider.class)
					.to(QueryComponent.class).in(Singleton.class);
			
			// If configuration values are to be injected into classes, they
			// may be specified here.
		}
	}
}
