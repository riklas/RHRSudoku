package com.example.rhrsudoku;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;

public class ExactCoverSolver<E> {	

	public Set<Set<E>> solve(ExactCoverProblem<E> p) {
			/*
			 * we will use the dancing links implementation.
			 * columns represent elements
			 * rows represent subsets of S
			 * 
			 * this function tries to solve the problem,
			 * if a solution is found it is returned
			 * if no solution is found, null is returned
			 * 
			 * 1) each element in SetX is given an identifier
			 * 2) each element in Sets is given an identifier
			 * 3) a matrix is created using the identifiers
			 * 4) a solution is found using dancing links
			 * 5) the identifiers are using to identify the subsets to return
			 * 
			 */
		
		int id = 1;
		BiMap<Integer, E> mapX = HashBiMap.create(p.setX.size());
		BiMap<Integer, Set<E>> mapS = HashBiMap.create(p.setS.size());

		Iterator<E> it = p.setX.iterator();
		while (it.hasNext()) {
			mapX.put(id, (E) it.next() );
			id++;
		}
		id=1;
		it = (Iterator<E>) p.setS.iterator();
		while (it.hasNext()) {
			mapS.put(id, (Set<E>) it.next());
			id++;
		}
		
		Matrix matrix = buildMatrix(p, mapX, mapS);
		Set<Integer> result1 = solveMatrix(matrix);
		if (result1 == null)
			return null;
		Set<Set<E>> result2 = new HashSet<Set<E>>();
		it = (Iterator<E>) result1.iterator();
		while(it.hasNext()) {
			
			result2.add(mapS.get(it.next()));
		}
		return result2;
	}
	
	Set<Integer> solveMatrix(Matrix matrix) {
		Set<Integer> solutions = new HashSet<Integer>();
		/*				return null;

		 * try to solve the matrix. if solvable, return the set of integers
		 * identifying the subsets/rows
		 * if unsolvable, return null
		 */
		
		boolean isSolved = solveMatrix1(matrix, solutions);
		if (isSolved)
			return solutions;
		
		return null;
	}
	
	boolean solveMatrix1(Matrix matrix, Set<Integer> solutions) {
		if(matrix.isEmpty())
			return true;
		return solveMatrix2(matrix, solutions);
		}
	
	boolean solveMatrix2(Matrix matrix, Set<Integer> solutions) {
		MatrixCellHeader chosenColumn = matrix.columnStarter.starter;
		return solveMatrix3(matrix, solutions, chosenColumn);
		
	}
	boolean solveMatrix3(Matrix matrix, Set<Integer> solutions, MatrixCellHeader chosenColumn) {
		if (chosenColumn.isEmpty())
			return false;
		MatrixCellHeader chosenRow = chosenColumn.starter.rowHeader;
		boolean value = solveMatrix4(matrix, solutions, chosenColumn, chosenRow);
		if(value == false) {
			matrix.rowStarter.removeCellHeader(chosenRow);
			return solveMatrix3(matrix, solutions, chosenColumn);			
		}
		else
			return true;
		
	}
	boolean solveMatrix4(Matrix matrix, Set<Integer> solutions, 
			MatrixCellHeader chosenColumn, MatrixCellHeader chosenRow) {
		solutions.add(chosenRow.id);
		LinkedList<MatrixCellHeader> removedHeaders = new LinkedList<MatrixCellHeader>();
		
		for (MatrixCellHeader columnHeader : chosenRow.getIntersectingHeaders()) {
			for (MatrixCellHeader rowHeader2 : columnHeader.getIntersectingHeaders()) {
				matrix.rowStarter.removeCellHeader(rowHeader2);
				removedHeaders.add(rowHeader2);
			}
			matrix.columnStarter.removeCellHeader(columnHeader);
			removedHeaders.add(columnHeader);
		}
		boolean value = solveMatrix1(matrix, solutions);
		if (value == false) {
			while(!removedHeaders.isEmpty()) {
				MatrixCellHeader removedHeader1 = removedHeaders.removeLast();
				if (removedHeader1.orientation == Matrix.HORIZONTAL)
					matrix.rowStarter.restoreCellHeader(removedHeader1);
				else if (removedHeader1.orientation == Matrix.VERTICAL)
					matrix.columnStarter.restoreCellHeader(removedHeader1);
			}
			solutions.remove(chosenRow);
			return false;
		}
		else
			return true;
		
	}
	boolean solveMatrix5() {
		return false;
		
	}
	boolean solveMatrix6() {
		return false;
		
	}
	
	
	Matrix buildMatrix(ExactCoverProblem<E> p, BiMap<Integer, E> mapX,
			BiMap<Integer, Set<E>> mapS ) {
		/*
		 * 1) build the headers (rows and columns)
		 * 2) build the cells, linking with each other and the headers
		 * 3) build the Matrix
		 */
		MatrixHeaderStarter columnStarter, rowStarter;
		columnStarter = new MatrixHeaderStarter(null, Matrix.VERTICAL);
		rowStarter = new MatrixHeaderStarter(null, Matrix.HORIZONTAL);
		initializeHeaders(columnStarter, mapX);
		initializeHeaders(rowStarter, mapS);
		Matrix matrix = new Matrix(columnStarter, rowStarter);
		initializeMatrixCells(p, mapX, mapS, matrix);
		
		return matrix;
		}
	
