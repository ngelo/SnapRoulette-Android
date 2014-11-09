package com.example.snaproulette;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Toast.makeText(MainActivity.this, "Setting up tabs", Toast.LENGTH_SHORT).show();
		
		// setup action bar for tabs
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab cameraTab = actionBar
				.newTab()
				.setText("Camera")
				.setTabListener(
						new TabListener<CameraFragment>(this, "Camera",
								CameraFragment.class));
		actionBar.addTab(cameraTab);

		Tab rouletteTab = actionBar
				.newTab()
				.setText("Roulette")
				.setTabListener(
						new TabListener<RouletteFragment>(this, "Roulette",
								RouletteFragment.class));
		actionBar.addTab(rouletteTab);

	}
}
