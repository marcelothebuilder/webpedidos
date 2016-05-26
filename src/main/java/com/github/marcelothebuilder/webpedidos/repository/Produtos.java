package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.filter.ProdutoFilter;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

public class Produtos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto guardar(Produto produto) {
		return manager.merge(produto);
	}

	@Transactional
	public void remover(Produto produto) {
		try {
			Produto produtoConectado = porCodigo(produto.getCodigo());
			manager.remove(produtoConectado);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído.", e);
		}

	}

	public Produto porCodigo(Long codigo) {
		try {
			return manager.createQuery("from Produto where codigo = :codigo", Produto.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Produto porSku(String sku) {
		String lcSku = sku.toLowerCase();
		String preparedSku = StringUtils.remove(lcSku, '-');

		try {
			return manager.createQuery("from Produto where lower(sku) = :sku", Produto.class)
					.setParameter("sku", preparedSku).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Produto> filtrados(ProdutoFilter filter) {

		Metamodel meta = manager.getMetamodel();
		EntityType<Produto> type = meta.entity(Produto.class);

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		if (StringUtils.isNotBlank(filter.getSku())) {
			Predicate equalsPredicate = criteriaBuilder.equal(root.get("sku"), filter.getSku());
			criteriaQuery.where(equalsPredicate);
		}

		// vamos usar o atributo nome para filtragem e ordenação
		Path<String> nomeAttr = root.get(type.getDeclaredSingularAttribute("nome", String.class));

		if (StringUtils.isNotBlank(filter.getNome())) {
			Expression<String> expr = criteriaBuilder.lower(nomeAttr);

			// where nome like '%nome%'
			String match = String.format("%%%s%%", filter.getNome().toLowerCase());

			Predicate likePredicate = criteriaBuilder.like(expr, match);
			criteriaQuery.where(likePredicate);
		}

		Order ordernacao = criteriaBuilder.asc(nomeAttr);

		criteriaQuery.orderBy(ordernacao);

		return manager.createQuery(criteriaQuery).getResultList();
	}

	public List<Produto> porNome(String query) {
		String nomeQuery = query.toLowerCase() + "%";
		return this.manager.createQuery("from Produto where lower(nome) like :nome", Produto.class)
				.setParameter("nome", nomeQuery).getResultList();
	}

}
