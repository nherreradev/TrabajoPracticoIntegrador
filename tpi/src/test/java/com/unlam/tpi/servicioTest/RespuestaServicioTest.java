package com.unlam.tpi.servicioTest;

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

import com.unlam.tpi.core.interfaces.PreguntaServicio;
import com.unlam.tpi.core.interfaces.RespuestaRepositorio;
import com.unlam.tpi.core.interfaces.RespuestaServicio;
import com.unlam.tpi.core.modelo.Respuesta;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.servicio.RespuestaServicioImpl;
import com.unlam.tpi.delivery.dto.PreguntaDTO;
import com.unlam.tpi.delivery.dto.PreguntaMapper;
import com.unlam.tpi.delivery.dto.RespuestaDTO;
import com.unlam.tpi.delivery.dto.RespuestaMapper;
import com.unlam.tpi.delivery.dto.TipoComponente;

@ExtendWith(MockitoExtension.class)
public class RespuestaServicioTest {

	@InjectMocks
	private RespuestaServicioImpl respuestaServicio;

	@Mock
	private RespuestaRepositorio respuestaRepositorio;

	@Mock
	private PreguntaServicio preguntaServicio;

	@Test
	public void testQuePuedaGuardarRespuestaYLaObtenga() {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setCodigo("RA");
		respuesta.setNombre("Respuesta A");
		respuesta.setValor(10);
		respuesta.setOrden(1);
		verify(respuestaRepositorio, never()).save(any(Respuesta.class));
	}

	@Test
	public void testQuePuedaCargarLasRespuestaDesdeExcelYLasListe() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelRespuesta", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setCodigo("RR");
		respuesta.setNombre("R1");
		respuesta.setInstrumento("A");
		respuesta.setValor(10);
		respuesta.setOrden(1);
		List<RespuestaDTO> dtoRespuestas = new ArrayList<>();
		dtoRespuestas.add(respuesta);
		when(respuestaRepositorio.findByCodigoAndInstrumento("R1", "A"))
				.thenReturn(RespuestaMapper.dTOaEntidad(respuesta));
		when(preguntaServicio.getPreguntaPorCodigo("A1")).thenReturn(PreguntaMapper.dTOaEntidad(crearPreguntaDTO()));
		when(respuestaRepositorio.findAll()).thenReturn(RespuestaMapper.traductorDeListaDTOaEntidad(dtoRespuestas));
		getRespuestaServicio().cargaDesdeExcel(excelFile);
		assertNotNull(getRespuestaServicio().listar());
	}

	private PreguntaDTO crearPreguntaDTO() {
		PreguntaDTO pregunta = new PreguntaDTO();
		pregunta.setEnunciado("A1");
		pregunta.setOrden(1);
		pregunta.setTipoComponente(TipoComponente.RADIO);
		return pregunta;
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
			getRespuestaServicio().getRespuestaDTOPorCodigo(nombre);
		});
		String expectedMessage = "Error al obtener la respuesta: " + nombre;
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	public RespuestaServicio getRespuestaServicio() {
		return respuestaServicio;
	}

	public void setRespuestaServicio(RespuestaServicioImpl respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}

}