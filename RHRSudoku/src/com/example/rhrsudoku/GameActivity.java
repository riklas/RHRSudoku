package com.example.rhrsudoku;

import java.io.IOException;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


public class GameActivity extends Activity {
	
	
	/*
	 * GLOBAL MEMBERS
	 */
	SudokuPuzzleWithSolution puzWithSol;
	SudokuSolver solver;
	StateInfo stateInfo;
	DigitButton[] digits1 = new DigitButton[9];
	Random randomGen = new Random();
	/*
	 * BEGIN CALLBACKS
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		int difficulty = intent.getIntExtra(DifficultyChooser.DIFFICULTY, 0);
		stateInfo = new StateInfo();
		HardCodedPuzzles3 puzzleGenerator = null;
		puzzleGenerator = new HardCodedPuzzles3(getResources());
		puzWithSol = puzzleGenerator.getRandomPuzzle(difficulty);
		
		
		
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
			//NavUtils.navigateUpFromSameTask(this);
		case R.id.menu_new_game:
			startNewGame();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		/*
		 * need to save StateInfo
		 */
		stateInfo.saveInstanceState(bundle);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		stateInfo.restoreInstanceState(bundle);
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
			digits1[i].state1 = this.stateInfo;
			digits1[i].number1 = (i+1);
		}
	}
	
		
	private void createSmallBoxes() {
		
		final ViewGroup grid1 = (ViewGroup) findViewById(R.id.gridLayout1);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		int screenWidth = displayMetrics.widthPixels;
		int screenHeight = displayMetrics.heightPixels;
		
		int divisor = 10;
		int screenSmallest;
		if (screenWidth<screenHeight) {
			screenSmallest = screenWidth;
			divisor = 10;
		}
		else {
			screenSmallest = screenHeight;
			divisor = 12;
		}
		int cellSize = screenSmallest/divisor;
		View.OnClickListener listener1 = getSmallBoxOnClickListener();
		View.OnLongClickListener listener2 = getSmallBoxOnLongClickListener();
		//creating small boxes
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				SmallBox box1 = new SmallBox(this, this.stateInfo, 
						puzWithSol.puzzle.puzzle[row][col], row, col, cellSize);
				box1.setOnClickListener(listener1);
				box1.setOnLongClickListener(listener2);
				grid1.addView(box1);
				box1.setId(row*9 + col);
			}
		}
	}
	
	/*
	 * END CREATION METHOD
	 * BEGIN LISTENER METHODS
	 */
	
	public void smallBoxClicked(View v) {
		SmallBox sb2 = (SmallBox) v;
		SmallBox sb1 = stateInfo.selectedSmallBox;
		/*
		 * set has selected to true
		 * set the selected box
		 * set setting final value to true
		 * invalidate the small box and the previously selected small box
		 * invalidate the digitbuttons
		 */
		
		boolean hadSelectedSmallBox = stateInfo.hasSelectedSmallBox;
		stateInfo.selectedSmallBox = sb2;
		stateInfo.hasSelectedSmallBox = true;
		stateInfo.selectingState = StateInfo.SELECTING_FINAL_VALUE;
		if (hadSelectedSmallBox)
			sb1.invalidate();
		sb2.invalidate();
		refreshDigitButtons();
	}
	
	public void smallBoxLongClicked(View v) {
		SmallBox sb2 = (SmallBox) v;
		SmallBox sb1 = stateInfo.selectedSmallBox;
		boolean hadSelectedSmallBox = stateInfo.hasSelectedSmallBox;
		stateInfo.selectedSmallBox = sb2;
		stateInfo.hasSelectedSmallBox = true;
		stateInfo.selectingState = StateInfo.SELECTING_POSSIBLE_VALUE;
		

		if (hadSelectedSmallBox)
			sb1.invalidate();
		sb2.invalidate();
		refreshDigitButtons();
	}
	
	public void digitButtonClicked(View v) {
		DigitButton db2 = (DigitButton) v;
		if (!stateInfo.hasSelectedSmallBox) {
			System.err.println("ERROR: No SmallBox selected");
			return;
		}
		
		if (stateInfo.selectingState == StateInfo.SELECTING_FINAL_VALUE) {
			/*
			 * if cell already has same final value to be set, clear it instead
			 * otherwise set has final value on cell
			 * unset has possible values
			 * set final value on cell
			 * call invalidate on all digit buttons
			 */
			boolean wasConflicting = stateInfo.selectedSmallBox.cell1.isConflicting();
			
			if (stateInfo.selectedSmallBox.cell1.hasValue && 
					stateInfo.selectedSmallBox.cell1.getValue() == db2.number1) {
				stateInfo.selectedSmallBox.clearFinalValue();
			}
			
			else {
				stateInfo.selectedSmallBox.setFinalValue(db2.number1, SudokuPuzzleCell.USER_INPUT);
				refreshDigitButtons();
			}
			
			if (wasConflicting || stateInfo.selectedSmallBox.cell1.isConflicting()) {
				for (SudokuPuzzleCell cell2 : stateInfo.selectedSmallBox.cell1.neighbours) {
					cell2.box1.invalidate();
				}
			}
		}
		else if (stateInfo.selectingState == StateInfo.SELECTING_POSSIBLE_VALUE) {
			if(stateInfo.selectedSmallBox.containsPossibleValue(db2.number1))
				stateInfo.selectedSmallBox.removePossibleValue(db2.number1);
			else
				stateInfo.selectedSmallBox.addPossibleValue(db2.number1);
		}
		db2.invalidate();
		testPuzzleCompletion();
	}
	
	public void functionButtonClicked(View v) {
		/*
		 * this would better be replaced by 3 functions for each function button
		 */
	}
	
	public void blankAreaClicked(View v) {
		boolean hadSelectedSmallBox = stateInfo.hasSelectedSmallBox;
		stateInfo.hasSelectedSmallBox = false;
		if (hadSelectedSmallBox) {
			stateInfo.selectedSmallBox.invalidate();
			refreshDigitButtons();
		}
		
	}
	
	private void refreshDigitButtons() {
		for (DigitButton db : digits1) {
			db.invalidate();
			if (stateInfo.hasSelectedSmallBox && stateInfo.selectedSmallBox.cell1.isEditable)
				db.setClickable(true);
			else
				db.setClickable(false);
		}
		
	}
	
	private void startNewGame() {
		Intent intent = new Intent(this, DifficultyChooser.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	private void testPuzzleCompletion() {
		if (puzWithSol.puzzle.isFilled() && puzWithSol.puzzle.isSolved()) {
			new AlertDialog.Builder(this)
		    .setMessage(R.string.puzzle_completed)
		     .show();
		}
	}
	
	private boolean userHasMadeError() {
		for (int row=0;row<9;row++) {
			for (int col=0;col<9;col++) {
				if (puzWithSol.puzzle.isConflicting())
					return true;
				if (puzWithSol.puzzle.puzzle[row][col].hasValue) {
					if (puzWithSol.puzzle.puzzle[row][col].getValue() !=
							puzWithSol.solution.puzzle[row][col].getValue())
						return true;
				}
			}
		}
		return false;
	}

	
	/*
	 * END LISTENER METHODS
	 * BEGIN LOGIC METHODS
	 */
	
	public void showHint(View v) {
		if (userHasMadeError()) {
			new AlertDialog.Builder(this)
		    .setMessage(R.string.user_made_mistake)
		     .show();
			return;
		}
		
		if (puzWithSol.puzzle.isFilled() && puzWithSol.puzzle.isSolved())
			return;
		
		int[][] list1 = new int[81][2];
		int count = 0;
		for (int row=0;row<9;row++) {
			for (int col=0;col<9;col++) {
				if (!puzWithSol.puzzle.puzzle[row][col].hasValue) {
					list1[count][0] = row;
					list1[count][1] = col;
					count++;
				}
			}
		}
		int rand = randomGen.nextInt(count);
		int row = list1[rand][0];
		int col = list1[rand][1];
		int answer = puzWithSol.solution.puzzle[row][col].getValue();
		puzWithSol.puzzle.puzzle[row][col].box1.setFinalValue(answer, SudokuPuzzleCell.HINT_GENERATED);
	}
	
	
	public void clearBox(View v) {
		if (!stateInfo.hasSelectedSmallBox) {
			System.err.println("ERROR: No SmallBox selected");
			return;
		}
		stateInfo.selectedSmallBox.clearFinalValue();
		stateInfo.selectedSmallBox.removePossibleValues();
		refreshDigitButtons();
		}
	
	public void clearAll1(View v) {
		new AlertDialog.Builder(this)
			.setMessage(R.string.confirm_clear_all)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					clearAll2();
				}
			})
			.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			})
			.show();
	}
		
		
		
		private void clearAll2() {
			for(int row=0;row<9;row++) {
				for (int col=0; col<9; col++) {
					if (puzWithSol.puzzle.puzzle[row][col].getInput() != SudokuPuzzleCell.GENERATED) {					
						puzWithSol.puzzle.puzzle[row][col].removeValue();
						puzWithSol.puzzle.puzzle[row][col].setInput(SudokuPuzzleCell.NONE);
						puzWithSol.puzzle.puzzle[row][col].box1.invalidate();
						puzWithSol.puzzle.puzzle[row][col].box1.removePossibleValues();
					}
				}
			}
			refreshDigitButtons();
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
		
		void saveInstanceState(Bundle bundle) {
			bundle.putBoolean("hasSelectedSmallBox", hasSelectedSmallBox);
			bundle.putInt("selectingState", selectingState);
//			bundle.putInt("selectedSmallBox_row", selectedSmallBox.cell1.row);
//			bundle.putInt("selectedSmallBox_col", selectedSmallBox.cell1.col);
			if (hasSelectedSmallBox)
				bundle.putInt("selectedSmallBox_ID", selectedSmallBox.getId());
		}
		
		void restoreInstanceState(Bundle bundle) {
			hasSelectedSmallBox = bundle.getBoolean("hasSelectedSmallBox");
			selectingState = bundle.getInt("selectingState");
//			int row = bundle.getInt("selectedSmallBox_row");
//			int col = bundle.getInt("selectedSmallBox_col");
			int selectedSmallBox_ID = bundle.getInt("selectedSmallBox_ID");
			if (hasSelectedSmallBox)
				selectedSmallBox = (SmallBox) findViewById(selectedSmallBox_ID);
		}
	}

}
