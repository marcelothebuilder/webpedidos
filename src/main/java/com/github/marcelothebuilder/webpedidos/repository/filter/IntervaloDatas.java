package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.util.Date;

public interface IntervaloDatas {

	/**
	 * Acessor de leitura para o campo dataInicial
	 * @return o dataInicial
	 */
	Date getDataInicial();

	/**
	 * Acessor de leitura para o campo dataFinal
	 * @return o dataFinal
	 */
	Date getDataFinal();

	/**
	 * Acessor de leitura para o campo intervaloEmDias
	 * 
	 * @return o intervaloEmDias
	 */
	Integer getIntervaloEmDias();

}