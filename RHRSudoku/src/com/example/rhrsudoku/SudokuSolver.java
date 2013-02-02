package com.example.rhrsudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exactCover.*;

public class SudokuSolver {
	static class Choice {
		// A choice is defined as putting a number [1-9] in a cell[1-81]
		// Thus, there are 729 possible choices
		int row, column, value;
		// row,column,value are all in range [1,9]
			
		public Choice(int row, int column, int value) {
			if (row<1 || row>9 || column<1 || column>9 || value<1 || value>9 )
				System.out.println("ERROR, invalid choice");
			this.row = row;
			this.column = column;
			this.value = value;
		}
		static public Set<Integer> ChoiceToConstraintSet(Choice chc1) {
			// row constaint
			Constraint c1 = new Constraint(Constraint.ROW, chc1.row, chc1.value );
			//column constraint
			Constraint c2 = new Constraint(Constraint.COL, chc1.column, chc1.value);
			// Box constraint
			Constraint c3 = new Constraint(Constraint.BOX, getBoxNumber(chc1.row,chc1.column), chc1.value );
			// Cell Constraint
			Constraint c4 = new Constraint(Constraint.CELL, chc1.row, chc1.column);
			
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
				Constraint c1 = Constraint.IntToConstraint(it.next());
				if (c1.constraintType == Constraint.CELL) {
					row = c1.X; column = c1.Y;
				}
				else if (c1.constraintType == Constraint.ROW) {
					row = c1.X;	value = c1.Y;
				}
				else if (c1.constraintType == Constraint.COL) {
					column = c1.X; value = c1.Y;
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
			int answer = 3*((row-1)/3) + ((col-1)/3) + 1;
			return answer;
		}
		
		static int ChoiceToInt(Choice c) {
			// answer is [0,728]
			int answer;
			answer = (81*(c.row-1)) + (9*(c.column-1)) + (c.value-1);
			return answer;
		}
		static Choice IntToChoice(int i) {
			// int is in range [0,728]
			int row = i/81 + 1;
			int tmp1 = i-(81*(row-1));
			int column =  tmp1/9 + 1;
			tmp1 = tmp1 - (9*(column-1));
			int value = tmp1 + 1;
			return new Choice(row, column, value);
		}
	}
	static class Constraint {
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
		static final int ROW = 1;
		static final int COL = 2;
		static final int BOX = 3;
		static final int CELL = 4;
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
			return ((81*(c.constraintType-1))+(9*(c.X-1))+c.Y-1);			
		}
		static public Constraint IntToConstraint(int i) {
			//i is [0,323]
			int constraintType = ((i)/81) +1;
			int tmp1 = i - (81*(constraintType-1));
			int X = (tmp1/9) + 1;
			tmp1 = tmp1 - (9*(X-1));
			int Y = tmp1 + 1;
			return new Constraint(constraintType, X, Y);
		}
	}
	
	boolean isSolvable(SudokuPuzzle puzzle) {
		return false;
		
	}
	int numberOfSolutions(SudokuPuzzle puzzle) {
		return 0;
		
	}
	SudokuPuzzle solvePuzzle(SudokuPuzzle puzzle) {
		return puzzle;
	
	}
	
	ExactCoverProblem<Integer> puzzleToExactCover(SudokuPuzzle puzzle) {
		if (puzzle.isConflicting()) {
			System.out.println("Puzzle is Conflicting");
			return null;
		}
		return null;

	}
	
	ExactCoverProblem<Integer> blankSudokuGrid() {
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
		return new ExactCoverProblem<Integer>(setX, setS);
	}
}


