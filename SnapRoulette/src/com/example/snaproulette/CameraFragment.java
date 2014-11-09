package com.example.snaproulette;

import java.io.ByteArrayOutputStream;

import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
			Toast.makeText(getActivity(), "Sent Snap", Toast.LENGTH_SHORT).show();
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
		Bitmap snapImageBitmap = BitmapFactory.decodeByteArray(rawJpegImageData, 0, rawJpegImageData.length);

		// Calculate the scaled size and scale the image.
		float scaledWidth = 0;
		float scaledHeight = 0;
		System.out.println(snapImageBitmap.getWidth()+" "+snapImageBitmap.getHeight());
		// The width is greater than the height.
		if (snapImageBitmap.getWidth() > snapImageBitmap.getHeight()) {
			scaledWidth = 640;
			scaledHeight = ((float)snapImageBitmap.getHeight() / snapImageBitmap.getWidth()) * 640;
		}

		// The height is greater than the width.
		else if (snapImageBitmap.getWidth() < snapImageBitmap.getHeight()) {
			scaledWidth = ((float)snapImageBitmap.getWidth() / snapImageBitmap.getHeight()) * 640;
			scaledHeight = 640;
		}

		// Image is square
		else {
			scaledWidth = 640;
			scaledHeight = 640;
		}
		System.out.println(scaledWidth+" "+scaledHeight);
		Bitmap scaledSnapImageBitmap = Bitmap.createScaledBitmap(snapImageBitmap, (int)scaledWidth, (int)scaledHeight, true);
		System.out.println(scaledSnapImageBitmap.getWidth()+" "+scaledSnapImageBitmap.getHeight());
		// Rotate the image
		Matrix rotationMatrix = new Matrix();
		rotationMatrix.postRotate(90);
		Bitmap rotatedScaledSnapImageBitmap = Bitmap.createBitmap(scaledSnapImageBitmap, 0, 
				0, scaledSnapImageBitmap.getWidth(), scaledSnapImageBitmap.getHeight(), rotationMatrix, true);
		
		// Get the raw data of the processed image.
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		rotatedScaledSnapImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] rawScaledImage = stream.toByteArray();
		
		ParseObject snap = new ParseObject("Snap");
		ParseFile imageFile = new ParseFile("image.jpg", rawScaledImage);
		snap.put("imageFile", imageFile);
		snap.put("hasBeenViewed", Boolean.FALSE);
		snap.put("deviceId", ParseInstallation.getCurrentInstallation().getObjectId());
		snap.saveInBackground();
	}
}