	void initializeHeaders(MatrixHeaderStarter headersStarter, BiMap<Integer, ?> map) {
		boolean initializedStarter = false;
		for (Map.Entry<Integer, ?> entry : map.entrySet()) {
			
			if (!initializedStarter) {
				MatrixCellHeader header = new MatrixCellHeader(entry.getKey(),
						null, null, headersStarter.orientation);
				headersStarter.starter = header;
				headersStarter.starter.next = headersStarter.starter;
				headersStarter.starter.previous = headersStarter.starter;
				initializedStarter = true;
				continue;
			}
			
			MatrixCellHeader header2 = new MatrixCellHeader(entry.getKey(), headersStarter.starter,
					headersStarter.starter.next, headersStarter.orientation);
			
			header2.previous.next = header2;
			header2.next.previous = header2;
		}
	}
	
	void initializeMatrixCells(ExactCoverProblem<E> p, BiMap<Integer, E> mapX,
			BiMap<Integer, Set<E>> mapS, Matrix matrix) {
		/*
		 * iterate through each subset.
		 * for each element, create a new MatrixCell
		 */
		Iterator<Set<E>> it = p.setS.iterator();
		while (it.hasNext()) {
			Set<E> subset = (Set<E>) it.next();
			for (E element : subset) {
				int rowID = mapS.inverse().get(subset);
				int columnID = mapX.inverse().get(element);
				MatrixCellHeader rowHeader = matrix.rowStarter.findHeader(rowID).get();
				MatrixCellHeader columnHeader = matrix.columnStarter.findHeader(columnID).get();
				MatrixCell cell = new MatrixCell(columnHeader, rowHeader);
				rowHeader.addCell(cell);
				columnHeader.addCell(cell);
			}
		}
	}
	
	class Matrix {
		final static int VERTICAL = 1;
		final static int HORIZONTAL = 2;
		MatrixHeaderStarter columnStarter, rowStarter;
		public Matrix( MatrixHeaderStarter columnStarter, MatrixHeaderStarter rowStarter) {
			this.columnStarter = columnStarter;
			this.rowStarter = rowStarter;
		}
		boolean isEmpty() {
			return columnStarter.isEmpty();		
		}
	}
	class MatrixCell {
		MatrixCell north, east, south, west;
		{ north = east = south = west = this; }

