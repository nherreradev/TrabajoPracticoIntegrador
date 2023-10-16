package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.servicio.CategoriaServicio;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaControladorImpl implements CategoriaControlador {

	@Autowired
	private CategoriaServicio categoriaServicio;
	
	@Override
	@PostMapping("/guardar")
	public ResponseEntity<String> guardar(@RequestBody CategoriaDTO categoria) {
		getCategoriaServicio().guardar(categoria);
		return ResponseEntity.ok("Categoria Guardada");
	}
	
	@Override
	@RequestMapping(path = "/carga-categoria-excel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void cargaDesdeExcel(@RequestParam MultipartFile excelCategoria) {
		getCategoriaServicio().cargaDesdeExcel(excelCategoria);
	}

	@Override
	@GetMapping("/obtener")
	public CategoriaDTO obtener(Long id) {
		return getCategoriaServicio().getCategoriaDTOPorID(id);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getCategoriaServicio().borrar(id);		
	}

	@Override
	@GetMapping("/listar")
	public List<CategoriaDTO> listar() {
		return getCategoriaServicio().listar();
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}
