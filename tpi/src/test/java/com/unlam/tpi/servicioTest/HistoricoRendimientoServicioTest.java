package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.HistoricoRendimientoRepositorio;
import com.unlam.tpi.core.modelo.HistoricoRendimientos;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;
import com.unlam.tpi.core.servicio.HistoricoRendimientoServicioImpl;

@ExtendWith(MockitoExtension.class)
class HistoricoRendimientoServicioTest {

	@InjectMocks
	HistoricoRendimientoServicioImpl historicoRendimientoServicioImpl;

	@Mock
	HistoricoRendimientoRepositorio historicoRendimientoRepositorio;

	@Test
	void testPuedoGuardarUnHistoricoDeInstrumento() {

		String simbolo = "AUSO";
		LocalDateTime fecha = LocalDateTime.now();

		List<HistoricoRendimientos> historicoRendimientoBuscado = new ArrayList<>();
		HistoricoRendimientos historicoRendimientos = new HistoricoRendimientos();
		historicoRendimientos.setRendimientoTotal(new BigDecimal(100));
		historicoRendimientos.setSimbolo(simbolo);
		historicoRendimientos.setFecha(fecha);

		when(historicoRendimientoRepositorio.buscarPorSimboloYFecha(historicoRendimientos.getSimbolo(),
				historicoRendimientos.getFecha())).thenReturn(historicoRendimientoBuscado);

		historicoRendimientoServicioImpl.guardar(historicoRendimientos);

		verify(historicoRendimientoRepositorio).save(any(HistoricoRendimientos.class));

	}

	@Test
	void testPuedoObtenerRendimientosHistoricosPorSimbolo() {

		Long usuarioOid = 1L;
		String simbolo = "AUSO";
		LocalDateTime fecha = LocalDateTime.now();

		List<HistoricoRendimientos> listaHistoricoRendimientos = new ArrayList<>();
		HistoricoRendimientos historicoRendimientos = new HistoricoRendimientos();
		historicoRendimientos.setSimbolo(simbolo);
		historicoRendimientos.setRendimientoTotal(new BigDecimal(100));
		historicoRendimientos.setRendimientoTotalPorcentaje(new BigDecimal(50));
		historicoRendimientos.setFecha(fecha);
		historicoRendimientos.setCantidadDeTitulos(new BigDecimal(2));
		historicoRendimientos.setValorInversion(new BigDecimal(2500));
		listaHistoricoRendimientos.add(historicoRendimientos);
		

		when(historicoRendimientoRepositorio.obtenerRendimientosHistoricosPorSimbolo(simbolo,
				usuarioOid)).thenReturn(listaHistoricoRendimientos);

		List<HistoricoRendimientosResponse> listaHistoricoRendimientosResponse = historicoRendimientoServicioImpl.obtenerRendimientosHistoricosPorSimbolo(simbolo, usuarioOid);

		assertNotNull(listaHistoricoRendimientosResponse);
		assertTrue(!listaHistoricoRendimientosResponse.isEmpty());

	}
}
