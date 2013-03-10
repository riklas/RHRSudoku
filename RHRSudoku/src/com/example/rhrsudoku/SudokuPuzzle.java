package com.example.rhrsudoku;

public class SudokuPuzzle {
	/*
	 * This class represent a sudoku puzzle
	 */
	
	SudokuPuzzleCell[][] puzzle = new SudokuPuzzleCell[9][9]; 
	// puzzle[a][b] refers to row a, column b. origin is at top left, with puzzle[0][0];
	//bottom middle cell is puzzle[8][4];
	
	
	public SudokuPuzzle() {
		initPuzzle();
	}
	
	public SudokuPuzzle copyPuzzle() {
		SudokuPuzzle newPuzzle = new SudokuPuzzle();
		for(int row=0;row<9;row++)
			for (int col=0; col<9; col++) {
				SudokuPuzzleCell oldCell1 = puzzle[row][col];
				if(!oldCell1.hasValue)
					continue;
				newPuzzle.puzzle[row][col].setValue(oldCell1.getValue()); 
				newPuzzle.puzzle[row][col].setInput(oldCell1.inputMethod);
			}
		return newPuzzle;
	}
	private void initPuzzle() {
		for (int row=0; row<9; row++) {
			for (int column=0;column<9;column++) {
				puzzle[row][column] = new SudokuPuzzleCell(row, column);
			}
		}
		for (int row=0; row<9; row++) {
			for (int column=0;column<9;column++) {
				SudokuPuzzleCell[] neighbours = new SudokuPuzzleCell[20];
				SudokuPuzzleCell cell1 = puzzle[row][column];
				int i2=0;
				//add all cell in same column to neighbours
				for (int row1=0; row1<9; row1++) {
					if (row1 == cell1.row)
						continue;
					neighbours[i2] = puzzle[row1][cell1.col]; 
					i2++;
				}
				//add all cells in same row to neighbours
				for (int column1=0; column1<9; column1++) {
					if (column1 == cell1.col)
						continue;
					neighbours[i2] = puzzle[cell1.row][column1];
					i2++;
				}
				//add all cells in same big box to neighbours
				int[] rows3 = getOtherBigBoxIndices(cell1.row);
				int[] cols3 = getOtherBigBoxIndices(cell1.col);
				// 19,18,17,16    20,19,18,17
				neighbours[16] = puzzle[rows3[0]][cols3[0]];
				neighbours[17] = puzzle[rows3[1]][cols3[0]];
				neighbours[18] = puzzle[rows3[0]][cols3[1]];
				neighbours[19] = puzzle[rows3[1]][cols3[1]];
				
				cell1.setNeighbours(neighbours);

			}
		}
	
	}
	private int[] getOtherBigBoxIndices(int i) {
		switch(i) {
		case 0: return new int[] {1,2};
		case 1: return new int[] {0,2};
		case 2: return new int[] {0,1};
		case 3: return new int[] {4,5};
		case 4: return new int[] {3,5};
		case 5: return new int[] {3,4};
		case 6: return new int[] {7,8};
		case 7: return new int[] {6,8};
		case 8: return new int[] {6,7};
		}
		return null;
	}
	boolean isConflicting() {
			for (SudokuPuzzleCell[] row1 : puzzle)
				for (SudokuPuzzleCell cell2 : row1)
					if (cell2.isConflicting()) {
						System.err.println("Cell ("+cell2.row + "," +
								cell2.col + ") is conflicting");
						return true;
					}
		return false;
	}
	
	boolean isFilled() {
		for (SudokuPuzzleCell[] row1 : puzzle)
			for (SudokuPuzzleCell cell2 : row1)
				if (!cell2.hasValue)
					return false;
		return true;
	}
	
	boolean solutionIsFilled() {
		for (SudokuPuzzleCell[] row1 : puzzle)
			for (SudokuPuzzleCell cell2 : row1)
				if (!cell2.hasSolution)
					return false;
		return true;
	}
	
	boolean isValid() {
		for (SudokuPuzzleCell[] row1 : puzzle)
			for (SudokuPuzzleCell cell2 : row1)
				if (!cell2.isValid())
					return false;
		return true;
	}
	
	boolean isSolved() {
		if (!isFilled()) {
			System.err.println("Error: Puzzle not filled");
			return false;
		}
		else if (!isValid()) {
			System.err.println("Error: Puzzle is not valid");
			return false;
		}
		else if (isConflicting()) {
			System.out.println("Error: Puzzle is conflicting");
			return false;
		}
		return true;
	}
	
	/*void printPuzzle() {
		System.out.println("=========================");
		for (int row1=0;row1<9;row1++) {
			if (row1==3 || row1==6)
				System.out.println("------------------------");
			for (int col1=0;col1<9;col1++) {
				if (col1==3 || col1==6)
					System.out.print("| ");
				String value;
				if (puzzle[row1][col1].hasValue)
					 value = Integer.toString(puzzle[row1][col1].getValue());
				else
					value = "*";
				System.out.print(value + " ");
			}
			System.out.println();
		}
		System.out.println("=========================");
	}*/

	void printPuzzle() {
	System.out.println("        <puzzle>");
	for (int row1=0;row1<9;row1++) {
		//if (row1==3 || row1==6)
			//System.out.println("------------------------");
		for (int col1=0;col1<9;col1++) {					
			//if (col1==3 || col1==6)
			//	System.out.print("| ");
			String value;
			if (puzzle[row1][col1].hasValue) {
				 value = Integer.toString(puzzle[row1][col1].getValue());
				 System.out.println("                <cell row=\"" + row1 + "\" col=\"" + col1 + "\" val=\"" + value + "\" />");
			}
			else
				value = "0";			
		}
	}
	System.out.println("        </puzzle>");
	}
	
	void printSolution() {
		System.out.println("        <solution>");
		for (int row1=0;row1<9;row1++) {
			//if (row1==3 || row1==6)
				//System.out.println("------------------------");
			for (int col1=0;col1<9;col1++) {					
				//if (col1==3 || col1==6)
				//	System.out.print("| ");
				String value;
				if (puzzle[row1][col1].hasValue)
					 value = Integer.toString(puzzle[row1][col1].getValue());
				else
					value = "0";
				System.out.println("                <cell row=\"" + row1 + "\" col=\"" + col1 + "\" val=\"" + value + "\" />");
			}
		}
		System.out.println("        </solution>");
		}

}

