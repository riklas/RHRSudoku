package com.example.rhrsudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

		Iterator it = p.setX.iterator();
		while (it.hasNext()) {
			mapX.put(id, (E) it.next() );
			id++;
		}
		id=1;
		it = p.setS.iterator();
		while (it.hasNext()) {
			mapS.put(id, (Set<E>) it.next());
			id++;
		}
		
		Matrix matrix = buildMatrix(p, mapX, mapS);
		Set<Integer> result1 = solveMatrix(matrix);
		if (result1 == null)
			return null;
		Set<Set<E>> result2 = new HashSet<Set<E>>();
		it = result1.iterator();
		while(it.hasNext()) {
			result2.add(mapS.get(it.next()));
		}
		return result2;
	}
	
	Set<Integer> solveMatrix(Matrix matrix) {
		/*				return null;

		 * try to solve the matrix. if solvable, return the set of integers
		 * identifying the subsets/rows
		 * if unsolvable, return null
		 */
		
		
		return null;// CHANGE
	}
	
	Matrix buildMatrix(ExactCoverProblem<E> p, BiMap<Integer, E> mapX,
			BiMap<Integer, Set<E>> mapS ) {
		/*
		 * 1) build the headers (rows and columns)
		 * 2) build the cells, linking with each othert and the headers
		 * 3) build the Matrix
		 */
		MatrixHeaderStarter columnStarter, rowStarter;
		columnStarter = new MatrixHeaderStarter(null);
		rowStarter = new MatrixHeaderStarter(null);
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
				MatrixCellHeader header = new MatrixCellHeader(entry.getKey(), null, null);
				headersStarter.starter = header;
				headersStarter.starter.next = headersStarter.starter;
				headersStarter.starter.previous = headersStarter.starter;
				initializedStarter = true;
				continue;
			}
			
			MatrixCellHeader columnHeader = new MatrixCellHeader(entry.getKey(),
					headersStarter.starter, headersStarter.starter.next);
			
			columnHeader.previous.next = columnHeader;
			columnHeader.next.previous = columnHeader;
		}
	}
	
	void initializeMatrixCells(ExactCoverProblem p, BiMap<Integer, E> mapX,
			BiMap<Integer, Set<E>> mapS, Matrix matrix) {
		/*
		 * iterate through each subset.
		 * for each element, create a new MatrixCell
		 */
		Iterator it = p.setS.iterator();
		while (it.hasNext()) {
			Set<E> subset = (Set<E>) it.next();
			for (E element : subset) {
				int rowID = mapS.inverse().get(subset);
				int columnID = mapX.inverse().get(element);
				MatrixCellHeader rowHeader = matrix.rowStarter.findHeader(rowID).get();
				MatrixCellHeader columnHeader = matrix.columnStarter.findHeader(columnID).get();
				MatrixCell cell = new MatrixCell(columnHeader, rowHeader);
				rowHeader.addCell(cell, Matrix.HORIZONTAL);
				columnHeader.addCell(cell, Matrix.VERTICAL);
			}
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
		MatrixCellHeader next, previous;
		public MatrixCellHeader(int id, MatrixCellHeader previous, MatrixCellHeader next) {
			this.id = id;
			this.next = next;
			this.previous = previous;
		}
		MatrixCell starter;
		boolean starterInitialized = false;
		void addCell(MatrixCell c, int orientation ) {
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
	}
	class MatrixHeaderStarter {
		MatrixCellHeader starter;
		public MatrixHeaderStarter (MatrixCellHeader starter) {
			this.starter = starter;
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
	
	
}
