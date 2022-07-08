package com.sprintboot.mercadoLibre.exception;

	
public abstract class GeneralException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public GeneralException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
