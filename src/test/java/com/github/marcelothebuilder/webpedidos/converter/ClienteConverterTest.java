package com.github.marcelothebuilder.webpedidos.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.marcelothebuilder.webpedidos.model.cliente.Cliente;
import com.github.marcelothebuilder.webpedidos.repository.Clientes;

public class ClienteConverterTest {
	
	@Mock
	private Clientes clientes;
	
	@InjectMocks
	private ClienteConverter converter;
	
	private Cliente entitadeEntradaSaida;
	
	private Long codigoEntradaSaidaNumber;
	private String codigoEntradaSaidaString;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.codigoEntradaSaidaNumber = 5L;
		this.codigoEntradaSaidaString = this.codigoEntradaSaidaNumber.toString();
		
		this.entitadeEntradaSaida = new Cliente();
		this.entitadeEntradaSaida.setCodigo(this.codigoEntradaSaidaNumber);
		
		Mockito.when(clientes.porCodigo(this.codigoEntradaSaidaNumber))
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
	
	@Test
	public void deveRetornarStringVaziaQuandoCodigoForNulo() {
		Cliente clienteSemCodigo = new Cliente();
		clienteSemCodigo.setCodigo(null);
		
		String actual = converter.getAsString(null, null, clienteSemCodigo);
		String expected = "";
		
		assertEquals("Deve retornar uma string nula", expected, actual);
	}
}
