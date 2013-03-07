package com.example.rhrsudoku;

import java.util.Random;

public class HardcodedPuzzles2 implements SudokuGenerator  {
	final static int puzzlesM = 1;
	final static int difficultyLevelsM = 4;
	Random randGen;
		
	static int[][][][] puzzles = new int[difficultyLevelsM][puzzlesM][9][9];
	static int[][][][] solutions = new int[difficultyLevelsM][puzzlesM][9][9];
	
	public HardcodedPuzzles2() {
		randGen = new Random();
		initHardcodedValues();
	}
	
	public SudokuPuzzleWithSolution getPuzzle(int difficulty) {
		if (!(difficulty < difficultyLevelsM)) {
			System.err.println("Invalid Difficulty!");
			difficulty = 0;
		}
		difficulty = 0; //TEMPORARY TILL WE HAVE PUZZLES FOR OTHER DIFFICULTIES
		int r2 = randGen.nextInt(puzzlesM);
		SudokuPuzzle puzzle1 = createPuzzle(puzzles[difficulty][r2]);
		SudokuPuzzle solution1 = createPuzzle(solutions[difficulty][r2]);
		SudokuPuzzleWithSolution spws2 = new SudokuPuzzleWithSolution(puzzle1, solution1);
		return spws2;
	}
	
	private SudokuPuzzle createPuzzle(int[][] values) {
		SudokuPuzzle puzzle2 = new SudokuPuzzle();
		for(int row=0; row<9; row++) {
			for(int column=0; column<9; column++) {
				if (values[row][column] != 0) {
					puzzle2.puzzle[row][column].setValue(values[row][column]);
					puzzle2.puzzle[row][column].setInput(SudokuPuzzleCell.GENERATED);
				}				
			}
		}
		return puzzle2;
	}
	
	private static void initHardcodedValues() {
		puzzles[0][0] = new int[][]
			{
				{3,0,8,4,0,0,5,0,0},
				{0,2,0,0,9,0,0,0,1},
				{0,6,0,0,0,5,4,8,0},
				{8,0,6,0,4,9,2,0,7},
				{0,0,0,0,0,0,0,0,0},
				{2,0,4,5,1,0,9,0,8},
				{0,7,9,2,0,0,0,5,0},
				{5,0,0,0,6,0,0,9,0},
				{0,0,2,0,0,1,7,0,4}
			};
		solutions[0][0] = new int[][] 
			{
				{3,1,8,4,7,6,5,2,9},
				{4,2,5,8,9,3,6,7,1},
				{9,6,7,1,2,5,4,8,3},
				{8,5,6,3,4,9,2,1,7},
				{7,9,1,6,8,2,3,4,5},
				{2,3,4,5,1,7,9,6,8},
				{1,7,9,2,3,4,8,5,6},
				{5,4,3,7,6,8,1,9,2},
				{6,8,2,9,5,1,7,3,4}
			};
	}
	
}
