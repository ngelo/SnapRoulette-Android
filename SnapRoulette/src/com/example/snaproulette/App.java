package com.example.snaproulette;

import android.app.Application;
import android.util.Log;

import com.parse.*;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Configure the application with the Parse SDK
		Parse.initialize(this, "KaobAtZ2RN71vQlWoZWPKVD51cqCIPirGfTsSq8m",
				"AuyBC5IMWQttOIhhoQ44mxEMpQ0IelFsDyX8gFTO");
		//Parse.initialize(this, "uwnoGFcYk6ipDUhVfRJN0RmaEBG4IMSaE4cfvBjt",
		//		"gf5gbmS9q5OuqYuMZbJ4AAHStucejxU3zSFQPlcT");

		// Save the current Installation to Parse.
		ParseInstallation.getCurrentInstallation().saveInBackground();

		// Also in this method, specify a default Activity to handle push
		// notifications
		// PushService.setDefaultPushCallback(this, MainActivity.class);

		ParseAnonymousUtils.logIn(new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (e != null) {
					Log.d("MyApp", "Anonymous login failed.");
				} else {
					Log.d("MyApp", "Anonymous user logged in.");
				}
			}

		});
	}
}
