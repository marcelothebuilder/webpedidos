/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.model.endereco.Cidade;
import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;
import com.github.marcelothebuilder.webpedidos.repository.Cidades;
import com.github.marcelothebuilder.webpedidos.repository.Estados;
import com.github.marcelothebuilder.webpedidos.repository.filter.CidadeFilter;
import com.github.marcelothebuilder.webpedidos.repository.filter.EstadoFilter;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@ViewScoped
public class CidadeEstadoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private @Inject Estados estados;
	private @Inject Cidades cidades;

	private Estado estado;
	private Cidade cidade;

	public List<Estado> pesquisaEstado(String criterio) {
		EstadoFilter filter = new EstadoFilter();
		filter.setNome(criterio);

		Set<Estado> resultSet = estados.filtrados(filter);
		return new ArrayList<>(resultSet);
	}

	public List<Cidade> pesquisaCidade(String criterio) {
		if (estado == null) {
			return null;
		}

		CidadeFilter filter = new CidadeFilter();
		filter.setEstado(estado);
		filter.setNome(criterio);

		Set<Cidade> resultSet = cidades.filtrados(filter);
		return new ArrayList<>(resultSet);
	}

	/**
	 * Acessor de leitura para o campo estado
	 * 
	 * @return o estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Define um novo valor para o campo estado
	 * 
	 * @param estado
	 *            o estado a ser definido
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
		setCidade(null);
	}

	/**
	 * Acessor de leitura para o campo cidade
	 * 
	 * @return o cidade
	 */
	public Cidade getCidade() {
		return cidade;
	}

	/**
	 * Define um novo valor para o campo cidade
	 * 
	 * @param cidade
	 *            o cidade a ser definido
	 */
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public boolean isEstadoSelecionado() {
		return estado != null;
	}

	public boolean isEstadoNaoSelecionado() {
		return !this.isEstadoSelecionado();
	}
}
