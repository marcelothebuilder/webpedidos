/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.github.marcelothebuilder.webpedidos.util.report.EmissorRelatorio;
import com.github.marcelothebuilder.webpedidos.util.report.ExecutorRelatorio;
import com.github.marcelothebuilder.webpedidos.util.report.Relatorio;
import com.github.marcelothebuilder.webpedidos.util.report.impl.EmissorRelatorioServlet;
import com.github.marcelothebuilder.webpedidos.util.report.impl.RelatorioPedidosEmitidos;

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

	private @Inject HttpServletResponse response;
	private @Inject FacesContext fContext;
	private @Inject ExecutorRelatorio executor;

	private Date dataInicio;
	private Date dataFim;

	/**
	 * Acessor de leitura para o campo dataInicio
	 * 
	 * @return o dataInicio
	 */
	@Past
	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * Acessor de leitura para o campo dataFim
	 * 
	 * @return o dataFim
	 */
	@Past
	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * Define um novo valor para o campo dataInicio
	 * 
	 * @param dataInicio
	 *            o dataInicio a ser definido
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * Define um novo valor para o campo dataFim
	 * 
	 * @param dataFim
	 *            o dataFim a ser definido
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

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

	/**
	 * Valida as informações informadas pelo usuário, e caso estejam corretas,
	 * emite a stream do relatório gerado para o client, caso contrário,
	 * fornecem uma mensagem de erro.
	 * 
	 * @throws IOException
	 */
	public void emitir() {

		if (this.getDataInicio().after(getDataFim())) {
			String message = "A data de início deve ser maior que a data fim.";
			FacesMessage fMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
			fContext.addMessage(null, fMessage);
			return;
		}

		int diasBetween = getIntervaloDias(this.getDataInicio(), this.getDataFim());

		if (diasBetween > 180) {
			String message = "Não é possível emitir um relatório para mais que 180 dias.";
			FacesMessage fMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
			fContext.addMessage(null, fMessage);
			return;
		}

		Relatorio relatorioPedidos = new RelatorioPedidosEmitidos(getDataInicio(), getDataFim());

		EmissorRelatorio emissor = new EmissorRelatorioServlet(relatorioPedidos, response);
		executor.setEmissor(emissor);
		executor.emite();

		if (emissor.isRelatorioGerado()) {
			fContext.responseComplete();
		} else {
			String message = "Não foram emitidos pedidos nas datas especificadas.";
			FacesMessage fMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
			fContext.addMessage(null, fMessage);
		}
	}

	private static int getIntervaloDias(Date dataInicio, Date dataFim) {
		LocalDate dayInicio = LocalDate.fromDateFields(dataInicio);
		LocalDate dayFim = LocalDate.fromDateFields(dataFim);
		Days between = Days.daysBetween(dayInicio, dayFim);
		return between.getDays();
	}

}
