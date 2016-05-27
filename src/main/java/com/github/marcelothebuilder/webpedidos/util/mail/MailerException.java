/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class MailerException extends Exception {
	private static final long serialVersionUID = 1L;

	public MailerException() {}

	/**
	 * @param message
	 */
	public MailerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MailerException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MailerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MailerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
