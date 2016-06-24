package com.github.marcelothebuilder.webpedidos.util.jsf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

public class JsfExceptionHandlerWrapper extends ExceptionHandlerWrapper {
	
	private @Inject Log log;

	private ExceptionHandler wrappedExceptionHandler;

	public JsfExceptionHandlerWrapper(ExceptionHandler exceptionHandler) {
		this.wrappedExceptionHandler = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrappedExceptionHandler;
	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> exQueuedEventsIterator;

		{
			Iterable<ExceptionQueuedEvent> exQueuedEvents = wrappedExceptionHandler.getUnhandledExceptionQueuedEvents();
			exQueuedEventsIterator = exQueuedEvents.iterator();
		}

		while (exQueuedEventsIterator.hasNext()) {
			ExceptionQueuedEvent queuedEvent = exQueuedEventsIterator.next();
			ExceptionQueuedEventContext context = queuedEvent.getContext();
			Throwable exception = context.getException();

			boolean handled = false;

			try {
				NegocioException negException = getNegocioExceptionFromStack(exception);
				if (negException != null) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							negException.getLocalizedMessage(), negException.getLocalizedMessage());

					FacesContext fc = FacesContext.getCurrentInstance();
					
					fc.addMessage(null, message);
					
				} else if (exception instanceof ViewExpiredException) {
					redirect("/");
				} else {
					redirect("/Erro.xhtml");
					log.error("Erro de sistema: " + exception.getMessage(), exception);
				}

				handled = true;

			} finally {
				if (handled) {
					exQueuedEventsIterator.remove();
				}
			}
		}

		this.getWrapped().handle();
	}

	private NegocioException getNegocioExceptionFromStack(Throwable exception) {
		if (exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if (exception.getCause() != null) {
			return getNegocioExceptionFromStack(exception.getCause());
		}

		return null;

	}

	private void redirect(String path) {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String contextPath = externalContext.getRequestContextPath();
			externalContext.redirect(contextPath + path);
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}
}
