package exactCover;

import java.util.Set;

public class ExactCoverProblem<E> {
	/*
	 * represents an exact cover set problem
	 */
	
	Set<E> setX; // The set of elements that need to be covered
	Set<Set<E>> setS;	// The set of subsets of setX used to cover it
	Set<Set<E>> setW;  // an optional set of subsets that must be included in the solution
						// they must be removed prior to other processing
						// if not needed, set to null
	
	public ExactCoverProblem(Set<E> setX, Set<Set<E>> setS, Set<Set<E>> setW) {
		if ((setW != null) && (!setS.containsAll(setW))) {
			System.err.println("ERROR: SetS is not a strict superset of setW");
			System.exit(1);
		}
		this.setX = setX;
		this.setS = setS;
		this.setW = setW;
	}
	
}
