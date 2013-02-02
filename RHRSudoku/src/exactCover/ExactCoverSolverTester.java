package exactCover;

import java.util.HashSet;
import java.util.Set;

public class ExactCoverSolverTester {
	static final int ELEMENTS_M = 40; // number of element
	
	static int[][] setSarray = {{18,38,32,1}, {11,27}, {10}, {23,3},
		{22,4},{12,33,17}, {8,26,20,40}, {35,29},{6,30,39}, {5,16,2}, {14,25}, {9,24,28},
		{19,31}, {7,36,21,13}, {37,15,34}, {6,30,39}, {23,2,7}, {33,12,1}, {21,24,25}, {6,5,21},
		{12,13,30}, {2,6,17,14}, {11,7,9,21,38,40}, {13,26,25,29,10}, {5,8,37,21,2}, 
		{7,5,9,28,35,33,38}, {6}, {16,2}, {3,2}, {29}, {31,30}};
	
	//static int[][] setSarray = {{11,3,8},{4,7,1},{2,7},{5,7,2},{5,1},{9,6},{10},{12},{12,10},{4},{4,2,8},{12,6,7},{3,10,1},{11,4}};
	
	
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
		System.out.println("\nSolution found:\n===================");
		System.out.println(solution.toString());
	}

	private static Set<Set<Integer>> buildSetS() {
		Set<Set<Integer>> setS = new HashSet<Set<Integer>>();
		for (int[] i : setSarray) {
			Set<Integer> subset = new HashSet<Integer>();
			for (int j : i)
				subset.add(j);
			setS.add(subset);
		}
				
		return setS;
	}

	private static Set<Integer> buildSetX() {
		Set<Integer> setX = new HashSet<Integer>();
		for (int i=1; i<ELEMENTS_M+1;i++)
			setX.add(i);
		return setX;
	}
}
