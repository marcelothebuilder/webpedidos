package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.produto.Categoria;
import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.Categorias;
import com.github.marcelothebuilder.webpedidos.service.CadastroProdutoService;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

/**
 * Controller de cadastro de produto. Fornece uma interface para preencher as
 * categorias e subcategorias da tela de cadastro de produtos. Também permite
 * criar produtos novos.
 * 
 * @author Marcelo Paixao Resende
 *
 */
@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto produto;

	@Inject
	private Categorias categorias;

	@Inject
	private CadastroProdutoService cadastroProdutoService;

	private List<Categoria> categoriasRaiz;

	private Categoria categoriaPai;

	public CadastroProdutoBean() {
		// evita sobrescrever um produto
		if (produto == null) {
			produto = new Produto();
		}
	}

	/**
	 * Salva o produto que estiver na propriedade produto
	 */
	public void salvar() {
		FacesMessage facesMessage = null;

		try {
			produto = cadastroProdutoService.salvar(produto);

			// salvo, continua
			limparFormulario();

			// exibe mensagem de confirmação
			String message = String.format("Produto foi salvo.");
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		} catch (NegocioException e) {
			String message = String.format("%s", e.getMessage());
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMessage);
	}

	/**
	 * Preenche a propriedade categoriasRaiz com valores da camada de
	 * persistência.
	 */
	public void inicializar() {
		FacesContext fContext = FacesContext.getCurrentInstance();

		if (!fContext.isPostback() || categoriasRaiz == null) {
			categoriasRaiz = categorias.raizes();
		}
	}

	/* getters e setters */

	/**
	 * 
	 * @return o produto atual sendo inserido ou editado
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * Define o produto atual. Adicionalmente, se o produto não for nulo e tiver
	 * uma categoria, a obtém e preenche a propriedade categoriaPai com ela.
	 * 
	 * @param produto
	 *            o produto atual a ser editado ou inserido
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;

		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

	/**
	 * Recebe um objeto Produto para editar e insere ele na propriedade produto.
	 * 
	 * @param produto
	 *            o produto que será editado
	 */
	public void setProdutoEditavel(Produto produto) {
		if (produto != null && !FacesContext.getCurrentInstance().isPostback()) {
			setProduto(produto);
		}
	}

	/**
	 * Sempre retorna nulo, pois não temos um produto editável. Este método deve
	 * ser público pois o componente viewParam exige isto.
	 * 
	 * @return null
	 */
	public Produto getProdutoEditavel() {
		/* dummy */
		return null;
	}

	/**
	 * 
	 * @return as atuais categorias raiz
	 */
	public List<Categoria> getCategoriasRaiz() {
		return categoriasRaiz;
	}

	/**
	 * 
	 * @return a atual categoria pai
	 */
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	/**
	 * 
	 * @param categoriaPai
	 *            a categoria pai deste bean
	 */
	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	/**
	 * Obtém as categorias filhas da atual propriedade categoriaPai e as
	 * retorna.
	 * 
	 * @return categorias filhas
	 */
	public List<Categoria> getSubcategorias() {
		if (categoriaPai != null) {
			return categorias.subcategoriasDe(categoriaPai);
		}
		return null;
	}

	/**
	 * Retorna se o produto atual é novo ou se está sendo editado.
	 * 
	 * @return true se o objeto produto atual já existe, false se for um novo
	 */
	public boolean isEditando() {
		return produto != null && produto.getCodigo() != null;
	}

	/*
	 * Métodos privados
	 */

	private void limparFormulario() {
		produto = new Produto();
		categoriaPai = null;
	}
}
