package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.produto.Categoria;
import com.github.marcelothebuilder.webpedidos.repository.Categorias;

@Named
@Dependent
public class CategoriaConverter extends GenericEntityConverter<Categoria> {

	@Inject
	private Categorias categorias;

	@Override
	protected Categoria buscaPorChave(String chaveString) {
		Long chave = ConverterUtils.parseLongOuRetornaNull(chaveString);
		Categoria categoria = (chave != null) ? categorias.porCodigo(chave) : null;
		return categoria;
	}

	@Override
	protected String extraiChave(Categoria objeto) {
		return objeto.getCodigo().toString();
	}
}
