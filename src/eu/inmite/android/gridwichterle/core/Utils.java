package eu.inmite.android.gridwichterle.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
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

	/**
	 * Opens external browser, e.g. Chrome.
	 */
	public static void openBrowser(Context context, String url) {
		context.startActivity(createOpenBrowserIntent(url));
	}

	/**
	 * Creates intent for opening browser.
	 */
	public static Intent createOpenBrowserIntent(String url) {
		if (!url.startsWith("http")) {
			url = "http://" + url;
		}
		return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	}

	/**
	 * Opens e-mail client, e.g. Gmail.
	 */
	public static void sendEmail(Context context, String recipient) {
		sendEmail(context, new String[]{recipient}, null, null, null);
	}

	/**
	 * Opens e-mail client e.g. Gmail.
	 */
	public static void sendEmail(Context context, String[] recipients, String subject, String text, Uri stream) {
		context.startActivity(createSendEmailIntent(recipients, subject, text, stream));
	}

	/**
	 * Creates intent for sending e-mail.
	 */
	public static Intent createSendEmailIntent(String[] recipients, String subject, String text, Uri stream) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		if (recipients != null) {
			i.putExtra(Intent.EXTRA_EMAIL, recipients);
		}
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		if (!TextUtils.isEmpty(text)) {
			i.putExtra(Intent.EXTRA_TEXT, text);
		}
		if (stream != null) {
			i.putExtra(Intent.EXTRA_STREAM, stream);
		}
		return i;
	}

}
