package com.example.rhrsudoku;

import java.util.HashSet;
import java.util.Set;

public class ExactCoverSolverTester {
	public static void main(String[] args) {
		Set<Integer> setX = buildSetX();
		Set<Set<Integer>> setS = buildSetS();
		ExactCoverProblem<Integer> problem = new ExactCoverProblem<Integer>(setX, setS);
		ExactCoverSolver<Integer> solver = new ExactCoverSolver<Integer>();
		Set<Set<Integer>> solution = solver.solve(problem);
		printSets(solution);
	}

	private static void printSets(Set<Set<Integer>> solution) {
		if (solution == null) {
			System.out.println("No solution found");
			return;
		}
		System.out.println();
		for (Set<Integer> subset: solution) {
			for (Integer i : subset) {
				System.out.print(i + ", ");
			}
			System.out.println();
		}
	}

	private static Set<Set<Integer>> buildSetS() {
		Set<Set<Integer>> setS = new HashSet<Set<Integer>>();
		Set<Integer> subset;
		
		subset = new HashSet<Integer>();
		subset.add(1); subset.add(4); subset.add(7);
		setS.add(subset);
		
		subset = new HashSet<Integer>();
		subset.add(1); subset.add(4);
		setS.add(subset);
		
		subset = new HashSet<Integer>();
		subset.add(4); subset.add(5); subset.add(7);
		setS.add(subset);
		
		subset = new HashSet<Integer>();
		subset.add(3); subset.add(5); subset.add(6);
		setS.add(subset);
		
		subset = new HashSet<Integer>();
		subset.add(2); subset.add(3); subset.add(7); subset.add(6);
		setS.add(subset);
		
		subset = new HashSet<Integer>();
		subset.add(2); subset.add(7);
		setS.add(subset);
		
		return setS;
		
	}

	private static Set<Integer> buildSetX() {
		Set<Integer> setX = new HashSet<Integer>();
		for (int i=1; i<8;i++)
			setX.add(i);
		return setX;
	}
}
