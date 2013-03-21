package com.example.rhrsudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.rhrsudoku.GameActivity.StateInfo;

public class DigitButton extends Button {
	Paint paint1, paint2, paint3, paint4, paint5, paint6;
	GameActivity.StateInfo state1; 
	ShapeDrawable background;
	int number1;
	SudokuPuzzleCell cell2;
	/*
	 * paint
	 * paint1	default background colour
	 * paint2	entering final value
	 * paint3	entering final value + selected
	 * paint4	entering possible value
	 * paint5	entering possible value + selected
	 * paint6	text color
	 */

	public DigitButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.state1 = state1;
		createPaints();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (!state1.hasSelectedSmallBox)
			canvas.drawPaint(paint1);
		else if (state1.selectedSmallBox.cell1.isEditable == false)
			canvas.drawPaint(paint1);
		else {
			cell2 = state1.selectedSmallBox.cell1;
			if (state1.selectingState == StateInfo.SELECTING_FINAL_VALUE)
				if (cell2.hasValue && cell2.getValue() == number1)
					canvas.drawPaint(paint3);
				else
					canvas.drawPaint(paint2);
			else if (state1.selectingState == StateInfo.SELECTING_POSSIBLE_VALUE)
				if(state1.selectedSmallBox.containsPossibleValue(number1))
					canvas.drawPaint(paint5);
				else
					canvas.drawPaint(paint4);
		}

		int xpos = View.MeasureSpec.getSize(getWidth())/2;
		int ypos = (int) ((View.MeasureSpec.getSize(getHeight()) / 2) - ((paint6.descent() + paint6.ascent()) / 2)) ;
		canvas.drawText(Integer.toString(number1), xpos, ypos, paint6);
		
	}
	
	
	final private void createPaints() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		final float textSize1 = 40.0f;
		final float density = displayMetrics.scaledDensity;
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		int smallestSize;
		if (width<height)
			smallestSize = width;
		else
			smallestSize = height;
		int scale = smallestSize/800;
		float orientationScale = 1f;

		paint1 = new Paint();
		paint2 = new Paint();
		paint3 = new Paint();
		paint4 = new Paint();
		paint5 = new Paint();
		paint6 = new Paint();
		
		paint1.setColor(Color.LTGRAY);
		paint2.setColor(0xFF33B5E5);
		paint3.setColor(0xFF0099CC);
		paint4.setColor(0xFFFFBB33);
		paint5.setColor(0xFFFF8800);
		paint6.setColor(Color.BLACK);
		
		paint1.setStyle(Style.FILL);
		paint2.setStyle(Style.FILL);
		paint3.setStyle(Style.FILL);
		paint4.setStyle(Style.FILL);
		paint5.setStyle(Style.FILL);
		
		paint6.setTextAlign(Paint.Align.CENTER);
		paint6.setTextSize(textSize1);
		
		// TextSize expressed in DP
		// Convert the dps to pixels, based on density scale

		
		if (width>height)
			orientationScale = 0.75f;
		int textSize2 = (int) (textSize1 * density * 
				(scale) * orientationScale + 0.5f);
		
		paint6.setTextSize(textSize2);

	}
	
}
