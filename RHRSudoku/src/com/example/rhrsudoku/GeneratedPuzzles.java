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
		
		//this returns a solved puzzle
		puzzle = solver.solvePuzzle(puzzle);		
		
		//this returns puzzle with values taken out
		puzzle = setValues(puzzle);
		
		return puzzle;
	}

	
	public SudokuPuzzle setValues(SudokuPuzzle targetPuzzle) {
		
		targetPuzzle = removeValue(targetPuzzle);
		
		// set the input method 
		for(int row=0; row<9; row++) {
			for(int column=0; column<9; column++) {
				if (targetPuzzle.puzzle[row][column].getValue() != 0) {
					targetPuzzle.puzzle[row][column].setInput(SudokuPuzzleCell.GENERATED); //set input method as generated
				}
			}
		}
		
		return targetPuzzle;
	}
	
	public SudokuPuzzle removeValue(SudokuPuzzle targetPuzzle) {
		Quant numSolutions;
		int randx;
		int randy;
		int value;
		do {
			Random randomGenerator = new Random();
			randx = randomGenerator.nextInt(9);	//generates random number from 0 - 8
			randy = randomGenerator.nextInt(9);	// generates random number from 0 - 8
			value = targetPuzzle.puzzle[randx][randy].getValue();	//saves the value from the index of random number
			targetPuzzle.puzzle[randx][randy].setValue(0);  // set the value at index to 0
			targetPuzzle.puzzle[randx][randy].setInput(SudokuPuzzleCell.NONE); //set input method to none
			numSolutions = solver.solutionsM(targetPuzzle);	//see the number solutions with the value taken out
		}
		while (numSolutions == Quant.ONE); // if there is 1 solution left do it again
		
		targetPuzzle.puzzle[randx][randy].setValue(value);	//otherwise put the value back
			return targetPuzzle;	//return the puzzle
		}			
};

