/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.model.pedido.StatusPedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class EmissaoPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;
	private @Inject Pedidos pedidos;
	private @Inject EstoqueService estoqueService;
	private @Inject CadastroPedidoService cadastroPedidoService;

	@Transactional
	public Pedido emitir(Pedido pedido) {
		Pedido pedidoConectado = cadastroPedidoService.salvar(pedido);

		if (pedido.isNaoEmissivel()) {
			throw new NegocioException(
					"Pedido não pode ser emitido com status " + pedidoConectado.getStatusPedido().getDescricao());
		}

		this.estoqueService.baixarItensEstoque(pedidoConectado);

		pedidoConectado.setStatusPedido(StatusPedido.EMITIDO);

		pedidoConectado = this.pedidos.guardar(pedidoConectado);

		return pedidoConectado;
	}
}
