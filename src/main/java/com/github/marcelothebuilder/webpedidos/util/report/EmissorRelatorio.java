package com.github.marcelothebuilder.webpedidos.util.report;

import java.io.IOException;
import java.sql.Connection;

import net.sf.jasperreports.engine.JRException;

public interface EmissorRelatorio {

	void setConnection(Connection connection);

	void emitir() throws IOException, JRException;

	boolean isRelatorioGerado();

}