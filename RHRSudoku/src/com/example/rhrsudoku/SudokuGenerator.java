package com.example.rhrsudoku;

public interface SudokuGenerator {
	SudokuPuzzle getPuzzle(int difficulty);
	SudokuPuzzleCell[][] setValues(SudokuPuzzleCell[][] targetPuzzle, int[][] hardcodePuzzle);
}
