/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class UsuarioSistema extends User {
	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	/**
	 * @param usuario
	 * @param authorities
	 */
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	/**
	 * Acessor de leitura para o campo usuario
	 * 
	 * @return o usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

}
