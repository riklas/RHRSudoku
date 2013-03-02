package com.example.rhrsudoku;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
	 */
	
	
	/*
	 * GLOBAL MEMBERS
	 */
	SudokuPuzzle puzzle;
	SudokuSolver solver;
	StateInfo state1;
	DigitButton[] digits1 = new DigitButton[9];
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
		state1 = new StateInfo();
		
		SudokuGenerator hardcode = new HardcodedPuzzles();
		puzzle = hardcode.getPuzzle(difficulty);
		
		
		
		//GeneratedPuzzles generate = new GeneratedPuzzles();
		//puzzle = generate.getPuzzle(difficulty);
		
		setContentView(R.layout.activity_game);
		createSmallBoxes();
		initializeDigitButtons();
		
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
	
	View.OnClickListener getSmallBoxOnClickListener() {
		View.OnClickListener listener1 = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				smallBoxClicked(v);
			}
		};
		return listener1;
	}
	
	View.OnLongClickListener getSmallBoxOnLongClickListener() {
		View.OnLongClickListener listener2 = new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				smallBoxLongClicked(v);
				return true;
			}
		};
		return listener2;
	}
	
	private void initializeDigitButtons() {
		digits1[0] = (DigitButton) findViewById(R.id.one);
		digits1[1] = (DigitButton) findViewById(R.id.two);
		digits1[2] = (DigitButton) findViewById(R.id.three);
		digits1[3] = (DigitButton) findViewById(R.id.four);
		digits1[4] = (DigitButton) findViewById(R.id.five);
		digits1[5] = (DigitButton) findViewById(R.id.six);
		digits1[6] = (DigitButton) findViewById(R.id.seven);
		digits1[7] = (DigitButton) findViewById(R.id.eight);
		digits1[8] = (DigitButton) findViewById(R.id.nine);
		
		for(int i=0; i<digits1.length;i++) {
			digits1[i].state1 = this.state1;
			digits1[i].number1 = (i+1);
		}
	}
	
	private void createSmallBoxes() {
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		int screenSmallest;
		if (screenWidth<screenHeight)
			screenSmallest = screenWidth;
		else
			screenSmallest = screenHeight;
		int cellSize = screenSmallest/10;
		ViewGroup grid1 = (ViewGroup) findViewById(R.id.gridLayout1);
		View.OnClickListener listener1 = getSmallBoxOnClickListener();
		View.OnLongClickListener listener2 = getSmallBoxOnLongClickListener();
		//creating small boxes
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				SmallBox box1 = new SmallBox(this, this.state1, puzzle.puzzle[row][col], row, col, cellSize);
				box1.setOnClickListener(listener1);
				box1.setOnLongClickListener(listener2);
				grid1.addView(box1);
			}
		}
	}
	
	/*
	 * END CREATION METHOD
	 * BEGIN LISTENER METHODS
	 */
	
	public void smallBoxClicked(View v) {
		SmallBox sb2 = (SmallBox) v;
		SmallBox sb1 = state1.selectedSmallBox;
		/*
		 * set has selected to true
		 * set the selected box
		 * set setting final value to true
		 * invalidate the small box and the previously selected small box
		 * invalidate the digitbuttons
		 */
		
		state1.selectedSmallBox = sb2;
		state1.hasSelectedSmallBox = true;
		state1.selectingState = StateInfo.SELECTING_FINAL_VALUE;
		if (sb1 != null)
			sb1.invalidate();
		sb2.invalidate();
		for (DigitButton db : digits1)
			db.invalidate();
	}
	
	public void smallBoxLongClicked(View v) {
		SmallBox sb2 = (SmallBox) v;
		SmallBox sb1 = state1.selectedSmallBox;
		state1.selectedSmallBox = sb2;
		state1.hasSelectedSmallBox = true;
		state1.selectingState = StateInfo.SELECTING_POSSIBLE_VALUE;

		if (sb1 != null)
			sb1.invalidate();
		sb2.invalidate();
		for (DigitButton db : digits1)
			db.invalidate();
	}
	
	public void digitButtonClicked(View v) {
		DigitButton db2 = (DigitButton) v;
		if (!state1.hasSelectedSmallBox) {
			System.err.println("ERROR: No SmallBox selected");
			return;
		}
		
		if (state1.selectingState == StateInfo.SELECTING_FINAL_VALUE) {
			/*
			 * if cell already has same final value to be set, clear it instead
			 * otherwise set has final value on cell
			 * unset has possible values
			 * set final value on cell
			 * call invalidate on all digit buttons
			 */
			boolean wasConflicting = state1.selectedSmallBox.cell1.isConflicting();
			
			if (state1.selectedSmallBox.cell1.hasValue && 
					state1.selectedSmallBox.cell1.getValue() == db2.number1) {
				state1.selectedSmallBox.clearFinalValue();
			}
			
			else {
				state1.selectedSmallBox.setFinalValue(db2.number1, SudokuPuzzleCell.USER_INPUT);
				for (DigitButton db : digits1)
					db.invalidate();
			}
			
			if (wasConflicting || state1.selectedSmallBox.cell1.isConflicting()) {
				for (SudokuPuzzleCell cell2 : state1.selectedSmallBox.cell1.neighbours) {
					cell2.box1.invalidate();
				}
			}
		}
		else if (state1.selectingState == StateInfo.SELECTING_POSSIBLE_VALUE) {
			if(state1.selectedSmallBox.containsPossibleValue(db2.number1))
				state1.selectedSmallBox.removePossibleValue(db2.number1);
			else
				state1.selectedSmallBox.addPossibleValue(db2.number1);
		}
		db2.invalidate();
	}
	
	public void functionButtonClicked(View v) {
		/*
		 * this would better be replaced by 3 functions for each function button
		 */
	}
	/*
	 * END LISTENER METHODS
	 * BEGIN LOGIC METHODS
	 */

	public void showHint(View v) {
		solver = new SudokuSolver();
		SudokuPuzzle solvedPuzzle = solver.solvePuzzle(puzzle);		
		Random randomGenerator = new Random();
		int randx;
		int randy;
		
		randx = randomGenerator.nextInt(9);
		randy = randomGenerator.nextInt(9);

outer:		for(int row=randx;row<9;row++) {
			for (int col=randy; col<9; col++) {
				
				if (solvedPuzzle.puzzle[randx][randy].getInput() == SudokuPuzzleCell.SOLVER_GENERATED) {
					int value = solvedPuzzle.puzzle[randx][randy].getValue();
					puzzle.puzzle[randx][randy].box1.setFinalValue(value, SudokuPuzzleCell.USER_INPUT);
					//puzzle.puzzle[randx][randy].setValue(value);
					//puzzle.puzzle[randx][randy].setInput(SudokuPuzzleCell.USER_INPUT);
					//puzzle.puzzle[randx][randy].box1.invalidate();
					break outer;
				}
				
				if (row != randx) randy = 0; 	//when the random index reaches the next row, start from col index 0
			}
		}
	}
	
	public void clearBox(View v) {
		if (!state1.hasSelectedSmallBox) {
			System.err.println("ERROR: No SmallBox selected");
			return;
		}
		state1.selectedSmallBox.clearFinalValue();
		
		state1.selectedSmallBox.removePossibleValues();
	
	}
	
	public void clearAll(View v) {
		
		for(int row=0;row<9;row++) {
			for (int col=0; col<9; col++) {
				if (puzzle.puzzle[row][col].getInput() != SudokuPuzzleCell.GENERATED) {					
					puzzle.puzzle[row][col].removeValue();
					puzzle.puzzle[row][col].setInput(SudokuPuzzleCell.NONE);
					puzzle.puzzle[row][col].box1.invalidate();
					puzzle.puzzle[row][col].box1.removePossibleValues();
				}

			}
		}
	
		/*for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				puzzle.puzzle[row][col].box1.invalidate();
			}
		}*/
	}
	
	/*
	 * END LOGIC METHODS
	 */
	
	class StateInfo {
		static final int SELECTING_FINAL_VALUE = 1;
		static final int SELECTING_POSSIBLE_VALUE = 2;
		static final int NONE = 3;
		
		boolean hasSelectedSmallBox;
		SmallBox selectedSmallBox;
		int selectingState = NONE;
	}

}
