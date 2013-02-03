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
		puzzle.printPuzzle();
		//set the values of the smallbox views to that the values retrieved from the database
		puzzle.puzzle = setValues(puzzle.puzzle, hrd.hardcodePuzzle);
		puzzle.printPuzzle();
		return puzzle.puzzle;		
	}
	
	public SudokuPuzzleCell[][] setValues(SudokuPuzzleCell[][] targetPuzzle, int[][] hardcodePuzzle) {
		for(int row=0; row<9; row++) {
			for(int column=0; column<9; column++) {
				if (hardcodePuzzle[row][column] != 0) {
					targetPuzzle[row][column].setValue(hardcodePuzzle[row][column], SudokuPuzzleCell.GENERATED);
				}				
			}
		}
		return targetPuzzle;
	}

}
