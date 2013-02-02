package com.example.rhrsudoku;

public class HardcodedPuzzles implements SudokuGenerator {

	public HardcodedPuzzles() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public SudokuPuzzleCell[][] getPuzzle(int difficulty) {
		
		SudokuPuzzle puzzle = new SudokuPuzzle();
		
		//get harcodepuzzle (int[][]) from database
		Database hrd = new Database();
		
		//set the values of the smallbox views to that the values retrieved from the database
		//database: [x][y] = "3", -> set .iseditable = false, .hasvalue=true
		//database: [x][y] = null -> set .iseditable = true, .hasvalue=false
		puzzle = setValues(puzzle, int[][] hardcodePuzzle);
		
		return puzzle.puzzle;		
	}
	
	public SudokuPuzzleCell[][] setValues(SudokuPuzzleCell[][] targetPuzzle, int[][] hardcodePuzzle) {
		return targetPuzzle;
	}

}
