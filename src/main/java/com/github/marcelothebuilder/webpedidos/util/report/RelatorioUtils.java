package com.github.marcelothebuilder.webpedidos.util.report;

/**
 * Agrupa métodos estáticos para auxiliar na emissão de relatórios.
 * 
 * @author Marcelo Paixao Resende
 *
 */
public final class RelatorioUtils {
	private static final String RELATORIOS_FOLDER = "/relatorios/";
	private static final String RELATORIOS_EXT = ".jasper";

	/**
	 * Localiza e retorna o arquivo .jasper de relatórios.
	 * 
	 * @param nomeRelatorio
	 *            o nome do arquivo de relatório, sem extensão.
	 * @return o caminho para o arquivo de relatório.
	 */
	public static RelatorioFile localizaRelatorio(String nomeRelatorio) {
		RelatorioFile file = new RelatorioFile();
		file.setNome(nomeRelatorio);
		file.setCaminho(RELATORIOS_FOLDER + nomeRelatorio + RELATORIOS_EXT);
		return file;
	}

}
