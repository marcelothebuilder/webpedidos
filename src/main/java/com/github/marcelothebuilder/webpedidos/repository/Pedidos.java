/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.model.pedido.StatusPedido;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.model.vo.DataValor;
import com.github.marcelothebuilder.webpedidos.model.vo.comparator.DataValorDataComparator;
import com.github.marcelothebuilder.webpedidos.repository.filter.IntervaloDatas;
import com.github.marcelothebuilder.webpedidos.repository.filter.PedidoFilter;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class Pedidos implements Serializable {
	private static final long serialVersionUID = 1L;
	private @Inject EntityManager manager;

	public Pedido porCodigo(Long codigo) {
		assert (codigo >= 0);
		assert (codigo != null);
		assert (manager != null);

		Pedido pedido = manager.find(Pedido.class, codigo);

		return pedido;
	}

	public Set<Pedido> filtrados(PedidoFilter filtro) {

		assert (filtro != null);

		EntityType<Pedido> type = manager.getMetamodel().entity(Pedido.class);

		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Pedido> cQuery = builder.createQuery(Pedido.class);

		Root<Pedido> root = cQuery.from(Pedido.class);

		List<Predicate> predicatesList = new LinkedList<>();

		if (filtro.getCodigoDe() != null || filtro.getCodigoAte() != null) {
			Path<Long> path = root.get(type.getSingularAttribute("codigo", Long.class));
			if (filtro.getCodigoDe() != null) {
				Predicate predicate = builder.greaterThanOrEqualTo(path, filtro.getCodigoDe());
				predicatesList.add(predicate);
			}

			if (filtro.getCodigoAte() != null) {
				Predicate predicate = builder.lessThanOrEqualTo(path, filtro.getCodigoAte());
				predicatesList.add(predicate);
			}
		}

		if (filtro.getDataCriacaoInicio() != null || filtro.getDataCriacaoFim() != null) {
			Path<Date> path = root.get(type.getSingularAttribute("dataCriacao", Date.class));
			if (filtro.getDataCriacaoInicio() != null) {
				Predicate predicate = builder.greaterThanOrEqualTo(path, filtro.getDataCriacaoInicio());
				predicatesList.add(predicate);
			}

			if (filtro.getDataCriacaoFim() != null) {
				Predicate predicate = builder.lessThanOrEqualTo(path, filtro.getDataCriacaoFim());
				predicatesList.add(predicate);
			}
		}

		Join<Pedido, Usuario> vendedorJoin = root.join(type.getSingularAttribute("vendedor", Usuario.class));

		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			EntityType<Usuario> usuarioType = manager.getMetamodel().entity(Usuario.class);

			Expression<String> usuarioNomePathLc = builder
					.lower(vendedorJoin.get(usuarioType.getSingularAttribute("nome", String.class)));

			Predicate predicate = builder.like(usuarioNomePathLc, "%" + filtro.getNomeVendedor().toLowerCase() + "%");

			predicatesList.add(predicate);
		}

		Join<Pedido, Cliente> clienteJoin = root.join(type.getSingularAttribute("cliente", Cliente.class));

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			EntityType<Cliente> clienteType = manager.getMetamodel().entity(Cliente.class);

			Expression<String> clienteNomePathLc = builder
					.lower(clienteJoin.get(clienteType.getSingularAttribute("nome", String.class)));

			Predicate predicate = builder.like(clienteNomePathLc, "%" + filtro.getNomeCliente().toLowerCase() + "%");

			predicatesList.add(predicate);

		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			Path<StatusPedido> path = root.get(type.getDeclaredSingularAttribute("statusPedido", StatusPedido.class));

			Collection<StatusPedido> statusCollection = new HashSet<>();
			Collections.addAll(statusCollection, filtro.getStatuses());

			Predicate predicate = path.in(statusCollection);

			predicatesList.add(predicate);
		}

		Predicate[] predicatesArray = predicatesList.toArray(new Predicate[] {});

		cQuery.where(builder.and(predicatesArray));

		TypedQuery<Pedido> tQuery = manager.createQuery(cQuery);

		return new HashSet<>(tQuery.getResultList());

	}

	public Pedido guardar(Pedido pedido) {
		return manager.merge(pedido);
	}

	public Set<DataValor> valoresTotaisPorData(IntervaloDatas interval) {
		return valoresTotaisPorData(interval, null);
	}

	public Set<DataValor> valoresTotaisPorData(IntervaloDatas interval, Usuario vendedor) {
		EntityType<Pedido> pedidoType = manager.getMetamodel().entity(Pedido.class);
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<DataValor> cQuery = cb.createQuery(DataValor.class);

		// root
		Root<Pedido> root = cQuery.from(Pedido.class);

		// paths
		Path<BigDecimal> valorTotalPath = root.get("valorTotal");
		Path<Date> dataPath = root.get(pedidoType.getSingularAttribute("dataCriacao", Date.class));
		Path<Usuario> vendedorPath = root.get(pedidoType.getSingularAttribute("vendedor", Usuario.class));
		Expression<BigDecimal> sumExpr = cb.sum(valorTotalPath);
		Expression<Date> dataTruncPath = cb.function("DATE", Date.class, dataPath);

		// aliases
		sumExpr.alias("valor");
		dataTruncPath.alias("data");

		// select
		CriteriaQuery<DataValor> select = cQuery.multiselect(dataTruncPath, sumExpr);
		select.groupBy(dataTruncPath);

		// restrição: datas
		Predicate bewteenDatesPredicate = cb.and(cb.lessThanOrEqualTo(dataTruncPath, interval.getDataFinal()),
				cb.greaterThanOrEqualTo(dataTruncPath, interval.getDataInicial()));

		Predicate pred = bewteenDatesPredicate;

		// restrição: vendedor
		if (vendedor != null) {
			Predicate vendedorPredicate = cb.equal(vendedorPath, vendedor);
			pred = cb.and(bewteenDatesPredicate, vendedorPredicate);
		}

		// adiciona restrições ao select
		select.where(pred);

		// realiza a query
		TypedQuery<DataValor> typedQuery = manager.createQuery(select);
		List<DataValor> res = typedQuery.getResultList();

		// adiciona a um set
		Set<DataValor> set = new TreeSet<>(new DataValorDataComparator());
		set.addAll(res);

		// preenche as datas que faltam com valor ZERO
		preencheSetComDatasSemVenda(set, interval);

		return set;
	}

	private static void preencheSetComDatasSemVenda(Set<DataValor> set, IntervaloDatas interval) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(interval.getDataInicial());

		while (!calendar.getTime().after(interval.getDataFinal())) {

			// convert to sql date
			Date date = new java.sql.Date(calendar.getTime().getTime());

			// DateValor com valor ZERO
			DataValor dataValor = new DataValor(date, BigDecimal.ZERO);

			set.add(dataValor);

			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
