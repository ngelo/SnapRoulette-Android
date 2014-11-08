package com.example.snaproulette;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

import android.app.Application;

public class App extends Application {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(){
		super.onCreate();

		// Configure the application with the Parse SDK
		Parse.initialize(this, "KaobAtZ2RN71vQlWoZWPKVD51cqCIPirGfTsSq8m",
			"AuyBC5IMWQttOIhhoQ44mxEMpQ0IelFsDyX8gFTO");
		
		// Save the current Installation to Parse.
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		// Also in this method, specify a default Activity to handle push notifications
		PushService.setDefaultPushCallback(this, MainActivity.class);

		// Test the connection to Parse by creating a new object and 
		// saving it to the server.
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("redbull","dont give me wings");
		testObject.saveInBackground();
		
		
		//ParseFile
		//creating Parsefile in byte[]
		byte[] data = "SnapRoulette!".getBytes();
		ParseFile file = new ParseFile("imageFile.png", data);
		
		//saves the file
		file.saveInBackground();
		
		//associating Parse file with Parse Object
		ParseObject Pictures = new ParseObject("appPictures");
		Pictures.put("pictureName", "Joe Smith");
		Pictures.put("PicturesFile", file);
		Pictures.saveInBackground();
	}
}
