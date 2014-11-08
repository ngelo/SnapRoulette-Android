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
	Preview mPreview;
	ImageView cameraView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Check if camera is available.
        if (checkForCameraHardware(this)) {
        	Toast.makeText(CameraActivity.this, "Camera is available! :).", Toast.LENGTH_SHORT).show();
        }
        
        else {
        	Toast.makeText(CameraActivity.this, "Camera is not available.", Toast.LENGTH_SHORT).show();
        }
        
        setupCamera();
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
    
    void setupCamera() {
    	// Attempt to get a Camera instance.
    	try {
    		mCamera = Camera.open();
    	}
    	
    	// Camera is not available (in use or does not exist)
    	catch (Exception e) {
    		Toast.makeText(CameraActivity.this, "Camera is not available (in use or does not exist, duration", Toast.LENGTH_LONG).show();
    	}
    }
    
    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;
        
        try {
            releaseCameraAndPreview();
           // mCamera.unlock();
            mCamera = Camera.open(0);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;    
    }

    private void releaseCameraAndPreview() {
        mPreview.setCamera(null);
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
