/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;
import com.github.marcelothebuilder.webpedidos.repository.Estados;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@Dependent
public class EstadoConverter implements Converter {

	private @Inject Estados estados;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.
	 * FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// value é nulo ou em branco?
		if (value == null || StringUtils.isBlank(value)) {
			return null;
		}

		try {
			Long codigo = Long.parseLong(value);
			return estados.porCodigo(codigo);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.
	 * FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}

		if (!(value instanceof Estado)) {
			return null;
		}

		Estado estado = (Estado) value;

		if (estado.getCodigo() == null) {
			return null;
		}

		return estado.getCodigo().toString();
	}

}
