package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.servicio.OrdenServicioImpl;
import com.unlam.tpi.core.servicio.PosicionServicio;
import com.unlam.tpi.delivery.dto.OrdenDTO;
import com.unlam.tpi.infraestructura.arquitectura.ServiceException;
import com.unlam.tpi.infraestructura.modelo.Orden;
import com.unlam.tpi.infraestructura.modelo.PuedeOperarResultado;
import com.unlam.tpi.infraestructura.repositorio.OrdenRepositorio;

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

		OrdenDTO ordenDTO = new OrdenDTO();
		ordenDTO.setCantidad(new BigDecimal(2));
		ordenDTO.setPrecio(new BigDecimal(1000));
		ordenDTO.setSimboloInstrumento("TGNO4");
		ordenDTO.setSentido("venta");
		ordenDTO.setCategoriaInstrumento("acciones");

		ModelMapper modelMapper = new ModelMapper();
		Orden orden = modelMapper.map(ordenDTO, Orden.class);

		PuedeOperarResultado puedeOperarResultado = new PuedeOperarResultado();
		puedeOperarResultado.setPuedeOperar(true);
		puedeOperarResultado.setDisponible(new BigDecimal(1000000));

		when(posicionServicio.puedeOperar(orden)).thenReturn(puedeOperarResultado);

		when(ordenRepositorio.save(orden)).thenReturn(orden);

		assertNotNull(ordenServicioImpl.crearOrden(ordenDTO));

	}

	@Test
	void testPuedoOperarSiLaValidacionDePuedeOperarEsPositiva() {

		OrdenDTO ordenDTO = new OrdenDTO();
		ordenDTO.setCantidad(new BigDecimal(2));
		ordenDTO.setPrecio(new BigDecimal(1000));
		ordenDTO.setSimboloInstrumento("TGNO4");
		ordenDTO.setSentido("venta");
		ordenDTO.setCategoriaInstrumento("acciones");

		ModelMapper modelMapper = new ModelMapper();
		Orden orden = modelMapper.map(ordenDTO, Orden.class);

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

		OrdenDTO ordenDTO = new OrdenDTO();
		ordenDTO.setCantidad(new BigDecimal(2));
		ordenDTO.setPrecio(new BigDecimal(1000));
		ordenDTO.setSimboloInstrumento("TGNO4");
		ordenDTO.setSentido("venta");
		ordenDTO.setCategoriaInstrumento("acciones");

		ModelMapper modelMapper = new ModelMapper();
		Orden orden = modelMapper.map(ordenDTO, Orden.class);

		PuedeOperarResultado puedeOperarResultado = new PuedeOperarResultado();
		puedeOperarResultado.setPuedeOperar(false);
		puedeOperarResultado.setDisponible(new BigDecimal(1000));

		when(posicionServicio.puedeOperar(orden)).thenReturn(puedeOperarResultado);

		assertThrows(ServiceException.class, () -> {
			ordenServicioImpl.puedeOperar(orden);
		});

	}

}
