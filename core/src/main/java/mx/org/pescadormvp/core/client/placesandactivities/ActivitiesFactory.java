/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import com.google.gwt.inject.client.GinModule;

import mx.org.pescadormvp.core.client.session.StatePointer;

/**
 * <p>
 * Used to create {@link PescadorMVPActivity PescadorMVPActivities} the Guice
 * way.
 * </p>
 * <p>
 * The following example, from
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule
 * QueryGinModule}, shows how to bind a {@link PescadorMVPActivity} to its
 * implementation using an {@link ActivitiesFactory}.
 * </p>
 * 
 * <pre>
 * <code class=java>
 *     install(new GinFactoryModuleBuilder().implement(
 *             QueryActivity.class, QueryActivityImpl.class)
 *             .build(
 *             new TypeLiteral&lt;ActivitiesFactory&lt;QueryPlace, QueryActivity>>() ));
 * </code>
 * </pre>
 * <p>
 * Bindings like these go in your {@link PAVComponent}s' {@link GinModule}s.
 * Then you inject {@link ActivitiesFactory ActivitiesFactories} in the
 * constructor of your {@link PAVComponent}s, and call
 * {@link PAVComponentBase#addRegionAndActivitiesFactory(Class, ActivitiesFactory)
 * addRegionAndActivitiesFactory(...)} to associated them with a UI region. See
 * {@link mx.org.pescadormvp.examples.jsonp.client ...examples.jsonp.client} for
 * more information.
 * </p>
 * 
 * @param <S>
 *            The class of {@link StatePointer} (often a subinterface of
 *            {@link PescadorMVPPlace}) for this activity.
 * @param <A>
 *            The class of {@link PescadorMVPActivity} created by the factory.
 */
@SuppressWarnings("javadoc")
public interface ActivitiesFactory<S extends StatePointer, A extends PescadorMVPActivity<?, S, ?>> {

	/**
	 * Create a new activity. 
	 */
	A create();
}
