
package com.javaduckspizza.com.controller;

public class TestStuff {
	static int addTwoDigits(int n) {
	    char [] carr = String.valueOf(n).toCharArray();
	    int retVal = 0;

	    
	    String s = String.valueOf(n);
	    retVal = Integer.valueOf(s.substring(0, 1)) + Integer.valueOf(s.substring(1));
	    

	    return retVal;
	}

	public static void main(String[] args) {
		System.out.println(addTwoDigits(29));
	}
}
