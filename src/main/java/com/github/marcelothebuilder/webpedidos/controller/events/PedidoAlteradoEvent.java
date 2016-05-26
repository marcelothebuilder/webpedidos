/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller.events;

import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class PedidoAlteradoEvent {
	private Pedido pedido;

	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Acessor de leitura para o campo pedido
	 * 
	 * @return o pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}
}
