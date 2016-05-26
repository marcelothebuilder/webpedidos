/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.marcelothebuilder.webpedidos.model.pedido.ItemPedido;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class EstoqueService implements Serializable {
	private static final long serialVersionUID = 1L;
	private @Inject Pedidos pedidos;

	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		Pedido pedidoConectado = this.pedidos.porCodigo(pedido.getCodigo());

		for (ItemPedido item : pedidoConectado.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	@Transactional
	public void retornarItensEstoque(Pedido pedido) {
		Pedido pedidoConectado = this.pedidos.porCodigo(pedido.getCodigo());

		for (ItemPedido item : pedidoConectado.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}

	}

}
