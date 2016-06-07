package com.github.marcelothebuilder.webpedidos.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;
import com.github.marcelothebuilder.webpedidos.repository.Grupos;

public class GrupoConverterTest {
	
	@Mock
	private Grupos grupos;
	
	@InjectMocks
	private GrupoConverter converter;
	
	private Grupo entitadeEntradaSaida;
	
	private Integer codigoEntradaSaidaNumber;
	private String codigoEntradaSaidaString;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.codigoEntradaSaidaNumber = 5;
		this.codigoEntradaSaidaString = this.codigoEntradaSaidaNumber.toString();
		
		this.entitadeEntradaSaida = new Grupo();
		this.entitadeEntradaSaida.setCodigo(this.codigoEntradaSaidaNumber);
		
		Mockito.when(grupos.porCodigo(this.codigoEntradaSaidaNumber))
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
