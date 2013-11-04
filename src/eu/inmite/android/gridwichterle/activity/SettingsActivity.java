package eu.inmite.android.gridwichterle.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import eu.inmite.android.gridwichterle.R;
import eu.inmite.android.gridwichterle.bus.BusProvider;
import eu.inmite.android.gridwichterle.bus.CancelGridBus;
import eu.inmite.android.gridwichterle.bus.ColorChangeBus;
import eu.inmite.android.gridwichterle.bus.GridOnOffBus;
import eu.inmite.android.gridwichterle.core.Config;
import eu.inmite.android.gridwichterle.core.Constants;
import eu.inmite.android.gridwichterle.core.Utils;
import eu.inmite.android.gridwichterle.dialogs.ColorsDialog;
import eu.inmite.android.gridwichterle.services.GridOverlayService;

import butterknife.InjectView;
import butterknife.Views;
import com.squareup.otto.Subscribe;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl
 * Date: 21.10.13
 * Time: 20:10
 */
public class SettingsActivity extends FragmentActivity {

	@InjectView(R.id.txtGridSize)
	public TextView txtGridSize;
	@InjectView(R.id.txtVersion)
	public TextView txtVersion;
	@InjectView(R.id.txtSendFeedback)
	public TextView txtSendFeedback;
	@InjectView(R.id.txtTheCode)
	public TextView txtTheCode;
	@InjectView(R.id.seekBar)
	public SeekBar seekBar;
	@InjectView(R.id.layoutColor)
	public RelativeLayout layoutColor;
	@InjectView(R.id.viewColor)
	public View viewColor;
	@InjectView(R.id.chckFullScreen)
	public CheckedTextView chckFullScreen;
	@InjectView(R.id.layoutDevelopers)
	public LinearLayout layoutDevelopers;
	@InjectView(R.id.gridSwitch)
	public Switch switchGrid;

	private Config mConfig;


	public static void call(Context context) {
		Intent intent = new Intent(context, SettingsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		Views.inject(this);

		Intent intent = new Intent(this, GridOverlayService.class);
		startService(intent);

		setupViews();
	}

	private void setupViews() {

		mConfig = (Config)getApplicationContext().getSystemService(Config.class.getName());
		final String seekBarString = getString(R.string.settings_seek_bar);

		chckFullScreen.setChecked(mConfig.isFullScreenModeActivated());
		chckFullScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (chckFullScreen.isChecked()) {
					chckFullScreen.setChecked(false);
					mConfig.setFullScreenMode(false);
				} else {
					chckFullScreen.setChecked(true);
					mConfig.setFullScreenMode(true);
				}
				applyNow();
			}
		});

		layoutDevelopers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Utils.openBrowser(SettingsActivity.this, "https://plus.google.com/110778431549186951626");
			}
		});

		txtTheCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Utils.openBrowser(SettingsActivity.this, "https://github.com/inmite/android-grid-wichterle");
			}
		});

		txtSendFeedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Utils.sendEmail(SettingsActivity.this, "android@inmite.eu");
			}
		});

		txtVersion.setText(getVersionName(this));

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
				txtGridSize.setText(String.format(seekBarString, Integer.toString(progress + 4)));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mConfig.setGridSideSize(seekBar.getProgress() + 4);
				applyNow();
			}
		});

		seekBar.setProgress(mConfig.getGridSideSize());
		txtGridSize.setText(String.format(seekBarString, Integer.toString(mConfig.getGridSideSize())));

		layoutColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ColorsDialog.show(getSupportFragmentManager());
			}
		});

		viewColor.setBackgroundColor(mConfig.getColor());

		switchGrid.setChecked(GridOverlayService.sIsGridShown);
		switchGrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {
				if (switchOn) {
					BusProvider.getInstance().post(new GridOnOffBus(GridOnOffBus.ACTION_GRID_ON));
				} else {
					BusProvider.getInstance().post(new GridOnOffBus(GridOnOffBus.ACTION_GRID_OFF));
				}
			}
		});

	}

	private void applyNow() {
		if (switchGrid.isChecked()) {
			BusProvider.getInstance().post(new GridOnOffBus(GridOnOffBus.ACTION_GRID_OFF));
			BusProvider.getInstance().post(new GridOnOffBus(GridOnOffBus.ACTION_GRID_ON));
		}
	}

	private String getVersionName(Context ctx) {
		try {
			ComponentName comp = new ComponentName(ctx, ctx.getClass());
			PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return "unknown";
		}
	}

	@Subscribe
	public void changeColor(ColorChangeBus colorChangeBus) {
		viewColor.setBackgroundColor(mConfig.getColor());
		applyNow();
	}

	@Subscribe
	public void cancelGrid(CancelGridBus cancelGridBus) {
		Log.d(Constants.TAG, "GridOverlayService.CancelGrid()");
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();
		BusProvider.getInstance().register(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BusProvider.getInstance().unregister(this);
	}
}
