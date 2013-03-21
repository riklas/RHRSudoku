package com.example.rhrsudoku;

import android.os.Bundle;

public class SudokuPuzzleCell {
	/*
	 * represent a cell in a sudoku puzzle. not gui related class
	 */
	
	static final int GENERATED = 1; 
	static final int USER_INPUT = 2;
	static final int NONE = 3;
	static final int HINT_GENERATED = 4;
	static final int SOLVER_GENERATED = 5;
	
	/* 
	 * BEGIN STATE INFO TO SAVE
	 */
	int row, col;// from 0-8
	boolean hasValue = false;
	boolean isEditable = true;
	int solution = 0;
	boolean hasSolution = false;
	private int value;
	int inputMethod = NONE;			// change when set by a generator or the user resp.
	
	/*
	 * END STATE INFO TO SAVE
	 */
	SudokuPuzzleCell[] neighbours;
	SmallBox box1;
	
	public SudokuPuzzleCell(int row, int column){
		if (row<0 || row>8 || column<0 || column>8) {
			System.err.println("Invalid (row,column)");
			return;
		}
		this.row = row;	
		this.col = column;
	}
	
	public Bundle createSaveBundle() {
		Bundle bundle = new Bundle();
		bundle.putBoolean("hasValue", hasValue);
		bundle.putInt("value", value);
		bundle.putInt("row", row);
		bundle.putInt("col", col);
		return bundle;
	}
	
	public void setSmallBox(SmallBox box1) {
		this.box1 = box1;
	}
	
	public void setValue(int value) {
		if (this.isEditable) {
			this.value = value;
			if ((value > 0) && (value < 10))
				this.hasValue = true;
			else
				this.hasValue = false;
		}
	}
	
	public void removeValue() {
		if (this.hasValue) {			
			this.isEditable = true;
			this.hasValue = false;
		}
	}
	
	public void setInput(int inputMethod) {	//should be called after setValue, to set isEditable as false for generated values
		this.inputMethod = inputMethod;
		if (inputMethod == GENERATED || 
				inputMethod == SOLVER_GENERATED || inputMethod == HINT_GENERATED) {
			this.isEditable = false;
		}
	}
	
	public int getInput() {
		return inputMethod;
	}
	
	public Integer getValue() {
		if (this.hasValue)
			return value;
		else
			return null;
	}
	
	public void setNeighbours(SudokuPuzzleCell[] neighbours) {
		this.neighbours = neighbours;
	}
	
	public boolean isConflicting() {
		if(!hasValue)
			return false;
		for (SudokuPuzzleCell c2: neighbours) {
			if(c2.hasValue && (c2.value == value))
				return true;
		}
		return false;
	}
	
	public boolean isValid() {
		if(hasValue && (value<1 || value>9))
			return false;
		else
			return true;
	}
	
	public void setSolution(int solution) {
		this.solution = solution;
		this.hasSolution = true;
		if (solution == 0) {
			System.err.println("Error: Set an invalid solution");
			System.exit(1);
		}
	}

}
