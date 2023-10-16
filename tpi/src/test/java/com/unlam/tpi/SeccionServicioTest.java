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
import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.modelo.persistente.Seccion;
import com.unlam.tpi.servicio.SeccionServicio;

@SpringBootTest
@Transactional
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
		getSeccionServicio().cargaDesdeExcel(excelFile);
		List<SeccionDTO> secciones = getSeccionServicio().listar();
		assertNotNull(secciones);
    }
	
	
	@Test
	public void testQueAlCargarLasSeccionesDesdeExcelFallePorqueNoExisteLaHojaSeccion() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelSeccion", "pregunta_sin_hojas.xls",
				"application/x-xlsx", new ClassPathResource("pregunta_sin_hojas.xlsx").getInputStream());
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getSeccionServicio().cargaDesdeExcel(excelFile);
		});
		String expectedMessage = "Error al importar excel verifique que exista la hoja Seccion";
		String actualMessage = serviceException.getMessage();
 		assertTrue(expectedMessage.contains(actualMessage));
	}

	@Test
	public void testQuePuedaListarSeccion() {
		assertTrue(getSeccionServicio().listar().size() > 0);
	}
	
	@Test
	public void testQuePuedaObtenerUnaSeccionDTOPorID() {
		assertTrue(SeccionDTO.class
				.isAssignableFrom(getSeccionServicio().getSeccionDTOPorID(27L).getClass()));
	}

	@Test
	public void testQuePuedaObtenerUnaSeccionDTOPorNombre() {
		assertTrue(SeccionDTO.class
				.isAssignableFrom(getSeccionServicio().getSeccionDTOPorNombre("SecccionPrueba").getClass()));
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
		assertTrue(
				Seccion.class.isAssignableFrom(getSeccionServicio().getSeccionPorNombre("SecccionPrueba").getClass()));
	}
	
	@Test
	public void testQuePuedaBorrarUnaSeccionPorID() {
		getSeccionServicio().borrar(30L);
		assertNotNull(getSeccionServicio().getSeccionDTOPorID(30L));
	}
	
	
	@Test
	public void testQuePuedaListarLasSecciones() {
		assertTrue(getSeccionServicio().listar().size()>0);
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicio seccionServicio) {
		this.seccionServicio = seccionServicio;
	}

}