package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.repository.Usuarios;

@Named
@Dependent
public class UsuarioConverter implements Converter {

	private @Inject Usuarios usuarios;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}

		Long codigo = null;

		try {
			codigo = Long.parseLong(value);

			assert usuarios != null;

			Usuario usuario = usuarios.porCodigo(codigo);

			return usuario;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		/* retorna nulo imediatamente */
		if (value == null || !(value instanceof Usuario)) {
			return "";
		}

		Usuario usuario = (Usuario) value;

		// podemos receber usuario sem código
		if (usuario.getCodigo() == null) {
			return "";
		}

		return usuario.getCodigo().toString();
	}

}
