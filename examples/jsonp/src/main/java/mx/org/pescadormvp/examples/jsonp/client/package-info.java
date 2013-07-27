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
 * Here's how it works.
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
 * <li><a href="#generalsetup">General Setup</a></li>
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
 * Since we're using dependency injection, we want to define variables using
 * only interfaces as much as possible. So the public methods of almost every
 * concrete class are set out in a corresponding interface, which is used in
 * variable definitions. Interfaces have nearly the same name as the concrete
 * classes that implement them (the difference is just that concrete class names
 * have an "Impl" on the end).
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
 * <td class="colOne"><div class="block">The activity associated with this
 * component and the main (and only) region of the UI.</div></td>
 * </tr>
 * <tr class="rowColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryView.html"
 * > QueryView</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryViewImpl.html"
 * > QueryViewImpl</a></td>
 * <td class="colOne"><div class="block">The view associated with this component
 * and the main (and only) region of the UI.</div></td>
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
 * Let's look at each of these pairs, one by one.
 * </p>
 * <h3><a name="generalqcstuff"/>General Query Component Stuff</h3>
 * <p>
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * takes responsibility for everything of general interest to the Query
 * Component. One such responsibility is the implementation
 * {@link mx.org.pescadormvp.core.client.components.Component}&mdash;Pescado MVP
 * requires that all components include an implementation of that interface.
 * </p>
 * <p>
 * However, Query Component is not just an ordinary component&mdash;it's a
 * "place-activity-view" (PAV) component. That is, it's a special kind of
 * component that associates a place class with one or more activity classes,
 * view classes, and regions of the UI. Pescado MVP requires that PAV components
 * include an implementation of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * (a subinterface of
 * {@link mx.org.pescadormvp.core.client.components.Component}).
 * </p>
 * <p>
 * The {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}
 * interface is a subinterface of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * . So
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}, by
 * implementing
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}, also
 * implements
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * and {@link mx.org.pescadormvp.core.client.components.Component}.
 * </p>
 * <p>
 * Here is the definition of
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public interface QueryComponent extends PescadorMVPPAVComponent&lt;
 *             QueryComponent, 
 *             QueryPlace>,
 *             RawDefaultPlaceProvider { }
 * </code>
 * </pre>
 * <p>
 * As you can see,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent} fills
 * in the generic parameters of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * . (For now, don't worry about the reference to
 * {@link mx.org.pescadormvp.core.client.placesandactivities.RawDefaultPlaceProvider}
 * &mdash;it'll be explained in a moment.) The opening line of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * goes like this:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public interface PescadorMVPPAVComponent&lt;
 *             I extends PescadorMVPPAVComponent&lt;I,P>, // component's interface
 *             P extends PescadorMVPPlace>             // place
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
 *     public class QueryComponentImpl extends PescadorMVPPAVComponentBase&lt; 
 *             QueryComponent,
 *             QueryPlace>
 *             implements
 *             QueryComponent
 * </code>
 * </pre>
 * <p>
 * By extending
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponentBase}
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
 * about this in a minute.)
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
 *             // bind the enclosing class
 *             bind(QueryComponent.class).
 *                     to(QueryComponentImpl.class).in(Singleton.class);
 * 
 *             // bind place provider
 *             bind(QueryPlaceProvider.class).in(Singleton.class);
 *             
 *             // create activities factory
 *             install(new GinFactoryModuleBuilder().implement(
 *                     QueryActivity.class, QueryActivityImpl.class)
 *                     .build(
 *                     new TypeLiteral&lt;
 *                     ActivitiesFactory&lt;QueryPlace, QueryActivity>>(){}));
 *             
 *             // bind view
 *             bind(QueryView.class).to(QueryViewImpl.class)
 *                     .in(Singleton.class);
 *             
 *             // bind jsonp action helper
 *             bind(GetLatLonActionHelper.class)
 *                     .to(GetLatLonActionHelperImpl.class)
 *                     .in(Singleton.class);
 *             
 *             // bind OSMMap
 *             bind(OSMMap.class).to(OSMMapImpl.class).in(Singleton.class);
 *         }
 *     }
 * </code>
 * </pre>
 * <p>
 * DI-related code is completed by another static inner class, a place
 * {@link com.google.inject.Provider}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public static class QueryPlaceProvider
 *         extends PescadorMVPRawPlaceProvider&lt;QueryPlace> {
 *         
 *         {@literal @}Override
 *         public QueryPlace get() {
 *             return new QueryPlaceImpl();
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
 *             QueryPlaceProvider queryPlaceProvider,
 *             ActivitiesFactory&lt;QueryPlace, QueryActivity> activitiesFactory,
 *             GetLatLonActionHelper actionHelper,
 *             DataManager dataManager) {
 *         
 *         // send the place provider to the superclass
 *         super(queryPlaceProvider);
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
 * Here, we tell the superclass about our place provider, and link the UI region
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body
 * Layout.Body} to the activities factory we created in our
 * {@link com.google.gwt.inject.client.GinModule} (see above). This provides the
 * framework with everything it needs to activate the
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
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper}s below).
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
 *         // Attach the view to the container for this region of the layout.
 *         // (In this example there's only one region.)
 *         QueryView view = getView(); 
 *         contaier.setWidget(view);
 *         ...
 * </code>
 * </pre>
 * <p>
 * The only unusual thing here is that we conveniently have access to our view
 * via the
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityBase#getView()
 * getView()} method.
 * </p>
 * <p>
 * Standard Pescador MVP fare is to have the activity push to and control the
 * view, not vice versa. The view doesn't do anything except control UI details
 * and fire off events.
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
 *                 ActivateInternalLinkEvent.TYPE,
 *                 view, this);
 * 
 *          doQuery();
 *      }
 * </code>
 * </pre>
 * <p>
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryView} has five
 * renderable states:
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
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace}
 * instance, available via the
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase#getPlace()
 * getPlace()} method. If the
 * current Place has no
 * information on this, then the activity tells the view to render an empty
 * state.
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
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryPlace} <i>does</i>
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
 *                     if (result.isValid()) {
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
 * messages it needs to do this. If the Lat-lon state is to be rendered, 
 * we also do a bit
 * of string processing, set the map location and make sure
 * the view has the required map widget (
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.OSMMap}&mdash; see
 * below).
 * </p>
 * <p>
 * In truth, there's not much extraordinary about this activity. The most
 * significant things about it are: we get everything we need via DI and methods
 * provided by the superclass, push to the view and handle events from it, and
 * send requests via a handy
 * {@link mx.org.pescadormvp.core.client.data.DataManager} that uses the command
 * pattern (more on that below).
 * </p>
 * <h3><a name="queryview"/>The Query View</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * <h3><a name="map"/>The Map Widget</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * <h3><a name="queryplace"/>The Query Place</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * <h3><a name="actionhelper"/>The JSONP Action Helper</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * <h3><a name="layout"/>Application Layout</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * <h3><a name="generalsetup"/>General Setup</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * <h3><a name="tests"/>Tests</h3>
 * <p>
 * <i>[TODO]</i>
 * </p>
 * 
 * <pre>
 * <code class=java>
 * </code>
 * </pre>
 */
package mx.org.pescadormvp.examples.jsonp.client;

