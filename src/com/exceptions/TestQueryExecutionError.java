package com.exceptions;

public class TestQueryExecutionError extends Exception {

	private static final long serialVersionUID = 1L;

	public String msg;

	public TestQueryExecutionError(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
