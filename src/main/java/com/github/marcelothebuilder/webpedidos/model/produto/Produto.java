package com.github.marcelothebuilder.webpedidos.model.produto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.github.marcelothebuilder.webpedidos.service.NegocioException;
import com.github.marcelothebuilder.webpedidos.validation.Sku;

/**
 * Entity implementation class for Entity: Produto
 *
 */
@Entity
@Table(name = "produto", uniqueConstraints = { @UniqueConstraint(name = "uk_sku", columnNames = { "sku" }) })
public class Produto implements Serializable {
	private Long codigo;
	private String nome;
	private String sku;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private Integer quantidadeEstoque;
	private Categoria categoria;
	private static final long serialVersionUID = 1L;

	public Produto() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 128)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotBlank
	@Sku
	@Column(nullable = false, length = 20)
	// @Column(nullable = false, length = 20, unique = true)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}

	@NotNull
	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return this.valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@NotNull
	@Min(0)
	@Max(9999)
	@Column(name = "quantidade_estoque", nullable = false)
	public Integer getQuantidadeEstoque() {
		return this.quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_to_categoria"))
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		if (!(obj instanceof Produto))
			return false;
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", getSku(), getNome());
	}

	public void baixarEstoque(Integer quantidade) {
		int novaQuantidade = this.getQuantidadeEstoque() - quantidade;
		if (novaQuantidade < 0) {
			String exceptionMessage = String.format("Não há disponibilidade no estoque de %d itens do produto %s - %s",
					quantidade, this.getSku(), this.getNome());
			throw new NegocioException(exceptionMessage);
		}
		this.setQuantidadeEstoque(novaQuantidade);
	}

	public void adicionarEstoque(Integer quantidade) {
		Integer novaQuantidadeEstoque = this.getQuantidadeEstoque() + quantidade;
		this.setQuantidadeEstoque(novaQuantidadeEstoque);
	}

}
