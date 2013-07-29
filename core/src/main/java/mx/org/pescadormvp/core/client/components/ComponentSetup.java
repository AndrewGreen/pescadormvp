/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import mx.org.pescadormvp.core.client.PescadorMVPGinjector;
import mx.org.pescadormvp.core.client.data.DataManager;
import mx.org.pescadormvp.core.client.logging.PescadorMVPLogger;
import mx.org.pescadormvp.core.client.placesandactivities.ActivityManagersFactory;
import mx.org.pescadormvp.core.client.placesandactivities.ActivityMappersFactory;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityMapper;
import mx.org.pescadormvp.core.client.placesandactivities.PAVComponent;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity;
import mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceMapper;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.core.client.regionsandcontainers.HasRegions;
import mx.org.pescadormvp.core.client.regionsandcontainers.RootHasFixedSetOfRegions;
import mx.org.pescadormvp.core.client.regionsandcontainers.RootRegionManager;
import mx.org.pescadormvp.core.client.regionsandcontainers.NullPanelTools.NullActivity;
import mx.org.pescadormvp.core.client.session.Session;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Instances of this class configure the app, including specific place-activity
 * bindings, DI bindings, and the root layout widget for viewport.
 */
public abstract class ComponentSetup implements RootRegionManager {

	// static stuff used for injecting scripts before startup
	private static String[] scriptsToLoad;
	private static Set<String> scriptsFailedToLoad = new HashSet<String>();
	private static Set<String> scriptsLoaded = new HashSet<String>();
	private static boolean loadScriptsInOrder;
	private static int scriptNowLoading = -1;
	private static LoadingPleaseWait loadingPleaseWait;

	private static PescadorMVPGinjectorHolder pendingGinjectorHolder;
	private static List<PendingLog> pendingLogs =
			new ArrayList<ComponentSetup.PendingLog>();

	private ComponentRegistry componentRegistry;
	private List<Component<?>[]> componentsToAdd = new ArrayList<Component<?>[]>();

	private RootHasFixedSetOfRegions regionsWidget;
	private Set<Class<? extends ForRegionTag>> regions;

	private PlaceController placeController;
	private EventBus eventBus;
	private PlaceHistoryHandler historyHandler;
	private ActivityMappersFactory activityMappersFactory;
	private ActivityManagersFactory activityManagersFactory;

	private PescadorMVPPlaceMapper placeMapper;
	private NullActivity nullActivity;

	/**
	 * A mini interface that allows us to pass around a specific
	 * {@link PescadorMVPGinjector} while delaying its actual instantiation.
	 * Necessary because {@link GWT#create(Class) GWT.create()} only takes class
	 * literals.
	 */
	public interface PescadorMVPGinjectorHolder {
		public PescadorMVPGinjector getPescadorMVPGinjector();
	}

	/**
	 * * This method is static so we can use it before DI boots up. That way the
	 * framework to take care of booting up DI.
	 * 
	 * @param ginjectorHolder
	 *            A holder for the {@link PescadorMVPGinjector} to use to boot
	 *            up DI.
	 */
	public static void startUp(PescadorMVPGinjectorHolder ginjectorHolder) {
		// The Ginjector provides the active ComponentSetup instance, which
		// we use to start the app. This will initialize the UI
		// and go to the default place.
		ginjectorHolder.getPescadorMVPGinjector().getComponetSetup().start();
	}

	/**
	 * <p>
	 * Request the loading of scripts before the framework starts. Scripts are
	 * injected in the top window. Then the framework is started.
	 * </p>
	 * <p>
	 * This method is static so we can use it before DI boots up. That way we
	 * don't have to worry about DI bringing in Java classes that rely on
	 * external JS before it's loaded. Also allows the framework to take care of
	 * booting up DI.
	 * </p>
	 * 
	 * @param ginjectorHolder
	 *            A holder for the {@link PescadorMVPGinjector} to use to boot
	 *            up DI.
	 * @param loadingPleaseWait
	 *            An object that does something when scriptloading starts (like
	 *            show a "please wait" message) and when it finishes (like
	 *            remove the message)
	 * @param loadScriptsInOrder
	 *            If more than one script is requested, make sure they are
	 *            loaded sequentially. (Some libraries need this.)
	 * @param scriptsToLoad
	 *            The URLs of scripts to load.
	 */
	public static void loadJSthenStartUp(
			PescadorMVPGinjectorHolder ginjectorHolder,
			LoadingPleaseWait loadingPleaseWait,
			boolean loadScriptsInOrder,
			String... scriptsToLoad) {

		ComponentSetup.pendingGinjectorHolder = ginjectorHolder;
		ComponentSetup.loadingPleaseWait = loadingPleaseWait;
		ComponentSetup.loadScriptsInOrder = loadScriptsInOrder;
		ComponentSetup.scriptsToLoad = scriptsToLoad;
		
		// if a loadingPleaseWait has been sent, start it up
		if (loadingPleaseWait != null)
			loadingPleaseWait.start();

		if (loadScriptsInOrder) {
			scriptNowLoading = 0;
			launchScriptInjector(scriptsToLoad[0]);

		} else {
			for (String url : scriptsToLoad)
				launchScriptInjector(url);
		}
	}

	private static void scriptInjectReturned() {
		if (allScriptsLoaded()) {
			// if a loadingPleaseWait has been sent, finish it
			if (loadingPleaseWait != null)
				loadingPleaseWait.finish();
			
			startUp(pendingGinjectorHolder);
			
		} else if (loadScriptsInOrder) {
			scriptNowLoading++;
			launchScriptInjector(scriptsToLoad[scriptNowLoading]);
		}
	}

