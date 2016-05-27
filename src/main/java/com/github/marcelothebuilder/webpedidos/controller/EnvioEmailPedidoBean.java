/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.controller.qualifiers.PedidoEdicao;
import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.util.mail.Mail;
import com.github.marcelothebuilder.webpedidos.util.mail.MailUtils;
import com.github.marcelothebuilder.webpedidos.util.mail.Mailer;
import com.github.marcelothebuilder.webpedidos.util.mail.MailerException;

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
		Cliente cliente = this.pedido.getCliente();
		String email = cliente.getEmail();

		FacesMessage fMessage = new FacesMessage();

		Mail mail = new Mail();
		mail.setDestinatario(email);
		mail.setAssunto(String.format("Seu pedido de venda #%d", this.pedido.getCodigo()));
		mail.addParametro("pedido", this.pedido);
		mail.setTemplate(MailUtils.localizaTemplate("pedido.template"));

		try {
			mailer.send(mail);
			fMessage.setDetail(String.format("Pedido enviado por e-mail para %s com sucesso.", mail.getDestinatario()));
		} catch (MailerException e) {
			fMessage.setDetail(e.getMessage());
		}

		fMessage.setSummary(fMessage.getDetail());

		FacesContext.getCurrentInstance().addMessage(null, fMessage);

	}

}
