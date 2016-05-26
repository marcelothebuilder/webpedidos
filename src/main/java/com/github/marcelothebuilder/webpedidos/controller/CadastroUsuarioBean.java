/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.primefaces.event.SelectEvent;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.repository.Grupos;
import com.github.marcelothebuilder.webpedidos.repository.Usuarios;
import com.github.marcelothebuilder.webpedidos.repository.filter.UsuarioFilter;

/**
 * Controller de cadastro de usuário.
 * 
 * @author Marcelo Paixao Resende
 *
 */
@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {
	private static final long serialVersionUID = -1052057176578639073L;

	private Usuario usuario;

	private @Inject Usuarios usuarios;

	private @Inject Grupos grupos;

	private Set<Grupo> grupoOptions;

	private Set<Usuario> usuariosTable;

	private UsuarioFilter usuarioFilter = new UsuarioFilter();

	private Boolean isRowSelected = false;

	private @Inject Validator validator;

	private @Inject FacesContext fContext;

	/**
	 * Acessor de leitura para o campo usuario
	 * 
	 * @return o usuario
	 */
	public Usuario getUsuario() {
		if (usuario == null) {
			limpaUsuario();
		}
		return usuario;
	}

	public void limpaUsuario() {
		usuario = new Usuario();
	}

	/**
	 * Define um novo valor para o campo usuario
	 * 
	 * @param usuario
	 *            o usuario a ser definido
	 */
	public void setUsuario(Usuario usuario) {
		usuario.getGrupos();
		this.usuario = usuario;
	}

	/**
	 * Acessor de leitura para o campo usuarioFilter
	 * 
	 * @return o usuarioFilter
	 */
	public UsuarioFilter getUsuarioFilter() {
		return usuarioFilter;
	}

	/**
	 * Define um novo valor para o campo usuarioFilter
	 * 
	 * @param usuarioFilter
	 *            o usuarioFilter a ser definido
	 */
	public void setUsuarioFilter(UsuarioFilter usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	public boolean isNovoUsuario() {
		return !isUsuarioExistente();
	}

	public boolean isUsuarioExistente() {
		return (usuario != null) && (usuario.getCodigo() != null);
	}

	public String getNovaSenha() {
		return usuario != null ? usuario.getSenha() : null;
	}

	public void setNovaSenha(String novaSenha) {
		// se o usuario ja existe e uma senha foi informada, queremos que ela
		// seja a nova
		if (isUsuarioExistente()) {
			if (novaSenha != null && !"".equals(novaSenha.trim())) {
				usuario.setSenha(novaSenha);
			}
		} else {
			// se o usuario nao existe, queremos que a senha informada seja a
			// senha, mesmo que seja nula.
			// neste caso quem fica responsável pela validação é o modelo
			usuario.setSenha(novaSenha);
		}
	}

	/**
	 * Acessor de leitura para o campo gruposList
	 * 
	 * @return o gruposList
	 */
	public Set<Grupo> getGrupoOpcoes() {
		if (grupoOptions == null) {
			grupoOptions = grupos.todos();
		}

		return grupoOptions;
	}

	public Set<Usuario> getUsuarios() {
		if (usuariosTable == null) {
			todosUsuarios();
		}

		return usuariosTable;
	}

	private boolean validaUsuario(Usuario usuarioValidacao) {
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuarioValidacao);
		if (violations.size() > 0) {
			for (ConstraintViolation<Usuario> constraintViolation : violations) {

				String message = String.format("%s %s", constraintViolation.getPropertyPath(),
						constraintViolation.getMessage());
				FacesMessage fMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
				fContext.addMessage(null, fMessage);
			}

			return false;
		}

		return true;
	}

	/**
	 * Persiste o usuário que está sendo incluído ou editado do campo usuario.
	 */
	public void salvar() {
		// precisamos de um repositório ou classe de serviço.

		if (!validaUsuario(usuario)) {
			return;
		}

		FacesMessage message = null;

		try {
			usuarios.guardar(usuario);
			String strMessage = String.format("Usuário %s salvo com sucesso.", usuario.getNome());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, strMessage, strMessage);

			limpaUsuario();

			isRowSelected = false;

			// força update
			usuariosTable = null;
		} catch (ConstraintViolationException e) { // |
			// TODO: Tratar exceção corretamente.
			String strMessage = String.format("Usuário %s não pôde ser salvo: %s", usuario.getNome(), e.getMessage());
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, strMessage, strMessage);
			// throw e;
		}

		fContext.addMessage(null, message);

	}

	public void deletaUsuario() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		try {
			usuarios.remover(usuario);
			String strMessage = String.format("Usuário %s removido com sucesso.", usuario.getNome());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, strMessage, strMessage);

			isRowSelected = false;

			limpaUsuario();

			// força update
			usuariosTable = null;

		} catch (Exception ex) {
			// TODO: Tratar exceção corretamente.
			String strMessage = String.format("Usuário %s não pôde ser removido: %s", usuario.getNome(),
					ex.getMessage());
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, strMessage, strMessage);
		}

		context.addMessage(null, message);

	}

	public void filtrar() {
		usuariosTable = usuarios.filtrados(usuarioFilter);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		String strMessage = "Exibindo resultados filtrados";
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, strMessage, strMessage);

		context.addMessage(null, message);
	}

	public void todosUsuarios() {
		usuarioFilter = new UsuarioFilter();
		usuariosTable = usuarios.todos(true);

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		String strMessage = "Exibindo todos os usuários";
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, strMessage, strMessage);

		context.addMessage(null, message);
	}

	/**
	 * Recebe um evento de seleção de linha da view.
	 * 
	 * @param event
	 *            o evento de seleção de linha
	 */
	public void onRowSelect(SelectEvent event) {
		isRowSelected = true;
	}

	public boolean isRowSelected() {
		return isRowSelected;
	}

}
