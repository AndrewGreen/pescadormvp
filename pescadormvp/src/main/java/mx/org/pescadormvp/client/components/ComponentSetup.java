/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.client.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import mx.org.pescadormvp.client.data.DataManager;
import mx.org.pescadormvp.client.logging.PescadorMVPLogger;
import mx.org.pescadormvp.client.placesandactivities.ActivityManagersFactory;
import mx.org.pescadormvp.client.placesandactivities.ActivityMappersFactory;
import mx.org.pescadormvp.client.placesandactivities.DefaultPlaceProvider;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPActivityMapper;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlaceActivity;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPPlaceMapper;
import mx.org.pescadormvp.client.placesandactivities.PescadorMVPViewComponent;
import mx.org.pescadormvp.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.client.regionsandcontainers.HasRegions;
import mx.org.pescadormvp.client.regionsandcontainers.RootHasFixedSetOfRegions;
import mx.org.pescadormvp.client.regionsandcontainers.RootRegionManager;
import mx.org.pescadormvp.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.client.session.Session;
import mx.org.pescadormvp.client.util.Reflect;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Instances of this class configure the app by setting specific place-activity
 * bindings and related DI bindings.
 * 
 * There are circular dependencies between this class and WebClientPlaceMapper,
 * as well as all instances of PlaceActivityBinding. The pattern used is:
 * once those dependencies are received by this class, this class registers
 * itself with them.
 * 
 * TODO: See about other possible injection techniques (?)
 */
public abstract class ComponentSetup implements RootRegionManager {
	
	private Map<String, PescadorMVPViewComponent<?,?>> mainTokenIndex =
			new HashMap<String, PescadorMVPViewComponent<?,?>>();
	
	private RootHasFixedSetOfRegions regionsWidget;
	private Set<Class<? extends ForRegionTag>> regions;
	
	private PlaceController placeController;
	private EventBus eventBus;
	private PlaceHistoryHandler historyHandler;
	private ActivityMappersFactory activityMappersFactory;
	private ActivityManagersFactory activityManagersFactory;
	
	private DefaultPlaceProvider defaultPlaceProvider;
	private NullActivity nullActivity;
	
	private Map<Class<?>, Component<?>> componentIndex = 
			new HashMap<Class<?>, Component<?>>();
	
	private Set<String> scriptURLsLoading = new HashSet<String>();
	private Set<String> scriptURLsFailedToLoad = new HashSet<String>();
	private boolean startCalled;
	private boolean started;
	
	public void addComponents(
			Component<?>... components) {
		for (Component<?> component : components)
			addComponent(component);
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
	}

	@Override
	public RootHasFixedSetOfRegions getRootRegionsWidget() {
		return regionsWidget;
	}

