package com.github.marcelothebuilder.webpedidos.converter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.marcelothebuilder.webpedidos.converter.util.ConverterUtils;
import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;

@Named
@Dependent
public class PedidoConverter extends GenericEntityConverter<Pedido> {

	private @Inject Pedidos pedidos;

	@Override
	protected Pedido buscaPorChave(String chave) {
		Long codigo = ConverterUtils.parseLongOuRetornaNull(chave);
		return pedidos.porCodigo(codigo);
	}

	@Override
	protected String extraiChave(Pedido objeto) {
		return ConverterUtils.getCodigoComoString(objeto.getCodigo());
	}

}
