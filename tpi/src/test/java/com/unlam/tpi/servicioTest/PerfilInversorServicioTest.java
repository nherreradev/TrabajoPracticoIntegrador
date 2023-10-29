package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.PerfilInversorServicio;
import com.unlam.tpi.core.modelo.PerfilInversor;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.servicio.PerfilInversorServicioImpl;
import com.unlam.tpi.delivery.dto.PerfilInversorDTO;
import com.unlam.tpi.delivery.dto.TipoNivelConocimiento;
import com.unlam.tpi.delivery.dto.TipoPerfilInversor;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.infraestructura.repositorio.PerfilInversorRepositorio;
import com.unlam.tpi.infraestructura.repositorio.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
public class PerfilInversorServicioTest {

    UsuarioDTO USUARIODTO = new UsuarioDTO("Usuario_Prueba", "Mercado", "Junior","Test@Test.com", "1234", Boolean.TRUE, Boolean.TRUE);
    Usuario USUARIO = new Usuario ("Usuario_Prueba", "Mercado", "Junior","Test@Test.com", "1234", Boolean.TRUE, Boolean.TRUE);
    
    @InjectMocks
	private PerfilInversorServicioImpl perfilInversorServicio;

    @Mock
    private PerfilInversorRepositorio perfilInversorRepositorio;
	
    @Mock
    private UsuarioRepositorio usuarioRepositorio;
	
    @Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte0YTolerancia24() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(0);
		perfilInversorDTO.setToleranciaRiesgo(24);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte1YTolerancia24() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(1);
		perfilInversorDTO.setToleranciaRiesgo(24);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte2YTolerancia24() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(2);
		perfilInversorDTO.setToleranciaRiesgo(24);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}

	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservadorHorizonte1YTolerancia1() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(1);
		perfilInversorDTO.setToleranciaRiesgo(1);
		perfilInversorDTO.setNivelConocimiento(37);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoConservador4() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(18);
		perfilInversorDTO.setToleranciaRiesgo(1);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.CONSERVADOR);
	}
	
	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoModerado() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(12);
		perfilInversorDTO.setToleranciaRiesgo(26);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.MODERADO);
	}

	
	@Test
	public void testQueMeDefinaUnPerfilSubjetivoAgresivo() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(1);
		perfilInversorDTO.setToleranciaRiesgo(25);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoPerfilSubjetivo(), TipoPerfilInversor.AGRESIVO);
	}
	
	
	@Test
	public void testQueMeDefinaNivelConocimientoPrincipiante() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(9);
		perfilInversorDTO.setHorizonteTemporal(15);
		perfilInversorDTO.setToleranciaRiesgo(35);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.PRINCIPIANTE);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoIntermedio() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(15);
		perfilInversorDTO.setHorizonteTemporal(15);
		perfilInversorDTO.setToleranciaRiesgo(35);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.PRINCIPIANTE);
	}

	@Test
	public void testQueMeDefinaNivelConocimientoAvanzando() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(31);
		perfilInversorDTO.setHorizonteTemporal(15);
		perfilInversorDTO.setToleranciaRiesgo(35);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.AVANZADO);
	}
	
	@Test
	public void testQueMeDefinaNivelConocimientoExperto() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setNivelConocimiento(37);
		perfilInversorDTO.setHorizonteTemporal(15);
		perfilInversorDTO.setToleranciaRiesgo(35);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getTipoNivelConocimiento(), TipoNivelConocimiento.EXPERTO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorConservadorSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoEsPrincipiante() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(6);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.CONSERVADOR);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorModeradoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoIntermedio() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(25);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorModeradoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoAvanzado() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(35);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorAgresivoSiendoQueElPerfilSubjetivoEsModeradoYElNivelDeConocimientoExperto() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(17);
		perfilInversorDTO.setNivelConocimiento(36);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
		perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.MODERADO);
	}
	
	@Test
	public void testQueMeDefinaUnPerfilInversorAgresivoSiendoQueElPerfilSubjetivoEsAgresivoYElNivelDeConocimientoExperto() {
		PerfilInversorDTO perfilInversorDTO = new PerfilInversorDTO();
		perfilInversorDTO.setHorizonteTemporal(10);
		perfilInversorDTO.setToleranciaRiesgo(39);
		perfilInversorDTO.setNivelConocimiento(36);
		perfilInversorDTO.setUsuarioDTO(USUARIODTO);
		PerfilInversor perfilInversor = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
	    when(perfilInversorRepositorio.findByUsuario_NombreUsuario(USUARIODTO.getNombreUsuario())).thenReturn(perfilInversor);
	    when(getPerfilInversorServicio().guardar(perfilInversor)).thenReturn(perfilInversor);
	    perfilInversorDTO = getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
		assertEquals(perfilInversorDTO.getPerfilInversor(), TipoPerfilInversor.AGRESIVO);
	}
	
	public PerfilInversorServicio getPerfilInversorServicio() {
		return perfilInversorServicio;
	}

	public void setPerfilInversorServicio(PerfilInversorServicioImpl perfilInversorServicio) {
		this.perfilInversorServicio = perfilInversorServicio;
	}
}
