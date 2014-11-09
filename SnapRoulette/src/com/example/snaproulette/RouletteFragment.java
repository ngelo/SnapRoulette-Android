package com.example.snaproulette;

import com.parse.*;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RouletteFragment extends Fragment {
<<<<<<< Updated upstream

=======
	Button mRouletteButton;
>>>>>>> Stashed changes
	ParseFile file;
	ImageView image;
	boolean isDrawing;
	Bitmap bmp;
	public void SetUpFrame() {
	
	}
	public Bitmap getSnap() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Snap");

<<<<<<< Updated upstream
		// TODO Add filters
		// 1.) Where hasBeenViewed = false
		// 2.) Where the user id of the snap is not equal to the current user
		// 3.) Get the oldest one (or randomly)

		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					file = object.getParseFile("imageFile");
					// return the string name for now, display the byte[] when
					// finished
					System.out.println(file.getName());
				} else {
					System.out.println("Oh Shit");
					// something went wrong
				}

			}

		});
		try {
			byte[] bytes = file.getData();
			Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			// need to implement past null for imageView
			ImageView image = new ImageView(null);
			image.setImageBitmap(bmp);
=======
		query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {			
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) { 
		    	file = object.getParseFile("imageFile");
		    	// return the string name for now, display the byte[] when finished
		    	System.out.println(file.getName());
		    } else {
		    	System.out.println("Oh Shit");
		      // something went wrong
		    }
	    	
		  }
		});
		try {
			byte[] bytes = file.getData();
			bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			
>>>>>>> Stashed changes
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return bmp;
	}
	public void buttonClicked() {
		image = new ImageView(null);
		image.setImageBitmap(getSnap());
	}

}
