package com.github.marcelothebuilder.webpedidos.repository.filter;

import java.io.Serializable;

import com.github.marcelothebuilder.webpedidos.validation.Sku;

public class ProdutoFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String sku;
	private String nome;
	
	@Sku
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String toString() {
		return "ProdutoFilter [" + (getSku() != null ? "getSku()=" + getSku() + ", " : "")
				+ (getNome() != null ? "getNome()=" + getNome() : "") + "]";
	}
	
	
	
	

}
