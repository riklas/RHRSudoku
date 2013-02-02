package exactCover;

import java.util.Set;

public class ExactCoverProblem<E> {
	/*
	 * represents an exact cover set problem
	 */
	
	Set<E> setX; // The set of elements that need to be covered
	Set<Set<E>> setS;	// The set of subsets of setX used to cover it
	
	public ExactCoverProblem(Set<E> setX, Set<Set<E>> setS) {
		this.setX = setX;
		this.setS = setS;
	}
	
}
