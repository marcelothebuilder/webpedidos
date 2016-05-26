/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;
	private boolean invalid;

	@Inject
	private FacesContext facesContext;

	public LoginBean() {
	}

	public void login() throws ServletException, IOException {
		ExternalContext externalContext = facesContext.getExternalContext();

		HttpServletRequest httpRequest = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse httpResponse = (HttpServletResponse) externalContext.getResponse();

		RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(httpRequest, httpResponse);
		facesContext.responseComplete();
	}

	/**
	 * Acessor de leitura para o campo email
	 * 
	 * @return o email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Define um novo valor para o campo email
	 * 
	 * @param email
	 *            o email a ser definido
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public void preRender() {
		if (this.isInvalid()) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválidos",
					"Usuário ou senha inválidos"));
		}
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}
}
