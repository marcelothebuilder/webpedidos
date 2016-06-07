package com.github.marcelothebuilder.webpedidos.converter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.marcelothebuilder.webpedidos.model.produto.Produto;
import com.github.marcelothebuilder.webpedidos.repository.Produtos;

public class ProdutoConverterTest {
	
	@Mock
	private Produtos produtos;
	
	@InjectMocks
	private ProdutoConverter converter;
	
	private Produto entitadeEntradaSaida;
	
	private Long codigoEntradaSaidaNumber;
	private String codigoEntradaSaidaString;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.codigoEntradaSaidaNumber = 5L;
		this.codigoEntradaSaidaString = this.codigoEntradaSaidaNumber.toString();
		
		this.entitadeEntradaSaida = new Produto();
		this.entitadeEntradaSaida.setCodigo(this.codigoEntradaSaidaNumber);
		
		Mockito.when(produtos.porCodigo(this.codigoEntradaSaidaNumber))
			.thenReturn(this.entitadeEntradaSaida);
	}

	@Test
	public void testGetAsObject() {		
		assertEquals(entitadeEntradaSaida, converter.getAsObject(null, null, codigoEntradaSaidaString));
	}

	@Test
	public void testGetAsString() {
		assertEquals(codigoEntradaSaidaString, converter.getAsString(null, null, entitadeEntradaSaida));
	}
}
