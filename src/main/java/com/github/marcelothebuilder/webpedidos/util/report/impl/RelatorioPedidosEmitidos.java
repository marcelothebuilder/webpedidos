/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.report.impl;

import java.util.Date;

import com.github.marcelothebuilder.webpedidos.util.report.RelatorioUtils;

/**
 * @author Marcelo Paixao Resende
 *
 */
public final class RelatorioPedidosEmitidos extends RelatorioPedidos {
	public RelatorioPedidosEmitidos(Date dataInicio, Date dataFim) {
		super(dataInicio, dataFim, RelatorioUtils.localizaRelatorio("relatorio_pedidos_emitidos"));
	}
}
