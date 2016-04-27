package com.exceptions;

public class NoJoinPossible extends Exception {

	private static final long serialVersionUID = 1L;

	public String msg;

	public NoJoinPossible(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}
}
