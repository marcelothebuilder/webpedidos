/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.github.marcelothebuilder.webpedidos.util.mail.config.MailerConfig;
import com.github.marcelothebuilder.webpedidos.util.mail.impl.MailTemplateVelocity;

/**
 * @author Marcelo Paixao Resende
 *
 */

@RequestScoped
public class Mailer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MailerConfig config;

	private Session criarSession() {
		Session session = Session.getDefaultInstance(getConfig().getProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getConfig().getUsuario(), getConfig().getSenha());
			}
		});
		
		session.setDebug(true);

		return session;
	}

	/**
	 * Acessor de leitura para o campo config
	 * 
	 * @return o config
	 */
	protected MailerConfig getConfig() {
		return config;
	}

	public void send(Mail mail) throws MailerException {
		Session session = this.criarSession();

		MimeMessage message = new MimeMessage(session);

		MailTemplate template = new MailTemplateVelocity(mail);

		try {
			message.setFrom(new InternetAddress(this.getConfig().getRemetente()));
			Address[] toUser = InternetAddress.parse(mail.getDestinatario());
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(mail.getAssunto());
			message.setContent(template.getHtml(), "text/html");
			Transport.send(message);
		} catch (Exception e) {
			throw new MailerException(String.format("Erro ao enviar email: %s", e.getMessage()), e);
		}

	}

}
