package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;

import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;

public class CidadeFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Estado estado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}
