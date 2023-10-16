package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.servicio.SeccionServicio;

@SpringBootTest
public class SeccionServicioTest {

	@Autowired
	private SeccionServicio seccionServicio;

	@Test
	public void testQuePuedaGuardarSeccion() {
		SeccionDTO seccion = new SeccionDTO();
		seccion.setNombre("SeccionPrueba");
		seccion.setDescripcion("Esto es un test");
		getSeccionServicio().guardar(seccion);
	}
	
	@Test
	public void testQuePuedaListarCategoria() {
		assertTrue(getSeccionServicio().listar().size()>0);
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicio seccionServicio) {
		this.seccionServicio = seccionServicio;
	}

}