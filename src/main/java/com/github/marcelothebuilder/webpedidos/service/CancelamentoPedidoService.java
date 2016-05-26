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
public class CancelamentoPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;

	@Inject
	private EstoqueService estoqueService;

	@Transactional
	public Pedido cancelar(Pedido pedido) {

		if (pedido.isNovo()) {
			throw new NegocioException("Pedido não cadastrado não pode ser cancelado.");
		}

		Pedido pedidoConectado = this.pedidos.porCodigo(pedido.getCodigo());

		if (pedidoConectado.isNaoCancelavel()) {
			throw new NegocioException(
					"Pedido não pode ser cancelado no estado " + pedido.getStatusPedido().getDescricao());
		}

		if (pedidoConectado.isEmitido()) {
			estoqueService.retornarItensEstoque(pedidoConectado);
		}

		pedidoConectado.setStatusPedido(StatusPedido.CANCELADO);

		Pedido pedidoPesistente = this.pedidos.guardar(pedidoConectado);

		return pedidoPesistente;
	}

}
