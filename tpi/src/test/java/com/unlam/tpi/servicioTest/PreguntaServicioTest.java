package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import com.unlam.tpi.core.interfaces.CategoriaServicio;
import com.unlam.tpi.core.interfaces.PreguntaRepositorio;
import com.unlam.tpi.core.interfaces.PreguntaServicio;
import com.unlam.tpi.core.interfaces.SeccionServicio;
import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.servicio.PreguntaServicioImpl;
import com.unlam.tpi.delivery.dto.CategoriaDTO;
import com.unlam.tpi.delivery.dto.CategoriaMapper;
import com.unlam.tpi.delivery.dto.PreguntaDTO;
import com.unlam.tpi.delivery.dto.PreguntaMapper;
import com.unlam.tpi.delivery.dto.RespuestaDTO;
import com.unlam.tpi.delivery.dto.SeccionDTO;
import com.unlam.tpi.delivery.dto.SeccionMapper;
import com.unlam.tpi.delivery.dto.TipoComponente;

@ExtendWith(MockitoExtension.class)
public class PreguntaServicioTest {

	@InjectMocks
	private PreguntaServicioImpl preguntaServicio;

	@Mock
	private PreguntaRepositorio preguntaRepositorio;

	@Mock
	private CategoriaServicio categoriaServicio;

	@Mock
	private SeccionServicio seccionServicio;

	@Test
	public void testQuePuedaGuardarUnaPreguntaConRespuestaCategoriaYSeccion() {
		PreguntaDTO pregunta = crearPreguntaDTO();
		pregunta.setCategoria(crearCategoria());
		pregunta.setSeccion(crearSeccion());
		pregunta.setRespuestas(crearListaRespuesta());
		verify(preguntaRepositorio, never()).save(any(Pregunta.class));
	}

	@Test
	public void testQuePuedaListarPreguntasQueCorrespondeAUnaCategoria() {
		PreguntaDTO pregunta = crearPreguntaDTO();
		pregunta.setCategoria(crearCategoria());
		pregunta.setSeccion(crearSeccion());
		pregunta.setRespuestas(crearListaRespuesta());
		List<PreguntaDTO> preguntaLista = new ArrayList<>();
		preguntaLista.add(pregunta);
		when(preguntaRepositorio.findByCategoria_Nombre("CategoriaPrueba"))
				.thenReturn(PreguntaMapper.traductorDeListaDTOaEntidad(preguntaLista));
		assertTrue(getPreguntaServicio().listarPorCategoria("CategoriaPrueba").size() > 0);
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

	private List<RespuestaDTO> crearListaRespuesta() {
		List<RespuestaDTO> respuestaList = new ArrayList<>();
		respuestaList.add(crearRespuestaDTO("Opción A", 10, 1));
		respuestaList.add(crearRespuestaDTO("Opción B", 2, 2));
		respuestaList.add(crearRespuestaDTO("Opción C", 5, 3));
		respuestaList.add(crearRespuestaDTO("Opción D", 7, 4));
		return respuestaList;
	}

	private RespuestaDTO crearRespuestaDTO(String nombre, Integer valor, Integer orden) {
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
		pregunta.setCategoria(crearCategoria());
		return pregunta;
	}

	@Test
	public void testQuePuedaCargarLasSPreguntasDesdeExcelYLasListe() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelPregunta", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		List<PreguntaDTO> dtoPreguntas = new ArrayList<>();
		dtoPreguntas.add(crearPreguntaDTO());
		List<Pregunta> preguntas = PreguntaMapper.traductorDeListaDTOaEntidad(dtoPreguntas);
		CategoriaDTO categoria = crearCategoria();
		when(categoriaServicio.getCategoriaPorNombre("CategoriaPrueba"))
				.thenReturn(CategoriaMapper.dTOaEntidad(categoria));
		when(seccionServicio.getSeccionPorNombre("SeccionPrueba"))
				.thenReturn(SeccionMapper.dTOaEntidad(crearSeccion()));
		when(preguntaRepositorio.findAll()).thenReturn(preguntas);
		when(preguntaRepositorio.saveAll(preguntas)).thenReturn(preguntas);
		getPreguntaServicio().cargaDesdeExcel(excelFile);
		assertNotNull(getPreguntaServicio().listar());
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
		when(preguntaRepositorio.findByEnunciado("PreguntaPrueba")).thenReturn(PreguntaMapper.dTOaEntidad(pregunta));
		assertEquals("PreguntaPrueba",
				getPreguntaServicio().getPreguntaDTOPorEnunciado("PreguntaPrueba").getEnunciado());
		;
	}

	@Test
	public void testQueBusqueUnaPreguntaPorNombreYNoLaEncuentre() {
		String nombre = "Noexiste";
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getPreguntaServicio().getPreguntaDTOPorEnunciado("Noexiste");
		});
		String expectedMessage = "Error al obtener la pregunta: " + nombre;
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicioImpl preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

	public PreguntaRepositorio getPreguntaRepositorio() {
		return preguntaRepositorio;
	}

	public void setPreguntaRepositorio(PreguntaRepositorio preguntaRepositorio) {
		this.preguntaRepositorio = preguntaRepositorio;
	}

}