/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.github.marcelothebuilder.webpedidos.model.vo.DataValor;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;
import com.github.marcelothebuilder.webpedidos.repository.filter.IntervaloDatas;
import com.github.marcelothebuilder.webpedidos.repository.filter.VendasTotaisPorDataFilter;
import com.github.marcelothebuilder.webpedidos.security.UsuarioLogado;
import com.github.marcelothebuilder.webpedidos.security.UsuarioSistema;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@RequestScoped
public class GraficoPedidosCriadosBean implements Serializable {
	private static final long serialVersionUID = 2L;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");

	@Inject
	private Pedidos pedidos;

	private LineChartModel model;

	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioSistema;

	@PostConstruct
	private void inicializaGrafico() {
		createDateModel();
	}

	public LineChartModel getModel() {
		return model;
	}

	public void createDateModel() {
		model = new LineChartModel();

		IntervaloDatas intervalo = new VendasTotaisPorDataFilter(15);
		Set<DataValor> vendasPorData = pedidos.valoresTotaisPorData(intervalo);

		LineChartSeries seriesGerais = new LineChartSeries();

		seriesGerais.setLabel("Geral");

		for (DataValor dataValor : vendasPorData) {
			String data = DATE_FORMAT.format(dataValor.getData());
			BigDecimal valor = dataValor.getValor();

			seriesGerais.set(data, valor);
		}

		model.addSeries(seriesGerais);

		Set<DataValor> vendasPorDataDoVendedor = pedidos.valoresTotaisPorData(intervalo, usuarioSistema.getUsuario());

		LineChartSeries seriesVendedor = new LineChartSeries();

		seriesVendedor.setLabel(usuarioSistema.getUsuario().getNome());

		for (DataValor dataValor : vendasPorDataDoVendedor) {
			String data = DATE_FORMAT.format(dataValor.getData());
			BigDecimal valor = dataValor.getValor();

			seriesVendedor.set(data, valor);
		}

		model.addSeries(seriesVendedor);
		model.setTitle("Vendas totais por data");
		model.setLegendPosition("e");
		model.setZoom(true);

		DateAxis axis = new DateAxis("Datas");
		axis.setTickAngle(-50);
		axis.setMax(DATE_FORMAT.format(intervalo.getDataFinal()));
		axis.setTickFormat("%#d %b");

		model.getAxes().put(AxisType.X, axis);

		Axis axisY = model.getAxis(AxisType.Y);
		axisY.setMin(BigDecimal.ZERO);
		axisY.setTickFormat("R$ %.2f");

	}

	public void _createDateModel() {
		model = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		IntervaloDatas interval = new VendasTotaisPorDataFilter(15);

		// Set<DataValor> vendasPorData =
		// pedidos.valoresTotaisPorData(interval);
		//
		// for (DataValor dataValor : vendasPorData) {
		// series1.set(dataValor.getData(), dataValor.getValor());
		// }

		series1.set(new Date(), BigDecimal.ZERO);

		model.addSeries(series1);
		// model.addSeries(series2);

		model.setTitle("Zoom for Details");
		model.setZoom(true);
		model.getAxis(AxisType.Y).setLabel("Values");
		model.setLegendPosition("e");

		DateAxis axis = new DateAxis("Dates");
		axis.setTickAngle(-50);
		// axis.setMin(interval.getDataInicial());
		axis.setMax(interval.getDataFinal());
		axis.setTickFormat("%b %#d, %y");

		model.getAxes().put(AxisType.X, axis);
	}
}
