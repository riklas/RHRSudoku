package com.example.rhrsudoku;

import android.content.Context;
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

	public SmallBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// TODO Auto-generated constructor stub
	}
	
	//public SmallBox(Context context, SmallBox[] neighbours) {
	//	super(context);
	//	this.neighbours = neighbours;
	//}
	
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