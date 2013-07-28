/**
 * <p>
 * Pescador MVP is a modular <a
 * href="http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter"
 * >MVP</a> framework for <a
 * href="https://developers.google.com/web-toolkit/">GWT</a> that builds on <a
 * href=
 * "https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces"
 * >standard MVP-related classes provided by GWT</a>. Its features include <a
 * href="http://code.google.com/p/google-gin/">GIN-based dependency
 * injection</a> and the association of places, activities, views, UI regions
 * and components using <a
 * href="http://docs.oracle.com/javase/tutorial/java/generics/index.html"
 * >generics</a>.
 * </p>
 * <p>
 * Contents:
 * <ol>
 * <li><a href="#components">Components</a></li>
 * <li><a href="#pavcompoennts">Place-Activity-View Components</a></li>
 * <li><a href="#componentsetup">Activating Components and Starting the
 * Framework</a></li>
 * <li><a href="#places">Places</a></li>
 * <li><a href="#layout">Layout</a></li>
 * <li><a href="#avnoplace">Activities and Views Not Associated with a Place</a>
 * </li>
 * <li><a href="#session">Session</a></li>
 * <li><a href="#extras">Extras</a>
 * <ol>
 * <li><a href="#datamanager">Caching Command-Pattern Data Manager</a></li>
 * <li><a href="#internallinks">Internal Links</a></li>
 * <li><a href="#logger">Logger</a></li>
 * </ol>
 * </li>
 * </ol>
 * </p>
 * <h3><a name="components"/>Components</h3>
 * <p>
 * At its core, Pescador MVP is a set of conventions for organizing your code,
 * plus some classes that help you work with those conventions.
 * </p>
 * <p>
 * A central concept of Pescador MVP is the component. A component is a
 * collection of related classes, including a class that implements
 * {@link mx.org.pescadormvp.core.client.components.Component} and provides an
 * interface for any external services that the component may offer to other
 * components. The class that implements
 * {@link mx.org.pescadormvp.core.client.components.Component} also performs
 * tasks of general interest to the component. In addition, components provide a
 * {@link com.google.gwt.inject.client.GinModule} that sets GIN bindings for all
 * the classes in the component.
 * </p>
 * <p>
 * Normally, all the classes in a component reside in the same package, or at
 * least have a common ancestor package that is not shared with other
 * components.
 * </p>
 * <p>
 * To use the MVP pattern, you designate certain components as
 * "place-activity-view components". These special components associate a place
 * class with one or more activity classes, view classes, and regions of the UI.
 * They also include a class that implements
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PAVComponent}
 * (a subinterface of
 * {@link mx.org.pescadormvp.core.client.components.Component}). Pescador MVP
 * and GWT take care of activating activities and views in the right place at
 * the right time. If you need to share implementations and specifications
 * across places, activities, views and UI regions, you can use standard Java
 * inheritance and polymorphism. (See <a href="#pavcompoennts">below</a> for
 * more on place-activity-view components.)
 * </p>
 * <p>
 * In its present form, Pescador MVP does <b>not</b>:
 * </p>
 * <ul>
 * <li>enforce any conventions;</li>
 * <li>enforce encapsulation;</li>
 * <li>provide a way to specify dependencies among components;</li>
 * <li>provide a way to group components into larger units;</li>
 * <li>have fancy code generation to save you from reams of boilerplate; or</li>
 * <li>allow hot-swapping of components at runtime.</li>
 * </ul>
 * <p>
 * It is conceivable that these features (excluding the last one) might be
 * implemented some time in the future. But even without them, Pescador MVP can
 * help keep your client codebase nice and clean. (At least, that has been the
 * author's experience.)
 * </p>
 * <h3><a name="pavcompoennts" />Place-Activity-View Components</h3>
 * <p>
 * Place-activity-view (PAV) components associate a place class with activity
 * classes, view classes and regions of the UI. They do this through GIN,
 * generics and tag interfaces that designate UI regions.
 * </p>
 * <p>
 * The easiest way to understand this is by looking at an example. A peek at
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl} and
 * the classes and interfaces in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query
 * ...examples.jsonp.client.query} will give you a good idea of how PAV
 * components work. (See {@link mx.org.pescadormvp.examples.jsonp.client
 * ...examples.jsonp.client} for a step-by-step guide to this example. The
 * following is just a brief summary.)
 * </p>
 * <p>
 * As you can see in {@link mx.org.pescadormvp.examples.jsonp.client.query
 * ...examples.jsonp.client.query}, the single activity has an interface and an
 * implementation. The interface extends
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity}
 * and the implementation is a subclass of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase}
 * . This superclass provides methods for the activity to retrieve the correct
 * view and place instances when it starts. Note that there are interfaces
 * and/or abstract superclasses for the view and place, too.
 * </p>
 * <p>
 * To see how bindings are set up for classes in this component, look at the
 * component's {@link com.google.gwt.inject.client.GinModule},
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule
 * QueryGinModule}.
 * </p>
 * <p>
 * This PAV component has only one activity class and only one view class because, in
 * this simple example, there is only one UI region (designated with
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.Layout.Body}). For each
 * PAV component (and so for each place) there can
 * be as many activity-view pairs as there are UI regions. So in the example,
 * if there were more UI regions, there could be more activity-view pairs. 
 * </p>
 * <p>
 * To create your own PAV components, just copy the general pattern in the
 * example, then activate your components and start up the framework as
 * explained <a href="#componentsetup">below</a>, and you should be set. Behind
 * the scenes, Pescador MVP activates the standard GWT
 * {@link com.google.gwt.place.shared.PlaceController},
 * {@link com.google.gwt.place.shared.PlaceHistoryHandler} and
 * {@link com.google.gwt.activity.shared.ActivityManager}s as required.
 * </p>
 * <p>
 * Testing is facilitated through dependency injection and the avoidance of
 * references to implementations, as shown in the example. Incidentally, the
 * example also shows how to use <a
 * href="https://github.com/ArcBees/Jukito">Jukito</a> for pure Java tests and
 * {@link com.google.gwt.junit.client.GWTTestCase}s for tests that require a
 * browser context. (See the source code under examples/jsonp/src/test/java.)
 * </p>
 * <h3><a name="componentsetup" />Activating Components and Starting the
 * Framework</h3>
 * <p>
 * In Pescador MVP, you control the global configuration of your application by
 * creating a class that extends
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup} (in the
 * example app,
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveComponentSetup}).
 * Inside this class you define an interface that extends
 * {@link mx.org.pescadormvp.core.client.PescadorMVPGinjector} (which itself
 * extends {@link com.google.gwt.inject.client.Ginjector}) and has a
 * {@link com.google.gwt.inject.client.GinModules} annotation. You use the
 * annotation to declare all the {@link com.google.gwt.inject.client.GinModule}s
 * you'll activate in your application, including, of course, those provided by
 * your components.
 * </p>
 * <p>
 * In the constructor of your
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup} subclass,
 * you must send the superclass a list of the components you want to activate,
 * along with your application's general layout widget (see <a
 * href="#layout">below</a> for more about layouts).
 * </p>
 * <p>
 * Framework initialization works like this:
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup} has a static
 * method that you use to pass in your
 * {@link com.google.gwt.inject.client.Ginjector} and start everything up. You
 * can call this method directly from your GWT entry point method.
 * (Alternatively, you can do so from a static method in your subclass of
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup}, and call
 * <b>that</b> static method from the GWT entry point. See
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveComponentSetup} and
 * {@link mx.org.pescadormvp.examples.jsonp.client.JSONPExample} in the example
 * app. With this approach, you keep all general setup code inside your subclass
 * of {@link mx.org.pescadormvp.core.client.components.ComponentSetup}, and your
 * GWT entry point stays dirt-simple.)
 * </p>
 * <p>
 * Once the static startup method in
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup} is called,
 * that's that! Dependency injection will boot up, your layout widget will be
 * attached to the DOM and your app will go to the default place (or to whatever
 * place is described in the URL fragment identifier in the browser's location
 * bar).
 * </p>
 * <p>
 * Also: if you use external javascript libraries, you can ask the framework to
 * fetch them and make sure they're loaded before the application actually
 * starts up. There is also a facility for displaying "loading" messages and the
 * like while external JS libraries load.
 * </p>
 * <h3><a name="places"/>Places</h3>
 * <p>
 * Places in Pescador MVP have a few extra features that are not included in
 * plain GWT {@link com.google.gwt.place.shared.Place}s. One of them is that you
 * never have to make your own
 * {@link com.google.gwt.place.shared.PlaceTokenizer}s.
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * store information as key-value pairs that are automatically serialized to and
 * retrieved from the URL fragment identifier. They may also contain locale
 * information and presentation text (for describing places in the UI).
 * </p>
 * <p>
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * are passed around the Pescador MVP framework as interfaces, unlike standard
 * GWT {@link com.google.gwt.place.shared.Place}s, which are implementations.
 * However,
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * can always be converted to standard GWT
 * {@link com.google.gwt.place.shared.Place}s if necessary.
 * </p>
 * <h3><a name="layout" />Layout</h3>
 * <p>
 * Pescador MVP does <b>not</b> include a widget for laying out your app.
 * Rather, it provides some interfaces that define behavior that your layout
 * widget should implement. It does make some basic assumptions about your
 * application's layout, namely that it has regions. However, it does not assume
 * that the regions' dimensions will remain static, or that all regions must
 * always be visible and associated with a view and an activity. Also, it makes
 * allowances for UI elements that are not visually contained within a region
 * (though such elements should be <b>logically</b> part of a view).
 * </p>
 * <p>
 * The central requirements for a Pescador MVP layout widget are: it must
 * provide a set of interfaces that designate regions that it manages, and it
 * must provide a container widget for each region it manages. In addition, it
 * must provide an
 * {@link mx.org.pescadormvp.core.client.regionsandcontainers.RootHasFixedSetOfRegions#attach()
 * attach()} method. Whatever code attaches your layout widget to the DOM should
 * be placed in this method. It will be called just once when the framework
 * starts up.
 * </p>
 * <p>
 * For a very simple example, see
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.LayoutImpl}.
 * </p>
 * <h3><a name="avnoplace"/>Activities and Views Not Associated with a Place</h3>
 * <p>
 * It is possible to define activities and views that are not associated with a
 * specific place. This can be useful, for example, if you have a tab panel in a
 * region of your UI, and you want distinct activities and views for each tab.
 * To accomplish this, your tab activities should implement
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivity}
 * (instead of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity}
 * ) and should extend
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPActivityBase}
 * (instead of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase}
 * ). You can set up a container activity and view (that <b>are</b> associated
 * with a place) to manage the embedded activities and views. In such a
 * scenario, your container activity will probably look like a much simplified
 * version of GWT's {@link com.google.gwt.activity.shared.ActivityManager}.
 * </p>
 * <h3><a name="session"/>Session</h3>
 * <p>
 * The {@link mx.org.pescadormvp.core.client.session.Session} component has some
 * facilities for working with
 * {@link mx.org.pescadormvp.core.shared.PescadorMVPLocale}s (which are slightly
 * different from plain GWT locales). Plus, it provides a standardized way to
 * store serializable session data. This component is also for moving about: you
 * use {@link mx.org.pescadormvp.core.client.session.Session#goTo
 * Session.goTo(place)} make the app go to a new place.
 * </p>
 * <h3><a name="extras"/>Extras</h3>
 * <p>
 * Pescador MVP includes a few extra facilities that are not properly part of an
 * MVP framework, but that I've found quite useful.
 * </p>
 * <h4><a name="datamanager"/>Caching Command-Pattern Data Manager</h4>
 * <p>
 * Pescador MVP provides a data manager component for server requests following
 * the <a href="http://en.wikipedia.org/wiki/Command_pattern">command
 * pattern</a>, as implemented by <a
 * href="http://code.google.com/p/gwt-dispatch/">gwt-dispatch</a>. With this
 * pattern, your requests are encapsulated in
 * {@link net.customware.gwt.dispatch.shared.Action} objects (for example,
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonAction}) and
 * you get back {@link net.customware.gwt.dispatch.shared.Result} objects (such
 * as {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonResult})
 * that contain information provided by the server.
 * </p>
 * <p>
 * In addition to using the command pattern, Pescador MVP's data manager can
 * cache the results of server calls. (The code for this is based on <a href=
 * "http://turbomanage.wordpress.com/2010/07/12/caching-batching-dispatcher-for-gwt-dispatch/"
 * >this blog post</a> by David Chandler.) For results to be cached, the
 * corresponding action must implement the tag interface
 * {@link mx.org.pescadormvp.core.client.data.CacheableAction}. The maximum
 * cache size is configurable (look at
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveComponentSetup} to see
 * how to set it.)
 * </p>
 * <p>
 * The data manager can handle both requests to your own server and requests to
 * a third-party server (via JSONP). For requests to your own server, you must
 * configure a servlet and register an
 * {@link net.customware.gwt.dispatch.server.ActionHandler ActionHandler} as
 * described in the <a
 * href="http://code.google.com/p/gwt-dispatch/wiki/GettingStarted">gwt-
 * dispatch documentation</a>. For JSONP requests, create a
 * {@link mx.org.pescadormvp.core.client.data.JsonpActionHelper} (such as
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.GetLatLonActionHelperImpl}
 * ) and register it with the data manager (as shown in
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl#finalizeSetup
 * QueryComponentImpl}).
 * </p>
 * <h4><a name="internallinks"/>Internal Links</h4>
 * <p>
 * The {@link mx.org.pescadormvp.core.client.internallinks
 * ...core.client.internallinks} package has some utility classes for creating
 * internal links (i.e., links to places within your application). These classes
 * work well with
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * and produce native HTML hyperlinks that stay within your application when
 * clicked on normally, and can
 * also be control-clicked and right-clicked for native browser hyperlink
 * actions.
 * </p>
 * <h4><a name="logger"/>Logger</h4>
 * <p>
 * This is just an easy-to-use, injectable a wrapper for GWT's logger. An
 * example configuration is in
 * {@code examples/jsonp/src/main/resources/mx/org/pescadormvp/examples/jsonp/JSONPExample.gwt.xml}
 * .
 * </p>
 */
package mx.org.pescadormvp.core;

