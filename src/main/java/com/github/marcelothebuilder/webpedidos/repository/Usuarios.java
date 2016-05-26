/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.repository.filter.UsuarioFilter;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

/**
 * Repositório de usuários.
 * 
 * @author Marcelo Paixao Resende
 *
 */
public class Usuarios implements Serializable {
	private static final long serialVersionUID = 9157858269113943535L;

	private @Inject EntityManager manager;

	/**
	 * Procura um Usuario pelo código
	 * 
	 * @param codigo
	 *            o código do usuário a ser consultado
	 * @return o usuário se tivermos um usuário, null caso contrário
	 */
	public Usuario porCodigo(Long codigo) {
		try {
			return manager.createQuery("from Usuario where codigo = :codigo", Usuario.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Guarda um usuário no repositório, persistindo.
	 * 
	 * @param usuario
	 *            um objeto do tipo usuario a ser inserido ou atualizado
	 * @return o objeto usuario conectado
	 */
	@Transactional
	public Usuario guardar(Usuario usuario) {
		Usuario usuarioPersistido = null;

		usuarioPersistido = manager.merge(usuario);

		return usuarioPersistido;
	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			Usuario usuarioConectado = this.porCodigo(usuario.getCodigo());
			manager.remove(usuarioConectado);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído.", e);
		}
	}

	/**
	 * Retorna todos usuários.
	 * 
	 * @param fetch
	 *            se devemos trazer os grupos na mesma query
	 * @return todos usuários do sistema.
	 */
	public Set<Usuario> todos(Boolean fetch) {
		String query = null;
		if (fetch) {
			query = "select u from Usuario u left join fetch u.grupos";
		} else {
			query = "from Usuario";
		}
		return new LinkedHashSet<>(manager.createQuery(query, Usuario.class).getResultList());
	}

	public Set<Usuario> filtrados(UsuarioFilter filter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Metamodel meta = manager.getMetamodel();
		EntityType<Usuario> entity = meta.entity(Usuario.class);

		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		List<Predicate> predicates = new LinkedList<>();

		if (StringUtils.isNotBlank(filter.getNome())) {
			SingularAttribute<Usuario, String> nomeAttr = entity.getDeclaredSingularAttribute("nome", String.class);
			Path<String> attr = root.get(nomeAttr);
			String match = String.format("%%%s%%", filter.getNome().toLowerCase());
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(attr), match);
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(filter.getEmail())) {
			SingularAttribute<Usuario, String> nomeAttr = entity.getDeclaredSingularAttribute("email", String.class);
			Path<String> attr = root.get(nomeAttr);
			String match = String.format("%%%s%%", filter.getEmail().toLowerCase());
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(attr), match);
			predicates.add(predicate);
		}

		if (filter.getGrupos() != null && !filter.getGrupos().isEmpty()) {

			// CollectionAttribute<Usuario, Grupo> gruposAttr =
			// entity.getDeclaredCollection("grupos", Grupo.class);
			Expression<Collection<Grupo>> path = root.get("grupos");

			for (Grupo grupo : filter.getGrupos()) {
				Predicate predicate = criteriaBuilder.isMember(grupo, path);
				predicates.add(predicate);
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);

		return new HashSet<>(query.getResultList());
	}

	public Set<Usuario> vendedores() {
		return todos(false);
	}

	public Usuario porEmail(String email) {

		Usuario usuario = null;
		try {
			TypedQuery<Usuario> query = manager.createQuery("from Usuario where lower(email) = :email", Usuario.class);
			usuario = query.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuario encontrado com este e-mail
		}

		return usuario;
	}

}
