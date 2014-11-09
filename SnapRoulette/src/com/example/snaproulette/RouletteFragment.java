package com.example.snaproulette;

import com.parse.*;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RouletteFragment extends Fragment {

	ParseFile file;
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
		
		getSnap();
		
		return v;
	}

	public void getSnap() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Snap");
		query.orderByAscending("createdAt");
		query.whereEqualTo("hasBeenViewed", Boolean.FALSE);
		query.whereNotEqualTo("userId", ParseUser.getCurrentUser().getObjectId());

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
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println("Oh Shit");
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
