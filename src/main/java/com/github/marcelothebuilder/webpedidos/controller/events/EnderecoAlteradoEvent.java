/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller.events;

import com.github.marcelothebuilder.webpedidos.model.endereco.Endereco;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class EnderecoAlteradoEvent {

	private Endereco endereco;
	private boolean novo;

	public EnderecoAlteradoEvent() {
	}

	public EnderecoAlteradoEvent(Endereco endereco) {
		this();
		this.setEndereco(endereco);
	}

	public EnderecoAlteradoEvent(Endereco endereco, boolean novo) {
		this(endereco);
		this.setNovo(novo);
	}

	/**
	 * Acessor de leitura para o campo endereco
	 * 
	 * @return o endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * Define um novo valor para o campo endereco
	 * 
	 * @param endereco
	 *            o endereco a ser definido
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setNovo(boolean novo) {
		this.novo = novo;
	}

	public boolean isNovo() {
		return novo;
	}

	public boolean isExistente() {
		return !this.isNovo();
	}

}
