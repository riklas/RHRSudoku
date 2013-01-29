package com.example.rhrsudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
		Map<Integer, E> mapX = new HashMap<Integer, E>();
		Map<Integer, Set<E>> mapS = new HashMap<Integer, Set<E>>();
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
		/*
		 * try to solve the matrix. if solvable, return the set of integers
		 * identifying the subsets/rows
		 * if unsolvable, return null
		 */
		
		
		return null;
	}
	
	Matrix buildMatrix(ExactCoverProblem<E> p, Map<Integer, E> mapX,
			Map<Integer, Set<E>> mapS ) {
		/*
		 * 1) build the headers (rows and columns)
		 * 2) build the cells, linking with each othert and the headers
		 * 3) build the Matrix
		 */
		MatrixHeaderStarter columnStarter, rowStarter;
		columnStarter = new MatrixHeaderStarter(null);
		rowStarter = new MatrixHeaderStarter(null);
		boolean initializedStarter = false;
		for (Map.Entry<Integer, E> entry : mapX.entrySet()) {
			
			if (!initializedStarter) {
				MatrixCellHeader header = new MatrixCellHeader(entry.getKey(), null, null);
				columnStarter.starter = header;
				columnStarter.starter.next = columnStarter.starter;
				columnStarter.starter.previous = columnStarter.starter;
				initializedStarter = true;
				continue;
			}
			
			MatrixCellHeader columnHeader = new MatrixCellHeader(entry.getKey(),
					columnStarter.starter, columnStarter.starter.next);
			
			columnStarter.starter.next.previous = columnHeader;
			columnStarter.starter.next = columnHeader;
			
		}
		
		/*
		 * Column headers have now been initialized
		 */
		
				return null;
	}
	
	class Matrix {
		MatrixHeaderStarter starter;
	}
	class MatrixCell {
		MatrixCell north, east, south, west;
		MatrixCellHeader columnHeader, rowHeader;
	}
	class MatrixCellHeader {
		final int id;
		MatrixCellHeader next, previous;
		public MatrixCellHeader(int id, MatrixCellHeader previous, MatrixCellHeader next) {
			this.id = id;
			this.next = next;
			this.previous = previous;
		}
	}
	class MatrixHeaderStarter {
		MatrixCellHeader starter;
		public MatrixHeaderStarter (MatrixCellHeader starter) {
			this.starter = starter;
		}
	}
	
}
