package com.example.rhrsudoku;

public class SudokuPuzzle {
	/*
	 * This class represent a sudoku puzzle
	 */
	
	private SmallBox[] puzzle;
	
//	public SudokuPuzzle() {
//		SmallBox[][] puzzle2 = new SmallBox[9][9];
//		for (int i=0;i<puzzle2.length;i++)
//			for (int j=0;j<puzzle2[i].length;j++)
//				puzzle2[i][j] = new SmallBox();
//		setPuzzle(puzzle2);
//	}
	
	public SudokuPuzzle(SmallBox[] puzzle) {
		setPuzzle(puzzle);
	}
	
	private void setPuzzle(SmallBox[] puzzle) {
		this.puzzle = puzzle;
	}
	
	boolean isConflicting() {
		// Conflicting is defined as no two numbers in the same row/column/big box conflicting
			for (SmallBox box : puzzle)
				if (box.isConflicting())
					return true;
		return false;
	}

}

