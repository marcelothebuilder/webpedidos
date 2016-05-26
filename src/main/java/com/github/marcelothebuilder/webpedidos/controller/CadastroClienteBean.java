package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.github.marcelothebuilder.webpedidos.controller.events.EnderecoAlteradoEvent;
import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.cliente.TipoPessoa;
import com.github.marcelothebuilder.webpedidos.model.endereco.Endereco;
import com.github.marcelothebuilder.webpedidos.repository.Clientes;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private @Inject Cliente cliente;
	private @Inject Clientes clientes;

	/**
	 * Acessor de leitura para o campo cliente
	 * 
	 * @return o cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Define um novo valor para o campo cliente. Valores nulos inicializam um
	 * objeto vazio.
	 * 
	 * @param cliente
	 *            o cliente a ser definido
	 */
	public void setCliente(Cliente cliente) {
		if (cliente == null) {
			this.cliente = new Cliente();
		} else {
			this.cliente = cliente;
		}
	}

	public TipoPessoa[] getTiposPessoa() {
		return TipoPessoa.values();
	}

	public void salvar() {
		FacesMessage facesMessage = null;

		try {
			cliente = clientes.guardar(cliente);

			// exibe mensagem de confirmação
			String message = String.format("Cliente %s foi salvo.", cliente.getNome());
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);

		} catch (NegocioException e) {
			String message = String.format("%s", e.getMessage());
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
		} catch (PersistenceException e) {
			String message = String.format("Violação de chave única: %s", e.getMessage());
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMessage);
	}

	public List<Endereco> getEnderecos() {
		return cliente.getEndereco();
	}

	public boolean isClienteExistente() {
		return cliente != null && cliente.getCodigo() != null;
	}

	public boolean isClienteNovo() {
		return !isClienteExistente();
	}

	public void enderecoAlterado(@Observes EnderecoAlteradoEvent event) {
		Endereco endereco = event.getEndereco();
		if (event.isNovo()) {
			endereco.setCliente(cliente);
			cliente.getEndereco().add(endereco);
		}
		FacesMessage fMessage = new FacesMessage();
		fMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		if (event.isNovo()) {
			fMessage.setDetail("Endereço adicionado.");
		} else {
			fMessage.setDetail("Endereço alterado.");
		}

		fMessage.setSummary(fMessage.getDetail());
		FacesContext.getCurrentInstance().addMessage(null, fMessage);
	}

	public void deletaEndereco(Endereco endereco) {
		this.cliente.getEndereco().remove(endereco);
	}

}
