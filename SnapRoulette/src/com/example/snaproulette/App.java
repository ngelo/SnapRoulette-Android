package com.example.snaproulette;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class App extends Application {

	@Override
	public void onCreate(){
		super.onCreate();

		// Configure the applicaiton with the Parse SDK
		Parse.initialize(this, "KaobAtZ2RN71vQlWoZWPKVD51cqCIPirGfTsSq8m",
			"AuyBC5IMWQttOIhhoQ44mxEMpQ0IelFsDyX8gFTO");

		// Test the connection to Parse by creating a new object and 
		// saving it to the server.
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("redbull","dont give me wings");
		testObject.saveInBackground();
	}
}
