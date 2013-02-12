package com.example.rhrsudoku;

public class SudokuPuzzleCell {
	/*
	 * represent a cell in a sudoku puzzle. not gui related class
	 */
	
	static final int GENERATED = 1;
	static final int USER_INPUT = 2;
	static final int NONE = 3;
	static final int SOLVER_GENERATED = 4;
	
	int row, column;// from 0-8
	boolean hasValue = false;
	boolean isEditable = true;
	private int value;
	int inputMethod = NONE;			// change when set by a generator or the user resp.
	SudokuPuzzleCell[] neighbours;
	
	public SudokuPuzzleCell(int row, int column){
		if (row<0 || row>8 || column<0 || column>8) {
			System.err.println("Invalid (row,column)");
			return;
		}
		this.row = row;
		this.column = column;
	}
	
	public void setValue(int value) {
		if (this.isEditable) {
			this.value = value;
			this.hasValue = true;
		}
	}
	
	public void setInput(int inputMethod) {	//should be called after setValue, to set isEditable as false for generated values
		this.inputMethod = inputMethod;
		if (inputMethod == GENERATED || inputMethod == SOLVER_GENERATED) {
			this.isEditable = false;
		}
	}
	
	public int getValue() {
		if (this.hasValue)
			return value;
		else
			return (Integer) null;
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
	
}
