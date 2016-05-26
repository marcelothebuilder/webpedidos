package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;

import com.github.marcelothebuilder.webpedidos.model.cliente.TipoPessoa;
import com.github.marcelothebuilder.webpedidos.model.endereco.Cidade;
import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;

public class ClienteFilter implements Serializable {
	private static final long serialVersionUID = 8218092099095227354L;
	
	private String nome;
	private String email;
	private String documentoReceitaFederal;
	private TipoPessoa tipoPessoa;
	private Estado estado;
	private Cidade cidade;
	/**
	 * Acessor de leitura para o campo nome
	 * @return o nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Define um novo valor para o campo nome
	 * @param nome o nome a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Acessor de leitura para o campo email
	 * @return o email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Define um novo valor para o campo email
	 * @param email o email a ser definido
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Acessor de leitura para o campo documentoReceitaFederal
	 * @return o documentoReceitaFederal
	 */
	public String getDocumentoReceitaFederal() {
		return documentoReceitaFederal;
	}
	/**
	 * Define um novo valor para o campo documentoReceitaFederal
	 * @param documentoReceitaFederal o documentoReceitaFederal a ser definido
	 */
	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
		this.documentoReceitaFederal = documentoReceitaFederal;
	}
	/**
	 * Acessor de leitura para o campo tipoPessoa
	 * @return o tipoPessoa
	 */
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}
	/**
	 * Define um novo valor para o campo tipoPessoa
	 * @param tipoPessoa o tipoPessoa a ser definido
	 */
	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	/**
	 * Acessor de leitura para o campo estado
	 * @return o estado
	 */
	public Estado getEstado() {
		return estado;
	}
	/**
	 * Define um novo valor para o campo estado
	 * @param estado o estado a ser definido
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	/**
	 * Acessor de leitura para o campo cidade
	 * @return o cidade
	 */
	public Cidade getCidade() {
		return cidade;
	}
	/**
	 * Define um novo valor para o campo cidade
	 * @param cidade o cidade a ser definido
	 */
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	
}
