package com.example.rhrsudoku;

import java.util.Random;

import exactCover.Quant;

public class GeneratedPuzzles implements SudokuGenerator {

	SudokuPuzzle puzzle = new SudokuPuzzle();
	SudokuSolver solver = new SudokuSolver();
	Quant currentSolutions;
	
	public GeneratedPuzzles() {
	
	}

	@Override
	public SudokuPuzzle getPuzzle(int difficulty) {
		
		puzzle = solver.solvePuzzle(puzzle);
		//this returns a solved puzzle
		
		
		return puzzle;
	}

	
	public SudokuPuzzle setValues(SudokuPuzzle targetPuzzle) {
		Quant numSolutions;
		do {
			numSolutions = removeValue(targetPuzzle);
		}
		while (numSolutions == Quant.ONE);
	
			//put temp value back
		return targetPuzzle;
	}
	
	public Quant removeValue(SudokuPuzzle targetPuzzle) {
		Random randomGenerator = new Random();
		int randx = randomGenerator.nextInt(9) - 1;
		int randy = randomGenerator.nextInt(9) - 1;
		int value = targetPuzzle.puzzle[randx][randy].getValue();
		targetPuzzle.puzzle[randx][randy].setValue(0, SudokuPuzzleCell.NONE);
		Quant numSolutions = solver.solutionsM(targetPuzzle);
		if (numSolutions != Quant.ONE) {
			targetPuzzle.puzzle[randx][randy].setValue(value, SudokuPuzzleCell.GENERATED);
		}
		return numSolutions;
	}

	
}

