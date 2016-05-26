/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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

import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;
import com.github.marcelothebuilder.webpedidos.repository.filter.EstadoFilter;

/**
 * Repositório de estados.
 * 
 * @author Marcelo Paixao Resende
 *
 */
public class Estados implements Serializable {
	private static final long serialVersionUID = 9157858269113943535L;

	private @Inject EntityManager manager;

	public Set<Estado> todos() {
		List<Estado> list = manager.createQuery("from Estado", Estado.class).getResultList();
		return new HashSet<>(list);
	}

	public Estado porCodigo(Long codigo) {
		return manager.find(Estado.class, codigo);
	}

	public Set<Estado> filtrados(EstadoFilter filter) {
		// criteriaBuilder
		CriteriaBuilder cb = manager.getCriteriaBuilder();

		// metamodel
		Metamodel metamodel = manager.getMetamodel();

		// entity model
		EntityType<Estado> type = metamodel.entity(Estado.class);

		// criteriaQuery
		CriteriaQuery<Estado> cq = cb.createQuery(Estado.class);

		// root
		Root<Estado> root = cq.from(Estado.class);

		List<Predicate> predicates = new LinkedList<>();

		if (StringUtils.isNotBlank(filter.getSigla())) {
			SingularAttribute<Estado, String> attr = type.getDeclaredSingularAttribute("sigla", String.class);
			Path<String> path = root.get(attr);
			Predicate siglaPredicate = cb.equal(path, filter.getSigla());
			predicates.add(siglaPredicate);
		}

		if (StringUtils.isNotBlank(filter.getNome())) {
			SingularAttribute<Estado, String> attr = type.getDeclaredSingularAttribute("nome", String.class);
			Expression<String> lowerCaseNome = cb.lower(root.get(attr));

			String likeMatchString = String.format("%s%%", filter.getNome().toLowerCase());

			Predicate siglaPredicate = cb.like(lowerCaseNome, likeMatchString);
			predicates.add(siglaPredicate);
		}

		// adiciona predicates
		cq.where(predicates.toArray(new Predicate[] {}));

		// return query
		TypedQuery<Estado> query = manager.createQuery(cq);
		List<Estado> resultList = query.getResultList();
		return new HashSet<>(resultList);
	}

}
