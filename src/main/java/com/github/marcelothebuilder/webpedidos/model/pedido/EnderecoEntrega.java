package com.github.marcelothebuilder.webpedidos.model.pedido;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.github.marcelothebuilder.webpedidos.model.endereco.Cidade;
import com.github.marcelothebuilder.webpedidos.validation.Cep;

@Embeddable
public class EnderecoEntrega implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Cidade cidade;
	private String logradouro;
	private String complemento;
	private String cep;
	private Integer numero;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "entrega_cidade", foreignKey = @ForeignKey( name="fk_endereco_entrega_to_cidade" ) )
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@NotBlank
	@Size(min = 4, max = 150)
	@Column(nullable = false, length = 150, name = "entrega_logradouro")
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@Size(max = 150)
	@Column(nullable=false, length=150, name="entrega_complemento")
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@NotBlank
	@Cep
	@Column(nullable=false, length = 12, name="entrega_cep")
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@NotNull
	@Min(0)
	@Column(nullable=false, length = 20, name="entrega_numero")
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
}
