package eu.inmite.android.gridwichterle.views;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.OpacityBar;
import eu.inmite.android.gridwichterle.R;
import eu.inmite.android.gridwichterle.bus.BusProvider;
import eu.inmite.android.gridwichterle.bus.SettingsOnOffBus;
import eu.inmite.android.gridwichterle.core.Config;
import eu.inmite.android.gridwichterle.core.Constants;
import eu.inmite.android.gridwichterle.core.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 7/20/13
 * Time: 10:33 PM
 */
public class Settings extends FrameLayout {

	private Config mConfig;

	public Settings(Context context) {
		super(context);

		mConfig = (Config) context.getApplicationContext().getSystemService(Config.class.getName());

		setupSettings();
	}

	private void setupSettings() {

		//Settings layout
		RelativeLayout.LayoutParams layoutParamsSettings = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layoutParamsSettings.setMargins(0, Utils.getStatusBarHeight(getContext()), 0, 0);
		View settingsView = inflate(getContext(), R.layout.part_settings, null);
		settingsView.setLayoutParams(layoutParamsSettings);

		final Button btnClose = (Button) settingsView.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				BusProvider.getInstance().post(new SettingsOnOffBus(SettingsOnOffBus.ACTION_SETTINGS_OFF));
			}
		});


		final EditText edtSize = (EditText) settingsView.findViewById(R.id.edtSize);
		edtSize.setText(Integer.toString(mConfig.getGridSideSize()));

		SeekBar seekBar = (SeekBar) settingsView.findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
				progress = ((int) Math.round(progress / Constants.SEEK_BAR_STEP_SIZE)) * Constants.SEEK_BAR_STEP_SIZE;
				seekBar.setProgress(progress);
				edtSize.setText(Integer.toString(progress));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		CheckBox chckFullScreen = (CheckBox) settingsView.findViewById(R.id.chckFullScreen);
		chckFullScreen.setChecked(mConfig.isFullScreenModeActivated());
		chckFullScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mConfig.setFullScreenMode(isChecked);
			}
		});

		//Setup color choose layout
		final ScrollView layoutColor = (ScrollView) settingsView.findViewById(R.id.layoutColorSettings);

		final View colorView = settingsView.findViewById(R.id.colorView);
		colorView.setBackgroundColor(mConfig.getColor());


		settingsView.findViewById(R.id.btnColor).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				layoutColor.setVisibility(VISIBLE);
			}
		});

		final ColorPicker picker = (ColorPicker) settingsView.findViewById(R.id.picker);
		picker.setOldCenterColor(mConfig.getColor());
		picker.setColor(mConfig.getColor());

		OpacityBar opacityBar = (OpacityBar) settingsView.findViewById(R.id.opacitybar);
		picker.addOpacityBar(opacityBar);

		Button btnWhite = (Button) settingsView.findViewById(R.id.btnWhite);
		btnWhite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				colorView.setBackgroundColor(Color.WHITE);
				mConfig.setColor(Color.WHITE);
				layoutColor.setVisibility(View.GONE);
			}
		});

		Button btnBlack = (Button) settingsView.findViewById(R.id.btnBlack);
		btnBlack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				colorView.setBackgroundColor(Color.BLACK);
				mConfig.setColor(Color.BLACK);
				layoutColor.setVisibility(View.GONE);
			}
		});

		Button btnSetColor = (Button) settingsView.findViewById(R.id.btnSetColor);
		btnSetColor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				layoutColor.setVisibility(View.GONE);
				mConfig.setColor(picker.getColor());
				colorView.setBackgroundColor(mConfig.getColor());
			}
		});


		Button btnGenerate = (Button) settingsView.findViewById(R.id.btnGenerate);
		btnGenerate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!TextUtils.isEmpty(edtSize.getText().toString())) {
					if (Integer.parseInt(edtSize.getText().toString()) > 0) {
						mConfig.setGridSideSize(Integer.parseInt(edtSize.getText().toString()));
					} else {
						Toast.makeText(getContext(), R.string.error_lower_size, Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getContext(), R.string.error_empty_text_size, Toast.LENGTH_SHORT).show();
				}

				BusProvider.getInstance().post(new SettingsOnOffBus(SettingsOnOffBus.ACTION_SETTINGS_OFF));
			}
		});

		settingsView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() != KeyEvent.ACTION_DOWN)
					return true;

				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						if (layoutColor.getVisibility() == VISIBLE) {
							layoutColor.setVisibility(GONE);
						} else {
							BusProvider.getInstance().post(new SettingsOnOffBus(SettingsOnOffBus.ACTION_SETTINGS_OFF));
						}
						break;

				}

				return true;
			}
		});

		edtSize.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				//This is the filter
				if (event.getAction() != KeyEvent.ACTION_DOWN)
					return true;

				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						if (layoutColor.getVisibility() == VISIBLE) {
							layoutColor.setVisibility(GONE);
						} else {
							BusProvider.getInstance().post(new SettingsOnOffBus(SettingsOnOffBus.ACTION_SETTINGS_OFF));
						}
						break;

				}

				return true;
			}
		});

		this.addView(settingsView);
	}
}
