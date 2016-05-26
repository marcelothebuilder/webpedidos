package com.github.marcelothebuilder.webpedidos.model.pedido;

public enum StatusPedido {
	ORCAMENTO("Orçamento"),
	EMITIDO("Emitido"),
	CANCELADO("Cancelado");
	
	private String descricao;

	private StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * Acessor de leitura para o campo descricao
	 * @return o descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
