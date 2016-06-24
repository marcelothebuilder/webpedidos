package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.endereco.Estado;
import com.github.marcelothebuilder.webpedidos.repository.Estados;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Named
@Dependent
public class EstadoConverter extends GenericEntityConverter<Estado> {

	private @Inject Estados estados;

	@Override
	protected Estado buscaPorChave(String chave) {
		Long codigo = ConverterUtils.parseLongOuRetornaNull(chave);
		return (codigo != null) ? estados.porCodigo(codigo) : null;
	}

	@Override
	protected String extraiChave(Estado objeto) {
		return ConverterUtils.getCodigoComoString(objeto.getCodigo());
	}

}
