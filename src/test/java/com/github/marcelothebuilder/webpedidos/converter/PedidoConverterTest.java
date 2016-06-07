package com.github.marcelothebuilder.webpedidos.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.marcelothebuilder.webpedidos.model.pedido.Pedido;
import com.github.marcelothebuilder.webpedidos.repository.Pedidos;

public class PedidoConverterTest {
	
	@Mock
	private Pedidos pedidos;
	
	@InjectMocks
	private PedidoConverter converter;
	
	private Pedido entitadeEntradaSaida;
	
	private Long codigoEntradaSaidaNumber;
	private String codigoEntradaSaidaString;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.codigoEntradaSaidaNumber = 5L;
		this.codigoEntradaSaidaString = this.codigoEntradaSaidaNumber.toString();
		
		this.entitadeEntradaSaida = new Pedido();
		this.entitadeEntradaSaida.setCodigo(this.codigoEntradaSaidaNumber);
		
		Mockito.when(pedidos.porCodigo(this.codigoEntradaSaidaNumber))
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
