/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.controller.events.EnderecoAlteradoEvent;
import com.github.marcelothebuilder.webpedidos.model.endereco.Endereco;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@ViewScoped
public class CadastroEnderecoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Endereco endereco = new Endereco();

	@Inject
	private Event<EnderecoAlteradoEvent> enderecoEvent;

	private boolean novo;

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
		this.novo = false;
	}

	public void novo() {
		endereco = new Endereco();
		this.novo = true;
	}

	public void salvar() {
		EnderecoAlteradoEvent event = new EnderecoAlteradoEvent();
		event.setEndereco(this.endereco);
		event.setNovo(this.novo);
		enderecoEvent.fire(event);
	}

}
