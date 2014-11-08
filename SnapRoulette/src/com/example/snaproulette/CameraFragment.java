package com.example.snaproulette;

import com.parse.ParseFile;
import com.parse.ParseObject;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
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
	
	ShutterCallback mShutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d("CameraFragment", "on Shutter'd");
		}
	};
	
	PictureCallback mRawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d("CameraFragment", "onPictureTaken - raw");
		}
	};
	
	PictureCallback mJpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d("CameraFragment", "onPictureTaken - jpeg");
			sendSnap(data);
		}
	};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        
        // Check if camera is available.
        if (checkForCameraHardware(getActivity())) {
        	Log.d("CameraFragment", "Camera hardware is available! :)");
        } else {
        	Log.d("CameraFragment", "Camera hardware is not available.");
        }
        
        // Get an instance of the camera
        mCamera = getCameraInstance();
        
        if (mCamera == null) {
        	Log.d("CameraFragment", "Camera instance is null");
        } else {
        	Log.d("CameraFragment", "Camera instance retrieved!");
        }
        
        // Create our Preview view and set it as the content of our activity.
        mCameraPreview = new CameraPreview(getActivity(), mCamera);
        FrameLayout preview = (FrameLayout) v.findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
        
        mTakePhotoButton = (Button) v.findViewById(R.id.take_photo_button);
        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(getActivity(), "Take a photo", Toast.LENGTH_SHORT).show();
            	
            	mCamera.takePicture(mShutterCallback, mRawCallback, mJpegCallback);
            }
        });
        
        return v;
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
    
	private void sendSnap(byte[] rawJpegImageData) {
		ParseObject parse = new ParseObject("Snap");
		ParseFile imageFile = new ParseFile("image.jpg", rawJpegImageData);
		parse.put("imageFile", imageFile);
		parse.saveInBackground();
	}
}
