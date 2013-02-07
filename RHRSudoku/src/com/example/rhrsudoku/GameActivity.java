package com.example.rhrsudoku;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;


public class GameActivity extends Activity {
	
	/*
	 * TO IMPLEMENT
	 * ===============
	 * 		* onSaveInstanceState(), onRestoreInstanceState()
	 * 		* onCreate() -- setContentView(), onDestroy(), other callbacks
	 * 		* creation of the SmallBox Grid
	 * 				* set each id/row/column
	 * 				* create paint arguments
	 * 				* create + set onClick listeners
	 * 		* configure manifest.xml
	 * 		* finish() -- called when user has given up or solved
	 * 		* View.onSaveInstanceState() -- could save instance state in each view
	 * 
	 */
	
	
	/*
	 * GLOBAL MEMBERS
	 */
	SudokuPuzzle puzzle;
	
	
	/*
	 * BEGIN CALLBACKS
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*
		 * Game Activity Creation
		 * =======================
		 * 		* get SudokuPuzzle from generator, using difficulty level passed in
		 * 		* create SmallBoxes
		 * 		* create root view/inflate from xml
		 * 		* add smallboxes to root
		 * 		* add buttons, 1-9, hint, clear, clear all
		 * 		* call setContentView()
		 */
		Intent intent = getIntent();
		int difficulty = intent.getIntExtra(DifficultyChooser.DIFFICULTY, 0);	  	
		SudokuGenerator hardcode = new HardcodedPuzzles();
		puzzle = hardcode.getPuzzle(difficulty);
				
		
		Paint paint1 = getPaint1();
		Paint paint2 = getPaint2();
		//creating small boxes
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				SmallBox box1 = new SmallBox(this, puzzle.puzzle[row][col], 
						paint1, paint2, row, col);
			}
		}
		
		ViewGroup rootView = (ViewGroup) findViewById(R.layout.activity_game);
		
		setContentView(R.layout.activity_game);
		
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
	
	/*
	 * END CALLBACKS
	 */
	
	Paint getPaint1() {
		return null;
		//TODO
	}
	
	Paint getPaint2() {
		return null;
		//TODO
	}

}
