package com.github.marcelothebuilder.webpedidos.model.usuario;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(name = "uk_email", columnNames = { "email" }) })
public class Usuario implements Serializable {
	private Long codigo;
	private String nome;
	private String senha;
	private String email;
	private Boolean ativo;
	private Set<Grupo> grupos = new HashSet<>();
	private static final long serialVersionUID = 1L;

	public Usuario() {
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
	@Size(min = 3, max = 100)
	@Column(nullable = false, length = 100)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Define o nome de usuário deste objeto usuário. Deve ter no mínimo 3
	 * caracteres e no máximo 100. Não pode ser nulo.
	 * 
	 * @param nome
	 *            o nome a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotBlank
	@Size(min = 6, max = 100)
	@Column(nullable = false, length = 100)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Retorna o endereço de e-mail.
	 * 
	 * @return o endereço de e-mail armazenado no campo email.
	 */
	@NotBlank
	@Email
	@Column(nullable = false, length = 254)
	// @Column(nullable = false, length = 254, unique = true)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Define o endereço de e-mail. Deve ser uma {@link String} não nula e
	 * válida de acordo com as definições RFC. O tamanho máximo é de 254
	 * caracteres. Deve ser único.
	 * 
	 * @see <a href="https://tools.ietf.org/html/rfc5321#section-4.5.3">https://
	 *      tools.ietf.org/html/rfc5321#section-4.5.3</a>
	 * @param email
	 *            o email a ser definido
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Column(nullable = false)
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_grupo", joinColumns = {
			@JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario_grupo_to_usuario")

			) }, inverseJoinColumns = { @JoinColumn(name = "grupo_id", foreignKey = @ForeignKey(name = "fk_usuario_grupo_to_grupo")) })
	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
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
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
