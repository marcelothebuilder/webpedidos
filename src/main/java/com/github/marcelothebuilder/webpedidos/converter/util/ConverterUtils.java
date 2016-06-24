package com.github.marcelothebuilder.webpedidos.converter.util;

public class ConverterUtils {

	public static Long parseLongOuRetornaNull(String valor) {
		try {
			return Long.parseLong(valor);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Integer parseIntegerOuRetornaNull(String valor) {
		try {
			return Integer.parseInt(valor);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Recebe um {@link Number}, código da entidade, e devolve este
	 * número em versão de string. Caso seja null, retorna uma {@link String} nula.
	 * @param codigo o codigo da entidade
	 * @return o codigo informado como string ou uma string nula caso o codigo seja nulo
	 */
	public static String getCodigoComoString(Number codigo) {
		boolean isCodigoNulo = (codigo == null);
		return isCodigoNulo ? "" : codigo.toString();
	}

}
