package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

@Named
@Dependent
public abstract class GenericEntityConverter<T> implements Converter {

	protected abstract T buscaPorChave(String chave);
	protected abstract String extraiChave(T objeto);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String chave) {
		
		// nenhuma string ou valor em branco.
		if (chave == null || StringUtils.isBlank(chave)) {
			return null;
		}

		// subclasses devem prover esta função
		T entity = this.buscaPorChave(chave);

		return entity;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object objeto) {
		// se for nulo, retorna imediatamente
		if (objeto == null) {
			return "";
		}

		@SuppressWarnings("unchecked")
		String chave = this.extraiChave((T) objeto);

		if (chave == null) {
			return "";
		}

		return chave;
	}
}
