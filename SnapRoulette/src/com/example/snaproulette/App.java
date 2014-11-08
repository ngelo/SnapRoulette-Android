package com.example.snaproulette;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;

import android.app.Application;

public class App extends Application {

	@Override
	public void onCreate(){
		super.onCreate();

		// Configure the application with the Parse SDK
		Parse.initialize(this, "KaobAtZ2RN71vQlWoZWPKVD51cqCIPirGfTsSq8m",
			"AuyBC5IMWQttOIhhoQ44mxEMpQ0IelFsDyX8gFTO");

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
