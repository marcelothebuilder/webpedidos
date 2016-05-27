/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.report;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class RelatorioFile {
	private String nome;
	private String caminho;

	/**
	 * Acessor de leitura para o campo nome
	 * 
	 * @return o nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Acessor de leitura para o campo caminho
	 * 
	 * @return o caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * Define um novo valor para o campo nome
	 * 
	 * @param nome
	 *            o nome a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Define um novo valor para o campo caminho
	 * 
	 * @param caminho
	 *            o caminho a ser definido
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
}
