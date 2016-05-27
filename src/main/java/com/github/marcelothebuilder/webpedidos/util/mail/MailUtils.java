/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class MailUtils {
	private final static String CAMINHO_EMAILS = "/emails/";
	
	public static String localizaTemplate(String arquivo) {
		return CAMINHO_EMAILS + arquivo;
	}

}
