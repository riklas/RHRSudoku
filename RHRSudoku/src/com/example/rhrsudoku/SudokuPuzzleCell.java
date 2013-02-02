package com.example.rhrsudoku;

public class SudokuPuzzleCell {
	/*
	 * represent a cell in a sudoku puzzle. not gui related class
	 */
	
	static final int GENERATED = 1;
	static final int USER_INPUT = 2;
	static final int NONE = 3;
	
	int rowNumber, columnNumber; // from 0-8
	boolean hasValue = false;
	boolean isEditable = true;
	int value;
	int inputMethod = NONE;			// change when set by a generator or the user resp.
	SudokuPuzzleCell[] neighbours;
	
	public SudokuPuzzleCell(int rowNumber, int columnNumber){
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
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