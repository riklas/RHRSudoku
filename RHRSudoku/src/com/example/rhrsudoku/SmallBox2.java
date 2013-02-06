package com.example.rhrsudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class SmallBox2 extends View {
	/*
	 * Created a different class for me to work on, 
	 */
	
	int ID;
	boolean hasFinalValue = false;
	boolean hasPossibleValues = false;
	boolean isEditable = true;
	SudokuPuzzleCell cell1;
	
	public SmallBox2(Context context, SudokuPuzzleCell cell1, int ID) {
		super(context);
		this.cell1 = cell1;
		this.ID = ID;
		
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
		
	}
	
	
}