	private static boolean allScriptsLoaded() {
		return scriptsToLoad.length == scriptsLoaded.size()
				+ scriptsFailedToLoad.size();
	}

	private static void launchScriptInjector(final String url) {

		ScriptInjector
				.fromUrl(url)
				.setWindow(ScriptInjector.TOP_WINDOW)
				.setCallback(
						new Callback<Void, Exception>() {

							public void onFailure(Exception reason) {
								pendingLogs.add(new PendingLog(Level.SEVERE,
										"Failed to load JS " + url));

								scriptsFailedToLoad.add(url);
								scriptInjectReturned();
							}

							public void onSuccess(Void result) {
								pendingLogs.add(new PendingLog(Level.INFO,
										"Successfully loaded JS " + url));

								scriptsLoaded.add(url);
								scriptInjectReturned();
							}

						}).inject();
	}

	// TODO once multibindings and mapbindings are in Gin, look into using that
	/**
	 * Add components to the framework. Only call this in a constructor.
	 * 
	 * @param components
	 */
	public void addComponents(
			Component<?>... components) {
		// wait until the componet registry is received via method injection
		componentsToAdd.add(components);
	}

	private void reallyAddComponents(Component<?>... components) {
		componentRegistry.addComponents(components);
	}
	
	/**
	 * Use method injection to get some basic stuff, to keep subclasses'
	 * constructors simpler.
	 */
	@Inject
	public void setBasicComponents(
			ComponentRegistry componentRegistry,
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
		
		this.componentRegistry = componentRegistry;
		this.placeController = placeController;
		this.eventBus = eventBus;
		this.historyHandler = historyHandler;
		this.nullActivity = nullActivity;
		this.activityMappersFactory = activityMappersFactory;
		this.activityManagersFactory = activityManagersFactory;

		// Tell the component registry what the regions are, so it can check
		// that components handle regions that are actually available
		componentRegistry.setRegions(regions);
		
		reallyAddComponents(new Component<?>[] {
				dataManager,
				placeMapper,
				session,
				logger });

		for (Component<?>[] componentsArray : componentsToAdd)
			reallyAddComponents(componentsArray);
		
		this.placeMapper = placeMapper;
	}

	@Override
	public Set<Class<? extends ForRegionTag>> getRegions() {
		return regions;
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

	/**
	 * <p>
	 * Start up the framework: attach the layout widget to the viewport, create
	 * activity managers and attach them to the region they're concerned with,
	 * start history handling, and go to the default place (if no place is
	 * specified in the fragment identifier in the URL).
	 * </p>
	 * <p>
	 * Note that if there are scripts remaining to be injected, actual startup
	 * will be silently delayed until the scripts become available.
	 * </p>
	 */
	public void start() {

		// it is assumed that all constructor and method injection for
		// this class and components will have taken place by the time we get
		// here; so run through all components and call finalizeSetup,
		// which should be called after all components are loaded
		componentRegistry.callFinalizeSetup();

		final PescadorMVPLogger logger =
				componentRegistry.getComponent(PescadorMVPLogger.class);

		// Only log if a logger is available, of course--should not assume it is
		if (logger != null) {
			for (PendingLog pendingLog : pendingLogs)
				logger.log(pendingLog.getLevel(), pendingLog.getText());

			logger.log(Level.FINEST, "Components loaded, starting up framework");
		}

		// Check if any requested scripts failed to load
		if (scriptsFailedToLoad.size() > 0) {

			// If so, and if a logger is available, log the error
			if (logger != null) {
				for (String failedSriptURL : scriptsFailedToLoad)
					logger.log(Level.WARNING, "Couldn't inject script "
							+ failedSriptURL);
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
	}

	private <P extends PescadorMVPPlace> PAVComponent<?, P>
			getCastPAVComponent(P place) {

		PAVComponent<?, ?> pavComponent =
				componentRegistry.getPAVComponent(place.getMainToken());

		// GWT reflection doesn't provide for finding implemented interfaces
		@SuppressWarnings("unchecked")
		PAVComponent<?, P> castPAVComponent =
				(PAVComponent<?, P>) pavComponent;

		return castPAVComponent;
	}

	public <P extends PescadorMVPPlace,
			PS extends P> PescadorMVPPlaceActivity<?, ?, ?>
			getActivityForRegionAndPlace(
					Class<? extends ForRegionTag> region,
					P place) {

		PAVComponent<?, P> pavComponent =
				getCastPAVComponent(place);

		PescadorMVPPlaceActivity<?, ?, ?> activity =
				pavComponent.getActivity(region, place);

		if (activity != null)
			return activity;
		else
			return nullActivity;
	}

	private static class PendingLog {

		private final Level level;
		private final String message;

		PendingLog(Level level, String message) {
			this.level = level;
			this.message = message;
		}

		Level getLevel() {
			return level;
		}

		String getText() {
			return message;
		}
	}

	/**
	 * Implement this interface on a class that does something when JS
	 * scriptloading starts (like show a "please wait" message) and when it
	 * finishes (like remove the message). Then pass an instance to
	 * {@link ComponentSetup#loadJSthenStartUp}.
	 * 
	 * @author Andrew Green
	 */
	public interface LoadingPleaseWait {
		public void start();

		public void finish();
	}
}
