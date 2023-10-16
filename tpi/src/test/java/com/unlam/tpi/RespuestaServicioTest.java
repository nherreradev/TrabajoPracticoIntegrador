package com.unlam.tpi;

import static org.junit.Assert.assertNotNull;
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

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.servicio.CategoriaServicio;
import com.unlam.tpi.servicio.PreguntaServicio;
import com.unlam.tpi.servicio.RespuestaServicio;
import com.unlam.tpi.servicio.SeccionServicio;

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
	public void testQuePuedaGuardarCategoria() {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setNombre("Respuesta A");
		respuesta.setValor(10);
		respuesta.setOrden(1);
		getRespuestaServicio().guardar(respuesta);
	}
	
	@Test
    public void testQuePuedaCargarLasSPreguntasDesdeExcelYLasListe() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelPregunta", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		givenSeCreaLacategoria(excelFile);
		givenSeCreaLaSeccion(excelFile);
		givenSeCreaLaPregunta(excelFile);
		getRespuestaServicio().cargaDesdeExcel(excelFile);
		List<RespuestaDTO> preguntas = getRespuestaServicio().listar();
		assertNotNull(preguntas);
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
	
	
	@Test
	public void testQueAlCargarLasPreguntasDesdeExcelYFallePorqueNoExisteLaHojaPregunta() throws IOException {
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
	public void testQuePuedaListarPregunta() {
		assertTrue(getRespuestaServicio().listar().size() > 0);
	}
	
	@Test
	public void testQuePuedaObtenerUnaRespuestaDTOPorID() {
		assertTrue(RespuestaDTO.class.isAssignableFrom(getRespuestaServicio().getRespuestaDTOPorID(1L).getClass()));
	}
	
	@Test
	public void testQuePuedaListarRespuesta() {
		assertTrue(getRespuestaServicio().listar().size()>0);
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