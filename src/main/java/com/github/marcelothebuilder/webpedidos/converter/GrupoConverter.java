package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;
import com.github.marcelothebuilder.webpedidos.repository.Grupos;

@Named
@Dependent
public class GrupoConverter implements Converter {

	private @Inject Grupos grupos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}

		Integer codigo = null;

		try {
			codigo = Integer.parseInt(value);

			assert grupos != null;

			Grupo grupo = grupos.porCodigo(codigo);

			return grupo;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		/* retorna nulo imediatamente */
		if (value == null || !(value instanceof Grupo)) {
			return "";
		}

		Grupo grupo = (Grupo) value;

		// podemos receber produto sem código
		if (grupo.getCodigo() == null) {
			return "";
		}

		return grupo.getCodigo().toString();
	}

}
