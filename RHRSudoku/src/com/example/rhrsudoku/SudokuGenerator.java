package com.example.rhrsudoku;

public interface SudokuGenerator {
	SudokuPuzzleWithSolution getPuzzle(int difficulty);
	//SudokuPuzzleCell[][] setValues(SudokuPuzzleCell[][] targetPuzzle, int[][] hardcodePuzzle);
}
