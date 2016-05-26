package com.github.marcelothebuilder.webpedidos.model.cliente;

import org.apache.commons.lang3.StringUtils;

public enum TipoPessoa {
	FISICA, JURIDICA;
	
	@Override
	public String toString() {
		return StringUtils.capitalize(super.toString().toLowerCase());
	}
}
