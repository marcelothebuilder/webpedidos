package com.github.marcelothebuilder.webpedidos.model.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.marcelothebuilder.webpedidos.model.produto.Produto;

/**
 * Entity implementation class for Entity: ItemPedido
 *
 */
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {
	private Integer codigo;
	private Integer quantidade = 1;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private Produto produto;
	private Pedido pedido;
	private static final long serialVersionUID = 1L;

	public ItemPedido() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(nullable = false)
	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		// this.pedido.recalculaValorTotal();
		this.quantidade = quantidade;
	}

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return this.valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_pedido_to_produto"))
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_pedido_to_pedido"))
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Transient
	public BigDecimal getValorTotal() {
		if (getProduto() == null || getProduto().getCodigo() == null) {
			return BigDecimal.ZERO;
		}
		Integer quantidadew = this.getQuantidade();
		BigDecimal quantidadeAsBigDecimal = new BigDecimal(quantidadew);
		BigDecimal valorUnitarioProduto = this.getProduto().getValorUnitario();
		BigDecimal valorTotal = valorUnitarioProduto.multiply(quantidadeAsBigDecimal);

		return valorTotal;
	}

	@Transient
	public boolean hasProduto() {
		return this.getProduto() != null && this.getProduto().getCodigo() != null;
	}

	@Transient
	public boolean isEstoqueSuficiente() {
		return this.getPedido().isEmitido() || this.getProduto().getQuantidadeEstoque() >= this.getQuantidade();
	}

	@Transient
	public boolean isEstoqueInsuficiente() {
		return !this.isEstoqueSuficiente();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemPedido))
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

}
