package com.exceptions;

public class DBAlreadyExists extends Exception {

	private static final long serialVersionUID = 1L;

	public String msg;

	public DBAlreadyExists(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
