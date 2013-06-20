/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import mx.org.pescadormvp.core.client.data.DataManager;
import mx.org.pescadormvp.core.client.logging.PescadorMVPLogger;
import mx.org.pescadormvp.core.client.placesandactivities.ActivityManagersFactory;
import mx.org.pescadormvp.core.client.placesandactivities.ActivityMappersFactory;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityMapper;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceMapper;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.core.client.regionsandcontainers.HasRegions;
import mx.org.pescadormvp.core.client.regionsandcontainers.RootHasFixedSetOfRegions;
import mx.org.pescadormvp.core.client.regionsandcontainers.RootRegionManager;
import mx.org.pescadormvp.core.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.core.client.session.Session;
import mx.org.pescadormvp.core.client.util.Reflect;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Instances of this class configure the app, including specific place-activity
 * bindings, DI bindings, and the root layout widget for viewport.
 */
public abstract class ComponentSetup implements RootRegionManager {
	
	private ComponentRegistry componentRegistry;
	
	private RootHasFixedSetOfRegions regionsWidget;
	private Set<Class<? extends ForRegionTag>> regions;
	
	private PlaceController placeController;
	private EventBus eventBus;
	private PlaceHistoryHandler historyHandler;
	private ActivityMappersFactory activityMappersFactory;
	private ActivityManagersFactory activityManagersFactory;
	
	private PescadorMVPPlaceMapper placeMapper;
	private NullActivity nullActivity;
	
	private List<String> scriptURLsLoading = new ArrayList<String>();
	private List<String> scriptURLsFailedToLoad = new ArrayList<String>();
	private int scriptNowLoading = -1;
	private boolean startPending;
	private boolean started;
	
	public ComponentSetup(ComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}
	
	public void addComponents(
			Component<?>... components) {
		componentRegistry.addComponents(components);
	}
	
	/**
	 * Use method injection to get some basic stuff, to keep 
	 * subclasses' constructors simpler.
	 */
	@Inject
	public void setBasicComponents(
			PlaceController placeController,
			EventBus eventBus,
			PlaceHistoryHandler historyHandler,
			ActivityMappersFactory activityMappersFactory,
			ActivityManagersFactory activityManagersFactory,
			NullActivity nullActivity,
			
			DataManager dataManager,
			PescadorMVPPlaceMapper placeMapper,
			Session session,
			PescadorMVPLogger logger) {
		
		this.placeController = placeController;
		this.eventBus = eventBus;
		this.historyHandler = historyHandler;
		this.nullActivity = nullActivity;
		this.activityMappersFactory = activityMappersFactory;
		this.activityManagersFactory = activityManagersFactory;
		
		addComponents(new Component<?>[] {
				dataManager,
				placeMapper,
				session,
				logger});
		
		this.placeMapper = placeMapper;
	}

	@Override
	public RootHasFixedSetOfRegions getRootRegionsWidget() {
		return regionsWidget;
	}

	@Override
	public void setRootRegionsWidget(RootHasFixedSetOfRegions regionsWidget) {
		this.regionsWidget = regionsWidget;
		
		// a reference, not a copy
		regions = regionsWidget.getRegions();
		
		// Tell the component registry what the regions are, so it can check 
		// that components handle regions that are actually available
		componentRegistry.setRegions(regions);
	}

	// not used so far; TODO check
	@Override
	public HasRegions getRegionsWidget() {
		return regionsWidget;
	}

	// not used so far; TODO check
	@Override
	public void setRegionsWidget(HasRegions regionsWidget) {
		if (!(regionsWidget instanceof RootHasFixedSetOfRegions))
			throw new IllegalArgumentException("RootHasRegions widget required");
		
		setRootRegionsWidget((RootHasFixedSetOfRegions) regionsWidget);
	}

	@Override
	public Set<Class<? extends ForRegionTag>> getRegions() {
		return regions;
	}

	/**
	 * Request the injection of scripts before the framework starts. To ensure
	 * this order, call this method before calling {@link #start()}. Scripts
	 * are injected in the top window.
	 * 
	 * @param urls The URLs of scripts to inject.
	 */
	public void injectScripts(String... urls) {
		for (String url : urls)
			scriptURLsLoading.add(url);
		
		loadNextScript();
	}

