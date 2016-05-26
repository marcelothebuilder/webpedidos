package com.github.marcelothebuilder.webpedidos.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.Produtos;
import com.github.marcelothebuilder.webpedidos.util.interceptor.Transactional;

public class CadastroProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private Produtos produtos;

	@Transactional
	public Produto salvar(Produto produto) {

		Produto produtoExistente = produtos.porSku(produto.getSku());
		// TODO: ao invés de validar este erro aqui, deixar que o database
		// retorne o erro de unique constraint violation
		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o mesmo Sku");
		}

		return produtos.guardar(produto);
	}
}
