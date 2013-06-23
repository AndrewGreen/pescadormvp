/**
 * <p>
 * Pescador MVP is a modular <a
 * href="http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter"
 * >MVP</a> framework for <a
 * href="https://developers.google.com/web-toolkit/">GWT</a> that builds on <a
 * href=
 * "https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces"
 * >standard MVP-related classes provided by GWT</a>. Its main features include
 * <a href="http://code.google.com/p/google-gin/">GIN-based dependency
 * injection</a> and the association of places, activities, views, UI regions
 * and components using <a
 * href="http://docs.oracle.com/javase/tutorial/java/generics/index.html"
 * >generics</a>.
 * </p>
 * <h3>Components</h3>
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
 * class, an activity class, a view class, and a region of the UI, and include a
 * class that implements
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPAVComponent}
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
 * It is conceivable that some of these features (excluding the last one) might
 * be implemented some time in the future. But even without them, Pescador MVP
 * can help keep your client codebase nice and clean. (At least, that has been
 * the author's experience.)
 * </p>
 * <h3><a name="pavcompoennts" />Place-Activity-View Components</h3>
 * <p>
 * Place-activity-view (PAV) components associate a place class, an activity
 * class, a view class and a region of the UI. They do this through GIN,
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
 * ...examples.jsonp.client.query}, the activity has an interface and an
 * implementation. The interface extends
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivity}
 * and the implementation is a subclass of
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlaceActivityBase}
 * . This superclass provides methods for the activity to retrieve the correct
 * view and place instances when it starts. Note that there are interfaces
 * and/or abstract superclasses for the view and place, too.
 * </p>
 * <p>
 * Bindings for all the classes in the component are set in the component's
 * {@link com.google.gwt.inject.client.GinModule},
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule
 * QueryGinModule}.
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
 * <b>that</b> static method from the GWT entry point. With this approach, you
 * keep all general setup code inside your subclass of
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup}, and your
 * GWT entry point stays dirt-simple. See
 * {@link mx.org.pescadormvp.examples.jsonp.client.ActiveComponentSetup} and
 * {@link mx.org.pescadormvp.examples.jsonp.client.JSONPExample} in the example
 * app.)
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
 * starts up.
 * </p>
 * <h3>Places</h3>
 * <p>
 * Places in Pescador MVP have a few extra features that are not included in
 * plain GWT {@link com.google.gwt.place.shared.Place}s. Plus, with Pescador
 * MVP, you never have to make your own
 * {@link com.google.gwt.place.shared.PlaceTokenizer}s.
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * store information as key-value pairs that are automatically serialized to and
 * retrieved from the URL fragment identifier. They may also contain locale
 * information and presentation text (for describing the place in the UI).
 * </p>
 * <p>
 * {@link mx.org.pescadormvp.core.client.placesandactivities.PescadorMVPPlace}s
 * are passed around the Pescador MVP framework as interfaces, unlike standard
 * GWT {@link com.google.gwt.place.shared.Place}, which are implementations.
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
 * attach()} method. This method is normally where you attach your layout widget
 * to the DOM. It will be called just once when the framework starts up.
 * </p>
 * <p>
 * For a very simple example, see
 * {@link mx.org.pescadormvp.examples.jsonp.client.layout.LayoutImpl}.
 * </p>
 * <h3>Activities and Views Not Associated with a Place</h3>
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
 * ). You can set up a container activity and view (that <b>are</b>
 * associated with a place) to manage the embedded activities and views. In such
 * a case, your container activity will probably look like a much
 * simplified version of GWT's
 * {@link com.google.gwt.activity.shared.ActivityManager}.
 * </p>
 * <h3>Session Data</h3>
 * <h3>Extras</h3>
 * <p>
 * Pescador MVP includes a few extra facilities that are not properly part of an
 * MVP framework, but that I've found quite useful.
 * </p>
 * <h5>Caching Command-Pattern Data Manager</h5>
 * <h5>Internal Links</h5>
 * <h5>Logger</h5> 
 */
package mx.org.pescadormvp.core;

