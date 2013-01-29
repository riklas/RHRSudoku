package com.example.rhrsudoku;

import java.util.Iterator;
import java.util.Set;

public class ExactCoverSolver<E> {
	
	public Set<E> solve(ExactCoverProblem<E> exact) {
			/*
			 * it makes more sense to build an array from the exact hitting set
			 * to solve the array using algorithm X as described in wikipedia,
			 * the columns are the subsets and the rows are elements
			 */
		
		/*
		 * converting to array so each element can be identified
		 */
		E[] arrayX = (E[]) exact.setX.toArray();
		Set[] arrayS = (Set[]) exact.setS.toArray();
		
		int elementsM = arrayX.length;
		int subsetsM = arrayS.length;
		
		boolean[][] matrix = new boolean[elementsM][subsetsM];
		
		for (int i=0; i<elementsM; i++)
			for (int j=0; j<subsetsM; j++) {
				if(arrayS[j].contains(arrayX[i]))
					matrix[i][j] = true;
				else matrix[i][j] = false;
			}
		
		
		
		return null;
	}
	
}
