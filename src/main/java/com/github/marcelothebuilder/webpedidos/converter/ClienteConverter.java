package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.repository.Clientes;

@Named
@Dependent
public class ClienteConverter implements Converter {

	private @Inject Clientes clientes;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}

		Long codigo = null;

		try {
			codigo = Long.parseLong(value);

			assert clientes != null;

			Cliente cliente = clientes.porCodigo(codigo);

			return cliente;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		/* retorna nulo imediatamente */
		if (value == null || !(value instanceof Cliente)) {
			return "";
		}

		Cliente cliente = (Cliente) value;

		// podemos receber cliente sem código
		if (cliente.getCodigo() == null) {
			return "";
		}

		return cliente.getCodigo().toString();
	}

}
