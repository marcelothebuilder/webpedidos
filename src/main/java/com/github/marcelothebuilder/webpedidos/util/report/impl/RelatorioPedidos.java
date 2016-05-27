package com.github.marcelothebuilder.webpedidos.util.report.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.marcelothebuilder.webpedidos.util.report.Relatorio;
import com.github.marcelothebuilder.webpedidos.util.report.RelatorioFile;

public class RelatorioPedidos implements Relatorio {
	
	protected Date dataInicio;
	protected Date dataFim;
	protected RelatorioFile relatorioFile;

	/**
	 * @param dataInicio data inicial de pedidos
	 * @param dataFim data final de pedidos
	 * @param relatorioFile caminho para o relatorioFile do relatório
	 */
	public RelatorioPedidos(Date dataInicio, Date dataFim, RelatorioFile relatorioFile) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.relatorioFile = relatorioFile;
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.report.Relatorio#getParametros()
	 */
	@Override
	public Map<String, Object> getParametros() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		return parametros;
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.report.Relatorio#getArquivo()
	 */
	@Override
	public RelatorioFile getArquivo() {
		return this.relatorioFile;
	}
}