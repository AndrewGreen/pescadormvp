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
 * Bindings for all the classes in the component are set in the
 * component's {@link com.google.gwt.inject.client.GinModule},
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
 * references to implementations, as shown in the example.
 * Incidentally, the example also shows how to use <a
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
 * You can also ask the superclass to fetch any external javascript libraries
 * you may need, and to make sure they're injected before the rest of the
 * application actually starts up.
 * </p>
 * <p>
 * Finally, in your GWT entry point class, you simply follow standard GIN
 * protocol and instantiate the {@link com.google.gwt.inject.client.Ginjector}
 * you declared. You use the {@link com.google.gwt.inject.client.Ginjector} to
 * get an instance of your subclass of
 * {@link mx.org.pescadormvp.core.client.components.ComponentSetup}, and call
 * its {@link mx.org.pescadormvp.core.client.components.ComponentSetup#start()
 * start()} method.
 * </p>
 * <p>
 * And that's that! Your layout widget will be attached to the DOM and your app
 * will go to the default place (or to whatever place is described in the URL
 * fragment identifier in the browser's location bar).
 * </p>
 * <h3>Places</h3> <h3><a name="layout" />Layout</h3>
 * <p>
 * Pescacaor MVP does <b>not</b> include a widget for laying out your app.
 * Rather, it provides some interfaces that define behavior that your
 * layout widget should implement. It does make some basic assumptions about
 * your application's layout, namely that it has regions. However, it does not
 * assume that the regions' dimensions will remain static, or that all regions
 * must always be associated with a view and an activity. It also makes
 * allowances for UI elements that are not visually contained within a region (though
 * such elements <b>should</b> be <b>logically</b> part of a view).
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
 * ). You can set up a container activity and view (that <b>are</b> be
 * associated with a place) to manage the embedded activities and views.
 * </p>
 */
package mx.org.pescadormvp.core;

