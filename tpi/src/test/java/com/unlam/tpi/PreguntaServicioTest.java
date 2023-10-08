package com.unlam.tpi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.enums.TipoComponente;
import com.unlam.tpi.servicio.PreguntaServicio;

@SpringBootTest
public class PreguntaServicioTest {

	@Autowired
	private PreguntaServicio  preguntaServicio;

	@Test
	public void testQuePuedaGuardarUnaPreguntaConRespuestaCategoriaYSeccion() {
		PreguntaDTO pregunta = crearPreguntaDTO();
		pregunta.setCategoria(crearCategoria());
		pregunta.setSeccion(crearSeccion());
		pregunta.setRespuestas(crearListaRespuesta());
		getPreguntaServicio().guardar(pregunta);
	}
	
	@Test
	public void testQuePuedaListarPreguntas() {
		assertTrue(getPreguntaServicio().listar().size()>0);
	}

	private CategoriaDTO crearCategoria() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setNombre("CategoriaPrueba");
		categoria.setDescripcion("Esto es un test");
		return categoria;
	}
	
	private SeccionDTO crearSeccion() {
		SeccionDTO seccion = new SeccionDTO();
		seccion.setNombre("SeccionPrueba");
		seccion.setDescripcion("Esto es un test");
		return seccion;
	}
	
	private List<RespuestaDTO> crearListaRespuesta(){
		List<RespuestaDTO> respuestaList = new ArrayList<>();
		respuestaList.add(crearRespuestaDTO("Opción A", 10, 1));
		respuestaList.add(crearRespuestaDTO("Opción B", 2, 2));
		respuestaList.add(crearRespuestaDTO("Opción C", 5, 3));
		respuestaList.add(crearRespuestaDTO("Opción D", 7, 4));
		return respuestaList;
	}
	
	private RespuestaDTO crearRespuestaDTO(String nombre, Integer valor, Integer orden ) {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setNombre(nombre);
		respuesta.setValor(valor);
		respuesta.setOrden(orden);
		return respuesta;
	}
	
	private PreguntaDTO crearPreguntaDTO() {
		PreguntaDTO pregunta = new PreguntaDTO();
		pregunta.setEnunciado("Es Una Prueba de Pregunta");
		pregunta.setOrden(1);
		pregunta.setTipoComponente(TipoComponente.RADIO);
		return pregunta;
	}
	
		
	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicio preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

	
}
