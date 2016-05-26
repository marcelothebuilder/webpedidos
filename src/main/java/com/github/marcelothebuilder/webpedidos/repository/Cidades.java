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

import com.github.marcelothebuilder.webpedidos.model.endereco.Cidade;
import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;
import com.github.marcelothebuilder.webpedidos.repository.filter.CidadeFilter;

/**
 * Repositório de cidades.
 * 
 * @author Marcelo Paixao Resende
 *
 */
public class Cidades implements Serializable {
	private static final long serialVersionUID = 9157858269113943535L;

	private @Inject EntityManager manager;

	public Set<Cidade> todos() {
		List<Cidade> list = manager.createQuery("from Cidade", Cidade.class).getResultList();
		return new HashSet<>(list);
	}

	public Cidade porCodigo(Integer codigo) {
		return manager.find(Cidade.class, codigo);
	}

	public Set<Cidade> filtrados(CidadeFilter filter) {
		// criteriaBuilder
		CriteriaBuilder cb = manager.getCriteriaBuilder();

		// metamodel
		Metamodel metamodel = manager.getMetamodel();

		// entity model
		EntityType<Cidade> type = metamodel.entity(Cidade.class);

		// criteriaQuery
		CriteriaQuery<Cidade> cq = cb.createQuery(Cidade.class);

		// root
		Root<Cidade> root = cq.from(Cidade.class);

		List<Predicate> predicates = new LinkedList<>();

		if (StringUtils.isNotBlank(filter.getNome())) {
			SingularAttribute<Cidade, String> attr = type.getDeclaredSingularAttribute("nome", String.class);
			Expression<String> lowerCaseNome = cb.lower(root.get(attr));

			String likeMatchString = String.format("%s%%", filter.getNome().toLowerCase());

			Predicate siglaPredicate = cb.like(lowerCaseNome, likeMatchString);
			predicates.add(siglaPredicate);
		}

		if (filter.getEstado() != null) {
			SingularAttribute<Cidade, Estado> attr = type.getDeclaredSingularAttribute("estado", Estado.class);
			Path<Estado> estadoPath = root.get(attr);

			Predicate estadoPredicate = cb.equal(estadoPath, filter.getEstado());
			predicates.add(estadoPredicate);
		}

		// adiciona predicates
		cq.where(predicates.toArray(new Predicate[] {}));

		// return query
		TypedQuery<Cidade> query = manager.createQuery(cq);
		List<Cidade> resultList = query.getResultList();
		return new HashSet<>(resultList);
	}

}
