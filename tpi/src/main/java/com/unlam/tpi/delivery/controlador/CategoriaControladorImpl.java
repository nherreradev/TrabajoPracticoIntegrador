package com.unlam.tpi.delivery.controlador;

import java.io.IOException;
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

import com.unlam.tpi.core.interfaces.CategoriaControlador;
import com.unlam.tpi.core.interfaces.CategoriaServicio;
import com.unlam.tpi.core.modelo.Categoria;
import com.unlam.tpi.delivery.dto.CategoriaDTO;
import com.unlam.tpi.delivery.dto.CategoriaMapper;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaControladorImpl implements CategoriaControlador {

	@Autowired
	private CategoriaServicio categoriaServicio;
	
	@Override
	@PostMapping("/guardar")
	public ResponseEntity<String> guardar(@RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = CategoriaMapper.dTOaEntidad(categoriaDTO);
		getCategoriaServicio().guardar(categoria);
		return ResponseEntity.ok("Categoria Guardada");
	}
	
	@Override
	@RequestMapping(path = "/carga-categoria-excel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void cargaDesdeExcel(@RequestParam MultipartFile excelCategoria) throws IOException {
		getCategoriaServicio().cargaDesdeExcel(excelCategoria);
	}

	@Override
	@GetMapping("/obtener")
	public CategoriaDTO obtener(Long id) {
		return CategoriaMapper.entidadADTO(getCategoriaServicio().getCategoriaPorID(id));
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getCategoriaServicio().borrar(id);		
	}

	@Override
	@GetMapping("/listar")
	public List<CategoriaDTO> listar() {
		return  CategoriaMapper.entidadDTOLista(getCategoriaServicio().listar());
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}
