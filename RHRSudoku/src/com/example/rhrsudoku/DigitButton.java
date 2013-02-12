package com.example.rhrsudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class DigitButton extends Button {
	Paint paint1, paint2, paint3, paint4, paint5, paint6;
	GameActivity.StateInfo state1; 
	Drawable background;
	/*
	 * paint1	default background colour
	 * paint2	entering final value
	 * paint3	entering final value + selected
	 * paint4	entering possible value
	 * paint5	entering possible value + selected
	 * paint6	text color
	 */

	public DigitButton(Context context, AttributeSet attrs, 
			GameActivity.StateInfo state1) {
		super(context, attrs);
		this.state1 = state1;
		createPaints();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	}
	
	
	final private void createPaints() {
		paint1 = new Paint();
		paint2 = new Paint();
		paint3 = new Paint();
		paint4 = new Paint();
		paint5 = new Paint();
		paint6 = new Paint();
		
		paint1.setColor(Color.LTGRAY);
		paint2.setColor(0x0033B5E5);
		paint3.setColor(0x000099CC);
		paint4.setColor(0x00FFBB33);
		paint5.setColor(0x00FF8800);
		paint6.setColor(Color.BLACK);
	}
	
}
