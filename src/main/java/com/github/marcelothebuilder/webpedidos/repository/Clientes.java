package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.cliente.TipoPessoa;
import com.github.marcelothebuilder.webpedidos.repository.filter.ClienteFilter;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

public class Clientes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Cliente guardar(Cliente cliente) {
		Cliente clienteConectado = manager.merge(cliente);
		return clienteConectado;
	}

	public Cliente porCodigo(Long codigo) {
		Cliente clienteConectado = null;
		clienteConectado = manager.find(Cliente.class, codigo);
		return clienteConectado;
	}

	/**
	 * Pesquisa por cliente, case-insensitive e combinando a partir do início.
	 * 
	 * @param nome
	 *            parte do nome a ser pesquisado
	 * @return lista de clientes filtrada por nome
	 */
	public Set<Cliente> porNome(String nome) {
		TypedQuery<Cliente> query = manager.createQuery("from Cliente where lower(nome) like :nome", Cliente.class);
		query.setParameter("nome", nome.toLowerCase() + "%");
		List<Cliente> result = query.getResultList();
		return new HashSet<>(result);
	}

	public Set<Cliente> filtrados(ClienteFilter filtro) {
		// a entidade
		EntityType<Cliente> type = manager.getMetamodel().entity(Cliente.class);

		// criteriaBuilder
		CriteriaBuilder cb = manager.getCriteriaBuilder();

		// criteriaQuery
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);

		// root
		Root<Cliente> root = cq.from(Cliente.class);

		List<Predicate> predicates = new LinkedList<>();

		final String matchDoisLados = "%%%s%%";

		// private String nome;
		if (StringUtils.isNotBlank(filtro.getNome())) {
			String nomeAtributo = "nome";
			String matchEsperado = filtro.getNome().toLowerCase();

			SingularAttribute<Cliente, String> nomeAttr = type.getDeclaredSingularAttribute(nomeAtributo, String.class);
			Path<String> nomePath = root.get(nomeAttr);

			String nomeMatch = String.format(matchDoisLados, matchEsperado);

			Expression<String> nomeLower = cb.lower(nomePath);

			Predicate predicate = cb.like(nomeLower, nomeMatch);

			predicates.add(predicate);
		}

		// private String email;
		if (StringUtils.isNotBlank(filtro.getEmail())) {
			String nomeAtributo = "email";
			String matchEsperado = filtro.getEmail().toLowerCase();

			SingularAttribute<Cliente, String> nomeAttr = type.getDeclaredSingularAttribute(nomeAtributo, String.class);
			Path<String> nomePath = root.get(nomeAttr);

			String nomeMatch = String.format(matchDoisLados, matchEsperado);

			Expression<String> nomeLower = cb.lower(nomePath);

			Predicate predicate = cb.like(nomeLower, nomeMatch);

			predicates.add(predicate);
		}

		// private String documentoReceitaFederal;
		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			String nomeAtributo = "documentoReceitaFederal";
			String matchEsperado = filtro.getDocumentoReceitaFederal();

			SingularAttribute<Cliente, String> nomeAttr = type.getDeclaredSingularAttribute(nomeAtributo, String.class);
			Path<String> nomePath = root.get(nomeAttr);

			String nomeMatch = String.format(matchDoisLados, matchEsperado);

			Predicate predicate = cb.like(nomePath, nomeMatch);

			predicates.add(predicate);
		}

		// private TipoPessoa tipoPessoa;
		if (filtro.getTipoPessoa() != null) {
			String nomeAtributo = "tipoPessoa";
			SingularAttribute<Cliente, TipoPessoa> nomeAttr = type.getDeclaredSingularAttribute(nomeAtributo,
					TipoPessoa.class);
			Path<TipoPessoa> tipoPessoaPath = root.get(nomeAttr);

			Predicate predicate = cb.equal(tipoPessoaPath, filtro.getTipoPessoa());

			predicates.add(predicate);
		}

		// private Estado estado;
		// private Cidade cidade;

		cq.where(predicates.toArray(new Predicate[] {}));

		// retorna resultados
		TypedQuery<Cliente> typedQuery = manager.createQuery(cq);

		List<Cliente> resultList = typedQuery.getResultList();

		return new HashSet<>(resultList);
	}

	@Transactional
	public void remover(Cliente cliente) {
		try {
			Cliente clienteConectado = this.porCodigo(cliente.getCodigo());
			manager.remove(clienteConectado);
			manager.flush();
		} catch (PersistenceException e) {
			String exceptionMessage = String.format("Cliente %s não pôde ser excluído.", cliente.getNome());
			throw new NegocioException(exceptionMessage, e);
		}
	}
}
