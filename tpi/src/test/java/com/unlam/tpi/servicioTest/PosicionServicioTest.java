package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.PosicionRepositorio;
import com.unlam.tpi.core.modelo.CargaCreditoConstantes;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.core.modelo.OrdenConstantes;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.modelo.PuedeOperarResultado;
import com.unlam.tpi.core.modelo.Puntas;
import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;
import com.unlam.tpi.core.servicio.PanelPreciosImpl;
import com.unlam.tpi.core.servicio.PosicionServicioImpl;

@ExtendWith(MockitoExtension.class)
class PosicionServicioTest {

	@InjectMocks
	private PosicionServicioImpl posicionServicio;

	@Mock
	private PosicionRepositorio posicionRepositorio;

	@Test
	void testPuedoComprarSiTengoSuficienteDinero() throws ServiceException {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(1));
		orden.setMonedaOid(1L);
		orden.setSimboloInstrumento("AGRO");
		orden.setSentido(OrdenConstantes.COMPRA);
		orden.setCategoriaInstrumento("acciones");

		Posicion posicion = new Posicion();
		posicion.setCantidad(new BigDecimal(2000));
		posicion.setEsEfectivo(true);
		posicion.setMonedaOid(1L);

		List<Posicion> listaPosiciones = new ArrayList<>();
		listaPosiciones.add(posicion);

		Instrumento instrumento = new Instrumento();
		instrumento.setSimbolo("AGRO");

		Puntas puntas = new Puntas();
		puntas.setPrecioCompra(new BigDecimal(1800));
		puntas.setPrecioVenta(new BigDecimal(25));

		instrumento.setPuntas(puntas);

		PanelPreciosImpl.panelAcciones.put("AGRO", instrumento);

		when(posicionRepositorio.getPosicionEnEfectivo()).thenReturn(listaPosiciones);

		PuedeOperarResultado puedeOperarResultado = posicionServicio.puedeOperar(orden);

