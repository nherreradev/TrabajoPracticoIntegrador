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

import com.unlam.tpi.core.interfaces.SeccionRepositorio;
import com.unlam.tpi.core.interfaces.SeccionServicio;
import com.unlam.tpi.core.modelo.Seccion;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.servicio.SeccionServicioImpl;
import com.unlam.tpi.delivery.dto.SeccionDTO;
import com.unlam.tpi.delivery.dto.SeccionMapper;

@ExtendWith(MockitoExtension.class)
public class SeccionServicioTest {

	@InjectMocks
	private SeccionServicioImpl seccionServicio;

	@Mock
	private SeccionRepositorio seccionRepositorio;

	@Test
	public void testQuePuedaGuardarSeccion() {
		SeccionDTO seccion = new SeccionDTO();
		seccion.setNombre("SeccionPrueba");
		seccion.setDescripcion("Esto es un test");
		verify(seccionRepositorio, never()).save(any(Seccion.class));
	}

	@Test
	public void testQuePuedaGuardarSeccionDevuelvaUnaServiceException() {
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			SeccionDTO seccion = null;
			getSeccionServicio().guardar(seccion);
		});
		String expectedMessage = "Error en convertir SeccionDTO a Seccion";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testQuePuedaCargarLasSeccionesDesdeExcelYLasListe() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelSeccion", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		SeccionDTO seccionDTO = new SeccionDTO();
		seccionDTO.setNombre("SeccionPrueba");
		seccionDTO.setDescripcion("Esto es un test");
		List<SeccionDTO> dtoSecciones = new ArrayList<>();
		dtoSecciones.add(seccionDTO);
		List<Seccion> categorias = SeccionMapper.traductorDeListaDTOaEntidad(dtoSecciones);
		when(seccionRepositorio.findAll()).thenReturn(categorias);
		getSeccionServicio().cargaDesdeExcel(excelFile);
		assertNotNull(getSeccionServicio().listar());
	}

	@Test
	public void testQueAlCargarLasSeccionesDesdeExcelFallePorqueNoExisteLaHojaSeccion() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelSeccion", "pregunta_sin_hojas.xls",
				"application/x-xlsx", new ClassPathResource("pregunta_sin_hojas.xlsx").getInputStream());
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getSeccionServicio().cargaDesdeExcel(excelFile);
		});
		String expectedMessage = "Error al importar excel verifique que exista la hoja seccion";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testQueBusqueUnaSeccionPorNombreYNoLaEncuentre() {
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getSeccionServicio().getSeccionDTOPorNombre("Noexiste");
		});
		String expectedMessage = "Error al obtener la seccion por nombre";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testQuePuedaObtenerUnaSeccionPorNombre() {
		SeccionDTO seccion = new SeccionDTO();
		seccion.setNombre("SeccionPrueba");
		seccion.setDescripcion("Esto es un test");
		getSeccionServicio().guardar(seccion);
		when(seccionRepositorio.findByNombre("SeccionPrueba")).thenReturn(SeccionMapper.dTOaEntidad(seccion));
		assertNotNull(getSeccionServicio().getSeccionPorNombre("SeccionPrueba"));
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicioImpl seccionServicio) {
		this.seccionServicio = seccionServicio;
	}

}