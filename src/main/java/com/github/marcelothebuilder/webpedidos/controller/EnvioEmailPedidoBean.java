/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.github.marcelothebuilder.webpedidos.controller.qualifiers.PedidoEdicao;
import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.api.SendFailedException;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@RequestScoped
public class EnvioEmailPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	public void enviarPedido() {
		MailMessage message = mailer.novaMensagem();

		Cliente cliente = this.pedido.getCliente();
		String email = cliente.getEmail();

		FacesMessage fMessage = new FacesMessage();

		try {
			message.to(email).subject("Seu pedido de venda #" + this.pedido.getCodigo())
					.bodyHtml(new VelocityTemplate(this.getClass().getResourceAsStream("/emails/pedido.template")))
					.put("pedido", this.pedido).put("numberTool", new NumberTool()).put("locale", Locale.getDefault())
					.send();
			fMessage.setDetail("Pedido enviado por e-mail com sucesso.");
		} catch (SendFailedException e) {
			fMessage.setDetail(e.getMessage());
		}

		fMessage.setSummary(fMessage.getDetail());

		FacesContext.getCurrentInstance().addMessage(null, fMessage);

	}

}
