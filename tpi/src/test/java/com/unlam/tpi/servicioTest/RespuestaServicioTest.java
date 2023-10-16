package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.interfaces.CategoriaServicio;
import com.unlam.tpi.interfaces.RespuestaServicio;

@SpringBootTest
public class RespuestaServicioTest {

	@Autowired
	private RespuestaServicio  respuestaServicio;

	@Test
	public void testQuePuedaGuardarCategoria() {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setNombre("Respuesta A");
		respuesta.setValor(10);
		respuesta.setOrden(1);
		getRespuestaServicio().guardar(respuesta);
	}
	
	@Test
	public void testQuePuedaListarCategoria() {
		assertTrue(getRespuestaServicio().listar().size()>0);
	}

	public RespuestaServicio getRespuestaServicio() {
		return respuestaServicio;
	}

	public void setRespuestaServicio(RespuestaServicio respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}
	
}
