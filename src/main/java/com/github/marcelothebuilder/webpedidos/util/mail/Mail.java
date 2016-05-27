/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail;

import java.util.Properties;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class Mail {
	private String destinatarioEmail;
	private String caminhoTemplate;
	private String assunto;
	private Properties parametros = new Properties();
	
	public Mail() {}

	public void setDestinatario(String destinatarioEmail) {
		this.destinatarioEmail = destinatarioEmail.trim().toLowerCase();
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public void addParametro(String chave, Object parametro) {
		parametros.put(chave, parametro);
	}

	public void setTemplate(String caminhoTemplate) {
		this.caminhoTemplate = caminhoTemplate;
	}
	
	public String getAssunto() {
		return assunto;
	}
	
	public String getDestinatario() {
		return this.destinatarioEmail;
	}
	
	public Properties getParametros() {
		return this.parametros;
	}
	
	public String getTemplate() {
		return this.caminhoTemplate;
	}

}
