package com.example.rhrsudoku;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
	SmallBox selectedSmallBox;
	
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
		 * 
		 * 
		 * Logic
		 * ==========
		 * 		* onClickListener for small boxes
		 * 		* onClickListener for Hint, Clear, Clear All
		 * 		* 
		 */
		Intent intent = getIntent();
		int difficulty = intent.getIntExtra(DifficultyChooser.DIFFICULTY, 0);	  	
		SudokuGenerator hardcode = new HardcodedPuzzles();
		puzzle = hardcode.getPuzzle(difficulty);
				
		ViewGroup rootView = (ViewGroup) findViewById(R.layout.activity_game);
		Paint paint1 = getPaint1();
		Paint paint2 = getPaint2();
		View.OnClickListener SmallBoxlistener1 = getSmallBoxListener();
		//creating small boxes
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				SmallBox box1 = new SmallBox(this, this, puzzle.puzzle[row][col], 
						paint1, paint2, row, col);
				box1.setOnClickListener(SmallBoxlistener1);
				rootView.addView(box1);
			}
		}
		
		setContentView(rootView);
		
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
	 * BEGIN CREATION METHODS
	 */
	
	Paint getPaint1() {
		return null;
		//TODO
	}
	
	Paint getPaint2() {
		return null;
		//TODO
	}
	
	View.OnClickListener getSmallBoxListener() {
		View.OnClickListener listener1 = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SmallBox box1 = (SmallBox) v;
				selectBox(box1);
			}
		};
		return listener1;
	}
	
	View.OnClickListener getNumberButtonListener() {
		View.OnClickListener listener1 = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button button1 = (Button) v;
				
				// TODO Auto-generated method stub
				
			}
		};
		return listener1;
	}
	
	View.OnClickListener getHintButtonListener() {
		View.OnClickListener listener1 = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button button1 = (Button) v;
				
				// TODO Auto-generated method stub
				
			}
		};
		return listener1;
	}
	
	View.OnClickListener getClearAllListener() {
		View.OnClickListener listener1 = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button button1 = (Button) v;
				
				// TODO Auto-generated method stub
				
			}
		};
		return listener1;
	}
	/*
	 * END CREATION METHODS
	 * BEGIN LOGIC METHODS
	 */
	
	void selectBox(SmallBox box1) {
		box1.isSelected = true;
		selectedSmallBox = box1;
		//TODO  need to implement logic
		// change 1-9 buttons to reflect the state of box1
	}

}
