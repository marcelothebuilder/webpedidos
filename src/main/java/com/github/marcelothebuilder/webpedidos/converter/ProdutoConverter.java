package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.Produtos;

@Named
@Dependent
public class ProdutoConverter implements Converter {

	private @Inject Produtos produtos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (value == null) {
			return null;
		}

		Long codigo = null;

		try {
			codigo = Long.parseLong(value);

			assert produtos != null;

			Produto produto = produtos.porCodigo(codigo);

			return produto;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		/* retorna nulo imediatamente */
		if (value == null || !(value instanceof Produto)) {
			return "";
		}

		Produto produto = (Produto) value;

		// podemos receber produto sem código
		if (produto.getCodigo() == null) {
			return "";
		}

		return produto.getCodigo().toString();
	}

}
