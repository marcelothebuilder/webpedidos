package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;
import java.util.Set;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;

public class UsuarioFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	private Set<Grupo> grupos;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
