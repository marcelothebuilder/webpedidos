/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.report;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class RelatorioNaoExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RelatorioNaoExisteException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public RelatorioNaoExisteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RelatorioNaoExisteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RelatorioNaoExisteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public RelatorioNaoExisteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RelatorioNaoExisteException(Relatorio relatorio, Throwable cause) {
		this(String.format("Não foi possível encontrar o relatório %s", relatorio.getArquivo().getCaminho()), cause);
	}

}
