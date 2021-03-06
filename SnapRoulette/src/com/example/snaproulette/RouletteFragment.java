package com.example.snaproulette;

import com.parse.*;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RouletteFragment extends Fragment implements OnTouchListener {

	ParseFile file;
	boolean stillWatching= false;
	boolean timer = false;
	ImageView mSnapImageView;
	private Handler mHandler = null;
	private StateMachine mTask = null;
	private int mTickDelay = 0;
	private final int STATE_STOP=1;
	private final int STATE_START = 0;
	Bitmap homescreen;
	
	private final int MAX_TICK_DELAY = 30; // 1 second
	private int mState = 0;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_roulette, container, false);
		mSnapImageView = (ImageView) v.findViewById(R.id.snap_image_view);
		mSnapImageView.setOnTouchListener(this);
		homescreen = BitmapFactory.decodeResource(getResources(),
        		getResources().getIdentifier("logoroulette" , "drawable", getActivity().getPackageName()));
		mSnapImageView.setImageBitmap(homescreen);
		/*mSnapImageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				stillWatching = false;
				
			}
			
		});*/
		
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
				if (object != null && e == null) {				
					file = object.getParseFile("imageFile");
					// return the string name for now, display the byte[] when
					// finished
					//System.out.println(file.getName());
					
					try {
						byte[] bytes = file.getData();
						Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
						Matrix rotationMatrix = new Matrix();
						rotationMatrix.postRotate(0);
						Bitmap rotatedScaledSnapImageBitmap = Bitmap.createBitmap(bmp, 0, 0, 
								bmp.getWidth(), bmp.getHeight(), rotationMatrix, true);
						mSnapImageView.setImageBitmap(rotatedScaledSnapImageBitmap);
						object.put("hasBeenViewed", Boolean.TRUE);
						object.saveInBackground();
						/*new CountDownTimer(5000, 1000) {
							
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
						  }.start();*/
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					// alert the user that there are no more images to read!
					mSnapImageView.setImageBitmap(homescreen);
					stillWatching = false;
					Toast.makeText(getActivity(), "No more snaps...", Toast.LENGTH_SHORT).show();
					return;
					// something went wrong
				}
			}
		});
		
		
	}
	public void snapTimer()
	{
		System.out.println("coming in");
		if(!timer)
		{
			timer = true;
			getSnap();
			new CountDownTimer(3000, 1000) {
			     public void onTick(long millisUntilFinished) {
			     }

			     public void onFinish() {
			    	 // need to implement setting stillWatching to false if the screen's clicked
			    	 // while a picture is displaying
			    	 if (stillWatching) {
			 			getSnap();
			 		timer = false;
			 		}
			     }
			  }.start();
			  
		}
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
			System.out.println("in touch");
		if (view.getId() == R.id.snap_image_view) {
			System.out.println("on touch");
			switch (event.getAction()) {
			// An ACTION_DOWN event occurs when the user first touches
			case MotionEvent.ACTION_DOWN:
				touch_down(event.getX(), event.getY());
				break;
			// AN ACTION_MOVE event occurs when the user drags
			case MotionEvent.ACTION_MOVE:
				touch_move(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				touch_up(event.getX(), event.getY());
				break;
			}
			// "invalidate" means that the screen needs to be repainted
			// (adds an "onDraw" event to the DrawCanvas event queue)
			//mDrawCanvas.setShapes(mShapes);
			mSnapImageView.invalidate();
			return true;
		}
		return false;
	}
	private void touch_down(float x, float y) {
		stillWatching = true;
		//System.out.println("touch down");
		
	}
	private void touch_move(float x, float y) {
		stillWatching = true;
		snapTimer();
		//System.out.println("touch move");
	}
	private void touch_up(float x, float y) {
		stillWatching = false;
		mSnapImageView.setImageBitmap(homescreen);
		timer = false;
	}
	private class StateMachine implements Runnable {
		public void run() {
			if (mState == STATE_STOP) {
			}  else if (mState == STATE_START) {
			}  
			mSnapImageView.invalidate();
			mHandler.postDelayed(mTask, mTickDelay);
		}
	}
}
