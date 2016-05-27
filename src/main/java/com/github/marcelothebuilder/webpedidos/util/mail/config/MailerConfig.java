package com.github.marcelothebuilder.webpedidos.util.mail.config;

import java.util.Properties;

public interface MailerConfig {

	Properties getProperties();

	String getUsuario();

	String getRemetente();

	String getSenha();

}