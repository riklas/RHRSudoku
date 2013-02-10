package com.example.rhrsudoku;

import java.util.Random;

public class GeneratedPuzzles implements SudokuGenerator {
	SudokuPuzzle puzzle = new SudokuPuzzle();
	int[][] basePuzzle = new int[9][9];
	
	public GeneratedPuzzles() {
		
	}

	@Override
	public SudokuPuzzle getPuzzle(int difficulty) {
		basePuzzle = populate(basePuzzle);
		puzzle.puzzle = setValues(puzzle.puzzle, basePuzzle);
		return puzzle;
	}

	@Override
	public SudokuPuzzleCell[][] setValues(SudokuPuzzleCell[][] targetPuzzle,
			int[][] hardcodePuzzle) {		
		
		//remove a number from the targetPuzzle
		//check if the solutionsM returns "ONE" (number of solutions)
			//then remove another number
		//else put the number back 
		//run the solver to make sure that there is ONE solution
		
		// with difficulties, can think about having more numbers in to have different solutions
		
		
		return targetPuzzle;
	}
	
	public int[][] populate(int[][] basePuzzle) {
		Random randomGenerator = new Random();
		
		//populate the puzzle to form a solved sudoku puzzle
		//(run solver to solve empty puzzle)
		
		return basePuzzle;
	}
	

}
