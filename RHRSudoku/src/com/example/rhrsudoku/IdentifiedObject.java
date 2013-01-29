package com.example.rhrsudoku;

public class IdentifiedObject<E> implements Comparable<IdentifiedObject> {
	final int ID;
	E element;
	public IdentifiedObject(int ID, E element) {
		this.ID = ID;
		this.element = element;
	}
	@Override
	public int compareTo(IdentifiedObject object2) {
		int ID2 = object2.ID;
		if (ID2 > ID)
			return -1;
		if (ID2 == ID)
			return 0;
		return 1;
	}
}
