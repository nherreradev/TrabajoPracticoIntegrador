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

	@Test
	public void testSePudeConvertirJsonAObjeto() {
		String json = "{\r\n"
				+ "    \"titulos\": [\r\n"
				+ "        {\r\n"
				+ "            \"simbolo\": \"AA121PL\",\r\n"
				+ "            \"puntas\": null,\r\n"
				+ "            \"ultimoPrecio\": 150.00,\r\n"
				+ "            \"variacionPorcentual\": 2.5,\r\n"
				+ "            \"apertura\": 148.50,\r\n"
				+ "            \"maximo\": 152.00,\r\n"
				+ "            \"minimo\": 147.00,\r\n"
				+ "            \"ultimoCierre\": 148.00,\r\n"
				+ "            \"volumen\": 8000,\r\n"
				+ "            \"cantidadOperaciones\": 400,\r\n"
				+ "            \"fecha\": \"2023-09-15T03:00:23.703\",\r\n"
				+ "            \"tipoOpcion\": null,\r\n"
				+ "            \"precioEjercicio\": null,\r\n"
				+ "            \"fechaVencimiento\": null,\r\n"
				+ "            \"mercado\": \"1\",\r\n"
				+ "            \"moneda\": \"USD\",\r\n"
				+ "            \"descripcion\": \"Apple Inc.\",\r\n"
				+ "            \"plazo\": \"T2\",\r\n"
				+ "            \"laminaMinima\": 1,\r\n"
				+ "            \"lote\": 1\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}\r\n"
				+ "";
		
		ResponseEntity<String> responseEntityExitoso = new ResponseEntity<>(json, HttpStatus.OK);
		// Llamar al método y verificar la respuesta
		List<Instrumento> listaInstrumento = panelesServiceImpl.convertirListaDeJsonAListaDeIntrumentos(responseEntityExitoso);
		assertNotNull(listaInstrumento);
	}

}
