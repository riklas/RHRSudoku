package com.example.rhrsudoku;

public class SudokuPuzzleWithSolution {
	SudokuPuzzle puzzle;
	SudokuPuzzle solution;
	
	public SudokuPuzzleWithSolution(SudokuPuzzle puzzle, SudokuPuzzle solutions) {
		this.puzzle = puzzle;
		this.solution = solution;
		if (!solution.isSolved()) {
			System.err.println("Error: unsolved solution received");
			System.exit(1);
		}
		if (puzzle.isConflicting() || !puzzle.isValid()) {
			System.err.println("Error: bad puzzle received");
			System.exit(1);
		}
		for (int row=0;row<9;row++)
			for (int col=0;col<9;col++) {
				if (puzzle.puzzle[row][col].hasValue)
					if (puzzle.puzzle[row][col].getValue() != solution.puzzle[row][col].getValue()) {
						System.err.println("Error: solution does not solve puzzle received");
						System.exit(1);
					}
			}
	}

}