	@Override
	public void setRootRegionsWidget(RootHasFixedSetOfRegions regionsWidget) {
		this.regionsWidget = regionsWidget;
		this.regions = regionsWidget.getRegions(); // a reference, not a copy
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

	protected void setDefaultPlaceProvider(DefaultPlaceProvider defaultPlaceProvider) {
		this.defaultPlaceProvider = defaultPlaceProvider;
	}
	
	public PescadorMVPPlace defaultPlace() {
		
		PescadorMVPPlace defaultPlace = defaultPlaceProvider.getDefaultPlace();
		
		// For some reason, it seems necessary to set this to an empty string
		// rather than the real history token. This allows links to the default
		// place in the UI (generated from this very object) to have no
		// history token at all. If they have their normal history token,
		// then clicking on them adds an extra, unwanted entry in the 
		// browser history.
		defaultPlace.setHistoryToken("");
		
		return defaultPlace;
	}

	/**
	 * Request the injection of scripts before the framework starts. To ensure
	 * this order, call this method before calling {@link #start()}. Scripts
	 * are injected in the top window.
	 * 
	 * @param urls The URLs of scripts to inject.
	 */
	public void injectScripts(String... urls) {
		for (String url : urls) {
			
			final String finalURL = url;
			
			scriptURLsLoading.add(finalURL);
			
			ScriptInjector
					.fromUrl(finalURL)
					.setWindow(ScriptInjector.TOP_WINDOW)
					.setCallback(
					new Callback<Void, Exception>() {

				public void onFailure(Exception reason) {
					scriptURLsLoading.remove(finalURL);
					scriptURLsFailedToLoad.add(finalURL);
					scriptInjectCallback();
				}
		                                                 
				public void onSuccess(Void result) {
					scriptURLsLoading.remove(finalURL);
					scriptInjectCallback();
				}
						
			}).inject();
		}
	}
	
	private void scriptInjectCallback() {
		// We should never have started before all requested scripts have been
		// injected.
		if (started) {
			PescadorMVPLogger logger = tryToGetLogger();
			if (logger != null)
				logger.log(Level.SEVERE, "Framework started before all " +
						"requested scripts injected.");
		}
		
		// If start has been called but the framework isn't started, that means
		// that starting was postponed until all scripts have loaded. So try
		// starting again.
		if (startCalled)
			start();
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

		// if there are still Javascript scripts to be injected, wait.
		if (scriptURLsLoading.size() > 0) {
			startCalled = true;
			
		} else {
			// it is assumed that all constructor and method injection for
			// this class and components will have taken place by the time we get
			// here; so run through all components and call finalizeSetup,
			// which should be called after all components are loaded
			for (Component<?> component : componentIndex.values()) {
				component.finalizeSetup();
			}

			// Check if any requested scripts failed to load
			if (scriptURLsFailedToLoad.size() > 0) {

				// If so, and if a logger is available, log the error
				PescadorMVPLogger logger = tryToGetLogger();
				
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
					defaultPlace().asGWTPlace());
			
			historyHandler.handleCurrentHistory();
			
			started = true;
			
		}
	}
	
	/**
	 * There are times when it's appropriate to log messages from here, however
	 * we can't assume that a logger is necessarily available. Here we try to
	 * get one, and return null if there is none. 
	 */
	private PescadorMVPLogger tryToGetLogger() {
		if (componentIndex.containsKey(PescadorMVPLogger.class))
			return getComponent(PescadorMVPLogger.class);
		else
			return null;	
	}
	
	private void addComponent(Component<?> component) {
		component.setComponentSetup(this);
		componentIndex.put(component.publicInterface(), component);
		
		if (component instanceof PescadorMVPViewComponent<?,?>) {
			PescadorMVPViewComponent<?,?> pavComponent =
					(PescadorMVPViewComponent<?,?>) component;
			
			mainTokenIndex.put(pavComponent.getMainToken(), pavComponent);
			
			for (Class<? extends ForRegionTag> region : 
					pavComponent.handlesRegions()) {
				
				if (!regions.contains(region))
					throw new IllegalArgumentException(
							pavComponent + " handles region " + region +
							" which is not managed by " + this);
			}
		}
	}
	
	public PescadorMVPPlace getPlace(String mainToken) {
		PescadorMVPViewComponent<?,?> pavComponent =
				mainTokenIndex.get(mainToken);
		
		if (pavComponent != null)
			return pavComponent.getPlace();
		else
			return defaultPlace(); // in case we get an incorrect token
	}

	/**
	 * A method for getting a copy of a any place. Convenience method,
	 * delegated to {@link PescadorMVPPlaceMapper}.
	 */
	public <P extends PescadorMVPPlace> P copyPlace(P originalPlace) {
		PescadorMVPPlaceMapper placeMapper = 
				getComponent(PescadorMVPPlaceMapper.class);
		
		PescadorMVPViewComponent<?,P> pavComponent =
				getCastPAVComponent(originalPlace);
		
		P newPlace = pavComponent.getPlace();
		
		return placeMapper.copyPlaceInto(originalPlace, newPlace);
	}

	private <P extends PescadorMVPPlace> PescadorMVPViewComponent<?, P>
			getCastPAVComponent(P place) {

		PescadorMVPViewComponent<?, ?> pavComponent =
				mainTokenIndex.get(place.getMainToken());

		if (!Reflect.isOfSameClassOrSubclass(pavComponent.getPlaceClass(),
				place))
			throw new RuntimeException();

		@SuppressWarnings("unchecked")
		PescadorMVPViewComponent<?, P> castPAVComponent =
				(PescadorMVPViewComponent<?, P>) pavComponent;

		return castPAVComponent;
	}
	
	public <P extends PescadorMVPPlace,
			PS extends P> PescadorMVPPlaceActivity<?,?,?>
			getActivityForRegionAndPlace(
			Class<? extends ForRegionTag> region,
			P place) {
		
		PescadorMVPViewComponent<?,P> pavComponent =
				getCastPAVComponent(place);
		
		PescadorMVPPlaceActivity<?,?,?> activity =
				pavComponent.getActivity(region, place);
		
		if (activity != null)
			return activity;
		else
			return nullActivity;
	}
	
	public <I extends Component<I>> I getComponent(Class<I> publicInterface) {
		Component<?> uncastComponent =
				componentIndex.get(publicInterface);

		// GWT reflection doesn't provide for finding implemented interfaces
		@SuppressWarnings("unchecked")
		I component = (I) uncastComponent;

		return component;
	}
	
	public Collection<Component<?>> getComponents() {
		return componentIndex.values();
	}
}
