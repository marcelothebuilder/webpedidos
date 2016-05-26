package com.github.marcelothebuilder.webpedidos.service;

public class NegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NegocioException() {
		super();
	}

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(Throwable cause) {
		super(cause);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

}
