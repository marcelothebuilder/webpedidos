package com.github.marcelothebuilder.webpedidos.model.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "uk_categoria_nome", columnNames = { "descricao" }) })
public class Categoria implements Serializable {
	private Long codigo;
	private String descricao;
	private Categoria categoriaPai;
	private List<Categoria> subcategorias = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public Categoria() {
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

	/**
	 * Retorna a descrição em texto da categoria.
	 * 
	 * @return descrição da categoria
	 */
	@NotBlank
	@Column(nullable = false)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "categoria_pai_id", foreignKey = @ForeignKey(name = "fk_categoria_to_categoria_pai"))
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaPai", orphanRemoval = true)
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Categoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	@Transient
	public boolean isSubcategoria() {
		return this.categoriaPai != null;
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
		if (!(obj instanceof Categoria))
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
