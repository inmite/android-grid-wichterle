package eu.inmite.android.gridwichterle.bus;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 10/13/13
 * Time: 7:05 PM
 */
public class SettingsOnOffBus {

	public static final int ACTION_SETTINGS_ON = 0;
	public static final int ACTION_SETTINGS_OFF = 1;

	private int mAction;

	public SettingsOnOffBus(int action) {
		mAction = action;
	}

	public int getAction() {
		return mAction;
	}
}
