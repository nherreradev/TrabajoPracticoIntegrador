package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.PerfilInversorRepositorio;
import com.unlam.tpi.core.interfaces.PerfilInversorServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.PerfilInversor;
import com.unlam.tpi.core.modelo.TipoNivelConocimiento;
import com.unlam.tpi.core.modelo.TipoPerfilInversor;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.servicio.PerfilInversorServicioImpl;

@ExtendWith(MockitoExtension.class)
public class PerfilInversorServicioTest {

	Usuario USUARIO = new Usuario("Usuario_Prueba", "Mercado", "Junior", "Test@Test.com", "1234", Boolean.TRUE,
			Boolean.TRUE, "", Boolean.FALSE);
	@InjectMocks
	private PerfilInversorServicioImpl perfilInversorServicio;

	@Mock
	private PerfilInversorRepositorio perfilInversorRepositorio;

	@Mock
	private UsuarioServicio usuarioServicio;

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte0YTolerancia24() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(0);
		perfilInversor.setToleranciaRiesgo(24);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(perfilInversorRepositorio.save(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte1YTolerancia24() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(1);
		perfilInversor.setToleranciaRiesgo(24);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(perfilInversorRepositorio.save(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte2YTolerancia24() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(2);
		perfilInversor.setToleranciaRiesgo(24);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(perfilInversorRepositorio.save(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte1YTolerancia1() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(1);
		perfilInversor.setToleranciaRiesgo(1);
		perfilInversor.setNivelConocimiento(37);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(perfilInversorRepositorio.save(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte18YTolerancia1() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(18);
		perfilInversor.setToleranciaRiesgo(1);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(perfilInversorRepositorio.save(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoModerado() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(12);
		perfilInversor.setToleranciaRiesgo(26);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.MODERADO);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoAgresivo() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(1);
		perfilInversor.setToleranciaRiesgo(25);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversor);
		assertEquals(perfilInversor.getTipoPerfilSubjetivo(), TipoPerfilInversor.AGRESIVO);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoPrincipiante() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setNivelConocimiento(9);
		perfilInversor.setHorizonteTemporal(15);
		perfilInversor.setToleranciaRiesgo(35);
		perfilInversor.setUsuario(USUARIO);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversor);
		assertEquals(perfilInversor.getTipoNivelConocimiento(), TipoNivelConocimiento.PRINCIPIANTE);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoIntermedio() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setNivelConocimiento(15);
		perfilInversor.setHorizonteTemporal(15);
		perfilInversor.setToleranciaRiesgo(35);
		perfilInversor.setUsuario(USUARIO);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversor);
		assertEquals(perfilInversor.getTipoNivelConocimiento(), TipoNivelConocimiento.PRINCIPIANTE);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoAvanzando() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setNivelConocimiento(31);
		perfilInversor.setHorizonteTemporal(15);
		perfilInversor.setToleranciaRiesgo(35);
		perfilInversor.setUsuario(USUARIO);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversor);
		assertEquals(perfilInversor.getTipoNivelConocimiento(), TipoNivelConocimiento.AVANZADO);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoExperto() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setNivelConocimiento(37);
		perfilInversor.setHorizonteTemporal(15);
		perfilInversor.setToleranciaRiesgo(35);
		perfilInversor.setUsuario(USUARIO);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversor);
		assertEquals(perfilInversor.getTipoNivelConocimiento(), TipoNivelConocimiento.EXPERTO);
	}

	@Test
	public void testQueMeDefinaUnPerfilInversorConservadorSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoEsPrincipiante() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(10);
		perfilInversor.setToleranciaRiesgo(17);
		perfilInversor.setNivelConocimiento(6);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversor);
		assertEquals(perfilInversor.getPerfilInversor(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilInversorModeradoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoIntermedio() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(10);
		perfilInversor.setToleranciaRiesgo(17);
		perfilInversor.setNivelConocimiento(25);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversor);
		assertEquals(perfilInversor.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}

	@Test
	public void testQueMeDefinaUnPerfilInversorModeradoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoAvanzado() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(10);
		perfilInversor.setToleranciaRiesgo(17);
		perfilInversor.setNivelConocimiento(35);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversor);
		assertEquals(perfilInversor.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}

	@Test
	public void testQueMeDefinaUnPerfilInversorAgresivoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoExperto() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(10);
		perfilInversor.setToleranciaRiesgo(17);
		perfilInversor.setNivelConocimiento(36);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversor);
		assertEquals(perfilInversor.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}

	@Test
	public void testQueMeDefinaUnPerfilInversorAgresivoSiendoQueElPerfilSubjetivoEsAgresivoYElNivelDeConocimientoExperto() {
		PerfilInversor perfilInversor = new PerfilInversor();
		perfilInversor.setHorizonteTemporal(10);
		perfilInversor.setToleranciaRiesgo(39);
		perfilInversor.setNivelConocimiento(36);
		perfilInversor.setUsuario(USUARIO);
		when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario()))
				.thenReturn(perfilInversor);
		when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversor = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversor);
		assertEquals(perfilInversor.getPerfilInversor(), TipoPerfilInversor.AGRESIVO);
	}

	public PerfilInversorServicio getPerfilInversorServicio() {
		return perfilInversorServicio;
	}

	public void setPerfilInversorServicio(PerfilInversorServicioImpl perfilInversorServicio) {
		this.perfilInversorServicio = perfilInversorServicio;
	}
}
