package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.Produtos;

@Named
@Dependent
public class ProdutoConverter extends GenericEntityConverter<Produto> {

	private @Inject Produtos produtos;

	@Override
	protected Produto buscaPorChave(String chave) {
		Long codigo = ConverterUtils.parseLongOuRetornaNull(chave);
		return produtos.porCodigo(codigo);
	}

	@Override
	protected String extraiChave(Produto objeto) {
		return objeto.getCodigo().toString();
	}

}
