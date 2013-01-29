package com.example.rhrsudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class GameActivity extends Activity {

	int difficulty = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//Capture the intent sent from main activity and get the integer associated with the difficulty
		// setting the default integer to 0 (kids) in case there is a problem retrieving the value
		Intent intent = getIntent();
		difficulty = intent.getIntExtra(MainActivity.DIFFICULTY, 0);	  	
						
		//test code to see if integer was passed
		TextView test = new TextView(this);
		test.setText(Integer.toString(difficulty));
		
		//Call the generator (at the moment implemented for hard-coded puzzles)
		//HardcodedPuzzles hardcode = new HardcodedPuzzles();
		//SmallBox[] smallboxarray = hardcode.getPuzzle(difficulty);
		//generator returns array of small boxes to pass to constructor of sudokupuzzle
		
		setContentView(test);
		
		// Show the Up button in the action bar.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
