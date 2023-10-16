package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.constantes.OrdenConstantes;
import com.unlam.tpi.modelo.persistente.Instrumento;
import com.unlam.tpi.modelo.persistente.Orden;
import com.unlam.tpi.modelo.persistente.Posicion;
import com.unlam.tpi.modelo.persistente.Puntas;
import com.unlam.tpi.modelo.pojo.PuedeOperarResultado;
import com.unlam.tpi.modelo.rest.ValuacionTotalRespuesta;
import com.unlam.tpi.repositorio.PosicionRepositorio;
import com.unlam.tpi.servicio.PanelPreciosImpl;
import com.unlam.tpi.servicio.PosicionServicioImpl;

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

		when(posicionRepositorio.getTitulosDisponiblesPorSimbolo(orden.getSimboloInstrumento()))
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

		when(posicionRepositorio.getTitulosDisponiblesPorSimbolo(orden.getSimboloInstrumento()))
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
		posicionTitulos.setPrecio(new BigDecimal(10));
		posicionTitulos.setSimboloInstrumento("AGRO");
		posicionTitulos.setEsEfectivo(false);
		posicionTitulos.setMonedaOid(1L);

		List<Posicion> listaPosiciones = new ArrayList<>();
		listaPosiciones.add(posicionDinero);
		listaPosiciones.add(posicionTitulos);

		when(posicionRepositorio.findAll()).thenReturn(listaPosiciones);

		ValuacionTotalRespuesta valuacionTotalRespuesta = posicionServicio.getValuacionTotal();
		
		assertEquals(totalMonedas, valuacionTotalRespuesta.getTotalMonedas());
		assertEquals(totalInstrumentos, valuacionTotalRespuesta.getTotalInstrumentos());
		assertEquals(totalCartera, valuacionTotalRespuesta.getTotalCartera());

	}

}
