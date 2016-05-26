package com.github.marcelothebuilder.webpedidos.model.pedido;

public enum FormaPagamento {
	DINHEIRO("Dinheiro"),
	CARTAO_CREDITO("Cartão de Crédito"),
	CARTAO_DEBITO("Cartão de Débito"),
	CHEQUE("Cheque"),
	BOLETO_BANCARIO("Boleto Bancário"),
	DEPOSITO_BANCARIO("Depósito Bancário");
	
	private String descricao;
	
	private FormaPagamento(String descricao) {
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
