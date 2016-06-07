package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.usuario.Usuario;
import com.github.marcelothebuilder.webpedidos.repository.Usuarios;

@Named
@Dependent
public class UsuarioConverter extends GenericEntityConverter<Usuario> {

	private @Inject Usuarios usuarios;

	@Override
	protected Usuario buscaPorChave(String chave) {
		Long codigo = ConverterUtils.parseLongOuRetornaNull(chave);
		return usuarios.porCodigo(codigo);
	}

	@Override
	protected String extraiChave(Usuario objeto) {
		return objeto.getCodigo().toString();
	}


}
