/*******************************************************************************
 * Copyright 2013 Instituto de Investigaciones Dr. José María Luis Mora See
 * LICENSE.txt for redistribution conditions. D.R. 2013 Instituto de
 * Investigaciones Dr. José María Luis Mora Véase LICENSE.txt para los términos
 * bajo los cuales se permite la redistribución.
 ******************************************************************************/
package mx.org.pescadormvp.examples.jsonp.client;

import mx.org.pescadormvp.core.client.components.ComponentSetup.LoadingPleaseWait;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * Class for showing an initial loading timer while external JS resources are
 * loaded.
 * </p>
 * <p>
 * Since this class is only used before the component framework even boots up,
 * there's no sense in worrying about what component it's a
 * part of.
 * </p>
 * 
 * @author Andrew Green
 */
public class InitialLoadingTimer extends Composite implements LoadingPleaseWait {

	private RootLayoutPanel rootLayoutPanel;
	@UiField DivElement timer;
	private TimerThrob timerThrob = new TimerThrob();
	private boolean active; 
	private int THROB_ANIMATION_DURATION_MS = 2300;
	
	interface InitialLoadingTimerUiBinder extends
			UiBinder<Widget, InitialLoadingTimer> {
	}

	private static InitialLoadingTimerUiBinder uiBinder =
			GWT.create(InitialLoadingTimerUiBinder.class);
	
	public InitialLoadingTimer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void start() {
		rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.add(this);
		active = true;

		RepeatingCommand cmd = new RepeatingCommand() {

			@Override
			public boolean execute() {
				timerThrob.run(THROB_ANIMATION_DURATION_MS, timer);
				return active;
			}
		};
		
		Scheduler.get().scheduleFixedPeriod(cmd, THROB_ANIMATION_DURATION_MS + 10);
	}

	@Override
	public void finish() {
		active = false;
		timerThrob.cancel();
		rootLayoutPanel.remove(this);
	}

	private class TimerThrob extends Animation {

		@Override
		protected void onUpdate(double progress) {
			double msgOpacity = Math.min(1, 2 - Math.abs(2 - (progress * 4)));
			timer.getStyle().setOpacity(msgOpacity);
		}

		@Override
		protected void onCancel() {
			super.onCancel();
			timer.getStyle().setOpacity(0);
		}
	}
}
