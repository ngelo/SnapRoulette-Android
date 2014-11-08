package com.example.snaproulette;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;


public class App extends Application {

	public void onCreate(){
		super.onCreate();
		Parse.enableLocalDatastore(this);
		System.out.println("before");
	Parse.initialize(this, "KaobAtZ2RN71vQlWoZWPKVD51cqCIPirGfTsSq8m",
    		"AuyBC5IMWQttOIhhoQ44mxEMpQ0IelFsDyX8gFTO");
    ParseObject testObject = new ParseObject("TestObject");
    testObject.put("redbull","dont give me wings");
    testObject.saveInBackground();
    System.out.println("finished");
	}
}
