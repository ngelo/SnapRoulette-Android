package com.example.snaproulette;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.parse.Parse;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "13Zp2eWvtRH4inAQYcT274S7M5NvsAWFUcSb9YAw"
        		, "ScLzSvB6bnCoPv9dDdoXV2jWM6aPXCx7FSdOzAy9");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
