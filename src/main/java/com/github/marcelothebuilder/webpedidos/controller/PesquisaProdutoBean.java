package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.Produtos;
import com.github.marcelothebuilder.webpedidos.repository.filter.ProdutoFilter;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

@Named
@ViewScoped
public class PesquisaProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private @Inject Produtos produtos;

	private ProdutoFilter produtoFilter = new ProdutoFilter();

	private List<Produto> produtosFiltrados = new ArrayList<>();

	private Produto produtoSelecionado;

	public List<Produto> getProdutos() {
		return produtosFiltrados;
	}

	public ProdutoFilter getProdutoFilter() {
		return produtoFilter;
	}

	public void setProdutoFilter(ProdutoFilter produtoFilter) {
		this.produtoFilter = produtoFilter;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public void pesquisar() {
		String message = "Exibindo mensagens filtradas";
		FacesMessage fMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);

		FacesContext.getCurrentInstance().addMessage("pesquisaMessages", fMessage);
		produtosFiltrados = produtos.filtrados(produtoFilter);
	}

	public void excluir() {
		FacesMessage fMessage = null;
		try {
			produtos.remover(produtoSelecionado);
			produtosFiltrados.remove(produtoSelecionado);
			String message = String.format("Produto %s excluído", produtoSelecionado.getSku());
			fMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		} catch (NegocioException e) {
			fMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
		}

		FacesContext.getCurrentInstance().addMessage("pesquisaMessages", fMessage);
	}
}
