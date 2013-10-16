package eu.inmite.android.gridwichterle.core;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 7/20/13
 * Time: 11:00 PM
 */
public class Utils {

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static int getPxFromDpi(Context context, int px) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) px, context.getResources().getDisplayMetrics());
	}

}
