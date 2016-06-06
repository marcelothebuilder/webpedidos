package com.github.marcelothebuilder.webpedidos.converter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.marcelothebuilder.webpedidos.model.produto.Categoria;
import com.github.marcelothebuilder.webpedidos.repository.Categorias;

public class CategoriaConverterTest {
	
	@Mock
	private Categorias categorias;
	
	@InjectMocks
	private CategoriaConverter converter;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAsObject() {
		// categoria que queremos receber
		Categoria expected = new Categoria();
		expected.setCodigo(2L);
		
		// mock: comportamento do repositorio
		Mockito.when(categorias.porCodigo(2L))
			.thenReturn(expected);
		
		// testa método
		Object actual = converter.getAsObject(null, null, "2");
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAsString() {
		// categoria de amostra
		Categoria categoriaAmostra = new Categoria();
		categoriaAmostra.setCodigo(5L);
		
		// valor esperado para a categoria passada
		String expected = "5";
		
		String actual = converter.getAsString(null, null, categoriaAmostra);
		
		assertEquals(expected, actual);
	}

}
