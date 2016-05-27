/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.report.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import javax.servlet.http.HttpServletResponse;

import com.github.marcelothebuilder.webpedidos.util.report.EmissorRelatorio;
import com.github.marcelothebuilder.webpedidos.util.report.Relatorio;
import com.github.marcelothebuilder.webpedidos.util.report.RelatorioNaoExisteException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author Marcelo Paixao Resende
 *
 */
public final class EmissorRelatorioServlet implements EmissorRelatorio {
	private Relatorio relatorio;
	private Connection connection;
	private HttpServletResponse servletResponse;
	private boolean relatorioGerado = false;

	public EmissorRelatorioServlet(Relatorio relatorio, HttpServletResponse servletResponse) {
		this.relatorio = relatorio;
		this.servletResponse = servletResponse;
	}

	public EmissorRelatorioServlet(Relatorio relatorio, HttpServletResponse output, Connection connection) {
		this(relatorio, output);
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.report.impl.EmissorRelatorio#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.report.impl.EmissorRelatorio#emitir()
	 */
	@Override
	public void emitir() throws JRException {
		try (InputStream relatorioArquivoStream = this.getClass().getResourceAsStream(relatorio.getArquivo().getCaminho())) {
			JasperPrint print = JasperFillManager.fillReport(relatorioArquivoStream, relatorio.getParametros(), connection);
			this.relatorioGerado = print.getPages().size() > 0;
			
			if(this.isRelatorioGerado()) {
				String attachment = String.format("attachment; filename=\"%s.pdf\"", relatorio.getArquivo().getNome());
				servletResponse.setHeader("Content-Disposition", attachment);
				JasperExportManager.exportReportToPdfStream(print, servletResponse.getOutputStream());		
			}
		} catch (IOException e) {
			throw new RelatorioNaoExisteException(relatorio, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.report.impl.EmissorRelatorio#isRelatorioGerado()
	 */
	@Override
	public boolean isRelatorioGerado() {
		return this.relatorioGerado;
	}

}