		assertTrue(puedeOperarResultado.getPuedeOperar());

	}

	@Test
	void testNoPuedoComprarSinoTengoSuficienteDinero() throws ServiceException {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(1));
		orden.setMonedaOid(1L);
		orden.setSimboloInstrumento("AGRO");
		orden.setSentido(OrdenConstantes.COMPRA);
		orden.setCategoriaInstrumento("acciones");

		Posicion posicion = new Posicion();
		posicion.setCantidad(new BigDecimal(2000));
		posicion.setEsEfectivo(true);
		posicion.setMonedaOid(1L);

		List<Posicion> listaPosiciones = new ArrayList<>();
		listaPosiciones.add(posicion);

		Instrumento instrumento = new Instrumento();
		instrumento.setSimbolo("AGRO");

		Puntas puntas = new Puntas();
		puntas.setPrecioCompra(new BigDecimal(2500));
		puntas.setPrecioVenta(new BigDecimal(25));

		instrumento.setPuntas(puntas);

		PanelPreciosImpl.panelAcciones.put("AGRO", instrumento);

		when(posicionRepositorio.getPosicionEnEfectivo()).thenReturn(listaPosiciones);

		PuedeOperarResultado puedeOperarResultado = posicionServicio.puedeOperar(orden);

		assertFalse(puedeOperarResultado.getPuedeOperar());

	}

	@Test
	void testPuedoVenderSiTengoSuficientesTitulos() throws ServiceException {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(900));
		orden.setMonedaOid(1L);
		orden.setSimboloInstrumento("AGRO");
		orden.setSentido(OrdenConstantes.VENTA);
		orden.setCategoriaInstrumento("acciones");

		Posicion posicion = new Posicion();
		posicion.setCantidad(new BigDecimal(1000));
		posicion.setSimboloInstrumento("AGRO");
		posicion.setEsEfectivo(false);
		posicion.setMonedaOid(1L);

		List<Posicion> listaPosiciones = new ArrayList<>();
		listaPosiciones.add(posicion);

		Instrumento instrumento = new Instrumento();
		instrumento.setSimbolo("AGRO");

		Puntas puntas = new Puntas();
		puntas.setPrecioCompra(new BigDecimal(2500));
		puntas.setPrecioVenta(new BigDecimal(5000));

		instrumento.setPuntas(puntas);

		PanelPreciosImpl.panelAcciones.put("AGRO", instrumento);

		when(posicionRepositorio.obtenerTodosLosMovimientosAsociadosAUnSimbolo(orden.getSimboloInstrumento()))
				.thenReturn(listaPosiciones);

		PuedeOperarResultado puedeOperarResultado = posicionServicio.puedeOperar(orden);

		assertTrue(puedeOperarResultado.getPuedeOperar());

	}

	@Test
	void testNoPuedoVenderSiNoTengoSuficientesTitulos() throws ServiceException {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(900));
		orden.setMonedaOid(1L);
		orden.setSimboloInstrumento("AGRO");
		orden.setSentido(OrdenConstantes.VENTA);
		orden.setCategoriaInstrumento("acciones");

		Posicion posicion = new Posicion();
		posicion.setCantidad(new BigDecimal(800));
		posicion.setSimboloInstrumento("AGRO");
		posicion.setEsEfectivo(false);
		posicion.setMonedaOid(1L);

		List<Posicion> listaPosiciones = new ArrayList<>();
		listaPosiciones.add(posicion);

		Instrumento instrumento = new Instrumento();
		instrumento.setSimbolo("AGRO");

		Puntas puntas = new Puntas();
		puntas.setPrecioCompra(new BigDecimal(2500));
		puntas.setPrecioVenta(new BigDecimal(5000));

		instrumento.setPuntas(puntas);

		PanelPreciosImpl.panelAcciones.put("AGRO", instrumento);

		when(posicionRepositorio.obtenerTodosLosMovimientosAsociadosAUnSimbolo(orden.getSimboloInstrumento()))
				.thenReturn(listaPosiciones);

		PuedeOperarResultado puedeOperarResultado = posicionServicio.puedeOperar(orden);

		assertFalse(puedeOperarResultado.getPuedeOperar());

	}

	@Test
	void testPuedoObtenerLaValuacionDeMiCartera() {

		String totalMonedas = "4500";
		String totalInstrumentos = "20";
		String totalCartera = "4520";

		Posicion posicionDinero = new Posicion();
		posicionDinero.setCantidad(new BigDecimal(4500));
		posicionDinero.setEsEfectivo(true);
		posicionDinero.setMonedaOid(1L);

		Posicion posicionTitulos = new Posicion();
		posicionTitulos.setCantidad(new BigDecimal(2));
		posicionTitulos.setPrecioActualDeVenta(new BigDecimal(10));
		posicionTitulos.setSimboloInstrumento("AGRO");
		posicionTitulos.setEsEfectivo(false);
		posicionTitulos.setMonedaOid(1L);

		List<Posicion> listaPosiciones = new ArrayList<>();
		listaPosiciones.add(posicionDinero);
		listaPosiciones.add(posicionTitulos);

		when(posicionRepositorio.findAll()).thenReturn(listaPosiciones);

	//	ValuacionTotalRespuesta valuacionTotalRespuesta = posicionServicio.getValuacionTotal();

	//	assertEquals(totalMonedas, valuacionTotalRespuesta.getTotalMonedas());
	//	assertEquals(totalInstrumentos, valuacionTotalRespuesta.getTotalInstrumentos());
	//	assertEquals(totalCartera, valuacionTotalRespuesta.getTotalCartera());

	}

	@Test
	void testSiNuncaHiceElPerfilObjetivoSeMeAcreditaElPremio() {

		RequestCargaDeDinero requestCargaDeDinero = new RequestCargaDeDinero();
		requestCargaDeDinero.setCantidadPorAcreditar(new BigDecimal(5000));
		requestCargaDeDinero.setConcepto(CargaCreditoConstantes.PREMIO_PREGUNTAS_OBJETIVAS);

		Posicion posicionDinero = new Posicion();
		posicionDinero.setCantidad(new BigDecimal(4500));
		posicionDinero.setEsEfectivo(true);
		posicionDinero.setMonedaOid(1L);
		posicionDinero.setConcepto("carga manual");

		//when(posicionRepositorio.obtenerPosicionPorConcepto(requestCargaDeDinero.getConcepto()))
			//	.thenReturn(posicionDinero);

		posicionServicio.acreditarDinero(requestCargaDeDinero);

		verify(posicionRepositorio).save(any(Posicion.class));

	}

	@Test
	void testSiYaHiceElPerfilObjetivoYLoVuelvoAHacerNoSeDeberiaAcreditarElPremio() {

		RequestCargaDeDinero requestCargaDeDinero = new RequestCargaDeDinero();
		requestCargaDeDinero.setCantidadPorAcreditar(new BigDecimal(5000));
		requestCargaDeDinero.setConcepto(CargaCreditoConstantes.PREMIO_PREGUNTAS_OBJETIVAS);

		Posicion posicionDinero = new Posicion();
		posicionDinero.setCantidad(new BigDecimal(4500));
		posicionDinero.setEsEfectivo(true);
		posicionDinero.setMonedaOid(1L);
		posicionDinero.setConcepto(CargaCreditoConstantes.PREMIO_PREGUNTAS_OBJETIVAS);

		//when(posicionRepositorio.obtenerPosicionPorConcepto(requestCargaDeDinero.getConcepto()))
		//		.thenReturn(posicionDinero);

		posicionServicio.acreditarDinero(requestCargaDeDinero);

		verify(posicionRepositorio, never()).save(any(Posicion.class));

	}

}
