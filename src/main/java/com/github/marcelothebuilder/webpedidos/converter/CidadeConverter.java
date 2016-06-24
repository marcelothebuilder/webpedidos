/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.endereco.Cidade;
import com.github.marcelothebuilder.webpedidos.repository.Cidades;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@Dependent
public class CidadeConverter extends GenericEntityConverter<Cidade> {

	private @Inject Cidades cidades;

	@Override
	protected Cidade buscaPorChave(String chave) {
		Integer codigo = ConverterUtils.parseIntegerOuRetornaNull(chave);
		return (codigo != null) ? cidades.porCodigo(codigo) : null;
	}

	@Override
	protected String extraiChave(Cidade objeto) {
		return ConverterUtils.getCodigoComoString(objeto.getCodigo());
	}
}
