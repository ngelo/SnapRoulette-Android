package com.example.snaproulette;

import com.parse.*;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
<<<<<<< Updated upstream
import android.os.Handler;
=======
import android.os.CountDownTimer;
>>>>>>> Stashed changes
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RouletteFragment extends Fragment {

	ParseFile file;
	boolean stillWatching;
	ImageView mSnapImageView;
	private Handler mHandler = null;
	private StateMachine mTask = null;
	// Game variables and constants
	private int mTickDelay = 0;
	private final int MAX_TICK_DELAY = 30; // 1 second
	private int mState = 0;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_roulette, container, false);
		
		mSnapImageView = (ImageView) v.findViewById(R.id.snap_image_view);
		mSnapImageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				stillWatching = false;
				
			}
			
		});
		getSnap();
		
		return v;
	}

	public void getSnap() {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Snap");
		query.orderByAscending("createdAt");
		query.whereEqualTo("hasBeenViewed", Boolean.FALSE);
		query.whereNotEqualTo("deviceId", ParseInstallation.getCurrentInstallation().getObjectId());

		// TODO
		// DONE 1.) Where hasBeenViewed = false
		// 2.) Where the user id of the snap is not equal to the current user
		// DONE 3.) Get the oldest one (or randomly)
		// DONE 4.) Mark image as read after shown to screen

		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					file = object.getParseFile("imageFile");
					// return the string name for now, display the byte[] when
					// finished
					System.out.println(file.getName());
					
					try {
						byte[] bytes = file.getData();
						Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
						mSnapImageView.setImageBitmap(bmp);
						object.put("hasBeenViewed", Boolean.TRUE);
						object.saveInBackground();
						new CountDownTimer(5000, 1000) {
							
						     public void onTick(long millisUntilFinished) {
						         System.out.println("cool");
						     }

						     public void onFinish() {
						    	 // need to implement setting stillWatching to false if the screen's clicked
						    	 // while a picture is displaying
						    	 if (stillWatching) {
						 			getSnap();
						 		}
						     }
						  }.start();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					// alert the user that there are no more images to read!
					
					return;
					// something went wrong
				}
			}
		});
		
		
	}
	private class StateMachine implements Runnable {
		public void run() {
			/*if (mState == STATE_STOP) {
			}  else if (mState == STATE_ROTATE) {
				timercount++;
				animateObjects();
			}  
			mDrawCanvas.invalidate();
			mHandler.postDelayed(mTask, mTickDelay);*/
		}
	}
}
