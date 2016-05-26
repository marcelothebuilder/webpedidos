package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.marcelothebuilder.webpedidos.model.produto.Categoria;
import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

public class Categorias implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Categoria guardar(Categoria categoria) {
		Categoria categoriaConectada = manager.merge(categoria);
		return categoriaConectada;
	}

	public Categoria porCodigo(Long codigo) {
		Categoria categoria = null;
		categoria = manager.find(Categoria.class, codigo);
		return categoria;
	}

	public List<Categoria> raizes() {
		TypedQuery<Categoria> query = manager.createQuery("from Categoria where categoriaPai is null", Categoria.class);
		// .createQuery("from Categoria where categoriaPai is null",
		// Categoria.class);

		List<Categoria> lista = query.getResultList();
		return lista;
	}

	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :raiz", Categoria.class)
				.setParameter("raiz", categoriaPai).getResultList();
	}

	public Long quantidadeDeProdutos(Categoria categoria) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);

		// dos produtos
		Root<Produto> root = query.from(Produto.class);

		// selecione a quantidade
		query.select(cb.count(root));

		// que sejam da categoria atual
		Predicate categoriaCorreta = cb.equal(root.get("categoria"), categoria);
		query.where(categoriaCorreta);

		return manager.createQuery(query).getSingleResult();

	}

	@Transactional
	public void remover(Categoria categoria) {
		try {
			Categoria categoriaConectada = this.porCodigo(categoria.getCodigo());
			manager.remove(categoriaConectada);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Categoria não pode ser excluída.", e);
		}
	}
}
