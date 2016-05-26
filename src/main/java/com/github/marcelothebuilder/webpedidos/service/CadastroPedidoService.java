/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.model.pedido.StatusPedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class CadastroPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;
	private @Inject Pedidos pedidos;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatusPedido(StatusPedido.ORCAMENTO);
		}

		if (pedido.isNaoAlteravel()) {
			throw new NegocioException(String.format("Pedido não pode ser alterado no status %s.",
					pedido.getStatusPedido().getDescricao()));
		}

		if (pedido.getItens().size() == 0) {
			throw new NegocioException("O pedido deve ter pelo menos um item.");
		}

		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("O valor do pedido nao pode ser negativo");
		}

		// checa se temos os itens em estoque

		Pedido pedidoConectado = this.pedidos.guardar(pedido);
		return pedidoConectado;
	}
}
