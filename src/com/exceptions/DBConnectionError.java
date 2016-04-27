package com.exceptions;

public class DBConnectionError extends Exception {

	private static final long serialVersionUID = 1L;

	public String msg;

	public DBConnectionError(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
