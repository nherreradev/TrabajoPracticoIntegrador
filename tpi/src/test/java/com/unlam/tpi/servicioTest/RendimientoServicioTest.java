package com.unlam.tpi.servicioTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;
import com.unlam.tpi.core.modelo.RendimientoResponse;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.servicio.PosicionServicioImpl;
import com.unlam.tpi.core.servicio.RendimientoServicioImpl;

@ExtendWith(MockitoExtension.class)
public class RendimientoServicioTest {

	@Mock
	private PosicionServicioImpl posicionServicio;

	@Mock
	private UsuarioServicio usuarioServicio;

	@InjectMocks
	private RendimientoServicioImpl rendimientoServicio;

	@Test
	public void persistirCierresDiariosTarea() {

		Usuario usuario1 = new Usuario();
		usuario1.setOid(1L);

		Usuario usuario2 = new Usuario();
		usuario2.setOid(2L);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario1);
		usuarios.add(usuario2);

		RendimientoResponse rendimientoResponse = new RendimientoResponse();
		rendimientoResponse.setCantidadDeTitulos(BigDecimal.TEN);
		rendimientoResponse.setSimbolo("ALUA");
		rendimientoResponse.setFecha(LocalDateTime.now());
		rendimientoResponse.setRendimientoTotal(BigDecimal.TEN);
		rendimientoResponse.setRendimientoTotalPorcentaje(new BigDecimal(50));
		rendimientoResponse.setValorActualDeLaInversion(new BigDecimal(15));
		Map<String, RendimientoResponse> rendimientosActuales = new HashMap<>();
		rendimientosActuales.put("ALUA", rendimientoResponse);
		RendimientoActualResponse rendimientoActualResponse = new RendimientoActualResponse();
		rendimientoActualResponse.setRendimientosActuales(rendimientosActuales);

		when(usuarioServicio.obtenerTodosLosUsuarios()).thenReturn(usuarios);
		when(posicionServicio.calcularRendimientoActual(1L)).thenReturn(rendimientoActualResponse);
		when(posicionServicio.calcularRendimientoActual(2L)).thenReturn(rendimientoActualResponse);

		rendimientoServicio.persistirCierresDiariosTarea();

		verify(usuarioServicio, times(1)).obtenerTodosLosUsuarios();
		verify(posicionServicio, times(1)).calcularRendimientoActual(1L);
		verify(posicionServicio, times(1)).calcularRendimientoActual(2L);
	}
}
