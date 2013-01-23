package com.example.rhrsudoku;

public interface SudokuSolver {
	boolean isSolvable(SudokuPuzzle puzzle);
	int numberOfSolutions(SudokuPuzzle puzzle);
	SudokuPuzzle solvePuzzle(SudokuPuzzle puzzle);
}
