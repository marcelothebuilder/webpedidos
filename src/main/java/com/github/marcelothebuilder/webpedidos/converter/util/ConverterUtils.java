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

}
