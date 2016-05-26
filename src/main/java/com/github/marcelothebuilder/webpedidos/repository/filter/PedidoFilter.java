package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.github.marcelothebuilder.webpedidos.model.pedido.StatusPedido;

public class PedidoFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long codigoDe;
	private Long codigoAte;
	private Date dataCriacaoInicio;
	private Date dataCriacaoFim;
	private String nomeVendedor;
	private String nomeCliente;
	private StatusPedido[] statuses;

	/**
	 * Acessor de leitura para o campo codigoDe
	 * @return o codigoDe
	 */
	public Long getCodigoDe() {
		return codigoDe;
	}

	/**
	 * Define um novo valor para o campo codigoDe
	 * @param codigoDe o codigoDe a ser definido
	 */
	public void setCodigoDe(Long codigoDe) {
		this.codigoDe = codigoDe;
	}

	/**
	 * Acessor de leitura para o campo codigoAte
	 * @return o codigoAte
	 */
	public Long getCodigoAte() {
		return codigoAte;
	}

	/**
	 * Define um novo valor para o campo codigoAte
	 * @param codigoAte o codigoAte a ser definido
	 */
	public void setCodigoAte(Long codigoAte) {
		this.codigoAte = codigoAte;
	}

	/**
	 * Acessor de leitura para o campo dataCriacaoInicio
	 * 
	 * @return o dataCriacaoInicio
	 */
	public Date getDataCriacaoInicio() {
		return dataCriacaoInicio;
	}

	/**
	 * Define um novo valor para o campo dataCriacaoInicio
	 * 
	 * @param dataCriacaoInicio
	 *            o dataCriacaoInicio a ser definido
	 */
	public void setDataCriacaoInicio(Date dataCriacaoInicio) {
		this.dataCriacaoInicio = dataCriacaoInicio;
	}

	/**
	 * Acessor de leitura para o campo dataCriacaoFim
	 * 
	 * @return o dataCriacaoFim
	 */
	public Date getDataCriacaoFim() {
		return dataCriacaoFim;
	}

	/**
	 * Define um novo valor para o campo dataCriacaoFim
	 * 
	 * @param dataCriacaoFim
	 *            o dataCriacaoFim a ser definido
	 */
	public void setDataCriacaoFim(Date dataCriacaoFim) {
		this.dataCriacaoFim = dataCriacaoFim;
	}

	/**
	 * Acessor de leitura para o campo nomeVendedor
	 * 
	 * @return o nomeVendedor
	 */
	public String getNomeVendedor() {
		return nomeVendedor;
	}

	/**
	 * Define um novo valor para o campo nomeVendedor
	 * 
	 * @param nomeVendedor
	 *            o nomeVendedor a ser definido
	 */
	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	/**
	 * Acessor de leitura para o campo nomeCliente
	 * 
	 * @return o nomeCliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}

	/**
	 * Define um novo valor para o campo nomeCliente
	 * 
	 * @param nomeCliente
	 *            o nomeCliente a ser definido
	 */
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	/**
	 * Acessor de leitura para o campo statuses
	 * 
	 * @return o statuses
	 */
	public StatusPedido[] getStatuses() {
		return statuses;
	}

	/**
	 * Define um novo valor para o campo statuses
	 * 
	 * @param statuses
	 *            o statuses a ser definido
	 */
	public void setStatuses(StatusPedido[] statuses) {
		this.statuses = statuses;
	}

}
