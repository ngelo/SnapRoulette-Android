package com.example.snaproulette;

import android.app.Application;
import android.util.Log;

import com.parse.*;


public class App extends Application {

	@Override
	public void onCreate(){
		super.onCreate();

		// Configure the application with the Parse SDK
		Parse.initialize(this, "KaobAtZ2RN71vQlWoZWPKVD51cqCIPirGfTsSq8m",
			"AuyBC5IMWQttOIhhoQ44mxEMpQ0IelFsDyX8gFTO");
		
		// Save the current Installation to Parse.
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		// Also in this method, specify a default Activity to handle push notifications
		//PushService.setDefaultPushCallback(this, MainActivity.class);
		
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
        ParseObject Pictures = new ParseObject("Pictures");
        Pictures.put("pictureName", "Joe Smith");
        Pictures.put("pictureResumeFile", file);
        Pictures.saveInBackground();
        ParseFile applicantResume = (ParseFile)Pictures.get("pictureResumeFile");
        applicantResume.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // data has the bytes for the resume
                } else {
                    // something went wrong
                }
            }
        });
	}
}
