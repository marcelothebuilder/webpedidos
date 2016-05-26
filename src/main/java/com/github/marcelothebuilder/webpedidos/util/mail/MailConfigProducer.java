/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class MailConfigProducer {

	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException {

		Properties props = new Properties();
		props.load(getClass().getResourceAsStream("/mail.properties"));

		SimpleMailConfig config = new SimpleMailConfig();

		Integer port = Integer.parseInt(props.getProperty("mail.server.port"));
		Boolean enableSsl = Boolean.parseBoolean(props.getProperty("mail.enable.ssl"));

		config.setServerHost(props.getProperty("mail.server.host"));
		config.setServerPort(port);
		config.setEnableSsl(enableSsl);
		config.setAuth(Boolean.parseBoolean(props.getProperty("mail.auth")));
		config.setUsername(props.getProperty("mail.username"));
		config.setPassword(props.getProperty("mail.password"));

		return config;
	}
}
