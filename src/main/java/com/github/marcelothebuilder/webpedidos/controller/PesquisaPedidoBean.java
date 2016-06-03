/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.model.pedido.StatusPedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;
import com.github.marcelothebuilder.webpedidos.repository.filter.PedidoFilter;

/**
 * @author Marcelo Paixao Resende
 *
 */

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private @Inject Pedidos pedidos;

	private Set<Pedido> pedidosFiltrados;

	private PedidoFilter filtro;

	@PostConstruct
	public void init() {
		filtro = new PedidoFilter();
		pesquisar();
	}

	/**
	 * Acessor de leitura para o campo filtro
	 * 
	 * @return o filtro
	 */
	public PedidoFilter getFiltro() {
		return filtro;
	}

	/**
	 * Define um novo valor para o campo filtro
	 * 
	 * @param filtro
	 *            o filtro a ser definido
	 */
	public void setFiltro(PedidoFilter filtro) {
		this.filtro = filtro;
	}

	/**
	 * Retorna os pedidos armazenados no campo pedidosFiltrados. Caso não esteja
	 * inicializado, inicializa chamando o método {@link #pesquisar()}
	 * 
	 * @return pedidos distintos
	 */
	public Set<Pedido> getPedidosFiltrados() {
		if (pedidosFiltrados == null) {
			pesquisar();
		}

		return pedidosFiltrados;
	}

	public StatusPedido[] getStatusesPedido() {
		return StatusPedido.values();
	}

	/**
	 * Aplica o filtro de pedidos nos pedidos retornados.
	 */
	public void pesquisar() {
		String message = "Filtrando pedidos.";
		FacesMessage fMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		FacesContext.getCurrentInstance().addMessage(null, fMessage);

		pedidosFiltrados = pedidos.filtrados(filtro);
	}

}
