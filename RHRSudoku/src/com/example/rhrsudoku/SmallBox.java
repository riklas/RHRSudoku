package com.example.rhrsudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SmallBox extends View {
	
	SmallBox[] neighbours;
	int finalValue;
	int ID;
	boolean hasFinalValue;
	int[] possibleValues;
	boolean isEditable = true;	// when the small boxes are created, boxes with values can be set as isEditable = false
	boolean isSelected;			

	private String text;
	private Paint borderPaint;
	private Paint textPaint;
	
	
	public SmallBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init();
		
		//Creates a TypedArray object that stores the attribute values in the XML tag
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmallBox);
		
		setTextColor(a.getColor(R.styleable.SmallBox_textColor, 0xFF0000));
		
		a.recycle();	//must recycle (send the TypedArray back for re-use) as it is a shared resource
	}
	
	
	private void init() {
		textPaint = new Paint();	//initialise the text paint
		borderPaint = new Paint();	//initialise the borderpaint object
		borderPaint.setColor(0xFFFFFF);	//changes the colour to black
		
		
	}
	
	public void setTextColor(int color) {
		textPaint.setColor(color);
		invalidate();
	}
	
	
	public void setText(int number) {
		//method to set the text of the view, getting it from the SudokuPuzzleCell array
		//int intvalue = getValue();
		text = Integer.toString(number);
		invalidate();	//invalidate the view so system knows it needs to be redrawn
		requestLayout();	//request a new layout 
	}
	
	
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(10, 10, 10, 10, borderPaint);
		canvas.drawText(text, getPaddingLeft(), getPaddingTop(), textPaint);
	}
	
	
	
	
	
	
	
	
	
	boolean isConflicting() {
		if (!hasFinalValue)
			return false;
		for (SmallBox sb : neighbours)
			if (sb.hasFinalValue && (sb.finalValue == finalValue))
				return true;
		return false;
	}
	
	void setFinalValue(int finalValue) {
		this.finalValue = finalValue;
	}
	
	public void setIsSelected() {
		if (isEditable) {
			//if the button can be edited then get final value if present
			if (hasFinalValue) {
				//if final value is present highlight it in numeric pad
			}
		}
	}
	
}	