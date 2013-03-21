package rhrstudios.rhrsudoku;

import rhrstudios.rhrsudoku.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class FunctionButton extends Button {
	final boolean topDown;
	final String textOrientation;
	
	public FunctionButton(Context context, AttributeSet attrs){
		super(context, attrs);
		final int gravity = getGravity();
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FunctionButton);
		textOrientation = a.getString(R.styleable.FunctionButton_textOrientation);
		
		if(Gravity.isVertical(gravity) && 
				(gravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
			setGravity((gravity&Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
			topDown = false;
		}else
			topDown = true;
	}
	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//		if (!textOrientation.equals("vertical")) {
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//			return;
//		}
//		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
//		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
//	}
//
//	@Override
//	protected void onDraw(Canvas canvas){	
//		if (!textOrientation.equals("vertical")) {
//			super.onDraw(canvas);
//			return;
//		}
//			
//		TextPaint textPaint = getPaint(); 
//		textPaint.setColor(getCurrentTextColor());
//		textPaint.drawableState = getDrawableState();
//
//		canvas.save();
//
//		if(topDown){
//			canvas.translate(getWidth(), 0);
//			canvas.rotate(90);
//		}else {
//			canvas.translate(0, getHeight());
//			canvas.rotate(-90);
//		}
//
//		canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
//		getLayout().draw(canvas);
//		canvas.restore();
//	}
	

}
