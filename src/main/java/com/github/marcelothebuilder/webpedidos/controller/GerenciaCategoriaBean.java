/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.github.marcelothebuilder.webpedidos.model.produto.Categoria;
import com.github.marcelothebuilder.webpedidos.repository.Categorias;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@ViewScoped
public class GerenciaCategoriaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;

	private TreeNode raiz = null;

	private Categoria categoriaEdicao = new Categoria();
	private Categoria categoriaExclusao;

	/**
	 * Retorna a raiz da árvore do TreeTable, iniciando-a caso já não esteja
	 * iniciada.
	 * 
	 * @return o nodo raiz da árvore do componente TreeTable
	 */
	public TreeNode getRaiz() {
		// inicia a árvore
		if (raiz == null) {
			System.out.println("called getRaiz");
			raiz = new DefaultTreeNode("Raiz", null);
			List<Categoria> raizes = categorias.raizes();
			preencheSubCategorias(raizes, raiz);
		}

		return raiz;
	}

	/**
	 * Calcula o número de produtos em uma categoria.
	 * 
	 * @param categoria
	 *            um objeto da entidade categoria
	 * @return o número de produtos na categoria ou "--" caso não seja uma
	 *         subcategoria
	 */
	public String calculaProdutos(Categoria categoria) {
		if (categoria.isSubcategoria()) {
			return categorias.quantidadeDeProdutos(categoria).toString();
		}

		return "--";
	}

	/**
	 * Acessor de leitura para o campo categoriaEdicao
	 * 
	 * @return o categoriaEdicao
	 */
	public Categoria getCategoriaEdicao() {
		return categoriaEdicao;
	}

	/**
	 * Define um novo valor para o campo categoriaEdicao
	 * 
	 * @param categoriaEdicao
	 *            o categoriaEdicao a ser definido
	 */
	public void setCategoriaEdicao(Categoria categoriaEdicao) {
		this.categoriaEdicao = categoriaEdicao;
	}

	public Categoria getCategoriaExclusao() {
		return categoriaExclusao;
	}

	public void setCategoriaExclusao(Categoria categoriaExclusao) {
		this.categoriaExclusao = categoriaExclusao;
	}

	/**
	 * Limpa o campo categoriaEdicao com uma nova categoria para operação de
	 * criação
	 */
	public void limpaCategoria() {
		categoriaEdicao = new Categoria();
	}

	/**
	 * Exclui categoria do atributo categoriaSelecionada da camada de
	 * persistência.
	 */
	public void excluirCategoria() {

		FacesMessage message = null;

		try {
			categorias.remover(categoriaExclusao);
			String strMessage = String.format("Categoria %s removida", categoriaExclusao.getDescricao());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, strMessage, strMessage);
		} catch (NegocioException ne) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ne.getMessage(), ne.getMessage());
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

		// raiz deve ser atualizada
		raiz = null;
	}

	/**
	 * Salva a categoria que está sendo atualmente editada
	 */
	public void salvarCategoria() {

		FacesMessage message = null;
		try {
			categorias.guardar(categoriaEdicao);
			String strMessage = String.format("Categoria %s salva.", categoriaEdicao.getDescricao());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, strMessage, strMessage);
		} catch (Exception e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

		// raiz deve ser atualizada
		raiz = null;
	}

	/**
	 * Preenche um nodo com subnodos, que são suas subcategorias.
	 * 
	 * @param raizes
	 *            subcategorias a serem adicionadas como sub-nodos no nodo
	 *            fornecido
	 * @param node
	 *            nodo raiz que receberá os sub-nodos.
	 */
	private void preencheSubCategorias(List<Categoria> raizes, TreeNode node) {
		for (Categoria categoria : raizes) {
			TreeNode subNode = new DefaultTreeNode(categoria, node);

			if (categoria.getCategoriaPai() == null) {
				preencheSubCategorias(categoria.getSubcategorias(), subNode);
			}
		}
	}
}
