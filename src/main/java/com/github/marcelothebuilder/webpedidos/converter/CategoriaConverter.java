package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.produto.Categoria;
import com.github.marcelothebuilder.webpedidos.repository.Categorias;

/* Não podemos usar @FacesConverter, já que estamos utilizando @Named */
//@FacesConverter(forClass = Categoria.class)

@Named
@Dependent
public class CategoriaConverter implements Converter {

	/*
	 * <p>The message identifier of the Message to be created if the conversion
	 * fails. The message format string for this message may optionally include
	 * <code>{0}</code> and <code>{1}</code> placeholders, which will be
	 * replaced by the object and value.</p>
	 */
	public static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";

	@Inject
	private Categorias categorias;

	public CategoriaConverter() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria categoria = null;

		if (value != null) {
			try {
				Long id = Long.parseLong(value);
				categoria = categorias.porCodigo(id);
				return categoria;
			} catch (NumberFormatException ex) {
				return null;
			}
		}

		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		/* retorna nulo imediatamente */
		if (value == null || !(value instanceof Categoria)) {
			return null;
		}

		Categoria categoria = (Categoria) value;

		// podemos receber categoria sem código
		if (categoria.getCodigo() == null) {
			return null;
		}

		return categoria.getCodigo().toString();

	}
}