	private void loadNextScript() {
		// Only log if a logger is available, of course--should not assume it is
		PescadorMVPLogger logger = 
				componentRegistry.getComponent(PescadorMVPLogger.class);
		
		scriptNowLoading++;
		if (allScriptsLoaded()) {
			if (startPending)
				start();
		} else {
			if (started) {

				if (logger != null)
					logger.log(Level.SEVERE, "Framework started before all " +
							"requested scripts injected.");
			}
			
			String urlToLoad = scriptURLsLoading.get(scriptNowLoading);
			launchScriptInjector(urlToLoad);
		}
	}
	
	private boolean allScriptsLoaded() {
		return (scriptNowLoading >= scriptURLsLoading.size()) || 
				scriptNowLoading == -1;
	}
	
	private void launchScriptInjector(final String url) {

		// Only log if a logger is available, of course--should not assume it is
		final PescadorMVPLogger logger = 
				componentRegistry.getComponent(PescadorMVPLogger.class);

		
		ScriptInjector
				.fromUrl(url)
				.setWindow(ScriptInjector.TOP_WINDOW)
				.setCallback(
				new Callback<Void, Exception>() {

			public void onFailure(Exception reason) {
				scriptURLsFailedToLoad.add(url);

				if (logger != null)
					logger.log(Level.FINEST, "Failed to load " + url);
				
				loadNextScript();
			}

			public void onSuccess(Void result) {

				loadNextScript();
			}

		}).inject();
	}
	
	/**
	 * <p>Start up the framework: attach the layout widget to the viewport,
	 * create activity managers and attach them to the region they're
	 * concerned with, start history handling, and go to the default place
	 * (if no place is specified in the fragment identifier in the URL).</p>
	 * 
	 * <p>Note that if there are scripts remaining to be injected, actual startup
	 * will be silently delayed until the scripts become available.</p>
	 */
	public void start() {

		PescadorMVPLogger logger = 
				componentRegistry.getComponent(PescadorMVPLogger.class);
		
		// if there are still Javascript scripts to be injected, wait.
		if (!allScriptsLoaded()) {

			startPending = true;

			if (logger != null)
				logger.log(Level.FINEST, "Not actually starting; scripts still loading: " + scriptURLsLoading.get(scriptNowLoading));
			
		} else {

			if (logger != null)
				logger.log(Level.FINEST, "Now finally loading");

			// it is assumed that all constructor and method injection for
			// this class and components will have taken place by the time we get
			// here; so run through all components and call finalizeSetup,
			// which should be called after all components are loaded
			componentRegistry.callFinalizeSetup();

			// Check if any requested scripts failed to load
			if (scriptURLsFailedToLoad.size() > 0) {

				// If so, and if a logger is available, log the error
				if (logger != null) {
					for (String failedSriptURL : scriptURLsFailedToLoad)
						logger.log(Level.WARNING, "Couldn't inject script " + failedSriptURL);
				}
			}
			
			// create activity mappers, and 
			// create and set display widgets for activity managers
			for (Class<? extends ForRegionTag> region : regions) {
				PescadorMVPActivityMapper mpr =
						activityMappersFactory.create(region);

				ActivityManager mgr = 
						activityManagersFactory.create(mpr);
						
				mgr.setDisplay(regionsWidget.getContainer(region));
			}
			
			regionsWidget.attach();
			
			historyHandler.register(
					placeController,
					eventBus,
					placeMapper.defaultPlace().asGWTPlace());
			
			historyHandler.handleCurrentHistory();
			
			started = true;
		}
	}
	
	private <P extends PescadorMVPPlace> PescadorMVPPAVComponent<?, P>
			getCastPAVComponent(P place) {

		PescadorMVPPAVComponent<?, ?> pavComponent =
				componentRegistry.getPAVComponent(place.getMainToken());
				
		if (!Reflect.isOfSameClassOrSubclass(pavComponent.getPlaceClass(),
				place))
			throw new RuntimeException();

		@SuppressWarnings("unchecked")
		PescadorMVPPAVComponent<?, P> castPAVComponent =
				(PescadorMVPPAVComponent<?, P>) pavComponent;

		return castPAVComponent;
	}
	
	public <P extends PescadorMVPPlace,
			PS extends P> PescadorMVPPlaceActivity<?,?,?>
			getActivityForRegionAndPlace(
			Class<? extends ForRegionTag> region,
			P place) {
		
		PescadorMVPPAVComponent<?,P> pavComponent =
				getCastPAVComponent(place);
		
		PescadorMVPPlaceActivity<?,?,?> activity =
				pavComponent.getActivity(region, place);
		
		if (activity != null)
			return activity;
		else
			return nullActivity;
	}
}
