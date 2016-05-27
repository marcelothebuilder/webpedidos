package com.github.marcelothebuilder.webpedidos.util.mail.config.impl;

import java.util.Properties;

import com.github.marcelothebuilder.webpedidos.util.mail.config.MailerConfig;

public class MailerConfigImpl implements MailerConfig {
	private Properties props;

	public MailerConfigImpl(Properties props) {
		this.props = props;
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.mail.config.MailerConfig#getProperties()
	 */
	@Override
	public Properties getProperties() {
		return this.props;
	}
	
	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.mail.config.MailerConfig#getUsuario()
	 */
	@Override
	public String getUsuario() {
		return props.getProperty("mail.username");
	}
	
	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.mail.config.MailerConfig#getRemetente()
	 */
	@Override
	public String getRemetente() {
		return this.getUsuario();
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.mail.config.MailerConfig#getSenha()
	 */
	@Override
	public String getSenha() {
		return props.getProperty("mail.password");
	}

}
