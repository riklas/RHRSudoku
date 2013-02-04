package com.example.rhrsudoku;

class Digit {
	/*
	 * This class is used for numbers bound by the range[0-8]
	 * that is, identifiers for rows and columns in a sudoku grid
	 * UNUSED ATM
	 */
	private int v = 0;
	public Digit(int v) {
		set(v);
	}
	public void set(int v) {
		if (v<0 || v>8) {
			System.err.println("Invalid value: "+v);
			return;
		}
		this.v = v;
	}
	public int get() {
		return v;
	}
}
