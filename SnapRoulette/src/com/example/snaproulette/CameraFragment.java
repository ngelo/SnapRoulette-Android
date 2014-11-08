package com.example.snaproulette;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraFragment extends Fragment {
	
	Button mTakePhotoButton;
	Camera mCamera;
	CameraPreview mCameraPreview;
	ImageView cameraView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
        // Check if camera is available.
        if (checkForCameraHardware(getActivity())) {
        	Toast.makeText(getActivity(), "Camera hardware is available! :).", Toast.LENGTH_SHORT).show();
        } else {
        	Toast.makeText(getActivity(), "Camera hardware is not available.", Toast.LENGTH_SHORT).show();
        }
        
//        // Get an instance of the camera
//        mCamera = getCameraInstance();
//        
//        if (mCamera == null) {
//        	Toast.makeText(CameraFragment.this, "Camera instance is null.", Toast.LENGTH_SHORT).show();
//        } else {
//        	Toast.makeText(CameraFragment.this, "Camera instance retrieved!.", Toast.LENGTH_SHORT).show();
//        }
//        
//        // Create our Preview view and set it as the content of our activity.
//        mCameraPreview = new CameraPreview(CameraFragment.this, mCamera);
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//        preview.addView(mCameraPreview);
//        
//        mTakePhotoButton = (Button) findViewById(R.id.take_photo_button);
//        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	Toast.makeText(CameraActivity.this, "Take a photo", Toast.LENGTH_SHORT).show();
////                mCamera.ta
//            }
//        });
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
