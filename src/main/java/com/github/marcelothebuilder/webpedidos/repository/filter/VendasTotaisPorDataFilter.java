/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Period;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class VendasTotaisPorDataFilter implements Serializable, IntervaloDatas {
	private static final long serialVersionUID = 1L;
	
	private final Date dataInicial;
	private final Date dataFinal;
	
	public VendasTotaisPorDataFilter(Date dataInicial, Date dataFinal) {
		this.dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		this.dataFinal = DateUtils.truncate(dataFinal, Calendar.DAY_OF_MONTH);
	}
	
	public VendasTotaisPorDataFilter(Date data, Integer diasAntes) {
		this(getDiasAntesDe(data, diasAntes), data);
	}
	
	public VendasTotaisPorDataFilter(Integer diasAntesDeHoje) {
		this(new Date(), diasAntesDeHoje);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.marcelothebuilder.webpedidos.repository.filter.IntervaloDatas#getDataInicial(
	 * )
	 */
	@Override
	public Date getDataInicial() {
		return dataInicial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.marcelothebuilder.webpedidos.repository.filter.IntervaloDatas#getDataFinal()
	 */
	@Override
	public Date getDataFinal() {
		return dataFinal;
	}

	private static Date getDiasAntesDe(Date data, Integer diasAntes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.DAY_OF_MONTH, diasAntes * -1);
		return calendar.getTime();
	}

	@Override
	public Integer getIntervaloEmDias() {
		Period period = new Period(dataInicial.getTime(), dataFinal.getTime());
		return period.getDays();
	}
}
