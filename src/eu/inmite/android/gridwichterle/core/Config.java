package eu.inmite.android.gridwichterle.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 10/12/13
 * Time: 10:36 PM
 */
public class Config {

	private final String SHARED_CONFIG = "config";

	private Context mContext;

	public Config(Context context) {
		mContext = context;
	}

	private SharedPreferences getPrefs() {
		return mContext.getSharedPreferences(SHARED_CONFIG, Activity.MODE_PRIVATE);
	}

	public void setFullScreenMode(boolean fullScreenMode) {
		getPrefs().edit()
				.putBoolean("fullscreen", fullScreenMode)
				.apply();
	}

	public boolean isFullScreenModeActivated() {
		return getPrefs().getBoolean("fullscreen", false);
	}

	public void setGridSideSize(int side) {
		getPrefs().edit()
				.putInt("side_size", side)
				.apply();
	}

	public int getGridSideSize() {
		return getPrefs().getInt("side_size", Constants.DEFAULT_SQUARE_SIDE);
	}

	public void setAlternateGridSideSize(int side) {
		getPrefs().edit()
				.putInt("alternate_side_size", side)
				.apply();
	}

	public int getAlternateGridSideSize() {
		return getPrefs().getInt("alternate_side_size", Constants.DEFAULT_SQUARE_SIDE);
	}

	public void setTopMargin(int topMargin) {
		getPrefs().edit()
				.putInt("top_margin", topMargin)
				.apply();
	}

	public int getTopMargin() {
		return getPrefs().getInt("top_margin", Constants.DEFAULT_SQUARE_SIDE);
	}

	public void setLeftMargin(int leftMargin) {
		getPrefs().edit()
				.putInt("left_margin", leftMargin)
				.apply();
	}

	public int getLeftMargin() {
		return getPrefs().getInt("left_margin", Constants.DEFAULT_SQUARE_SIDE);
	}

	public void setColor(int color) {
		getPrefs().edit()
				.putInt("color", color)
				.apply();
	}

	public int getColor() {
		return getPrefs().getInt("color", Color.BLUE);
	}
}
