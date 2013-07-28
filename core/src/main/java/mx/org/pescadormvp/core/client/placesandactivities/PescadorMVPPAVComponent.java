/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * See LICENSE.txt for redistribution conditions.
 * 
 * D.R. 2013 Instituto de Investigaciones Dr. José María Luis Mora
 * Véase LICENSE.txt para los términos bajo los cuales se permite
 * la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.core.client.placesandactivities;

import java.util.Set;

import mx.org.pescadormvp.core.client.components.Component;
import mx.org.pescadormvp.core.client.regionsandcontainers.ForRegionTag;
import mx.org.pescadormvp.core.client.session.SessionData;

public interface PescadorMVPPAVComponent<
		// public interface offered as a component
		I  extends PescadorMVPPAVComponent<I,P>,
		
		// place we're binding to
		P extends PescadorMVPPlace>

		extends Component<I> {

	P getRawPlace();
	
	Set<Class<? extends ForRegionTag>> handlesRegions();

	<A extends PescadorMVPPlaceActivity<?,P,?>>
			A getActivity(Class<? extends ForRegionTag> region, P place);

	<S extends SessionData> S ensureSessionData();
	
}
