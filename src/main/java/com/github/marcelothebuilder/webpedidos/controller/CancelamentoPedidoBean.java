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
import com.github.marcelothebuilder.webpedidos.service.CancelamentoPedidoService;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CancelamentoPedidoService cancelamentoPedidoService;

	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	public void cancelarPedido() {
		FacesMessage fMessage = null;
		try {
			this.pedido = this.cancelamentoPedidoService.cancelar(this.pedido);
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));

			fMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido cancelado.", "Pedido cancelado.");
		} catch (NegocioException e) {
			fMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage());
		}
		FacesContext.getCurrentInstance().addMessage(null, fMessage);
	}

}
