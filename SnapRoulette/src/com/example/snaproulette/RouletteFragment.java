package com.example.snaproulette;

import com.parse.*;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class RouletteFragment extends Fragment {
	
	ParseFile file;

	public void getSnap() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Snap");

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
			Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			// need to implement past null for imageView
			ImageView image= new ImageView(null);
			image.setImageBitmap(bmp);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
