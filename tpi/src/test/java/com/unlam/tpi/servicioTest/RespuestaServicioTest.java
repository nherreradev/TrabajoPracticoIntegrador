package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import com.unlam.tpi.core.servicio.CategoriaServicio;
import com.unlam.tpi.core.servicio.PreguntaServicio;
import com.unlam.tpi.core.servicio.RespuestaServicio;
import com.unlam.tpi.core.servicio.SeccionServicio;
import com.unlam.tpi.delivery.dto.CategoriaDTO;
import com.unlam.tpi.delivery.dto.RespuestaDTO;
import com.unlam.tpi.infraestructura.arquitectura.ServiceException;

@SpringBootTest
@Transactional
public class RespuestaServicioTest {

	@Autowired
	private RespuestaServicio  respuestaServicio;
	
	@Autowired
	private PreguntaServicio  preguntaServicio;
	
	@Autowired
	private CategoriaServicio categoriaServicio;
	
	@Autowired
	private SeccionServicio seccionServicio;

	@Test
	public void testQuePuedaGuardarRespuestaYLaObtenga() {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setNombre("Respuesta A");
		respuesta.setValor(10);
		respuesta.setOrden(1);
		getRespuestaServicio().guardar(respuesta);
		assertNotNull(getRespuestaServicio().getRespuestaDTOPorNombre("Respuesta A"));
	}
	
	@Test
	public void testQueAlGuardarUnaRespuestaDevuelvaUnaServiceException() {
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			RespuestaDTO respuesta = null;
			getRespuestaServicio().guardar(respuesta);
		});
		String expectedMessage = "Error en convertir RespuestaDTO a Respuesta";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
    public void testQuePuedaCargarLasRespuestaDesdeExcelYLasListe() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelRespuesta", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		givenSeCreaLacategoria(excelFile);
		givenSeCreaLaSeccion(excelFile);
		givenSeCreaLaPregunta(excelFile);
		getRespuestaServicio().cargaDesdeExcel(excelFile);
		List<RespuestaDTO> respuestas = getRespuestaServicio().listar();
		assertNotNull(respuestas);
    }
	
	@Test
	public void testQueAlCargarLasRespuestasDesdeExcelYFallePorqueNoExisteLaHojaRespuesta() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelPregunta", "pregunta_sin_hojas.xls",
				"application/x-xlsx", new ClassPathResource("pregunta_sin_hojas.xlsx").getInputStream());
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getRespuestaServicio().cargaDesdeExcel(excelFile);
		});
		String expectedMessage = "Error al importar excel verifique que exista la hoja respuesta";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testQueBusqueUnaRespuestaPorNombreYNoLaEncuentre() {
		String nombre = "Noexiste";
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getRespuestaServicio().getRespuestaDTOPorNombre(nombre);
		});
		String expectedMessage = "Error al obtener la respuesta: " + nombre;
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	private void givenSeCreaLacategoria(MockMultipartFile excelFile ) {
		getCategoriaServicio().cargaDesdeExcel(excelFile);
	}
	
	private void givenSeCreaLaSeccion(MockMultipartFile excelFile ) {
		getSeccionServicio().cargaDesdeExcel(excelFile);
	}
	
	private void givenSeCreaLaPregunta(MockMultipartFile excelFile ) {
		getPreguntaServicio().cargaDesdeExcel(excelFile);
	}
	
	public RespuestaServicio getRespuestaServicio() {
		return respuestaServicio;
	}

	public void setRespuestaServicio(RespuestaServicio respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}

	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicio preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicio seccionServicio) {
		this.seccionServicio = seccionServicio;
	}
	
}