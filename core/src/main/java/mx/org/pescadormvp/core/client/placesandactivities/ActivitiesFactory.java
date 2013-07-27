/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import mx.org.pescadormvp.core.client.session.StatePointer;

/**
 * <p>
 * Used by GIN to inject activities.
 * </p>
 * <p>The following example, from
 * {@link mx.org.pescadormvp.examples.jsonp.client.query.QueryComponentImpl.QueryGinModule QueryGinModule}
 * , shows how to bind a {@link PescadorMVPActivity} to its implementation using
 * an {@link ActivitiesFactory}.</p>
 * {@code
	install(new GinFactoryModuleBuilder().implement(
			QueryActivity.class, QueryActivityImpl.class)
			.build(
			new TypeLiteral< ActivitiesFactory<QueryPlace, QueryActivity>>() ));}
 * 
 * @author Andrew Green
 * @param <S>
 * @param <A>
 */
@SuppressWarnings("javadoc")
public interface ActivitiesFactory 
		<S extends StatePointer,
		A extends PescadorMVPActivity<?,S,?>> {
	 
	A create();
}
