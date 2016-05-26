package com.github.marcelothebuilder.webpedidos.util.jsf;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory parentFactory;
	
	public JsfExceptionHandlerFactory(ExceptionHandlerFactory parentFactory) {
		this.parentFactory = parentFactory;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfExceptionHandlerWrapper( parentFactory.getExceptionHandler() );
	}

}
