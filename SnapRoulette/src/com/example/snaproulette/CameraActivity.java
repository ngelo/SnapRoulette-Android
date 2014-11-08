package com.example.snaproulette;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

//import com.parse.ui.ParseLoginBuilder;

public class CameraActivity extends Activity {

	Camera mCamera;
	CameraPreview mCameraPreview;
	ImageView cameraView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Check if camera is available.
        if (checkForCameraHardware(this)) {
        	Toast.makeText(CameraActivity.this, "Camera hardware is available! :).", Toast.LENGTH_SHORT).show();
        }
        
        else {
        	Toast.makeText(CameraActivity.this, "Camera hardware is not available.", Toast.LENGTH_SHORT).show();
        }
        
        // Get an instance of the camera
        mCamera = getCameraInstance();
        
        if (mCamera == null) {
        	Toast.makeText(CameraActivity.this, "Camera instance is null.", Toast.LENGTH_SHORT).show();
        } else {
        	Toast.makeText(CameraActivity.this, "Camera instance retrieved!.", Toast.LENGTH_SHORT).show();
        }
        
        // Create our Preview view and set it as the content of our activity.
        mCameraPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
        
//        mCamera.startPreview();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Check if this device has a camera */
    private boolean checkForCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(0); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
