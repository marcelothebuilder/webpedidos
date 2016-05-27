/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail.config;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.github.marcelothebuilder.webpedidos.util.mail.config.impl.MailerConfigImpl;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class MailerConfigProducer {
	
	@Produces
	@ApplicationScoped
	public MailerConfig getMailConfig() throws IOException {
		Properties props = new Properties();
		
		props.load(this.getClass().getResourceAsStream("/mail.properties"));
		
		MailerConfig config = new MailerConfigImpl(props);
		assert(props.size() > 0);
		return config;
	}
}
