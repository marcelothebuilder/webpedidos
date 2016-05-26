package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;

@Named
@Dependent
public class PedidoConverter implements Converter {

	private @Inject Pedidos pedidos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}

		Long codigo = null;

		try {
			codigo = Long.parseLong(value);

			assert pedidos != null;
			assert codigo >= 0;

			Pedido pedido = pedidos.porCodigo(codigo);

			return pedido;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		/* retorna nulo imediatamente */
		if (value == null || !(value instanceof Pedido)) {
			return "";
		}

		Pedido pedido = (Pedido) value;

		// podemos receber pedido sem código
		if (pedido.getCodigo() == null) {
			return "";
		}

		return pedido.getCodigo().toString();
	}

}
