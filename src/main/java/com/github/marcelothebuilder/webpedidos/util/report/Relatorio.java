package com.github.marcelothebuilder.webpedidos.util.report;

import java.util.Map;

public interface Relatorio {

	Map<String, Object> getParametros();

	RelatorioFile getArquivo();
}