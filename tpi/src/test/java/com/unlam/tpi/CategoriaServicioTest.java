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
import org.springframework.test.annotation.Rollback;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.servicio.CategoriaServicio;

@SpringBootTest
@Transactional
public class CategoriaServicioTest {

	@Autowired
	private CategoriaServicio categoriaServicio;

	@Test
	public void testQuePuedaGuardarCategoria() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setNombre("CategoriaPrueba");
		categoria.setDescripcion("Esto es un test");
		getCategoriaServicio().guardar(categoria);
	}

	@Test
	public void testQuePuedaGuardarCategoriaDevuelvaUnaServiceException() {
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			CategoriaDTO categoria = null;
			getCategoriaServicio().guardar(categoria);
		});
		String expectedMessage = "Error en convertir CategoriaDTO a Categoria";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
    public void testQuePuedaCargarLasCategoriasDesdeExcelYMeListeLasCategorias() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelCategoria", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		getCategoriaServicio().cargaDesdeExcel(excelFile);
		List<CategoriaDTO> categorias = getCategoriaServicio().listar();
		assertNotNull(categorias);
    }
	
	
	@Test
	public void testQueAlCargarLasCategoriasDesdeExcelFallePorqueNoExisteLaHojaCategoria() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelCategoria", "pregunta_sin_hojas.xls",
				"application/x-xlsx", new ClassPathResource("pregunta_sin_hojas.xlsx").getInputStream());
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getCategoriaServicio().cargaDesdeExcel(excelFile);
		});
		String expectedMessage = "Error al importar excel verifique que exista la hoja categoria";
		String actualMessage = serviceException.getMessage();
 		assertTrue(actualMessage.contains(expectedMessage));
	}

	
	@Test
	public void testQuePuedaObtenerUnaCategoriaDTOPorNombre() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setNombre("CategoriaPrueba");
		categoria.setDescripcion("Esto es un test");
		getCategoriaServicio().guardar(categoria);
		assertNotNull(getCategoriaServicio().getCategoriaDTOPorNombre("CategoriaPrueba"));
	}
	
	@Test
	public void testQueBusqueUnaCategoriaPorNombreYNoLaEncuentre() {
		ServiceException serviceException = assertThrows(ServiceException.class, () -> {
			getCategoriaServicio().getCategoriaDTOPorNombre("Noexiste");
		});
		String expectedMessage = "Error al obtener la categoria por nombre";
		String actualMessage = serviceException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}
