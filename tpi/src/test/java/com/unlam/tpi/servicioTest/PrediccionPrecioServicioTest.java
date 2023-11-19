package com.unlam.tpi.servicioTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.core.interfaces.PrediccionPrecioApi;
import com.unlam.tpi.core.servicio.PrediccionPrecioServicioImpl;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrediccionPrecioServicioTest {

	@Mock
	private PrediccionPrecioApi prediccionPrecioApiMock;

	@InjectMocks
	private PrediccionPrecioServicioImpl prediccionPrecioServicio;

	@Test
	public void obtenerCotizacionDolar() throws JsonMappingException, JsonProcessingException {
		PrediccionPrecioDTO prediccionPrecioDTOEsperado = new PrediccionPrecioDTO();
		when(prediccionPrecioApiMock.obtenerPrecio()).thenReturn(prediccionPrecioDTOEsperado);
		PrediccionPrecioDTO resultado = prediccionPrecioServicio.obtenerCotizacionDolar();
		assertEquals(prediccionPrecioDTOEsperado, resultado);
	}
}
