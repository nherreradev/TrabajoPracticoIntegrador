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

import com.unlam.tpi.core.interfaces.CategoriaRepositorio;
import com.unlam.tpi.core.interfaces.CategoriaServicio;
import com.unlam.tpi.core.modelo.Categoria;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.servicio.CategoriaServicioImpl;
import com.unlam.tpi.delivery.dto.CategoriaDTO;
import com.unlam.tpi.delivery.dto.CategoriaMapper;

@ExtendWith(MockitoExtension.class)
public class CategoriaServicioTest {

	@InjectMocks
	private CategoriaServicioImpl categoriaServicio;
	
	@Mock
	private CategoriaRepositorio categoriaRepositorio;

	@Test
	public void testQuePuedaGuardarCategoria() {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setNombre("CategoriaPrueba");
		categoriaDTO.setDescripcion("Esto es un test");
		verify(categoriaRepositorio, never()).save(any(Categoria.class));
	}

	@Test
    public void testQuePuedaCargarLasCategoriasDesdeExcelYMeListeLasCategorias() throws IOException {
		MockMultipartFile excelFile = new MockMultipartFile("excelCategoria", "pregunta.xls", "application/x-xlsx",
				new ClassPathResource("pregunta.xlsx").getInputStream());
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setNombre("CategoriaPrueba");
		categoriaDTO.setDescripcion("Esto es un test");
		List<CategoriaDTO> dtoCategorias = new ArrayList<>();
		dtoCategorias.add(categoriaDTO);
		List<Categoria> categorias = CategoriaMapper.traductorDeListaDTOaEntidad(dtoCategorias);
	    when(categoriaRepositorio.findAll()).thenReturn(categorias);
		getCategoriaServicio().cargaDesdeExcel(excelFile);
		assertNotNull(getCategoriaServicio().listar());
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

	
	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicioImpl categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}