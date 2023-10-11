package com.unlam.tpi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.servicio.CategoriaServicio;

@SpringBootTest
public class CategoriaServicioTest {

	@Autowired
	private CategoriaServicio  categoriaServicio;

	@Test
	public void testQuePuedaGuardarCategoria() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setNombre("CategoriaPrueba");
		categoria.setDescripcion("Esto es un test");
		getCategoriaServicio().guardar(categoria);
	}
	
	@Test
	public void testQuePuedaListarCategoria() {
		assertTrue(getCategoriaServicio().listar().size()>0);
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}
	
}
