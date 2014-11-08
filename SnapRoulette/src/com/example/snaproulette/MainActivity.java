package com.example.snaproulette;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

//import com.parse.ui.ParseLoginBuilder;

public class MainActivity extends Activity {

	Camera mCamera;
	Preview mPreview;
	ImageView cameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("ok");
        mPreview = new Preview(this,mCamera);
        ((FrameLayout) findViewById(R.id.preview)).addView(mPreview);
        //cameraView = (ImageView)findViewById(R.id.camera_view);
        /*cameraView.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
              //open();
        	   boolean isgood = safeCameraOpen(0);
               System.out.println("opened good");
           }
        });*/

        
//        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
//        startActivityForResult(builder.build(), 0);
    }
    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;
        
        try {
        	System.out.println("try");
            releaseCameraAndPreview();
            System.out.println("hay");
           // mCamera.unlock();
            	System.out.println("ok");
            mCamera = Camera.open(0);

            System.out.println("opened");
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;    
    }

    private void releaseCameraAndPreview() {
    	System.out.println("in");
        mPreview.setCamera(null);
        System.out.println("set camera null");
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
   
    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