		MatrixCellHeader columnHeader, rowHeader;
		public MatrixCell(MatrixCellHeader columnHeader, MatrixCellHeader rowHeader) {
			this.columnHeader = columnHeader;
			this.rowHeader = rowHeader;
		}		
	}
	class MatrixCellHeader {
		final int id;
		final int orientation;
		boolean isRemoved = false;
		MatrixCellHeader next, previous;
		public MatrixCellHeader(int id, MatrixCellHeader previous, MatrixCellHeader next,
				int orientation) {
			this.id = id;
			this.next = next;
			this.previous = previous;
			this.orientation = orientation;
		}
		MatrixCell starter;
		boolean starterInitialized = false;
		void addCell(MatrixCell c) {
			if (!starterInitialized) {
				starter = c;
				starterInitialized = true;
				return;
			}
			if (orientation == Matrix.VERTICAL) {
				c.north = starter;
				c.south = starter.south;
				c.north.south = c;
				c.south.north = c;
			}
			else if (orientation == Matrix.HORIZONTAL) {
				c.west = starter;
				c.east = starter.east;
				c.west.east = c;
				c.east.west = c;
			}
		}
		boolean isEmpty() {
			if (starter == null)
				return true;
			return false;
		}
		void removeCells() {
			if (isEmpty()) {
				return;
			}
			if (orientation == Matrix.HORIZONTAL) {
				int startingColumnID = starter.columnHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if (cell1.columnHeader.starter == cell1)
						cell1.columnHeader.starter = cell1.south;
					cell1.north.south = cell1.south;
					cell1.south.north = cell1.north;
					if (cell1.columnHeader.starter == cell1)
						cell1.columnHeader.starter = null;
					
					cell1 = cell1.east;
					if(cell1.columnHeader.id == startingColumnID)
						break;
				}
			}
			else if (orientation == Matrix.VERTICAL) {
				int startingRowID = starter.rowHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if (cell1.rowHeader.starter == cell1)
						cell1.rowHeader.starter = cell1.east;
					cell1.east.west = cell1.west;
					cell1.west.east = cell1.east;
					if (cell1.rowHeader.starter == cell1)
						cell1.rowHeader.starter = null;
					cell1 = cell1.south;
					if (cell1.rowHeader.id == startingRowID)
						break;
				}
			}
		}
		
		void restoreCells() {
			if(!isRemoved)
				return;
			if (isEmpty())
				return;
			if (orientation == Matrix.HORIZONTAL) {
				int startingID = starter.columnHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if(cell1.columnHeader.starter == null)
						cell1.columnHeader.starter = cell1;
					cell1.north.south = cell1;
					cell1.south.north = cell1;
					cell1 = cell1.east;
					if (cell1.columnHeader.id == startingID)
						break;
				}
			}
			else if (orientation == Matrix.VERTICAL) {
				int startingID = starter.rowHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if(cell1.rowHeader.starter == null)
						cell1.rowHeader.starter = cell1;
					cell1.west.east = cell1;
					cell1.east.west = cell1;
					cell1 = cell1.south;
					if (cell1.rowHeader.id == startingID)
						break;
				}
			}
		}
		Set<MatrixCellHeader> getIntersectingHeaders() {
			Set<MatrixCellHeader> headers = new HashSet<MatrixCellHeader>();
			if (isEmpty())
				return headers;
			MatrixCell cell = starter;
			if (orientation == Matrix.HORIZONTAL) {
				int startingID = cell.columnHeader.id;
				while(true) {
					headers.add(cell.columnHeader);
					cell = cell.east;
					if(cell.columnHeader.id == startingID)
						break;
				}
			}
			else if (orientation == Matrix.VERTICAL) {
				int startingID = cell.rowHeader.id;
				while(true) {
					headers.add(cell.rowHeader);
					cell = cell.south;
					if (cell.rowHeader.id == startingID)
						break;
				}
				
			}
			return headers;
		}
	}
	class MatrixHeaderStarter {
		MatrixCellHeader starter;
		final int orientation;
		public MatrixHeaderStarter (MatrixCellHeader starter, int orientation) {
			this.starter = starter;
			this.orientation = orientation;
		}
		
		boolean isEmpty() {
			if(starter == null)
				return true;
			return false;
		}
		
		Set<MatrixCellHeader> getCellHeaders() {
			Set<MatrixCellHeader> headers = new HashSet<MatrixCellHeader>();
			if (isEmpty())
				return headers;
			MatrixCellHeader header1 = starter;
			while(true) {
				headers.add(header1);
				header1 = header1.next;
				if (header1 == starter)
					break;
			}
			return headers;
		}
		
		Optional<MatrixCellHeader> findHeader(int ID) {
			int startingID = starter.id;
			MatrixCellHeader header1 = starter;
			while(true) {
				if (header1.id == ID)
					return Optional.of(header1);
				else
					header1 = header1.next;
				if (header1.id == startingID)
					return Optional.absent();
			}
		}
		
		void removeCellHeader(MatrixCellHeader header) {
			if (header.isRemoved)
				return;
			header.removeCells();
			if (header == starter)
				starter = header.next;
			header.previous.next = header.next;
			header.next.previous = header.previous;
			if (header == starter)
				starter = null;
			header.isRemoved = true;
		}
		
		void restoreCellHeader(MatrixCellHeader header) {
			if(!header.isRemoved)
				return;
			if (header.orientation != orientation)
				return;
			header.restoreCells();
			if (isEmpty()) {
				starter = header;
				return;
			}
			header.previous.next = header;
			header.next.previous = header;
			header.isRemoved = false;
		}
	}
}







