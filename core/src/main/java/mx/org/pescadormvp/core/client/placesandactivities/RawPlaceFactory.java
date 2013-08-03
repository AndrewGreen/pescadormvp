package mx.org.pescadormvp.core.client.placesandactivities;

import com.google.gwt.inject.client.GinModule;


/**
 * <p>
 * Used to create {@link PescadorMVPPlace}s the Guice
 * way.
 * </p>
 * <p>
 * The following example, from
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule
 * QueryGinModule}, shows how to bind a {@link PescadorMVPPlace} to its
 * implementation using this class.
 * </p>
 * 
 * <pre>
 * <code class=java>
 *         install(new GinFactoryModuleBuilder().implement(
 *                 QueryPlace.class, QueryPlaceImpl.class)
 *                 .build(
 *                 new TypeLiteral&lt;
 *                 RawPlaceFactory&lt;QueryPlace>>(){})); * </code>
 * </pre>
 * <p>
 * Bindings like these go in your {@link PAVComponent}s' {@link GinModule}s. See
 * {@link mx.org.pescadormvp.examples.jsonp.client
 * ...examples.jsonp.client} for more information.
 * </p>
 * 
 * @param <P> The class of places to be created by this factory.
 */
@SuppressWarnings("javadoc")
public interface RawPlaceFactory<P extends PescadorMVPPlace> {
	P create();
}
