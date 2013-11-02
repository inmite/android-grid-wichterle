package eu.inmite.android.gridwichterle.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import eu.inmite.android.gridwichterle.R;
import eu.inmite.android.gridwichterle.bus.BusProvider;
import eu.inmite.android.gridwichterle.bus.ColorChangeBus;
import eu.inmite.android.gridwichterle.core.Config;
import eu.inmite.android.gridwichterle.core.Constants;

import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.OpacityBar;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 27.10.13
 * Time: 22:12
 */
public class ColorsDialog extends DialogFragment {

	public static Fragment show(FragmentManager fragmentManager) {
		try {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ColorsDialog fragment = (ColorsDialog)fragmentManager.findFragmentByTag("ColorDialog");

			if (fragment != null) {
				Log.v(Constants.TAG, "ColorDialog.showDialog() - found and close existing ColorDialog");
				ft.remove(fragment);
			}

			fragment = new ColorsDialog();

			fragment.show(ft, "ColorDialog");
			Log.v(Constants.TAG, "ColorDialog.showDialog() - show dialog was called");

			return fragment;
		} catch (Exception ex) {
			Log.wtf("ColorDialog.showDialog() - failed", ex);
		}
		return null;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Config config = (Config)getActivity().getApplicationContext().getSystemService(Config.class.getName());
		View view = getContentView();

		final ColorPicker picker = (ColorPicker)view.findViewById(R.id.picker);
		picker.setOldCenterColor(config.getColor());
		picker.setColor(config.getColor());

		OpacityBar opacityBar = (OpacityBar)view.findViewById(R.id.opacitybar);
		picker.addOpacityBar(opacityBar);

		Button btnWhite = (Button) view.findViewById(R.id.btnWhite);
		btnWhite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				config.setColor(Color.WHITE);
				BusProvider.getInstance().post(new ColorChangeBus());
				dismiss();
			}
		});

		Button btnBlack = (Button) view.findViewById(R.id.btnBlack);
		btnBlack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				config.setColor(Color.BLACK);
				BusProvider.getInstance().post(new ColorChangeBus());
				dismiss();
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);


		builder.setTitle(R.string.dialog_title_choose_color);
		builder.setPositiveButton(R.string.dialog_set_color,
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					config.setColor(picker.getColor());
					BusProvider.getInstance().post(new ColorChangeBus());
				}
			});

		builder.setNegativeButton(R.string.dialog_cancel,
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		Dialog dialog = builder.create();
		return dialog;
	}

	private View getContentView() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		return inflater.inflate(R.layout.dialog_settings_color, null);
	}
}
