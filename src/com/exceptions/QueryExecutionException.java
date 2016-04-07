package com.exceptions;

public class QueryExecutionException extends Exception {

	private static final long serialVersionUID = 1L;

	public String msg;

	public QueryExecutionException(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
