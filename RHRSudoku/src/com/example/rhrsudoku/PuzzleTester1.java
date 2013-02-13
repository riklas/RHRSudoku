package com.example.rhrsudoku;

import java.util.Set;
import static exactCover.ExactCoverSolver.*;

import com.example.rhrsudoku.SudokuSolver.Choice;

import exactCover.ExactCoverProblem;
import exactCover.ExactCoverSolver;
import exactCover.Quant;

public class PuzzleTester1 {
	public static void main(String[] args) {
		PuzzleTester1 puzzleTester = new PuzzleTester1();
		puzzleTester.runPuzzle1();
	}
	
	void runPuzzle1() {
		PuzzleTester1 tester1 = new PuzzleTester1();
		SudokuSolver solver1 = new SudokuSolver();
		GeneratedPuzzles HCP = new GeneratedPuzzles();
		SudokuPuzzle puzzle1 = HCP.getPuzzle(3);
		puzzle1.printPuzzle();
		System.out.println("SOLVING......");
		SudokuPuzzle puzzle2 = solver1.solvePuzzle(puzzle1);
		puzzle2.printPuzzle();
	}
	
	void runPuzzle2() {
		SudokuPuzzle puzzle = new SudokuPuzzle();
//		puzzle.puzzle[0][0].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][1].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][2].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][3].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][4].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][5].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][6].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][7].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[0][8].setValue(9, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[1][0].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][1].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][2].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][3].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][4].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][5].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][6].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][7].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[1][8].setValue(6, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[2][0].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][1].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][2].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][3].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][4].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][5].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][6].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][7].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[2][8].setValue(3, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[3][0].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][1].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][2].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][3].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][4].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][5].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][6].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][7].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[3][8].setValue(8, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[4][0].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][1].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][2].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][3].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][4].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][5].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][6].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][7].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[4][8].setValue(7, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[5][0].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][1].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][2].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][3].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][4].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][5].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][6].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][7].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[5][8].setValue(4, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[6][0].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][1].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][2].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][3].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][4].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][5].setValue(1, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][6].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][7].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[6][8].setValue(5, SudokuPuzzleCell.GENERATED);
//		
//		puzzle.puzzle[7][0].setValue(3, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][1].setValue(9, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][2].setValue(5, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][3].setValue(2, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][4].setValue(6, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][5].setValue(4, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][6].setValue(8, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][7].setValue(7, SudokuPuzzleCell.GENERATED);
//		puzzle.puzzle[7][8].setValue(1, SudokuPuzzleCell.GENERATED);
		
		puzzle.printPuzzle();
		SudokuSolver solver1 = new SudokuSolver();
		System.out.println("SOLVING......");
		Quant solutions = solver1.solutionsM(puzzle);
		System.out.println("Number of solutions: " + solutions);
		SudokuPuzzle puzzle2 = solver1.solvePuzzle(puzzle);
		if (puzzle2 == null)
			System.out.println("puzzle2 is null");
		else
		puzzle2.printPuzzle();

	}
	
	/*void runPuzzle3() {
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.puzzle[0][0].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[0][3].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[0][5].setValue(6, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[0][8].setValue(9, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[1][2].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[1][5].setValue(3, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[1][8].setValue(7, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][2].setValue(3, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][5].setValue(9, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][6].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[3][6].setValue(1, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[3][7].setValue(2, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[4][3].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[4][5].setValue(1, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[5][1].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[5][2].setValue(6, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][2].setValue(7, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][3].setValue(6, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][6].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[7][0].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[7][3].setValue(9, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[7][6].setValue(6, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][0].setValue(9, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][3].setValue(1, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][5].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][8].setValue(3, SudokuPuzzleCell.GENERATED);



		
		puzzle.printPuzzle();
		SudokuSolver solver1 = new SudokuSolver();
		System.out.println("SOLVING......");
		Quant solutions = solver1.solutionsM(puzzle);
		System.out.println("Number of solutions: " + solutions);
		SudokuPuzzle puzzle2 = solver1.solvePuzzle(puzzle);
		if (puzzle2 == null)
			System.out.println("puzzle2 is null");
		else
		puzzle2.printPuzzle();
	}
	
	void runPuzzle4() {
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.puzzle[0][0].setValue(9, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[0][2].setValue(3, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[0][3].setValue(5, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[0][4].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][1].setValue(1, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][4].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][6].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[2][7].setValue(3, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[3][0].setValue(5, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[3][2].setValue(9, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[3][8].setValue(1, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[4][1].setValue(3, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[4][4].setValue(1, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[4][7].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[5][0].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[5][6].setValue(2, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[5][8].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][1].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][2].setValue(7, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][4].setValue(5, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[6][7].setValue(4, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][4].setValue(2, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][5].setValue(8, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][6].setValue(6, SudokuPuzzleCell.GENERATED);
		puzzle.puzzle[8][8].setValue(9, SudokuPuzzleCell.GENERATED);
		
		puzzle.printPuzzle();
		SudokuSolver solver1 = new SudokuSolver();
		System.out.println("Solving....");
		Quant sols = solver1.solutionsM(puzzle);
		switch(sols) {
			case NONE: System.out.println("No Solutions"); break;
			case ONE: System.out.println("One Solution"); break;
			case MULTIPLE: System.out.println("Multiple Solutions"); break;
		}
		SudokuPuzzle puzzle2 = solver1.solvePuzzle(puzzle);
		puzzle2.printPuzzle();

	}*/
}