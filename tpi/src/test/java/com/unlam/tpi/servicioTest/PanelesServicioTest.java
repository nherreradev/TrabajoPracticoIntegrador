package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.interfaces.PanelPrecios;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.modelo.Puntas;
import com.unlam.tpi.core.servicio.PanelesServicioImpl;

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
	
	@Mock
	ListaPreciosServicio listaPreciosServicio;

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
		List<Instrumento> listaInstrumentos = new ArrayList<>();
		Posicion posicion = new Posicion();
		posicion.setCantidad(new BigDecimal(2000));
		posicion.setEsEfectivo(true);
		posicion.setMonedaOid(1L);
		List<Posicion> listaPosicion = new ArrayList<>();
		listaPosicion.add(posicion);
		when(listaPreciosServicio.getListaPrecioMongo(PanelesDePreciosConstantes.ACCIONES))
				.thenReturn(jsonBody);
		doNothing().when(instrumentoServicio).persistirInstrumentos(listaInstrumentos);
		doNothing().when(panelPrecios).agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
		panelPrecios.agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
		when(posicionServicio.obtenerPosicionTotal()).thenReturn(listaPosicion);
		panelesServicio.getPanelDeAcciones();

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
		List<Instrumento> listaInstrumentosAccionesAux = new ArrayList<>();
		listaInstrumentosAccionesAux.add(instrumentoViejo);

		/*----------------- PANEL NUEVO -----------------*/
		Puntas puntasNuevas = new Puntas();
		puntasNuevas.setPrecioCompra(new BigDecimal(100)); /* Precio compra bajo (-1) */
		puntasNuevas.setPrecioVenta(new BigDecimal(200)); /* Precio venta subio (1) */
		Instrumento instrumentoNuevo = new Instrumento();
		instrumentoNuevo.setSimbolo("AGRO");
		instrumentoNuevo.setPuntas(puntasNuevas);
		List<Instrumento> listaInstrumentosNuevos = new ArrayList<>();
		listaInstrumentosNuevos.add(instrumentoNuevo);

		panelesServicio.determinarFlashDeCompraVenta(panelAnterior, listaInstrumentosNuevos, listaInstrumentosAccionesAux);

		assertEquals((-1), instrumentoNuevo.getFlashCompra()); /* Precio compra bajo (-1) */
		assertEquals(1, instrumentoNuevo.getFlashVenta()); /* Precio venta subio (1) */

	}

}
