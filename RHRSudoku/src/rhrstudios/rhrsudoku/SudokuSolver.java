package com.example.rhrsudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exactCover.*;

public class SudokuSolver {
	public static class Choice {
		// A choice is defined as putting a number [1-9] in a cell[1-81] 
		// Thus, there are 729 possible choices
		int row, column, value;
		// row,column,value are all in range [1,9]
			
		public Choice(int row, int column, int value) {
			if (row<0 || row>8 || column<0|| column>8 || value<1 || value>9 )
				System.err.println("ERROR, invalid choice: " + row + "," +
						column + " : " + value);
			this.row = row;
			this.column = column;
			this.value = value; 
		}
		static public Set<Integer> ChoiceToConstraintSet(Choice chc1) {
			// row constaint
			Constraint c1 = new Constraint(Constraint.ROW, chc1.row+1, chc1.value );
			//column constraint
			Constraint c2 = new Constraint(Constraint.COL, chc1.column+1, chc1.value);
			// Box constraint
			Constraint c3 = new Constraint(Constraint.BOX, getBoxNumber(chc1.row+1,chc1.column+1), chc1.value );
			// Cell Constraint
			Constraint c4 = new Constraint(Constraint.CELL, chc1.row+1, chc1.column+1);
			
			Set<Integer> constraintSet = new HashSet<Integer>();
			constraintSet.add(Constraint.ConstraintToInt(c1));
			constraintSet.add(Constraint.ConstraintToInt(c2));
			constraintSet.add(Constraint.ConstraintToInt(c3));
			constraintSet.add(Constraint.ConstraintToInt(c4));
	
			return constraintSet;
		}
		
		public static Choice ConstraintSetToChoice(Set<Integer> constraintSet) {
			Iterator<Integer> it = constraintSet.iterator();
			int row,column,value;
			row = column = value = -1;
			while(it.hasNext()) {
				Constraint cnst1 = Constraint.IntToConstraint(it.next());
				if (cnst1.constraintType == Constraint.CELL) {
					row = cnst1.X-1; column = cnst1.Y-1;
				}
				else if (cnst1.constraintType == Constraint.ROW) {
					row = cnst1.X-1;	value = cnst1.Y;
				}
				else if (cnst1.constraintType == Constraint.COL) {
					column = cnst1.X-1; value = cnst1.Y;
				}
				if ((row != -1) && (column != -1) && (value !=-1))
					break;
			}
			
//			int row = Constraint.IntToConstraint(constraintSet[0]).X;
//			int column = Constraint.IntToConstraint(constraintSet[1]).X;
//			int value = Constraint.IntToConstraint(constraintSet[0]).Y;
			return new Choice(row, column, value);
		}
		
		private static int getBoxNumber(int row, int col) {
			int answer = 3*((row-1)/3) + ((col-1)/3) +1;
			return answer;
		}
		
		static int ChoiceToInt(Choice c) {
			// answer is [0,728]
			int answer;
			answer = (81*(c.row)) + (9*(c.column)) + (c.value-1);
			return answer;
		}
		static Choice IntToChoice(int i) {
			// int is in range [0,728]
			int row = i/81;
			int tmp1 = i-(81*(row));
			int column =  tmp1/9;
			tmp1 = tmp1 - (9*(column));
			int value = tmp1 + 1;
			return new Choice(row, column, value);
		}
		
