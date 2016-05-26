package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.model.cliente.TipoPessoa;
import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;
import com.github.marcelothebuilder.webpedidos.repository.Clientes;
import com.github.marcelothebuilder.webpedidos.repository.filter.ClienteFilter;
import com.github.marcelothebuilder.webpedidos.service.NegocioException;

@Named
@ViewScoped
public class PesquisaClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private @Inject Clientes clientes;

	private Cliente clienteSelecionado;

	private @Inject ClienteFilter clienteFilter;

	private Set<Cliente> clientesData;

	private @Inject CidadeEstadoBean clienteEstadoBean;

	public TipoPessoa[] getTiposPessoa() {
		return TipoPessoa.values();
	}

	/**
	 * Acessor de leitura para o campo clientesData
	 * 
	 * @return o clientesData
	 */
	public Set<Cliente> getClientesData() {
		if (clientesData == null) {
			clientesData = clientes.filtrados(clienteFilter);
		}

		return clientesData;
	}

	/**
	 * Define um novo valor para o campo clientesData
	 * 
	 * @param clientesData
	 *            o clientesData a ser definido
	 */
	public void setClientesData(Set<Cliente> clientesData) {
		this.clientesData = clientesData;
	}

	/**
	 * Acessor de leitura para o campo clienteFilter
	 * 
	 * @return o clienteFilter
	 */
	public ClienteFilter getClienteFilter() {
		return clienteFilter;
	}

	/**
	 * Define um novo valor para o campo clienteFilter
	 * 
	 * @param clienteFilter
	 *            o clienteFilter a ser definido
	 */
	public void setClienteFilter(ClienteFilter clienteFilter) {
		this.clienteFilter = clienteFilter;
	}

	/**
	 * Acessor de leitura para o campo clienteSelecionado
	 * 
	 * @return o clienteSelecionado
	 */
	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	/**
	 * Define um novo valor para o campo clienteSelecionado
	 * 
	 * @param clienteSelecionado
	 *            o clienteSelecionado a ser definido
	 */
	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	/**
	 * Acessor de leitura para o campo clienteEstadoBean
	 * 
	 * @return o clienteEstadoBean
	 */
	public CidadeEstadoBean getClienteEstadoCidadeBean() {
		return clienteEstadoBean;
	}

	public void filtrar() {
		Estado estado = clienteEstadoBean.getEstado();
		clienteFilter.setEstado(estado);
		Set<Cliente> data = clientes.filtrados(clienteFilter);
		setClientesData(data);
	}

	public void deletaCliente() {
		FacesMessage facesMessage = null;
		try {
			clientes.remover(clienteSelecionado);
			String message = String.format("Cliente %s deletado com sucesso.", clienteSelecionado.getNome());
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
			filtrar();
		} catch (NegocioException ex) {
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage());
		}

		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

}
