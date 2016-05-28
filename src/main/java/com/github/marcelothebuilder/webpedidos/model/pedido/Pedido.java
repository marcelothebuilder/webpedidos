package com.github.marcelothebuilder.webpedidos.model.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;

/**
 * Entity implementation class for Entity: Pedido
 *
 */
@Entity
@Table(name="pedido")
public class Pedido implements Serializable {
	private Long codigo;
	private Date dataCriacao;
	private Date dataEntrega;
	private String observacao;

	private BigDecimal valorFrete;
	private BigDecimal valorDesconto;
	private BigDecimal valorTotal;

	private StatusPedido statusPedido;
	private FormaPagamento formaPagamento;

	private EnderecoEntrega enderecoEntrega;
	private List<ItemPedido> itens = new ArrayList<>();

	/* cliente */
	private Cliente cliente;

	/* vendedor */
	private Usuario vendedor;

	private static final long serialVersionUID = 1L;

	public Pedido() {
		super();
		statusPedido = StatusPedido.ORCAMENTO;
		valorFrete = BigDecimal.ZERO;
		valorDesconto = BigDecimal.ZERO;
		valorTotal = BigDecimal.ZERO;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Future
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega", nullable = false)
	public Date getDataEntrega() {
		return this.dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	@Lob
	// @Transient
	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull
	@DecimalMin("0.0")
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
		// this.recalculaValorTotal();
	}

	@NotNull
	@DecimalMin("0.0")
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
		// this.recalculaValorTotal();
	}

	@NotNull
	@DecimalMin("0.0")
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_pedido", nullable = false, length = 20)
	public StatusPedido getStatusPedido() {
		return this.statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 20)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Embedded
	public EnderecoEntrega getEnderecoEntrega() {
		return this.enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_codigo", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_to_cliente"))
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "vendedor_codigo", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_to_vendedor"))
	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	/**
	 * Retorna a soma dos valores dos itens do pedido.
	 * 
	 * @return valor de todos os itens
	 */
	@Transient
	public BigDecimal getSubtotal() {
		BigDecimal valor = BigDecimal.ZERO;

		List<ItemPedido> collection = this.getItens();

		assert (collection != null);

		for (ItemPedido itemPedido : this.getItens()) {
			if (itemPedido.getProduto() != null && itemPedido.getProduto().getCodigo() != null) {
				valor = valor.add(itemPedido.getValorTotal());
			}
		}
		return valor;
	}

	@Transient
	public BigDecimal getValorTotalCalculado() {
		BigDecimal valor = getSubtotal();
		BigDecimal valorComFrete = valor.add(this.getValorFrete());
		BigDecimal valorComDescontoEFrete = valorComFrete.subtract(this.getValorDesconto());

		return valorComDescontoEFrete;
	}
	
	@Transient
	public ItemPedido getNovoItem() {
		if (this.isOrcamento()) {
			return this.getItens().get(0);
		}
		return null;
	}

	@Transient
	public boolean isNovo() {
		return getCodigo() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}
	
	@Transient
	public boolean isOrcamento() {
		return this.getStatusPedido() == StatusPedido.ORCAMENTO;
	}
	
	@Transient
	public boolean isEmitido() {
		return this.getStatusPedido() == StatusPedido.EMITIDO;
	}

	@Transient
	public boolean isValorTotalNegativo() {
		return this.getValorTotalCalculado().compareTo(BigDecimal.ZERO) < 0;
	}

	@Transient
	public boolean isEmissivel() {
		return this.isExistente() && this.isOrcamento();
	}

	@Transient
	public boolean isNaoEmissivel() {
		return !this.isEmissivel();
	}

	@Transient
	public boolean isCancelavel() {
		return this.isExistente() && !this.isCancelado();
	}

	@Transient
	public boolean isNaoCancelavel() {
		return !this.isCancelavel();
	}

	@Transient
	private boolean isCancelado() {
		return this.getStatusPedido() == StatusPedido.CANCELADO;
	}

	@Transient
	public boolean isAlteravel() {
		return this.isOrcamento();
	}

	@Transient
	public boolean isNaoAlteravel() {
		return !this.isAlteravel();
	}

	@Transient
	public boolean isNaoEnviavelPorEmail() {
		return this.isNovo() || this.isCancelado();
	}

	public void novoItem() {
		if (this.isOrcamento()) {
			Produto produto = new Produto();

			ItemPedido item = new ItemPedido();
			item.setProduto(produto);
			item.setPedido(this);
			item.setQuantidade(1);

			this.getItens().add(0, item);
			// this.recalculaValorTotal();
		}
	}
	
	public void recalculaValorTotal() {
		this.setValorTotal(this.getValorTotalCalculado());
	}

	public void removerNovoItem() {
		ItemPedido primeiroItem = this.getItens().get(0);
		if (primeiroItem != null && primeiroItem.getCodigo() == null) {
			this.getItens().remove(0);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pedido))
			return false;
		Pedido other = (Pedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
