package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
