package com.store.demo.utils;

public enum Discount {

	GET_TEN(10),
	GET_FIVE(5),
	GET_FIFTEEN(15);
	
	private int value;
	
	Discount(int newValue) {
		 value = newValue;
	}
	
	public int getValue() {
		return value;
	}
}
