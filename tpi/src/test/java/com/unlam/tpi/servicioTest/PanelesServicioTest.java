package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PanelPrecios;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.servicio.PanelesServicioImpl;
import com.unlam.tpi.infraestructura.modelo.Instrumento;
import com.unlam.tpi.infraestructura.modelo.Posicion;
import com.unlam.tpi.infraestructura.modelo.Puntas;

@ExtendWith(MockitoExtension.class)
class PanelesServicioTest {

	@InjectMocks
	PanelesServicioImpl panelesServicio;

	@Mock
	RestTemplate restTemplate;

	@Mock
	PanelPrecios panelPrecios;
	
	@Mock
	InstrumentoServicio instrumentoServicio;
	
	@Mock
	PosicionServicio posicionServicio;

	@Test
	void testPuedoConvertirListaDeJsonAListaDeIntrumentos() {
		String jsonBody = "{\r\n" + "    \"titulos\": [\r\n" + "        {\r\n"
				+ "            \"simbolo\": \"5913\",\r\n" + "            \"puntas\": null,\r\n"
				+ "            \"ultimoPrecio\": 0,\r\n" + "            \"variacionPorcentual\": 0,\r\n"
				+ "            \"apertura\": 0,\r\n" + "            \"maximo\": 0,\r\n"
				+ "            \"minimo\": 0,\r\n" + "            \"ultimoCierre\": 0,\r\n"
				+ "            \"volumen\": 0,\r\n" + "            \"cantidadOperaciones\": 0,\r\n"
				+ "            \"fecha\": \"2023-09-15T03:00:05.947\",\r\n" + "            \"tipoOpcion\": null,\r\n"
				+ "            \"precioEjercicio\": null,\r\n" + "            \"fechaVencimiento\": null,\r\n"
				+ "            \"mercado\": \"1\",\r\n" + "            \"moneda\": \"1\",\r\n"
				+ "            \"descripcion\": \"OCCIDENTAL PETROLEUM CORP. (OXY)\",\r\n"
				+ "            \"plazo\": \"T2\",\r\n" + "            \"laminaMinima\": 1,\r\n"
				+ "            \"lote\": 1\r\n" + "        },\r\n" + "        {\r\n"
				+ "            \"simbolo\": \"7485\",\r\n" + "            \"puntas\": null,\r\n"
				+ "            \"ultimoPrecio\": 0,\r\n" + "            \"variacionPorcentual\": 0,\r\n"
				+ "            \"apertura\": 0,\r\n" + "            \"maximo\": 0,\r\n"
				+ "            \"minimo\": 0,\r\n" + "            \"ultimoCierre\": 0,\r\n"
				+ "            \"volumen\": 0,\r\n" + "            \"cantidadOperaciones\": 0,\r\n"
				+ "            \"fecha\": \"2023-09-15T03:00:23.703\",\r\n" + "            \"tipoOpcion\": null,\r\n"
				+ "            \"precioEjercicio\": null,\r\n" + "            \"fechaVencimiento\": null,\r\n"
				+ "            \"mercado\": \"1\",\r\n" + "            \"moneda\": \"1\",\r\n"
				+ "            \"descripcion\": \"GLOBALSTAR TELECOMMUNICATIONS\",\r\n"
				+ "            \"plazo\": \"T2\",\r\n" + "            \"laminaMinima\": 1,\r\n"
				+ "            \"lote\": 1\r\n" + "        },\r\n" + "        {\r\n"
				+ "            \"simbolo\": \"93729\",\r\n" + "            \"puntas\": null,\r\n"
				+ "            \"ultimoPrecio\": 0,\r\n" + "            \"variacionPorcentual\": 0,\r\n"
				+ "            \"apertura\": 0,\r\n" + "            \"maximo\": 0,\r\n"
				+ "            \"minimo\": 0,\r\n" + "            \"ultimoCierre\": 0,\r\n"
				+ "            \"volumen\": 0,\r\n" + "            \"cantidadOperaciones\": 0,\r\n"
				+ "            \"fecha\": \"2023-09-15T03:00:07.693\",\r\n" + "            \"tipoOpcion\": null,\r\n"
				+ "            \"precioEjercicio\": null,\r\n" + "            \"fechaVencimiento\": null,\r\n"
				+ "            \"mercado\": \"1\",\r\n" + "            \"moneda\": \"1\",\r\n"
				+ "            \"descripcion\": \"Spdr Bloomberg Barclays Etf\",\r\n"
				+ "            \"plazo\": \"T2\",\r\n" + "            \"laminaMinima\": 1,\r\n"
				+ "            \"lote\": 1\r\n" + "        },\r\n" + "        {\r\n"
				+ "            \"simbolo\": \"AGRO\",\r\n" + "            \"puntas\": {\r\n"
				+ "                \"cantidadCompra\": 100,\r\n" + "                \"precioCompra\": 6000,\r\n"
				+ "                \"precioVenta\": 240,\r\n" + "                \"cantidadVenta\": 10\r\n"
				+ "            },\r\n" + "            \"ultimoPrecio\": 224.75,\r\n"
				+ "            \"variacionPorcentual\": -2.28,\r\n" + "            \"apertura\": 231,\r\n"
				+ "            \"maximo\": 234.75,\r\n" + "            \"minimo\": 220.25,\r\n"
				+ "            \"ultimoCierre\": 224.75,\r\n" + "            \"volumen\": 0,\r\n"
				+ "            \"cantidadOperaciones\": 652,\r\n"
				+ "            \"fecha\": \"2023-09-15T17:00:05.423\",\r\n" + "            \"tipoOpcion\": null,\r\n"
				+ "            \"precioEjercicio\": null,\r\n" + "            \"fechaVencimiento\": null,\r\n"
				+ "            \"mercado\": \"1\",\r\n" + "            \"moneda\": \"1\",\r\n"
				+ "            \"descripcion\": \"Agrometal\",\r\n" + "            \"plazo\": \"T2\",\r\n"
				+ "            \"laminaMinima\": 1,\r\n" + "            \"lote\": 1\r\n" + "        }\r\n" + "    ]\r\n"
				+ "}";
		ResponseEntity<String> panelAcciones = new ResponseEntity<String>(jsonBody, HttpStatus.OK);
		List<Instrumento> listaInstrumentos = new ArrayList<>();
		Posicion posicion = new Posicion();
		posicion.setCantidad(new BigDecimal(2000));
		posicion.setEsEfectivo(true);
		posicion.setMonedaOid(1L);
		List<Posicion> listaPosicion = new ArrayList<>();
		listaPosicion.add(posicion);
		when(restTemplate.getForEntity("https://api.mercadojunior.com.ar/list/precios/acciones", String.class))
				.thenReturn(panelAcciones);
		doNothing().when(instrumentoServicio).persistirInstrumentos(listaInstrumentos);
		doNothing().when(panelPrecios).agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
		panelPrecios.agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
		when(posicionServicio.obtenerPosicionTotal()).thenReturn(listaPosicion);
		try {
			panelesServicio.getPanelDeAcciones();
		} catch (Exception e) {
			fail("Se arrojó una excepción inesperada: " + e.getMessage());
		}
	}

