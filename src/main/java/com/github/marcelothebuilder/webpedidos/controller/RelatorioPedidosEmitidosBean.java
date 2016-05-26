/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Este bean é responsável pela emissão de relatórios dos pedidos emitidos.
 * Fornece limitações de data e exporta o relatório para o navegador
 * diretamente, validando erros de entrada do usuário.
 * 
 * @author Marcelo Paixao Resende
 *
 */
@Named
@RequestScoped
public class RelatorioPedidosEmitidosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Retorna a data máxima para emissão de relatórios. Esta data deve,
	 * obrigatoriamente, ser menor ou igual a data atual.
	 * 
	 * @return a data máxima permitida para emissão de relatórios
	 */
	public Date getDataMaxima() {
		// TODO: Retornar a data do pedido mais recente.
		return new Date();
	}

	/**
	 * Retorna a data mínima permitida para emissão de relatórios. Esta data
	 * deve ser menor que a data atual.
	 * 
	 * @return a data mínima permitida para emissão de relatórios.
	 */
	public Date getDataMinima() {
		// TODO: Retornar a data do pedido mais antigo.
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1L);
		return cal.getTime();
	}

}
