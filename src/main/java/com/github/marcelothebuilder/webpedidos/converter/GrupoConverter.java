package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;
import com.github.marcelothebuilder.webpedidos.repository.Grupos;

@Named
@Dependent
public class GrupoConverter extends GenericEntityConverter<Grupo> {

	private @Inject Grupos grupos;

	@Override
	protected Grupo buscaPorChave(String chave) {
		Integer codigo = ConverterUtils.parseIntegerOuRetornaNull(chave);
		return grupos.porCodigo(codigo);
	}

	@Override
	protected String extraiChave(Grupo objeto) {
		return objeto.getCodigo().toString();
	}
	
	


}
