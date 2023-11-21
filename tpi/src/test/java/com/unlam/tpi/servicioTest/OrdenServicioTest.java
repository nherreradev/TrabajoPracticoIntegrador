package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.OrdenRepositorio;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.core.modelo.PuedeOperarResultado;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.servicio.OrdenServicioImpl;

@ExtendWith(MockitoExtension.class)
class OrdenServicioTest {

	@InjectMocks
	OrdenServicioImpl ordenServicioImpl;

	@Mock
	OrdenRepositorio ordenRepositorio;

	@Mock
	PosicionServicio posicionServicio;

	@Test
	void testPuedoCrearUnaOrden() {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(2));
		orden.setPrecio(new BigDecimal(1000));
		orden.setSimboloInstrumento("TGNO4");
		orden.setSentido("venta");
		orden.setCategoriaInstrumento("acciones");

		PuedeOperarResultado puedeOperarResultado = new PuedeOperarResultado();
		puedeOperarResultado.setPuedeOperar(true);
		puedeOperarResultado.setDisponible(new BigDecimal(1000000));

		when(posicionServicio.puedeOperar(orden)).thenReturn(puedeOperarResultado);

		when(ordenRepositorio.save(orden)).thenReturn(orden);

		assertNotNull(ordenServicioImpl.crearOrden(orden));

	}

	@Test
	void testPuedoOperarSiLaValidacionDePuedeOperarEsPositiva() {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(2));
		orden.setPrecio(new BigDecimal(1000));
		orden.setSimboloInstrumento("TGNO4");
		orden.setSentido("venta");
		orden.setCategoriaInstrumento("acciones");

		PuedeOperarResultado puedeOperarResultado = new PuedeOperarResultado();
		puedeOperarResultado.setPuedeOperar(true);
		puedeOperarResultado.setDisponible(new BigDecimal(3000));

		when(posicionServicio.puedeOperar(orden)).thenReturn(puedeOperarResultado);

		assertDoesNotThrow(() -> {
			ordenServicioImpl.puedeOperar(orden);
		});

	}

	@Test
	void testNoPuedoOperarSiLaValidacionDePuedeOperarEsNegativa() {

		Orden orden = new Orden();
		orden.setCantidad(new BigDecimal(2));
		orden.setPrecio(new BigDecimal(1000));
		orden.setSimboloInstrumento("TGNO4");
		orden.setSentido("venta");
		orden.setCategoriaInstrumento("acciones");

		PuedeOperarResultado puedeOperarResultado = new PuedeOperarResultado();
		puedeOperarResultado.setPuedeOperar(false);
		puedeOperarResultado.setDisponible(new BigDecimal(1000));

		when(posicionServicio.puedeOperar(orden)).thenReturn(puedeOperarResultado);

		assertThrows(ServiceException.class, () -> {
			ordenServicioImpl.puedeOperar(orden);
		});

	}

}
