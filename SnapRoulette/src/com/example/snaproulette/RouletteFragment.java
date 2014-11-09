package com.example.snaproulette;

import com.parse.*;

import android.app.Fragment;

public class RouletteFragment extends Fragment {
	
	public void getSnap() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Snap");

		query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					ParseFile file = object.getParseFile("imageFile");
					// return the string name for now, display the byte[] when
					// finished
					System.out.println(file.getName());
				} else {
					System.out.println("Oh Shit");
					// something went wrong
				}
			}
		});
	}

}
