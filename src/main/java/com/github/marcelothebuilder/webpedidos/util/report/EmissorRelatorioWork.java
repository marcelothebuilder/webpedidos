/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.report;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class EmissorRelatorioWork implements Work {
	private EmissorRelatorio emissor;

	public EmissorRelatorioWork(EmissorRelatorio emissor) {
		this.emissor = emissor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
	 */
	@Override
	public void execute(Connection connection) throws SQLException {
		emissor.setConnection(connection);
		try {
			emissor.emitir();
		} catch (IOException | JRException e) {
			throw new SQLException(e);
		}
	}
}
