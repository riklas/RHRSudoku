package com.example.rhrsudoku;

import java.util.SortedSet;
import java.util.TreeSet;

import com.example.rhrsudoku.GameActivity.StateInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class SmallBox extends View {	
	
	static final int POSSIBLE_VALUES = 1;
	static final int FINAL_VALUE = 2;
	static final int NONE = 3;
	
	int displayState = NONE;
	int size1 = 60;
	int row, col;
	//boolean hasPossibleValues = false;
	boolean isEditable = true;
	SudokuPuzzleCell cell1;
	private SortedSet<Integer> possibleValues = new TreeSet<Integer>();
	String possibleValuesS = new String();
	Paint paint1, paint2, paint3, paint4, paint5, paint6, paint7, paint8;
	GameActivity.StateInfo state1;
	
	/*
	 * paint1	text final value numbers
	 * paint2	text possible value numbers
	 * paint3	default background colour
	 * paint4	solver generated background
	 * paint5	conflicting cells background
	 * paint6	generator generated background
	 * paint7	selected and entering final value
	 * paint8	selected and entering possible value
	 */
	
	public SmallBox(Context context, GameActivity.StateInfo state1, 
			SudokuPuzzleCell cell1, int row, int col) {
		super(context);
		this.state1 = state1;
		this.cell1 = cell1;
		this.row = row;
		this.col = col;
		//setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		createPaints();
		if (row<0 || row>8 || col<0 || col>8) {
			System.err.println("ERRONEOUS ROW/COL");
			System.exit(1);
		}
		if (cell1.hasValue)
			displayState = FINAL_VALUE;
	}
	
	/*
	 * BEGIN CALLBACKS
	 */
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * The width and height of each cell should be specified by the activity
		 * because each cell needs to be exactly the same size
		 */
		int width = setDimension(widthMeasureSpec);
		int height = setDimension(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		/*
		 * paint1	text final value numbers
		 * paint2	text possible value numbers
		 * paint3	default background colour
		 * paint4	solver generated background
		 * paint5	conflicting cells background
		 * paint6	generator generated background
		 * paint7	selected and entering final value
		 * paint8	selected and entering possible value
		 */
		// draw background first
		if (state1.hasSelectedSmallBox && state1.selectedSmallBox == this) {
			if (state1.selectingState == StateInfo.SELECTING_FINAL_VALUE)
				canvas.drawPaint(paint7);
			else if (state1.selectingState == StateInfo.SELECTING_POSSIBLE_VALUE)
				canvas.drawPaint(paint8);
		}
		else if (cell1.inputMethod == SudokuPuzzleCell.GENERATED)
			canvas.drawPaint(paint6);
		else if (cell1.inputMethod == SudokuPuzzleCell.SOLVER_GENERATED)
			canvas.drawPaint(paint4);
		else
			canvas.drawPaint(paint3);
		
		if (cell1.isConflicting())
			canvas.drawPaint(paint5);
		
		// then draw text
		
		if (displayState == FINAL_VALUE) {
			int x = getWidth()/2;
			int y = getHeight()/2;
			canvas.drawText(Integer.toString(cell1.getValue()), x, y, paint1);
		}
		else if (displayState == POSSIBLE_VALUES) {
			canvas.drawText(possibleValuesS, 0, 0, paint2);
		}
		
		
			return;
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		if (gainFocus) {
			state1.hasSelectedSmallBox = true;
			state1.selectedSmallBox = this;
		}
		
		else {
		}
	}
	
	/*
	 * END CALLBACKS
	 * BEGIN BUILDER METHODS
	 */
	
	private void createPaints() {
		/*
		 * paint1	text final value numbers
		 * paint2	text possible value numbers
		 * paint3	default background colour
		 * paint4	solver generated background
		 * paint5	conflicting cells background
		 * paint6	generator generated background
		 * paint7	selected and entering final value
		 * paint8	selected and entering possible value
		 */
		
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		
		paint1 = new Paint(); paint2 = new Paint(); paint3 = new Paint();
		paint4 = new Paint(); paint5 = new Paint(); paint6 = new Paint();
		paint7 = new Paint(); paint8 = new Paint();
		
		paint1.setColor(Color.BLACK);
		paint2.setColor(Color.RED);
		paint3.setColor(Color.WHITE);
		paint4.setColor(Color.GREEN);
		paint5.setColor(Color.RED);
		paint6.setColor(Color.LTGRAY);
		paint7.setColor(Color.BLUE);
		paint8.setColor(Color.WHITE);
		
		final float paint1TextSize = 36f;
		final float paint2TextSize = 10f;
		
		paint3.setStyle(Style.FILL); paint4.setStyle(Style.FILL);
		paint5.setStyle(Style.FILL); paint6.setStyle(Style.FILL); 
		paint7.setStyle(Style.FILL); paint8.setStyle(Style.FILL);
		
		paint1.setTextSize((int) (paint1TextSize * scale + 0.5f));
		paint2.setTextSize((int) (paint2TextSize * scale + 0.5f));
	}
	
	/*
	 * END BUILDER METHODS
	 * BEGIN GETTER/SETTER METHODS
	 */
	
	private void calculatePossibleValuesS() {
		/*
		 * Creates a string of numbers from the set of possible values
		 */
		possibleValuesS = new String();
		for (Integer i : possibleValues) {
			possibleValuesS = possibleValuesS.concat(Integer.toString(i));
		}
	}
	
	public SortedSet<Integer> getPossibleValues() {
		return possibleValues;
	}
	
	public void setFinalValue(int i, int inputMethod) {
		if (!isEditable) {
			System.err.println("ERROR: This cell is not editable");
			return;
		}
		if (!boundsCheck(i))
			return;
		cell1.setValue(i); 
		cell1.setInput(inputMethod);
		if (inputMethod == SudokuPuzzleCell.GENERATED)
			this.isEditable = false;
		displayState = FINAL_VALUE;
	}
	public void addPossibleValue(int i) {
		if (!isEditable) {
			System.err.println("Error: this cell is not editable");
			return;
		}
		if(!boundsCheck(i))
			return;
		displayState = POSSIBLE_VALUES;
		cell1.hasValue = false;
		possibleValues.add(i);
		calculatePossibleValuesS();
		invalidate();
	}
	
	public boolean containsPossibleValue(int i) {
		return possibleValues.contains(i);
	}
	public void removePossibleValue(int i) {
		possibleValues.remove(i);
		if (possibleValues.isEmpty())
			displayState = NONE;
		calculatePossibleValuesS();
		invalidate();
	}
	
	private boolean boundsCheck(int i) {
		/*
		 * bounds checking on numbers entered into finalValue and possibleValues
		 */
		if (i<1 || i>9) {
			System.out.println("ERRONEOUS VALUE: " + i);
			return false;
		}
		return true;
	}
	
	private int setDimension(int measureSpec) {
		int mode = View.MeasureSpec.getMode(measureSpec);
		int size2 = View.MeasureSpec.getSize(measureSpec);
		if (mode == View.MeasureSpec.EXACTLY)
			return size2;
		else if (mode == View.MeasureSpec.AT_MOST) {
			if (size2>size1)
				return size1;
			else 
				return size2;
		}
		else if (mode == View.MeasureSpec.UNSPECIFIED)
			return size1;
		return size1;
	}
	
	/*
	 * END GETTER/SETTER METHODS
	 */
}
