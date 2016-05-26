package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;

public class EstadoFilter implements Serializable {
	private static final long serialVersionUID = 8567143595622501666L;
	private String sigla;
	private String nome;
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
