/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.repository.Usuarios;
import com.github.marcelothebuilder.webpedidos.util.cdi.CDIServiceLocator;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuarios usuarios = CDIServiceLocator.getBean(Usuarios.class);

		Usuario usuario = usuarios.porEmail(email);

		User user = null;

		if (usuario == null) {
			throw new UsernameNotFoundException("Not found");
		}

		user = new UsuarioSistema(usuario, getGrupos(usuario));

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		Collection<SimpleGrantedAuthority> grupos = new ArrayList<>();

		for (Grupo grupo : usuario.getGrupos()) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(grupo.getNome().toUpperCase());
			grupos.add(sga);
		}

		return grupos;
	}

}
