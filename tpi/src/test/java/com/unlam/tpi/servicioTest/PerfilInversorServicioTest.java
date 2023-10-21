package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unlam.tpi.dto.PerfilInversorDTO;
import com.unlam.tpi.dto.TipoNivelConocimiento;
import com.unlam.tpi.dto.TipoPerfilInversor;
import com.unlam.tpi.interfaces.PerfilInversorServicio;

@SpringBootTest
@Transactional
public class PerfilInversorServicioTest {

	@Autowired
	private PerfilInversorServicio perfilInversorServicio;

	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservador0() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(0);
		perfilInversorDTO.setToleranciaRiesgo(24);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservador1() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(1);
		perfilInversorDTO.setToleranciaRiesgo(24);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservador2() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(2);
		perfilInversorDTO.setToleranciaRiesgo(24);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservador3() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(1);
		perfilInversorDTO.setToleranciaRiesgo(1);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservador4() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(18);
		perfilInversorDTO.setToleranciaRiesgo(1);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoModerado() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(12);
		perfilInversorDTO.setToleranciaRiesgo(26);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.MODERADO);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoAgresivo0() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(0);
		perfilInversorDTO.setToleranciaRiesgo(25);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.AGRESIVO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoAgresivo1() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(1);
		perfilInversorDTO.setToleranciaRiesgo(25);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.AGRESIVO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoAgresivo2() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(2);
		perfilInversorDTO.setToleranciaRiesgo(25);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.AGRESIVO);
	}
	
	@Test
	public void testQueMeDefinaNivelConocimientoPrincipiante() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(9);
		perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.PRINCIPIANTE);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoIntermedio() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(15);
		perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.INTERMEDIO);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoAvanzando() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(31);
		perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.AVANZADO);
	}
	
	@Test
	public void testQueMeDefinaNivelConocimientoExperto() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(37);
		perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.EXPERTO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorConservadorSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoEsPrincipiante() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(6);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorModeradoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoIntermedio() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(25);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorModeradoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoAvanzado() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(35);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorAgresivoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoExperto() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(36);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorAgresivoSiendoQueElPerfilSubjetivoEsAgresivoYElNivelDeConocimientoExperto() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(39);
		perfilInversorDTO.setNivelConocimiento(36);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.AGRESIVO);
	}
	
	public PerfilInversorServicio getPerfilInversorServicio() {
		return perfilInversorServicio;
	}

	public void setPerfilInversorServicio(PerfilInversorServicio perfilInversorServicio) {
		this.perfilInversorServicio = perfilInversorServicio;
	}
}
