package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelothebuilder.webpedidos.controller.events.PedidoAlteradoEvent;
import com.github.marcelothebuilder.webpedidos.controller.qualifiers.PedidoEdicao;
import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.endereco.Cidade;
import com.github.marcelothebuilder.webpedidos.model.pedido.EnderecoEntrega;
import com.github.marcelothebuilder.webpedidos.model.pedido.FormaPagamento;
import com.github.marcelothebuilder.webpedidos.model.pedido.ItemPedido;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.repository.Clientes;
import com.github.marcelothebuilder.webpedidos.repository.Produtos;
import com.github.marcelothebuilder.webpedidos.repository.Usuarios;
import com.github.marcelothebuilder.webpedidos.service.CadastroPedidoService;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;
import com.github.marcelothebuilder.webpedidos.validation.Sku;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {
	private static final long serialVersionUID = 2L;

	@Produces
	@PedidoEdicao
	private Pedido pedido;

	private Set<Usuario> vendedores;
	private Produto produtoLinhaEditavel;

	private String skuInsercao;

	private @Inject Usuarios usuarios;
	private @Inject Clientes clientes;
	private @Inject Produtos produtos;
	private @Inject CadastroPedidoService pedidoService;
	private @Inject CidadeEstadoBean cidadeEstadoBean;

	@PostConstruct
	public void inicializar() {
		vendedores = usuarios.vendedores();
	}

	public CadastroPedidoBean() {
		limpar();
	}

	private void limpar() {
		this.pedido = getPedidoLimpo();
	}

	private Pedido getPedidoLimpo() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
		pedido.getEnderecoEntrega().setCidade(new Cidade());
		pedido.novoItem();
		return pedido;
	}

	public void salvar() {
		this.pedido.removerNovoItem();

		// insere a cidade do bean na entidade
		this.pedido.getEnderecoEntrega().setCidade(cidadeEstadoBean.getCidade());

		FacesMessage fMessage = new FacesMessage();

		try {
			this.pedido.recalculaValorTotal();
			this.pedido = pedidoService.salvar(pedido);
			fMessage.setDetail("Pedido salvo com sucesso");
			fMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		} catch (NegocioException e) {
			fMessage.setDetail(e.getMessage());
			fMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		} finally {
			this.pedido.novoItem();
		}

		fMessage.setSummary(fMessage.getDetail());
		FacesContext.getCurrentInstance().addMessage(null, fMessage);
	}

	/**
	 * Acessor de leitura para o campo vendedores
	 * 
	 * @return o vendedores
	 */
	public Set<Usuario> getVendedores() {
		return vendedores;
	}

	public List<Cliente> pesquisaCliente(String nomePesquisa) {
		Set<Cliente> result = clientes.porNome(nomePesquisa);
		return new LinkedList<>(result);
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	/**
	 * Acessor de leitura para o campo cidadeEstadoBean
	 * 
	 * @return o cidadeEstadoBean
	 */
	public CidadeEstadoBean getCidadeEstadoBean() {
		return cidadeEstadoBean;
	}

	/**
	 * Acessor de leitura para o campo pedido
	 * 
	 * @return o pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * Define um novo valor para o campo pedido. Se o parâmetro pedido for uma
	 * referência para nulo, instancia um novo pedido.
	 * 
	 * @param pedido
	 *            o pedido a ser definido
	 */
	public void setPedido(Pedido pedido) {

		if (pedido == null) {
			limpar();
		} else {
			this.pedido = pedido;
			EnderecoEntrega enderecoEntrega = pedido.getEnderecoEntrega();
			Cidade cidade = enderecoEntrega.getCidade();
			if (this.cidadeEstadoBean.getEstado() == null) {
				this.cidadeEstadoBean.setEstado(cidade.getEstado());
				this.cidadeEstadoBean.setCidade(cidade);
			}

			this.pedido.novoItem();

		}
	}

	public void setPedidoParametro(Pedido pedido) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			setPedido(pedido);
		}
	}

	public Pedido getPedidoParametro() {
		return pedido;
	}

	/**
	 * Acessor de leitura para o campo produtoLinhaEditavel
	 * 
	 * @return o produtoLinhaEditavel
	 */
	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	/**
	 * Define um novo valor para o campo produtoLinhaEditavel
	 * 
	 * @param produtoLinhaEditavel
	 *            o produtoLinhaEditavel a ser definido
	 */
	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public List<Produto> pesquisaProdutoLinhaEditavel(String query) {
		return produtos.porNome(query);
	}

	public void carregarProdutoLinhaEditavel() {
		if (this.produtoLinhaEditavel != null) {
			addNovoProduto(produtoLinhaEditavel);
		}
	}

	private void addNovoProduto(Produto produto) {
		if (!isProdutoDuplicado(produto)) {
			ItemPedido item = this.pedido.getNovoItem();

			item.setProduto(produto);
			item.setValorUnitario(produto.getValorUnitario());

			pedido.novoItem();

			// pedido.recalculaValorTotal();
		} else {
			String message = String.format("Produto %s já está no pedido.", produto);
			FacesMessage fMessage = new FacesMessage(message);
			fMessage.setSeverity(FacesMessage.SEVERITY_WARN);

			FacesContext.getCurrentInstance().addMessage(null, fMessage);
		}

		skuInsercao = null;
		produtoLinhaEditavel = null;
	}

	/**
	 * Acessor de leitura para o campo skuInsercao
	 * 
	 * @return o skuInsercao
	 */
	@Sku
	public String getSkuInsercao() {
		return skuInsercao;
	}

	/**
	 * Define um novo valor para o campo skuInsercao
	 * 
	 * @param skuInsercao
	 *            o skuInsercao a ser definido
	 */
	public void setSkuInsercao(String skuInsercao) {
		this.skuInsercao = skuInsercao;
	}

	public void produtoPorSkuInsercao() {
		Produto produto = null;

		if (StringUtils.isNotBlank(skuInsercao)) {
			produto = produtos.porSku(skuInsercao);

			if (produto != null) {
				addNovoProduto(produto);
			} else {
				String message = String.format("Produto com SKU %s não existe.", skuInsercao);
				FacesMessage fMessage = new FacesMessage(message);
				fMessage.setSeverity(FacesMessage.SEVERITY_WARN);
				FacesContext.getCurrentInstance().addMessage(null, fMessage);
			}
		}

		skuInsercao = null;

	}

	private boolean isProdutoDuplicado(Produto produto) {
		for (ItemPedido item : getPedido().getItens()) {
			Produto itemProduto = item.getProduto();
			if (itemProduto.equals(produto)) {
				return true;
			}
		}
		return false;
	}

	public void processaQuantidade(ItemPedido item) {
		if (item.getQuantidade() < 1) {
			pedido.getItens().remove(item);
		}
	}

	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
		// this.pedido.recalculaValorTotal();
	}
}
