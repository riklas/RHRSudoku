package com.example.rhrsudoku;

import java.util.Set;

public class ExactCoverProblem<E> {
	/*
	 * represents an exact cover set problem
	 */
	
	Set<E> setX;
	Set<Set<E>> setS;
	
	public ExactCoverProblem(Set<E> setX, Set<Set<E>> setS) {
		this.setX = setX;
		this.setS = setS;
	}
	
}
