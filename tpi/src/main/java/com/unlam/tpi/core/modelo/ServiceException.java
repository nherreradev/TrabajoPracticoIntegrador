package com.unlam.tpi.core.modelo;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
