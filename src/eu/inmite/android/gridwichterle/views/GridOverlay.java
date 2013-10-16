package eu.inmite.android.gridwichterle.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import eu.inmite.android.gridwichterle.R;
import eu.inmite.android.gridwichterle.core.Config;
import eu.inmite.android.gridwichterle.core.Utils;


/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 7/15/13
 * Time: 11:13 PM
 */
public class GridOverlay extends RelativeLayout {



	public GridOverlay(Context context) {
		super(context);
		this.setId(R.id.grid_overlay);
		setupView(getDisplayHeight(context), getDisplayWidth(context));
	}

	private void setupView(int height, int width) {

		Config config = (Config)getContext().getApplicationContext().getSystemService(Config.class.getName());

		RelativeLayout.LayoutParams gridOverlayParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

		FrameLayout mGridHolder = new FrameLayout(getContext());
		if(!config.isFullScreenModeActivated()) {
			mGridHolder.setPadding(0, Utils.getStatusBarHeight(getContext()), 0, 0);
		}

		mGridHolder.setLayoutParams(gridOverlayParams);
		this.addView(mGridHolder);

		//DrawView
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		DrawView mDrawView = new DrawView(getContext(), height, width);
		mDrawView.setLayoutParams(layoutParams);

		mGridHolder.addView(mDrawView);
	}


	public static void show(Activity activity) {
		GridOverlay gridOverlay = new GridOverlay(activity);
		((ViewGroup) activity.getWindow().getDecorView().getRootView()).addView(gridOverlay);
	}

	public static boolean isVisible(Activity activity) {
		View view = (activity.getWindow().getDecorView().getRootView()).findViewById(R.id.grid_overlay);
		if (view == null) {
			return false;
		} else {
			return true;
		}
	}

	public static void remove(Activity activity) {
		View view = (activity.getWindow().getDecorView().getRootView()).findViewById(R.id.grid_overlay);
		if (view != null) {
			((ViewGroup) activity.getWindow().getDecorView().getRootView()).removeView(view);
		}
	}

	private int getDisplayHeight(Context context) {
		Point size = new Point();
		final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(size);
		return size.y;
	}

	private int getDisplayWidth(Context context) {
		Point size = new Point();
		final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(size);
		return size.x;
	}

}
