package eu.inmite.android.gridwichterle.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import eu.inmite.android.gridwichterle.bus.CancelGridBus;
import eu.inmite.android.gridwichterle.bus.BusProvider;
import eu.inmite.android.gridwichterle.bus.SettingsOnOffBus;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 7/21/13
 * Time: 9:00 PM
 */
public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();

		if ("notification_cancelled".equals(action)) {
			BusProvider.getInstance().post(new CancelGridBus());
		}

		if ("notification_settings".equals(action)) {
			Log.d(Constants.TAG, "Settings on");
			BusProvider.getInstance().post(new SettingsOnOffBus(SettingsOnOffBus.ACTION_SETTINGS_ON));
		}

		if ("notification_settings_off".equals(action)) {
			BusProvider.getInstance().post(new SettingsOnOffBus(SettingsOnOffBus.ACTION_SETTINGS_OFF));
		}
	}


}
