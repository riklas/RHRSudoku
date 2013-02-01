package com.example.rhrsudoku;

public class HardcodedPuzzles implements SudokuGenerator {

	public HardcodedPuzzles() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public SudokuPuzzle getPuzzle(int difficulty) {
		//get array of smallbox view. 
		SmallBox[] segPuzzle = new SmallBox[81];
		
		//set the values of the smallbox views to that the values retrieved from the database
		//create sudoku puzzle object passing in the array of small boxes
		SudokuPuzzle puzzle = new SudokuPuzzle(segPuzzle);
		return puzzle;
	}

}
