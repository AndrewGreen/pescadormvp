/**
 * <h3><a name="introduction"/>Introduction</h3>
 * <p>
 * JSONP Example is a sample application that demonstrates basic Pescador MVP
 * usage.
 * </p>
 * <p>
 * The JSONP Example application asks the user to enter a place name. Then, it
 * fetches the place's latitude and longitude via a JSONP request to
 * OpenStreetMap, and displays a map using OpenLayers. <a href=
 * "http://andrewgreen.github.io/pescadormvp/site/examples/jsonp/live/index.html"
 * >Here it is</a>, compiled and running in your browser.
 * </p>
 * <p>
 * Here's how it works.
 * </p>
 * <h3><a name="component"/>The Query Component</h3>
 * <p>
 * In Pescador MVP, you create a component by grouping some classes together in
 * a package, and making one of those classes implement the
 * {@link mx.org.pescadormvp.core.client.components.Component} interface.
 * </p>
 * <p>
 * The application is so simple that it has only one component: "Query". All the
 * classes that are part of this component are in the
 * {@link mx.org.pescadormvp.examples.jsonp.client.query
 * ...examples.jsonp.client.query} package.
 * </p>
 * <p>
 * Since we're using dependency injection, we want to define variables using
 * only interfaces as much as possible. So almost every concrete class has a
 * corresponding interface, which is used in variable definitions. Interfaces
 * have nearly the same name as the concrete classes that implement them (the
 * difference is just that concrete class names have an "Impl" on the end).
 * </p>
 * <p>
 * Here are the Query component's interface-implementation pairs:
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
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryPlace.html"
 * > QueryPlace</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryPlaceImpl.html"
 * > QueryPlaceImpl</a></td>
 * <td class="colOne"><div class="block">The place associated with this
 * component.</div></td>
 * </tr>
 * <tr class="rowColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryActivity.html"
 * > QueryActivity</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryActivityImpl.html"
 * > QueryActivityImpl</a></td>
 * <td class="colOne"><div class="block">The activity associated with this
 * component and the main (and only) region of the UI.</div></td>
 * </tr>
 * <tr class="altColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryView.html"
 * > QueryView</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/QueryViewImpl.html"
 * > QueryViewImpl</a></td>
 * <td class="colOne"><div class="block">The view associated with this component
 * and the main (and only) region of the UI.</div></td>
 * </tr>
 * <tr class="rowColor">
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/OSMMap.html"
 * > OSMMap</a></td>
 * <td class="colFirst"><a href=
 * "../../../../../../mx/org/pescadormvp/examples/jsonp/client/query/OSMMapImpl.html"
 * > OSMMapImpl</a></td>
 * <td class="colOne"><div class="block">The map widget.</div></td>
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
 * takes care of everything of general interest to the Query Component.
 * </p>
 * <p>
 * Like all components in Pescado MVP, Query Component must include a class that
 * implements {@link mx.org.pescadormvp.core.client.components.Component}.
 * </p>
 * <p>
 * However, Query Component is not just an ordinary component&mdash;it's a
 * "place-activity-view" (PAV) component. That is, it's a special kind of
 * component that associates a place class with one or more activity classes,
 * view classes, and regions of the UI. PAV components must include a class that
 * implements
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * (a subinterface of
 * {@link mx.org.pescadormvp.core.client.components.Component}).
 * </p>
 * <p>
 * The {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent}
 * interface is a subinterface of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * . So
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl} ,
 * by implementing
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
 *             RawDefaultPlaceProvider
 * </code>
 * </pre>
 * <p>
 * As you can see,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponent} fills
 * in the generic parameters of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
 * , which is defined as follows:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public interface PescadorMVPPAVComponent&lt;
 *             I extends PescadorMVPPAVComponent&lt;I,P>, // component's public interface
 *             P extends PescadorMVPPlace>             // place
 *             extends Component&lt;I>
 * </code>
 * </pre>
 * <p>
 * Now let's look at the implementation,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}:
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
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * extends
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponentBase}
 * , as should all PAV components. By doing so, they gain access to many
 * convenience methods for dealing with places, activities and UI regions.
 * </p>
 * <p>
 * What actual logic goes in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}?
 * First, according to Pescador MVP convention, if a component provides any
 * extra functionality to other parts of the system, that functionality should
 * be made available via the class that implements
 * {@link mx.org.pescadormvp.core.client.components.Component} (which is
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}).
 * In this case, only one method of this sort is offered,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl#getRawDefaultPlace()
 * getRawDefaultPlace()}, which provides the application's default place. (More
 * about this in a minute.)
 * </p>
 * <p>
 * Second, anything of general interest to the component should also be placed
 * in the class that implements
 * {@link mx.org.pescadormvp.core.client.components.Component}. Let's see what
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * does in this regard.
 * </p>
 * <p>
 * Importantly, it contains a static inner class that implements
 * {@link com.google.gwt.inject.client.GinModule} and sets all the dependency
 * injection bindings for the component:
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
 * Dependency injection-related code is completed by another static inner class,
 * a place {@link com.google.inject.Provider}:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     public static class QueryPlaceProvider
 *         extends PescadorMVPPlaceProvider&lt;QueryPlace> {
 *         
 *         {@literal @}Override
 *         public QueryPlace get() {
 *             return new QueryPlaceImpl();
 *         }
 *     }
 * </code>
 * </pre>
 * <p>
 * More general setup of the component is performed in the constructor:
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}Inject
 *     public QueryComponentImpl(
 *             QueryPlaceProvider queryPlaceProvider,
 *             ActivitiesFactory&lt;QueryPlace, QueryActivity> activitiesFactory,
 *             GetLatLonActionHelper actionHelper,
 *             Session session,
 *             DataManager dataManager) {
 *         
 *         // send some stuff to the superclass
 *         super(
 *                 QueryComponent.class, // component's public interface
 *                 "query",              // main token for this place in URL fragment
 *                 queryPlaceProvider,   // place provider
 *                 QueryPlace.class,     // place class
 *                 session               // interface for session component
 *                 );
 * 
 *         this.actionHelper = actionHelper;
 *         this.dataManager = dataManager;
 *         
 *         // Here we establish that this component has an activity for the
 *         // Layout.Body screen region (in this app, that's the only region).
 *         addRegionAndActivitiesFactory(Body.class, activitiesFactory);
 *     }
 * </code>
 * </pre>
 * <p>
 * Finally, there is one task that
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl}
 * performs after all the components have been registered, but before the
 * interface actually starts up: it registers a
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper} with the
 * {@link mx.org.pescadormvp.core.client.data.DataManager} (more about
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper}s below).
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     {@literal @}Override
 *     public void finalizeSetup() {
 *         dataManager.registerActionHelper(actionHelper);
 *     }
 * </code>
 * </pre>
 * <p>
 * ({@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponentBase#finalizeSetup()
 * finalizeSetup()} is called after components have been registered. There's no
 * need to override it if a component doesn't have to do anything special at
 * that time.)
 * </p>
 * 
 * <pre>
 * <code class=java>
 * </code>
 * </pre>
 */
package mx.org.pescadormvp.examples.jsonp.client;

