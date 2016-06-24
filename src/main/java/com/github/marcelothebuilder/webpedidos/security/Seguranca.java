/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@RequestScoped
public class Seguranca implements Serializable {
	private static final long serialVersionUID = 1L;

	private @Inject ExternalContext external;

	public Usuario getUsuarioLogado() {
		UsuarioSistema usuarioSistema = getUsuarioSistema();

		Usuario usuario = usuarioSistema.getUsuario();
		return usuario;

	}

	public boolean isLogado() {
		return !this.isDeslogado();
	}

	public boolean isDeslogado() {
		return getAuthentication() == null;
	}

	private Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		return auth;
	}

	private Object getPrincipal() {
		Authentication auth = getAuthentication();
		return auth.getPrincipal();
	}

	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioSistema() {

		Object authDetailsObject = getPrincipal();

		if (!(authDetailsObject instanceof UsuarioSistema)) {
			throw new RuntimeException("Spring Logged user details MUST be an instance of UsuarioSistema, got "
					+ authDetailsObject.getClass());
		}

		UsuarioSistema usuarioSistema = (UsuarioSistema) authDetailsObject;
		return usuarioSistema;
	}

	/**
	 * Retorna <code>true</code> se o usuário estiver em todos os grupos. Caso
	 * contrário, retorna <code>false</code>.
	 * 
	 * @param roles
	 *            grupos para verificação
	 * @return <code>true</code> se o usuário estiver em <strong>todos</strong>
	 *         os grupos
	 */
	@SuppressWarnings("unused")
	private boolean inAllRoles(String... roles) {
		for (String role : roles) {
			if (!external.isUserInRole(role)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retorna <code>true</code> se o usuário estiver em algum dos grupos. Caso
	 * contrário, retorna <code>false</code>.
	 * 
	 * @param roles
	 *            grupos para verificação
	 * @return <code>true</code> se o usuário estiver em <strong>algum</strong>
	 *         dos grupos
	 */
	private boolean inAnyRole(String... roles) {
		for (String role : roles) {
			if (external.isUserInRole(role)) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isPermitidoSalvarPedido() {
		return inAnyRole("ADMINISTRADORES");
	}

	public boolean isPermitidoEmitirPedido() {
		return inAnyRole("ADMINISTRADORES", "VENDEDORES");
	}

	public boolean isPermitidoCancelarPedido() {
		return inAnyRole("ADMINISTRADORES", "VENDEDORES");
	}

	public boolean isPermitidoEnviarPorEmail() {
		return !inAnyRole("DEMONSTRACAO");
	}
	
	public boolean isModoDemonstracao() {
		return inAnyRole("DEMONSTRACAO");
	}

}
