package com.github.marcelothebuilder.webpedidos.util.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesProducer {
	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	@RequestScoped
	public ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	// @Produces
	// @RequestScoped
	// public HttpServletRequest getHttpRequest() {
	// return (HttpServletRequest) getExternalContext().getRequest();
	// }
	//
	// @Produces
	// @RequestScoped
	// public HttpServletResponse getHttpResponse() {
	// return (HttpServletResponse) getExternalContext().getResponse();
	// }

}
