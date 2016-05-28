package com.github.marcelothebuilder.webpedidos.model.endereco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="estado")
public class Estado implements Serializable {
	private static final long serialVersionUID = 2L;
	private Long codigo;
	private String sigla;
	private String nome;
	private List<Cidade> cidades = new ArrayList<>();
	
	/**
	 * Acessor de leitura para o campo codigo
	 * @return o codigo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Define um novo valor para o campo codigo
	 * @param codigo o codigo a ser definido
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@NotNull
	@Column(length = 2)
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(nullable = false, length = 50)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="estado")
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Estado))
			return false;
		Estado other = (Estado) obj;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}	

	
}
