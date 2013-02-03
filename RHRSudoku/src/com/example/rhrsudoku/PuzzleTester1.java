package com.example.rhrsudoku;

import java.util.Set;

import com.example.rhrsudoku.SudokuSolver.Choice;

import exactCover.ExactCoverProblem;
import exactCover.ExactCoverSolver;

public class PuzzleTester1 {
	public static void main(String[] args) {
		PuzzleTester1 puzzleTester = new PuzzleTester1();
		puzzleTester.runPuzzle();
	}
	
	void runPuzzle() {
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
		
		SudokuSolver solver1 = new SudokuSolver();
		solver1.solvePuzzle(puzzle);
		puzzle.printPuzzle();
//		ExactCoverSolver<Integer> solver2 = new ExactCoverSolver<Integer>();
//		Set<Set<Integer>> solution = solver2.solve(problem1);
//		for (Set<Integer> subset : solution)  {
//			Choice chc = Choice.ConstraintSetToChoice(subset);
//			System.out.println("Row  " + chc.row + "     Col  " + chc.column + " " +
//					"     Value  " + chc.value);
//		}
	}
}