package com.example.rhrsudoku;

public interface SudokuGenerator {
	SudokuPuzzleCell[][] getPuzzle(int difficulty);
	SudokuPuzzleCell[][] setValues(SudokuPuzzleCell[][] targetPuzzle, int[][] hardcodePuzzle);
}
