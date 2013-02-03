package com.example.rhrsudoku;

public class PuzzleTester1 {
	public static void main(String[] args) {
		PuzzleTester1 puzzleTester = new PuzzleTester1();
		puzzleTester.runPuzzle();
	}
	
	void runPuzzle() {
		HardcodedPuzzles HCP = new HardcodedPuzzles();
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.puzzle = HCP.getPuzzle(0);
		puzzle.printPuzzle();
		System.out.println("=================");
		SudokuSolver solver = new SudokuSolver();
		SudokuPuzzle solvedPuzzle = solver.solvePuzzle(puzzle);
		solvedPuzzle.printPuzzle();
		System.out.println("=====================");
	}
}