	@Test
	void testPuedoDeterminarFlashDeCompraVenta() {

		/*----------------- PANEL VIEJO -----------------*/
		Map<String, Instrumento> panelAnterior = new HashMap<>();
		Puntas puntasViejas = new Puntas();
		puntasViejas.setPrecioCompra(new BigDecimal(150));
		puntasViejas.setPrecioVenta(new BigDecimal(180));
		Instrumento instrumentoViejo = new Instrumento();
		instrumentoViejo.setSimbolo("AGRO");
		instrumentoViejo.setPuntas(puntasViejas);
		panelAnterior.put("AGRO", instrumentoViejo);
		PanelesServicioImpl.listaInstrumentosAux.add(instrumentoViejo);

		/*----------------- PANEL NUEVO -----------------*/
		Puntas puntasNuevas = new Puntas();
		puntasNuevas.setPrecioCompra(new BigDecimal(100)); /* Precio compra bajo (-1) */
		puntasNuevas.setPrecioVenta(new BigDecimal(200)); /* Precio venta subio (1) */
		Instrumento instrumentoNuevo = new Instrumento();
		instrumentoNuevo.setSimbolo("AGRO");
		instrumentoNuevo.setPuntas(puntasNuevas);
		List<Instrumento> listaInstrumentosNuevos = new ArrayList<>();
		listaInstrumentosNuevos.add(instrumentoNuevo);

		panelesServicio.determinarFlashDeCompraVenta(panelAnterior, listaInstrumentosNuevos);

		assertEquals((-1), instrumentoNuevo.getFlashCompra()); /* Precio compra bajo (-1) */
		assertEquals(1, instrumentoNuevo.getFlashVenta()); /* Precio venta subio (1) */

	}
	

}
