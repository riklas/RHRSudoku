package com.example.rhrsudoku;

import android.content.Context;
import android.view.View;

public class SmallBox extends View {
	
	SmallBox[] neighbours;
	int finalValue;
	int ID;
	boolean hasFinalValue;
	int[] possibleValues;
	boolean isEditable = true;	// when the small boxes are created, boxes with values can be set as isEditable = false
	boolean isSelected;			

	public SmallBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SmallBox(Context context, SmallBox[] neighbours) {
		super(context);
		this.neighbours = neighbours;
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
	
}