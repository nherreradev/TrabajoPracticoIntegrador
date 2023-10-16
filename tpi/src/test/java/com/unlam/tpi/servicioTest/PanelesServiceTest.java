package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.unlam.tpi.modelo.persistente.Instrumento;
import com.unlam.tpi.servicio.PanelesServicioImpl;

@SpringBootTest
public class PanelesServiceTest {

	private PanelesServicioImpl panelesServiceImpl = new PanelesServicioImpl();

	@Test
	public void testObtenerAccionesDeAPI() {
		String url = "https://api.mercadojunior.com.ar/list/precios/acciones";
		// Llamar al método y verificar la respuesta
		ResponseEntity<String> responseEntity = panelesServiceImpl.getInstrumentos(url);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	@Test
	public void testObtenerBonosDeAPI() {
		String url = "https://api.mercadojunior.com.ar/list/precios/bonos";
		// Llamar al método y verificar la respuesta
		ResponseEntity<String> responseEntity = panelesServiceImpl.getInstrumentos(url);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	

}