		public void printChoice() {
			System.out.println("Cell ("+row+","+column+") = "+value);
		}
	}
	public static class Constraint {
		/*
		 * A contraint is something which must be filled exactly once
		 * read constraint as :
		 * Type X must contain a value of Y  (for Row, Col,Box)
		 * or: Cell[X][Y] must contain a value (for CELL)
		 * for Box constraint, X has values:
		 *  1  2  3
		 *  4  5  6
		 *  7  8  9
		 *  there are 324 possible constraints
		 */
		static final int ROW = 0;
		static final int COL = 1;
		static final int BOX = 2;
		static final int CELL = 3;
		int X, Y;
		int constraintType;
		public Constraint(int constraintType, int X, int Y){
			if (X<1 || X>9 || Y<1 || Y>9)
				System.err.println("Invalid Constraint Specification: " + constraintType + " " + X + " " + Y);
			this.constraintType = constraintType;
			this.X = X;
			this.Y = Y;
			}
		static public int ConstraintToInt(Constraint c) {
			// returns an int in the range [0,323] inclusive
			// identifying this constaint
			int answer = ((81*(c.constraintType))+(9*(c.X-1))+c.Y-1);
			return answer;
			
		}
		static public Constraint IntToConstraint(int i) {
			//i is [0,323]
			int constraintType = ((i)/81);
			int tmp1 = i - (81*(constraintType));
			int X = (tmp1/9) + 1;
			tmp1 = tmp1 - (9*(X-1));
			int Y = tmp1 + 1;
			return new Constraint(constraintType, X, Y);
		}
		public void printConstraint() {
			switch(constraintType) {
				case(ROW):
					System.out.println("Constraint: Row " + (X-1) + 
							" contains a  " + Y);
					break;
				case(COL):
					System.out.println("Constraint: Column " + (X-1) + 
							" contains a " + Y);
					break;
				case(BOX):
					System.out.println("Constraint: BOX " + (X-1) + 
							" contains a " + Y);
					break;
				case(CELL):
					System.out.println("Constraint: Cell (" + (X-1) + 
							"," + (Y-1) + ") contains a value");
					break;
			}
		}
	}
	
//	int isSolvable(SudokuPuzzle puzzle) {
//		// this function returns either UNSOLVED, SINGLE_SOLUTION or MULTIPLE_SOLUTIONS
//		// they are static finals in ExactCoverSolver
//		ExactCoverProblem<Integer> problem1 = puzzleToExactCover(puzzle);
//		ExactCoverSolver<Integer> solver1 = new ExactCoverSolver();
//		return solver1.isSolvable(problem1);
//	}
	
	Quant solutionsM(SudokuPuzzle puzzle) {
		ExactCoverProblem<Integer> p1 = puzzleToExactCover(puzzle);
		ExactCoverSolver<Integer> s1 = new ExactCoverSolver<Integer>();
		Quant solutions = s1.solutionsM(p1);
		return solutions;
	}
	
	SudokuPuzzle solvePuzzle(SudokuPuzzle puzzle2) {
		// convert into exactcoverproblem
		// solve
		// convert constraint sets of answer into choices
		// use choices to fill in sudoku puzzle
		// check it is solved
		// return it
		SudokuPuzzle puzzle = puzzle2.copyPuzzle();
		ExactCoverProblem<Integer> p1 = puzzleToExactCover(puzzle);
		ExactCoverSolver<Integer> s1 = new ExactCoverSolver<Integer>();
		Set<Set<Integer>> solution =  s1.solve(p1);
		if (solution == null) {
			System.out.println("No solution found");
			return null;
		}
		for (Set<Integer> subset : solution) {
			Choice chc1 = Choice.ConstraintSetToChoice(subset);
			SudokuPuzzleCell cell1 = puzzle.puzzle[chc1.row][chc1.column];
			if (cell1.hasValue)
				continue;
			cell1.setValue(chc1.value);
			cell1.setInput(SudokuPuzzleCell.SOLVER_GENERATED);
		}
		if (puzzle.isSolved())
			return puzzle;
		else {
			System.err.println("Error Solving Puzzle");
			return null;
		}
	
	}
	
	ExactCoverProblem<Integer> puzzleToExactCover(SudokuPuzzle puzzle) {
		if (puzzle.isConflicting()) {
			System.out.println("Puzzle is Conflicting");
			return null;
		}
		// create a setS containing 324 subsets, each of size 9
		// create a setX containing 729 elements,
		// give each subset in setS an ID
		// create another setS containing 729 subsets
		
		//setX will contain 324 elements (ints identifying a constraint)
		//setS will contain 729 subsets of ints, each of size 4
		Set<Integer> setX = new HashSet<Integer>();
		for (int i=0;i<324;i++)
			setX.add(i);
		
		Set<Set<Integer>> setS = new HashSet<Set<Integer>>();
		for (int i=0;i<729;i++) {
			Choice c = Choice.IntToChoice(i);
			setS.add(Choice.ChoiceToConstraintSet(c));
		}
		
		Set<Set<Integer>> setW = new HashSet<Set<Integer>>();
		for (SudokuPuzzleCell[] cell1 : puzzle.puzzle) {
			for (SudokuPuzzleCell cell2 : cell1) {
				if (cell2.hasValue) {
					if (!cell2.isValid())
						System.err.println("Cell "+cell2.row + "," +
								" " + cell2.col + " has invalid value of " + cell2.getValue());
					else {
						Choice chc1 = new Choice(cell2.row, cell2.col, cell2.getValue());
						setW.add(Choice.ChoiceToConstraintSet(chc1));
					}
				}
			}
		}
		return new ExactCoverProblem<Integer>(setX, setS, setW);
	}
}


