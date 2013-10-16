package eu.inmite.android.gridwichterle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import eu.inmite.android.gridwichterle.services.GridOverlayService;
import eu.inmite.android.gridwichterle.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		Intent intent = new Intent(this, GridOverlayService.class);
	    startService(intent);

	    finish();
    }



}
