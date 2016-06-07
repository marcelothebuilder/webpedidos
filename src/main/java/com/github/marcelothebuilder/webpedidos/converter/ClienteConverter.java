package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.repository.Clientes;

@Named
@Dependent
public class ClienteConverter extends GenericEntityConverter<Cliente> {

	private @Inject Clientes clientes;

	@Override
	protected Cliente buscaPorChave(String chave) {
		Long codigo = ConverterUtils.parseLongOuRetornaNull(chave);
		return (codigo != null) ? clientes.porCodigo(codigo) : null;
	}

	@Override
	protected String extraiChave(Cliente objeto) {
		return objeto.getCodigo().toString();
	}

}
