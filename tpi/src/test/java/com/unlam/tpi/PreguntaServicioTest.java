package com.unlam.tpi;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.dto.TipoComponente;
import com.unlam.tpi.servicio.CategoriaServicio;
import com.unlam.tpi.servicio.PreguntaServicio;
import com.unlam.tpi.servicio.SeccionServicio;

@SpringBootTest
@Transactional
public class PreguntaServicioTest {

	@Autowired
	private PreguntaServicio  preguntaServicio;
	
	@Autowired
	private CategoriaServicio categoriaServicio;
	
	@Autowired
	private SeccionServicio seccionServicio;

	@Test
	public void testQuePuedaGuardarUnaPreguntaConRespuestaCategoriaYSeccion() {
		PreguntaDTO pregunta = crearPreguntaDTO();
		pregunta.setCategoria(crearCategoria());
		pregunta.setSeccion(crearSeccion());
		pregunta.setRespuestas(crearListaRespuesta());
		getPreguntaServicio().guardar(pregunta);
	}
	
	@Test
	public void testQuePuedaListarPreguntasQueCorrespondeAUnaCategoria() {
		PreguntaDTO pregunta = crearPreguntaDTO();
		pregunta.setCategoria(crearCategoria());
		pregunta.setSeccion(crearSeccion());
		pregunta.setRespuestas(crearListaRespuesta());
		getPreguntaServicio().guardar(pregunta);
		assertTrue(getPreguntaServicio().listarPorCategoria("CategoriaPrueba").size()>0);
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
		pregunta.setEnunciado("PreguntaPrueba");
		pregunta.setOrden(1);
		pregunta.setTipoComponente(TipoComponente.RADIO);
		return pregunta;
	}
	
	@Test
    public void testQuePuedaCargarLasSPreguntasDesdeExcelYLasListe() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelPregunta", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		givenSeCreaLacategoria(excelFile);
		givenSeCreaLaSeccion(excelFile);
		getPreguntaServicio().cargaDesdeExcel(excelFile);
		List<PreguntaDTO> preguntas = getPreguntaServicio().listar();
		assertNotNull(preguntas);
    }
	
	private void givenSeCreaLacategoria(MockMultipartFile excelFile ) {
		getCategoriaServicio().cargaDesdeExcel(excelFile);
	}
	
	private void givenSeCreaLaSeccion(MockMultipartFile excelFile ) {
		getSeccionServicio().cargaDesdeExcel(excelFile);
	}
	
	@Test
	public void testQueAlCargarLasPreguntasDesdeExcelYFallePorqueNoExisteLaHojaPregunta() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelPregunta", "pregunta_sin_hojas.xls",
				"application/x-xlsx", new ClassPathResource("pregunta_sin_hojas.xlsx").getInputStream());
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getPreguntaServicio().cargaDesdeExcel(excelFile);
		});
		String expectedMessage = "Error al importar excel verifique que exista la hoja pregunta";
		String actualMessage = serviceException.getMessage();
 		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testQuePuedaObtenerUnaPreguntaDTOPorNombre() {
		PreguntaDTO pregunta = crearPreguntaDTO();
		pregunta.setCategoria(crearCategoria());
		pregunta.setSeccion(crearSeccion());
		pregunta.setRespuestas(crearListaRespuesta());
		getPreguntaServicio().guardar(pregunta);
		assertTrue(PreguntaDTO.class
				.isAssignableFrom(getPreguntaServicio().getPreguntaDTOPorEnunciado("PreguntaPrueba").getClass()));
	}
	
	@Test
	public void testQueBusqueUnaPreguntaPorNombreYNoLaEncuentre() {
		String nombre = "Noexiste";
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getPreguntaServicio().getPreguntaDTOPorEnunciado("Noexiste");
		});
		String expectedMessage = "Error al obtener la pregunta: "+nombre;
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
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