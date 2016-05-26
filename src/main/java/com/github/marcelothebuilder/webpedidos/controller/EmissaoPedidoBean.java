/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.controller.events.PedidoAlteradoEvent;
import com.github.marcelothebuilder.webpedidos.controller.qualifiers.PedidoEdicao;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.service.EmissaoPedidoService;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	@Inject
	private EmissaoPedidoService emissaoPedidoService;

	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;

	public void emitirPedido() {
		this.pedido.removerNovoItem();

		try {
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);

			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(pedido));

			FacesMessage fMessage = new FacesMessage("Pedido emitido!");
			FacesContext.getCurrentInstance().addMessage(null, fMessage);
		} finally {
			this.pedido.novoItem();
		}
	}
}
