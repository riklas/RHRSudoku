package com.example.rhrsudoku;

import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class SmallBox extends View {	
	/*
	 * Created a different class for me to work on, 
	 */
	
	int row, col;
	boolean hasFinalValue = false;
	boolean hasPossibleValues = false;
	boolean isEditable = true;
	boolean isSelected = false;
	SudokuPuzzleCell cell1;
	//int finalValue = 0; // final value must be stored in cell1 if we have one
	SortedSet<Integer> possibleValues = new TreeSet<Integer>();
	String possibleValuesS = new String();
	Paint paint1;
	Paint paint2;
	Drawable background;
	
	/*
	 * paint1:  painting of finalValues
	 * paint2:  painting of possibleValues
	 * possibleValuesCount the number of possibleValues, modified when adding and removing
	 */
	
	public SmallBox(Context context, SudokuPuzzleCell cell1, Paint paint1,
			Paint paint2, int row, int col) {
		super(context);
		this.cell1 = cell1;
		this.row = row;
		this.col = col;
		//setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		this.paint1 = paint1;
		this.paint2 = paint2;
		background = createBackground();
		
		if (row<0 || row>8 || col<0 || col>8) {
			System.err.println("ERRONEOUS ROW/COL");
			System.exit(1);
		}
	}
	
	/*
	 * CallBack Overrides
	 */
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * The width and height of each cell should be specified by the activity
		 * because each cell needs to be exactly the same size
		 */
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
		if (widthMode != View.MeasureSpec.EXACTLY || heightMode != View.MeasureSpec.EXACTLY)
			System.err.println("Expected an exact measurement");
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		int height = View.MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		/*
		 * if hasFinalValue, draw the value with paint1
		 * if hasPossibleValues, draw the possible values with paint2
		 * otherwise, do nothing
		 */
		if (hasFinalValue) {
			background.draw(canvas);
			String value = Integer.toString(cell1.getValue());
			canvas.drawText(value, getWidth()/2 , getHeight()/2 , paint1);
			//TODO unsure how to draw this in the centre of cell
		}
		
		else if (hasPossibleValues) {
			background.draw(canvas);
			canvas.drawText(possibleValuesS, 0, 0, paint2);
			//TODO must be drawn in top right corner
		}
		else
			return;
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		if (gainFocus)
			isSelected = true;
		else
			isSelected = false;
	}
	
	/*
	 * End of CallBacks
	 */
	
	private Drawable createBackground() {
		Drawable draw1 = new ColorDrawable(Color.LTGRAY);
		return draw1;
	}
	
	private void calculatePossibleValuesS() {
		/*
		 * Creates a string of numbers from the set of possible values
		 */
		possibleValuesS = new String();
		if (!hasPossibleValues)
			return;
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
		cell1.setValue(i, inputMethod);
		hasFinalValue = true;
		if (inputMethod == SudokuPuzzleCell.GENERATED)
			this.isEditable = false;
		
	}
	public void addPossibleValue(int i) {
		if (!isEditable) {
			System.err.println("Error: this cell is not editable");
			return;
		}
		if(!boundsCheck(i))
			return;
		hasPossibleValues = true;
		possibleValues.add(i);
		calculatePossibleValuesS();
		invalidate();
	}
	public void removePossibleValue(int i) {
		possibleValues.remove(i);
		if (possibleValues.isEmpty())
			hasPossibleValues = false;
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
}
