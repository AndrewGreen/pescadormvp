/**
 * <p>
 * JSONP Example is a sample application that demonstrates basic Pescador MVP
 * usage.
 * </p>
 * <p>
 * The application asks the user to enter a place name. Then, it fetches the
 * place's latitude and longitude via a JSONP request to OpenStreetMap, and
 * displays a map using OpenLayers. <a href=
 * "http://andrewgreen.github.io/pescadormvp/site/examples/jsonp/live/index.html"
 * >Here it is</a>, compiled and running in your browser.
 * </p>
 * <p>
 * This document explains how it works.
 * </p>
 * <p>
 * (Note: a shorter <a href="../../../core/package-summary.html">overview of
 * Pescador MVP</a> is also available.)
 * </p>
 * <h4>Contents:</h4>
 * <ol>
 * <li><a href="#component">The Query Component</a></li>
 * <li><a href="#generalqcstuff">General Query Component Stuff</a></li>
 * <li><a href="#queryactivity">The Query Activity</a></li>
 * <li><a href="#queryview">The Query View</a></li>
 * <li><a href="#map">The Map Widget</a></li>
 * <li><a href="#queryplace">The Query Place</a></li>
 * <li><a href="#actionhelper">The JSONP Action Helper</a></li>
 * <li><a href="#layout">Application Layout</a></li>
 * <li><a href="#globalsetup">Global Setup</a></li>
 * <li><a href="#frameworkstartup">Starting Up the Framework</a></li>
 * <li><a href="#tests">Tests</a></li>
 * </ol>
 * <h3><a name="component"/>The Query Component</h3>
 * <p>
 * In Pescador MVP, you create a component by grouping some classes together in
 * a package, and making one of those classes implement the
 * {@link mx.org.pescadormvp.core.client.components.Component} interface.
 * </p>
 * <p>
 * The JSONP Example application is so simple that it has only one component:
 * "Query". All the classes that are part of this component are in the
 * {@link mx.org.pescadormvp.examples.jsonp.client.query
 * ...examples.jsonp.client.query} package.
 * </p>
 * <p>
 * Since we're using dependency injection, we want variables to refer to
 * interfaces, rather than concrete classes, as much as possible. So, the public
 * methods of almost every concrete class are set out in a corresponding
 * interface, which is used in variable definitions. Interfaces have nearly the
 * same names as the concrete classes that implement them (the difference is
 * just that concrete class names have an "Impl" on the end).
 * </p>
 * <p>
 * Here are the Query Component's interface-implementation pairs:
 * </p>
 * <table class="packageSummary" border="0" cellpadding="3" cellspacing="0">
 * <tr>
 * <th class="colFirst" scope="col">Interface</th>
 * <th class="colFirst" scope="col">Implementation</th>
 * <th class="colOne" scope="col">Description</th>
 * </tr>
 * <tbody>
 * <tr class="rowColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryComponent.html"
 * > QueryComponent</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryComponentImpl.html"
 * > QueryComponentImpl</a></td>
 * <td class="colOne"><div class="block">General Query Component stuff.</div></td>
 * </tr>
 * <tr class="altColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryActivity.html"
 * > QueryActivity</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryActivityImpl.html"
 * > QueryActivityImpl</a></td>
 * <td class="colOne"><div class="block">The activity that this component
 * provides for the main (and only) region of the UI.</div></td>
 * </tr>
 * <tr class="rowColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryView.html"
 * > QueryView</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryViewImpl.html"
 * > QueryViewImpl</a></td>
 * <td class="colOne"><div class="block">The view that this component provides
 * for the main (and only) region of the UI.</div></td>
 * </tr>
 * <tr class="altColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/OSMMap.html"
 * > OSMMap</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/OSMMapImpl.html"
 * > OSMMapImpl</a></td>
 * <td class="colOne"><div class="block">The map widget.</div></td>
 * </tr>
 * <tr class="rowColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryPlace.html"
 * > QueryPlace</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryPlaceImpl.html"
 * > QueryPlaceImpl</a></td>
 * <td class="colOne"><div class="block">The place associated with this
 * component.</div></td>
 * </tr>
 * <tr class="altColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/GetLatLonActionHelper.html"
 * > GetLatLonActionHelper</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/GetLatLonActionHelperImpl.html"
 * > GetLatLonActionHelperImpl</a></td>
 * <td class="colOne"><div class="block">A helper for performing JSONP requests
 * to OpenStreetMap.</div></td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * Let's look at each of these pairs.
 * </p>
 * <h3><a name="generalqcstuff"/>General Query Component Stuff</h3>
 * <p>
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * takes responsibility for everything of general interest to the Query
 * Component. One such responsibility is the implementation
 * {@link mx.org.pescadormvp.core.client.components.Component}&mdash;Pescador
 * MVP requires that all components include an implementation of that interface.
 * </p>
 * <p>
 * However, Query Component is not just an ordinary component&mdash;it's a
 * "place-activity-view" (PAV) component. That is, it's a special kind of
 * component that associates a place class with one or more activity classes,
 * view classes, and regions of the UI. Pescador MVP requires that PAV
 * components include an implementation of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponent} (a
 * subinterface of {@link mx.org.pescadormvp.core.client.components.Component}).
 * </p>
 * <p>
 * The {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}
 * interface is a subinterface of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponent} . So
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}, by
 * implementing
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}, also
 * implements
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponent} and
 * {@link mx.org.pescadormvp.core.client.components.Component}.
 * </p>
 * <p>
 * Here is the definition of
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public interface QueryComponent extends PAVComponent&lt;
 *             QueryComponent, 
 *             QueryPlace>,
 *             RawDefaultPlaceProvider { }
 * </code>
 * </pre>
 * <p>
 * As you can see,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent} fills
 * in the generic parameters of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponent} .
 * (For now, don't worry about the reference to
 * {@link mx.org.pescadormvp.core.client.placesandactivities.RawDefaultPlaceProvider}
 * &mdash;it'll be explained in a <a href="#globalsetup">moment</a>.) The
 * opening line of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponent} goes
 * like this:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public interface PAVComponent&lt;
 *             I extends PAVComponent&lt;I,P>, // component's interface
 *             P extends PescadorMVPPlace>  // place
 *             extends Component&lt;I>
 * </code>
 * </pre>
 * <p>
 * Now let's look at the implementation,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl},
 * which starts out like this:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public class QueryComponentImpl extends PAVComponentBase&lt; 
 *             QueryComponent,
 *             QueryPlace>
 *             implements
 *             QueryComponent
 * </code>
 * </pre>
 * <p>
 * By extending
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponentBase}
 * (as all PAV components should)
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * gains access to many convenience methods for dealing with places, activities
 * and UI regions.
 * </p>
 * <p>
 * What actual logic goes in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}?
 * First, in Pescador MVP, if a component provides any extra functionality to
 * other parts of the system, that functionality should be made available via
 * the class that implements
 * {@link mx.org.pescadormvp.core.client.components.Component} (in this case,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}).
 * The Query Component offers only one method of this sort,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl#getRawDefaultPlace()
 * getRawDefaultPlace()}, which provides the application's default place. (More
 * about this <a href="#globalsetup">below</a>.)
 * </p>
 * <p>
 * Dependency injection bindings also are of general interest to the component,
 * so they, too, should be placed in the class that implements
 * {@link mx.org.pescadormvp.core.client.components.Component}.
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * contains a static inner class that implements
 * {@link com.google.gwt.inject.client.GinModule} and sets all the component's
 * DI bindings:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public static class QueryGinModule extends AbstractGinModule {
 * 
 *         {@literal @}Override
 *         protected void configure() {
 *             // Bind the component interface to its implementation
 *             // (which is the enclosing class).
 *             // This is something all components will do.
 *             bind(QueryComponent.class).
 *                     to(QueryComponentImpl.class).in(Singleton.class);
 * 
 *             // Create the place factory.
 *             // All place-activity-view components will do this.
 *             install(new GinFactoryModuleBuilder().implement(
 *                     QueryPlace.class, QueryPlaceImpl.class)
 *                     .build(
 *                     new TypeLiteral&lt;
 *                     RawPlaceFactory&lt;QueryPlace>>(){}));
 * 
 *             // The next two bindings are for the activity and the view.
 *             // Place-activity-view components will have bindings like these 
 *             // for each activity and view they define.
 *             // (This component has only one activity and one view.)
 *             
 *             // Create the activities factory.
 *             install(new GinFactoryModuleBuilder().implement(
 *                     QueryActivity.class, QueryActivityImpl.class)
 *                     .build(
 *                     new TypeLiteral&lt;
 *                     ActivitiesFactory&lt;QueryPlace, QueryActivity>>(){}));
 *             
 *             // Bind the view.
 *             bind(QueryView.class).to(QueryViewImpl.class)
 *                     .in(Singleton.class);
 *             
 *             // The remaining bindings are specific to this component.
 *             // Other place-activity-view components may or may not have
 *             // additional bindings like these, depending on what they do.
 *             
 *             // Bind JSONP action helper.
 *             // Any component that sets up JSONP requests will do something
 *             // like this.
 *             bind(GetLatLonActionHelper.class)
 *                     .to(GetLatLonActionHelperImpl.class)
 *                     .in(Singleton.class);
 *             
 *             // Bind OSMMap widget.
 *             bind(OSMMap.class).to(OSMMapImpl.class).in(Singleton.class);
 *         }
 *     }
 * </code>
 * </pre>
 * <p>
 * The remaining general setup is performed in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}'s
 * constructor:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}Inject
 *     public QueryComponentImpl(
 *             ActivitiesFactory&lt;QueryPlace, QueryActivity> activitiesFactory,
 *             GetLatLonActionHelper actionHelper,
 *             DataManager dataManager) {
 *         
 *         // Establish that this component has an activity for the
 *         // Layout.Body screen region (in this app, that's the only region).
 *         addRegionAndActivitiesFactory(Body.class, activitiesFactory);
 * 
 *         // register JSONP action helper for getting lat-lon data
 *         dataManager.registerActionHelper(actionHelper);
 *     }
 * </code>
 * </pre>
 * <p>
 * Here, we link the UI region
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body
 * Layout.Body} to the activities factory we created in our
 * {@link com.google.gwt.inject.client.GinModule} (see above). This tells the
 * framework to activate the
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryActivity} and
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryView} in the
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body
 * Layout.Body} UI region when the application goes to
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace}.
 * </p>
 * <p>
 * Finally, we register a
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper} with the
 * {@link mx.org.pescadormvp.core.client.data.DataManager}. This is necessary
 * for the JSONP requests the component will perform (more about
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper}s <a
 * href="#actionhelper">below</a>).
 * </p>
 * <p>
 * If you find all of the above a bit boilerplatey, you're right! Admittedly, it
 * would be possible to set up some fancy code generation to make PAV component
 * creation easier. Nonetheless, it's not an onerous amount of boilerplate, and
 * it does help you organize your dependency-injected MVP-architected code.
 * </p>
 * <h3><a name="queryactivity"/>The Query Activity</h3>
 * <p>
 * This is the fun part: the activity. Here's the constructor:
 * </p>
 * 
 * <pre>
 * <code class=java>
 * 	{@literal @}Inject
 * 	public QueryActivityImpl(
 * 			QueryComponent queryComponent,
 * 			QueryMessages messages,
 * 			OSMMap map,
 * 			DataManager dataManager,
 * 			PescadorMVPLogger logger) {
 * 
 * 		this.queryComponent = queryComponent;
 * 		this.messages = messages;
 * 		this.map = map;
 * 		this.dataManager = dataManager;
 * 		this.logger = logger;
 * 	}
 * </code>
 * </pre>
 * <p>
 * OK, that's pretty straightforward. We get everything we need via DI and store
 * it all in instance variables.
 * </p>
 * <p>
 * The activity's
 * {@link com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
 * start()} method is also unsurprising&mdash;after all, this is a standard GWT
 * {@link com.google.gwt.activity.shared.Activity}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}Override
 *     public void start(AcceptsOneWidget container, EventBus eventBus) {
 * 
 *         // Attach the view to the container for the only UI region
 *         QueryView view = getView(); 
 *         contaier.setWidget(view);
 *         ...
 * </code>
 * </pre>
 * <p>
 * The only unusual thing here is that we conveniently have access to our view
 * via the
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityBase#getView()
 * getView()} method (available to us because the activity extends
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase}
 * , as required).
 * </p>
 * <p>
 * Standard Pescador MVP fare is to have the activity push to and control the
 * view. The view doesn't do anything except control UI details and fire off
 * events.
 * </p>
 * <p>
 * The first thing we do with our passive view is ask it if the query area
 * (where the user will enter information) has been rendered. If it hasn't, we
 * tell the view to render it.
 * </p>
 * 
 * <pre>
 * <code class=java>
 *         if (!view.isQueryAreaRendered()) {
 * 
 *             view.renderQueryArea(
 *                     messages.beforeQueryTextBox(),
 *                     messages.afterQueryTextBox());
 *         }
 * </code>
 * </pre>
 * <p>
 * Then, we register to receive events from the view, and call
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryActivityImpl#doQuery()
 * doQuery()} (where the real action takes place):
 * </p>
 * 
 * <pre>
 * <code class=java>
 *         linkHandlerReg = eventBus.addHandlerToSource(
 *                 PlaceRequestEvent.TYPE,
 *                 view, this);
 * 
 *          doQuery();
 *      }
 * </code>
 * </pre>
 * <p>
 * Note that {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryView}
 * has five renderable states:
 * </p>
 * <ol>
 * <li><i>Empty</i>&nbsp;&nbsp;&nbsp;In this state, no messages or information
 * are shown.</li>
 * <li><i>Loading</i>&nbsp;&nbsp;&nbsp;The view displays a message saying that
 * the requested information is loading.</li>
 * <li><i>Error</i>&nbsp;&nbsp;&nbsp;The view displays a message explaining that
 * there was an error communicating with the server.</li>
 * <li><i>Lat-lon</i>&nbsp;&nbsp;&nbsp;The view displays a place's latitude and
 * longitude, and a map.</li>
 * <li><i>No such place</i>&nbsp;&nbsp;&nbsp;The view displays a message
 * explaining that no place was found with the name entered.</li>
 * </ol>
 * <p>
 * It's the activity's responsibility to tell the view which state to render,
 * and to provide it with the information it needs to do so.
 * </p>
 * <p>
 * So, in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryActivityImpl#doQuery()
 * doQuery()}, the activity first gets the name of the location the user wants
 * to hear about. It gets this information from the current
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace} instance,
 * available via the
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase#getPlace()
 * getPlace()} method. If the current Place has no information on this, then the
 * activity tells the view to render an empty state.
 * </p>
 * 
 * <pre>
 * <code class=java>
 *         final String location = getPlace().getLocation();
 *         
 *         // If there's no location data, set up the view as "empty"
 *         if ((location == null) || (location.length() == 0)) {
 *             view.setTextboxContents("");
 *             view.renderEmpty();
 * </code>
 * </pre>
 * <p>
 * On the other hand, if the
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace} <b>does</b>
 * have the name of a location to be queried, then the activity sends a request
 * to the {@link mx.org.pescadormvp.core.client.data.DataManager}, and decides
 * what to do on the basis of the result. Here's the code:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *         } else {
 *             
 *             // Make sure the text box contains the location string the user asked for
 *             // We don't sanitize it here, that's for the view to do
 *             view.setTextboxContents(location);
 *             
 *             // Before trying to retrieve information,
 *             // set a timer to display a "Loading" message if the request
 *             // doesn't come back quickly. The view will cancel the timer
 *             // once any state is rendered.
 *             view.setLoadingString(messages.loading());
 *             view.scheduleLoadingMessage();
 *                         
 *             // Create an action object with the name of the location to query
 *             GetLatLonAction action = new GetLatLonAction(location);
 *             
 *             // Perform the request
 *             dataManager.execute(action, GetLatLonResult.class,
 *                     new AsyncCallback&lt;GetLatLonResult>() {
 *     
 *                 {@literal @}Override
 *                 public void onFailure(Throwable caught) {
 *                     
 *                     // Log the error
 *                     logger.log(Level.WARNING, caught.getLocalizedMessage());
 *                     
 *                     // Notify the user
 *                     view.setErrorStrings(messages.errorCommunicating(),
 *                             messages.tryAgain());
 *                     view.renderError();
 *                 }
 *         
 *                 {@literal @}Override
 *                 public void onSuccess(GetLatLonResult result) {
 *                     
 *                     if (result.hasData()) {
 *                         
 *                         double lat = result.getLat();
 *                         double lon = result.getLon();
 *                         
 *                         String latNSStr = lat > 0 ? "N" : "S";
 *                         String latStr = Math.abs(lat) + " " + latNSStr;
 *                         
 *                         String lonEWStr = lon > 0 ? "E" : "W";
 *                         String lonStr = Math.abs(lon) + " " + lonEWStr;
 *                         
 *                         String info = messages.XisAtY(
 *                                 result.getDisplayName(), latStr, lonStr);
 *                         
 *                         // Send the info to the view
 *                         view.setLatLonMsg(info);
 *                         
 *                         // Ensure the view has the map it will use
 *                         if (!view.osmMapSet())
 *                             view.setOSMMap(map);
 *                         
 *                         // Set the map to the correct location
 *                         map.setLatLon(lat, lon);
 *                         
 *                         // Tell the view to render
 *                         view.renderLatLon();
 * 
 *                     } else {
 *                         
 *                         view.setNoSuchPlaceStrings(
 *                                 messages.neverHeardOf(location),
 *                                 messages.tryAgain());
 *                         
 *                         view.renderNoSuchPlace();
 *                     }
 *                 }
 *             });
 *         }
 * </code>
 * </pre>
 * <p>
 * In summary, we tell the view to set the contents of the text box with the
 * name of the location being queried, and to start a timer and render the
 * Loading state if it doesn't hear back from us right away. Then we create a
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonAction} with
 * the name of the location to query, and send it to the (third-party) server
 * via the {@link mx.org.pescadormvp.core.client.data.DataManager}. When the
 * request comes back, we tell the view to render Error, Lat-lon or No such
 * place, depending on the result. We also provide the view with the localized
 * messages it needs to do this. If the Lat-lon state is to be rendered, we also
 * do a bit of string processing, set the map location and make sure the view
 * has the required map widget (
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.OSMMap}&mdash; see <a
 * href="#map">below</a>).
 * </p>
 * <p>
 * In truth, there's not much extraordinary about this activity. The most
 * significant things about it are: we get everything we need via DI and methods
 * provided by the superclass, push to the view and handle events from it, and
 * send requests via a handy
 * {@link mx.org.pescadormvp.core.client.data.DataManager} that uses the command
 * pattern (more on that <a href="#actionhelper">in a minute</a>).
 * </p>
 * <h3><a name="queryview"/>The Query View</h3>
 * <p>
 * There's really nothing framework-specific about the view in this example app.
 * It's implemented as a {@link com.google.gwt.user.client.ui.ResizeComposite}
 * and uses standard GWT facilities like UiBinder, client bundles, sprites,
 * {@link com.google.gwt.safehtml.client.SafeHtmlTemplates} and animation. Check
 * it out if you're interested in how it uses those things.
 * </p>
 * <p>
 * This view knows nothing about the activity that controls it. It just provides
 * methods that the activity accesses, and fires off events. There's only one
 * non-display-related task that it's allowed to handle: setting data in
 * {@link com.google.gwt.place.shared.Place} objects.
 * </p>
 * <h3><a name="map"/>The Map Widget</h3>
 * <p>
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.OSMMapImpl} is a small
 * wrapper for the OpenLayers
 * {@link org.gwtopenmaps.openlayers.client.MapWidget}. It's been kept separate
 * from the view and the activity because it contains a bit of logic related to
 * map rendering that doesn't fit in either. The activity controls it and sends
 * it to the view for attachment at the appropriate spot in the UI.
 * </p>
 * <h3><a name="queryplace"/>The Query Place</h3>
 * <p>
 * In Pescador MVP, Places do have quite a bit of specialness: they're passed
 * around as interfaces rather than concrete classes, can store information in
 * key-value pairs, and have extra fields that are useful for creating
 * hyperlinks. Let's look at all these features.
 * </p>
 * <p>
 * Here's the code for
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlaceImpl}:
 * </p>
 * 
 * <pre>
 *  <code class=java>
 *      public class QueryPlaceImpl extends PescadorMVPPlaceBase implements QueryPlace {
 *      
 *          private static final String MAIN_TOKEN = "query";
 *          private static final String LOCATION_KEY = "l";
 *          
 *          private String location;
 *          
 *          public QueryPlaceImpl() {
 *          
 *              // Tell the superclass about the main token and the only
 *              // key.
 *              super(MAIN_TOKEN, new String[] { LOCATION_KEY });
 *          }
 *          
 *          /**
 *           * This method is called by the superclass whenever a property needs to be
 *           * set.
 *           *{@literal /}
 *          {@literal @}Override
 *          protected void processProperty(String key, String value) {
 *              if (key.compareTo(LOCATION_KEY) == 0) {
 *                  location = value;
 *              } 
 *          }
 *      
 *          {@literal @}Override
 *          public String getLocation() {
 *              return location;
 *          }
 * 
 *          /**
 *           * Here, instead of setting our instance variable directly, we tell the
 *           * superclass to follow its standard procedure for setting a property.
 *           *{@literal /}
 *          {@literal @}Override
 *          public void setLocation(String location) {
 *              setProperty(LOCATION_KEY, location);
 *          }
 *      }
 *  </code>
 * </pre>
 * <p>
 * All Pescador MVP facilities expect places to be subinterfaces of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace},
 * which {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace} is.
 * Refering to places as interfaces rather than concrete (or even abstract)
 * classes allows more flexibility, since interfaces can handle multiple
 * inheritance. (So, for example, if you have code that needs to deal with a
 * specific set of places, you can use a tag interface that may or may not
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}.)
 * </p>
 * <p>
 * At the same time, all implementations of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}
 * should also extend
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceBase}
 * , which itself extends GWT's {@link com.google.gwt.place.shared.Place}. That
 * means
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * are always GWT {@link com.google.gwt.place.shared.Place}s, too. They can be
 * gotten as such via the
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace#asGWTPlace()
 * asGWTPlace()} method.
 * </p>
 * <p>
 * As you can see, there's not much logic in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlaceImpl}. In the
 * constructor, we send the superclass the main token and keys to be used in the
 * URL fragment identifier. There is a getter and a setter for location, which
 * holds the name of the location the user wants to query. In the setter,
 * instead of setting our instance variable directly, we call
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceBase#setProperty(String, String)
 * setProperty(String, String)}, as shown. This call will eventually get back to
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceBase#processProperty(String, String)
 * processProperty(String, String)}, which we must implement. That's where we
 * set the instance variable, also as shown.
 * </p>
 * <p>
 * By doing things this way, we can give our places simple getters and setters
 * for the data they store, and allow the framework to serialize and deserailize
 * place state to and from URL fragment identifiers. In the case of
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace}, a query
 * for "London", for example, translates to the following fragment identifier: "
 * <code>#query;l=London</code>". (So with Pescador MVP, you don't have to write
 * any {@link com.google.gwt.place.shared.PlaceTokenizer}s.)
 * </p>
 * <p>
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * can hold the following additional information: a locale, a history token
 * (that is, the URL fragment identifier without the "#"), a full URL, and
 * presentation text. All this is useful mostly with the framework's facilities
 * for creating internal hyperlinks (see
 * {@link mx.org.pescadormvp.core.client.internallinks
 * ...core.client.internallinks}). (BTW, there are parts of the framework where
 * places are refered to as "raw"&mdash;that just means that they haven't had
 * any of this additional information set up yet.)
 * </p>
 * <h3><a name="actionhelper"/>The JSONP Action Helper</h3>
 * <p>
 * Pescador MVP includes a caching command-pattern
 * {@link mx.org.pescadormvp.core.client.data.DataManager} (based on <a
 * href="http://code.google.com/p/gwt-dispatch/">gwt-dispatch</a> and a <a href=
 * "http://turbomanage.wordpress.com/2010/07/12/caching-batching-dispatcher-for-gwt-dispatch/"
 * > blog post by David Chandler</a>). It doesn't have much to do with MVP, but
 * it's still kinda useful.
 * </p>
 * <p>
 * Normally, server calls go to your own server. The
 * {@link mx.org.pescadormvp.core.client.data.DataManager} manages such calls
 * via standard gwt-dispatch {@link net.customware.gwt.dispatch.shared.Action}
 * and {@link net.customware.gwt.dispatch.shared.Result} classes. To handle them
 * on the server, you implement
 * {@link net.customware.gwt.dispatch.server.ActionHandler ActionHandler}s, as
 * per the <a href="http://code.google.com/p/gwt-dispatch/wiki/GettingStarted">
 * gwt-dispatch documentation</a>.
 * </p>
 * <p>
 * Pescador MVP also lets you use the command pattern for calls to third-party
 * servers via JSONP. In that case, instead of implementing an
 * {@link net.customware.gwt.dispatch.server.ActionHandler ActionHandler}, you
 * create a {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper} that
 * encapsulates details of the server call, including the creation of a
 * {@link net.customware.gwt.dispatch.shared.Result Result} instance.
 * </p>
 * <p>
 * Here is the example app's
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper},
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonActionHelperImpl}
 * :
 * </p>
 * 
 * <pre>
 * <code class=java>
 *  public class GetLatLonActionHelperImpl 
 *             implements GetLatLonActionHelper {
 *     
 *         final static String BASE_URL = "http://nominatim.openstreetmap.org/search?format=json&limit=1&q=";
 *         final static String CALLBACK_PARAM ="json_callback";
 * 
 *         /**
 *          * Create the Result from the received JavaScriptObject.
 *          *{@literal /}
 *         {@literal @}Override
 *         public GetLatLonResult insantiateResult(JavaScriptObject jsResult) {
 *     
 *             LatLonInfo latLonInfo = jsResult.cast();
 *             GetLatLonResult result = new GetLatLonResult();
 *             
 *             result.setValid(latLonInfo.getCount() > 0);
 *             
 *             if (result.isValid()) {
 *                 result.setDisplayName(latLonInfo.getDisplayName());
 *                 result.setLat(Double.valueOf(latLonInfo.getLat()));
 *                 result.setLon(Double.valueOf(latLonInfo.getLon()));
 *             }
 *     
 *             return result;
 *         }
 *     
 *         {@literal @}Override
 *         public String getRequestURL(GetLatLonAction action) {
 *             return UriUtils.sanitizeUri(BASE_URL + action.getLocation());
 *         }
 *     
 *         {@literal @}Override
 *         public String getCallbackParameter() {
 *             return CALLBACK_PARAM;
 *         }
 *         
 *         {@literal @}Override
 *         public Class<GetLatLonAction> getActionType() {
 *             return GetLatLonAction.class;
 *         }
 *         
 *         /**
 *          * Overlay Java type for accessing contents of Javascript object
 *          * returned over JSONP.
 *          *{@literal /}
 *         static private class LatLonInfo extends JavaScriptObject {
 *     
 *             protected LatLonInfo() {}
 *             
 *             public final native int getCount() /*-{
 *                 return this.length;
 *             }-*{@literal /};
 *             
 *             public final native String getDisplayName() /*-{
 *                 return this[0].display_name;
 *             }-*{@literal /};
 *             
 *             public final native String getLat() /*-{
 *                 return this[0].lat;
 *             }-*{@literal /};
 *             
 *             public final native String getLon() /*-{
 *                 return this[0].lon;
 *             }-*{@literal /};
 *         }
 *     }
 * </code>
 * </pre>
 * <p>
 * Remember that {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper}s
 * must be registered with the
 * {@link mx.org.pescadormvp.core.client.data.DataManager}, as we <a
 * href="#generalqcstuff">saw earlier</a>.
 * </p>
 * <p>
 * Also, note that for an action's result to be cached, the action must
 * implement {@link mx.org.pescadormvp.core.client.data.CacheableAction}.
 * </p>
 * <h3><a name="layout"/>Application Layout</h3>
 * <p>
 * Pescacdor MVP is basically agnostic about how you lay out your application.
 * It does, however make a few simple assumptions&mdash;namely, that your app
 * has regions, and that each region has a container widget.
 * </p>
 * <p>
 * (However, the regions' dimensions need not remain static, and regions are not
 * required to always be visible. It's also possible to have UI elements that
 * are logically contained in a region without being visually tied to it.)
 * </p>
 * <p>
 * The framework's assumptions about regions are embodied in the
 * {@link mx.org.pescadormvp.core.client.regionsandcontainers.RootHasFixedSetOfRegions}
 * interface. Applications must provide an implementation of that interface.
 * Here is the very simple implementation used in the example app:
 * </p>
 * 
 * <pre>
 * <code class=java>
 * public class LayoutImpl extends ResizeComposite implements Layout {
 *     
 *        private SimpleLayoutPanel outer = new SimpleLayoutPanel();
 *        private DynamicSimpleLayoutPanel body = new DynamicSimpleLayoutPanel();
 *        
 *        private LayoutHelper layoutHelper;
 *        
 *        {@literal @}Inject
 *        public LayoutImpl(LayoutHelper layoutHelper) {
 *           this.layoutHelper = layoutHelper;
 *           
 *           // set up region(s)
 *           layoutHelper.setupRegion(Body.class, body);
 *     
 *           // take up the whole viewport
 *           body.setWidth("100%");
 *           body.setHeight("100%");
 *           outer.setWidth("100%");
 *           outer.setHeight("100%");
 *           
 *           outer.add(body);
 *           
 *           // required call for all children of GWT's Composite widget
 *           initWidget(outer);
 *        }
 *        
 *        {@literal @}Override
 *        public void attach() {
 *           RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
 *           rootLayoutPanel.add(this);
 *           onResize(); // make sure everything's the right size
 *        }
 *     
 *        {@literal @}Override
 *        public Set&lt;Class&lt;? extends ForRegionTag>> getRegions() {
 *           return layoutHelper.getRegions();
 *        }
 *     
 *        {@literal @}Override
 *        public DynamicContainer
 *              getContainer(Class&lt;? extends ForRegionTag> regionTag) {
 *           return layoutHelper.getContainer(regionTag);
 *        }
 *     }
 * </code>
 * </pre>
 * <p>
 * So there you go: one region (
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body Body}),
 * one container, an
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.LayoutImpl#attach()
 * attach()} method, and some boilerplate hidden away by
 * {@link mx.org.pescadormvp.core.client.regionsandcontainers.LayoutHelper}.
 * </p>
 * <h3><a name="globalsetup"/>Global Setup</h3>
 * <p>
 * So far we've seen how to create a PAV component, a
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper} and a global
 * layout widget. Now let's look at Pescador MVP's facilities for global
 * application setup.
 * </p>
 * <p>
 * Just as the setup of each component is contained in a single class, global
 * application setup is also done in a single class&mdash;which must be a
 * subclass of {@link mx.org.pescadormvp.core.client.components.GlobalSetup}. In
 * the example app, the class used for global setup is
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveGlobalSetup}.
 * </p>
 * <p>
 * An important part of global setup is DI binding. There will normally be some
 * bindings that are truly global, rather than being tied to a given component.
 * In the example app, global bindings are set in a static inner class within
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveGlobalSetup}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public static class ActiveGlobalSetupGinModule extends AbstractGinModule {
 *         {@literal @}Override
 *         protected void configure() {
 *             
 *             // global setup
 *             bind(GlobalSetup.class).to(ActiveGlobalSetup.class)
 *                     .in(Singleton.class);
 * 
 *             // global layout widget
 *             bind(Layout.class).to(LayoutImpl.class).in(Singleton.class);
 * 
 *             // WARNING: in the next two bindings, it's important to bind
 *             // to interfaces, not implementations.
 * 
 *             // tell the framework that this is our global layout widget
 *             bind(RootHasFixedSetOfRegions.class).to(Layout.class);
 *             
 *             // tell the framework that this is our default place provider
 *             bind(RawDefaultPlaceProvider.class).to(QueryComponent.class);
 *         }
 * 
 *         // An example of Guice-style global configurations:
 *         // set the max cache size for the DataManager.
 *         {@literal @}Provides
 *         {@literal @}Named("maxDataManagerCacheSize")
 *         Integer providesMaxDataManagerCacheSize() {
 *             return 50;
 *         }
 *     }
 * </code>
 * </pre>
 * <p>
 * First we bind the enclosing class, then the global layout widget, then tell
 * the framework that that widget is to be used for global layout.
 * </p>
 * <p>
 * The fourth binding tells the framework that
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent} will
 * provide the application's default place. That means that if there is no URL
 * fragment identifier, or if there is a fragment identifier that framework is
 * unable to interpret, the application will go to the place provided by
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}'s
 * {@link mx.org.pescadormvp.core.client.placesandactivities.RawDefaultPlaceProvider#getRawDefaultPlace()
 * getRawDefaultPlace()} method. (That's why the
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}
 * interface extends
 * {@link mx.org.pescadormvp.core.client.placesandactivities.RawDefaultPlaceProvider}
 * , as we <a href="#generalqcstuff">saw earlier</a>.)
 * </p>
 * <p>
 * The last few lines of the above code show how to use binding annotations to
 * set global configuration values. Here we override the
 * {@link mx.org.pescadormvp.core.client.data.DataManager}'s default maximum
 * cache size, setting it to 50. (Normally, there's no need to do this&mdash;the
 * default value should be fine.)
 * </p>
 * <p>
 * To complete DI setup, we define a
 * {@link com.google.gwt.inject.client.Ginjector} interface and bring in all the
 * {@link com.google.gwt.inject.client.GinModule}s containing bindings we want
 * to use. In the example app, we do this with an inner interface inside
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveGlobalSetup}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}GinModules({
 *         // Global bindings for this app that are not part of any component
 *         ActiveGlobalSetupGinModule.class,
 *         
 *         // Bindings for the Query Component
 *         QueryGinModule.class,
 *         
 *         // Modules required by the framework
 *         PescadorMVPGinModule.class,
 *         StandardDispatchModule.class
 *     })
 *     public interface ActiveGlobalSetupGinjector extends PescadorMVPGinjector {}
 * </code>
 * </pre>
 * <p>
 * The last two modules are required and should be included in all Pescador MVP
 * apps. The first module is the one we defined within this class, for global
 * bindings. The second module belongs to our Query Component.
 * </p>
 * <p>
 * Of course, most applications will have more than one component. The
 * {@link com.google.gwt.inject.client.GinModule}s for all components should be
 * brought in like this, just as we do for the Query Component.
 * </p>
 * <p>
 * The last step in global application setup is to inject our components in the
 * constructor of our subclass of
 * {@link mx.org.pescadormvp.core.client.components.GlobalSetup}, and tell the
 * superclass about them, as shown:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}Inject
 *     public ActiveGlobalSetup(QueryComponent queryComponent) {
 * 
 *         // Register components
 *         addComponents(queryComponent);
 *     }
 * </code>
 * </pre>
 * 
 * </p> <h3><a name="frameworkstartup"/>Starting Up the Framework</h3>
 * <p>
 * {@link mx.org.pescadormvp.core.client.components.GlobalSetup} provides two
 * static methods for starting up the application. They're static so they can be
 * accessed <b>before</b> DI gets going.
 * </p>
 * <p>
 * While it's possible to call these methods directly from your
 * {@link com.google.gwt.core.client.EntryPoint} class, the recommended strategy
 * is to call one of them from a static method in your subclass of
 * {@link mx.org.pescadormvp.core.client.components.GlobalSetup}, and call
 * <b>that</b> method from your {@link com.google.gwt.core.client.EntryPoint}.
 * That way, you keep all global setup code inside your subclass of
 * {@link mx.org.pescadormvp.core.client.components.GlobalSetup}. This is the
 * approach used in the example app.
 * </p>
 * <p>
 * Here is the example app's entry point:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public class JSONPExample implements EntryPoint {
 *     
 *         {@literal @}Override
 *         public void onModuleLoad() {
 *             ActiveGlobalSetup.startUp();
 *         }
 *     }
 * </code>
 * </pre>
 * <p>
 * And here's
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveGlobalSetup#startUp()}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public static void startUp() {
 *         
 *         PescadorMVPGinjectorHolder ginjectorHolder = 
 *                 new PescadorMVPGinjectorHolder() {
 * 
 *             {@literal @}Override
 *             public PescadorMVPGinjector getPescadorMVPGinjector() {
 *                 return GWT.create(ActiveGlobalSetupGinjector.class);
 *             }
 *         };
 *         
 *         // Inject external Javascript libraries for OpenLayers, display 
 *         // a loading image if the scripts take a long time to load,
 *         // and start up the application.
 *         GlobalSetup.loadJSthenStartUp(
 *                 ginjectorHolder,
 *                 new InitialLoadingTimer(),
 *                 true,
 *                 "JSONPExample/js/gwt-openlayers/util.js",
 *                 "http://www.openlayers.org/api/OpenLayers.js",
 *                 "http://www.openstreetmap.org/openlayers/OpenStreetMap.js");
 *     }
 * </code>
 * </pre>
 * <p>
 * Startup in the example app is a bit more complicated than in most cases,
 * because our OpenLayers map widget relies on Javascript libraries that must be
 * loaded before the widget can be used. The static method
 * {@link mx.org.pescadormvp.core.client.components.GlobalSetup#loadJSthenStartUp(mx.org.pescadormvp.core.client.components.GlobalSetup.PescadorMVPGinjectorHolder, mx.org.pescadormvp.core.client.components.GlobalSetup.LoadingPleaseWait, boolean, String...)
 * GlobalSetup.loadJSthenStartUp()} helps us handle such cases. We send it the
 * URLs of the Javascript libraries to load and a wrapper containing our
 * {@link com.google.gwt.inject.client.Ginjector}. It will ensure the Javascript
 * is loaded before the {@link com.google.gwt.inject.client.Ginjector} is
 * created. We also send it a widget that displays an egg timer to tell the user
 * the page is loading. This image will appear if it take a long time to get the
 * Javascript.
 * </p>
 * <p>
 * If you don't need to load any Javascript libraries before starting your app,
 * you can use the much simpler
 * {@link mx.org.pescadormvp.core.client.components.GlobalSetup#startUp(PescadorMVPGinjector)}
 * method, instead.
 * </p>
 * <h3><a name="tests"/>Tests</h3>
 * <p>
 * One of the biggest reasons for using DI is to facilitate testing. So, no
 * demonstration of a DI-based framework would be complete without some juicy
 * unit tests!
 * </p>
 * <p>
 * The example app, like many GWT apps, has two kinds of tests: those that
 * require a browser, and those that run on a plain JVM. (Tests of the second
 * kind are preferable because they're faster.)
 * </p>
 * <p>
 * The tests are run automatically by Maven when the example app is built (see
 * the <a href=
 * "https://github.com/AndrewGreen/pescadormvp#how-to-compile-run-and-debug">
 * README</a>). We use {@link com.google.gwt.junit.tools.GWTTestSuite} and the
 * <a href="http://mojo.codehaus.org/gwt-maven-plugin/user-guide/testing.html">
 * naming conventions provided by the gwt-maven-plugin</a> to specify how and
 * when tests should be run.
 * </p>
 * <p>
 * For example, here is the test for
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonActionHelperImpl}
 * :
 * </p>
 * 
 * <pre>
 * <code class=java>
 *      public class GetLatLonActionHelperImplTestGwt extends GWTTestCase {
 *         
 *         {@literal @}Override
 *         public String getModuleName() {
 *             return "mx.org.pescadormvp.examples.jsonp.JSONPExample";
 *         }
 *     
 *         public void testInstantiateResultWithData() {
 *             GetLatLonActionHelperImpl helper = new GetLatLonActionHelperImpl();
 *             GetLatLonResult result = helper.insantiateResult(getJSObjectWihData());
 *             assertTrue(result.hasData());
 *             assertEquals(result.getDisplayName(), "Somewhere, someplace");
 *             assertEquals(result.getLat(), 45.5224507);
 *             assertEquals(result.getLon(), -73.5912827);
 *         }
 *         
 *         public void testInstantiateResultWithoutData() {
 *             GetLatLonActionHelperImpl helper = new GetLatLonActionHelperImpl();
 *             GetLatLonResult result = helper.insantiateResult(getJSObjectWithoutData());
 *             assertFalse(result.hasData());
 *         }
 *         
 *         private final native JavaScriptObject getJSObjectWihData()  /*-{
 *             return [
 *                       {
 *                           "display_name": "Somewhere, someplace",
 *                           "lat": "45.5224507",
 *                           "lon": "-73.5912827",
 *                       }
 *                    ];
 *         }-*{@literal /};
 *         
 *         private final native JavaScriptObject getJSObjectWithoutData()  /*-{
 *             return [ ];
 *         }-*{@literal /};
 *     }
 * </code>
 * </pre>
 * <p>
 * This test requires a browser because it verifies that a native Javascript
 * object is correctly converted to a GWT Java object. (In fact, Maven doesn't
 * run it in a real browser, but in <a
 * href="http://htmlunit.sourceforge.net/">HtmlUnit</a>, a gui-less, simulated
 * browser for testing Java-based Web applications.)
 * </p>
 * <p>
 * The test for
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryActivityImpl}, on
 * the other hand, is JVM-only. For this kind of test, the example app uses <a
 * href="https://github.com/ArcBees/Jukito">Jukito</a>, which provides <a
 * href="http://code.google.com/p/mockito/">Mockito</a> and DI support for tests
 * in GWT. Here's a fragment of that test:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}Test
 *     public void testNoSuchPlace() {
 *         startTestActivity(true, true, false);
 * 
 *         InOrder inOrder = inOrder(queryView);
 *         inOrder.verify(queryView).scheduleLoadingMessage();
 *         inOrder.verify(queryView).renderNoSuchPlace();
 *     }
 *     
 *     private QueryActivity startTestActivity(
 *             boolean placeHasData,
 *             boolean jsonpCallSucceeds,
 *             boolean resultHasData) {
 * 
 *         QueryActivity activity = activitiesFactory.create();
 * 
 *         QueryPlace mockedPlace = placeHasData ? mockedPlaceWithData : mockedPlaceEmpty;
 *         activity.setPlace(mockedPlace);
 *         
 *         MockDataManager mockDataManager = (MockDataManager) dataManager;
 *         mockDataManager.setSucceed(jsonpCallSucceeds);
 *         GetLatLonResult result = resultHasData ? resultWithData : resultWithoutData;  
 *         mockDataManager.setResult(result);
 *         
 *         activity.start(panel, eventBus);
 *         return activity;
 *     }
 * </code>
 * </pre>
 * <p>
 * Here, <code>testNoSuchPlace()</code> requests a test activity with a
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace} containing
 * location information and a mock
 * {@link mx.org.pescadormvp.core.client.data.DataManager} that will appear to
 * perform a successful JSONP call and return a
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonResult} with
 * no data. (A dataless result means the server couldn't find a place with the
 * name indicated.) Then it uses Mockito's {@link org.mockito.InOrder} to verify
 * that the activity sets up the view as expected.
 * </p>
 * <p>
 * See the source code for more test examples.
 * </p>
 */
package mx.org.pescadormvp.examples.jsonp.client;