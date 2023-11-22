package com.unlam.tpi.delivery.controlador;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.interfaces.SeccionControlador;
import com.unlam.tpi.core.interfaces.SeccionServicio;
import com.unlam.tpi.delivery.dto.SeccionDTO;
import com.unlam.tpi.delivery.dto.SeccionMapper;

@RestController
@RequestMapping("/api/seccion")
public class SeccionControladorImpl implements SeccionControlador {

	@Autowired
	private SeccionServicio categoriaServicio;

	@Override
	@PostMapping("/guardar")
	public void guardar(@RequestBody SeccionDTO seccionDTO) {
		getCategoriaServicio().guardar(SeccionMapper.dTOaEntidad(seccionDTO));
	}

	@Override
	@RequestMapping(path = "/carga-seccion-excel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void cargaDesdeExcel(@RequestParam MultipartFile excelSeccion) throws IOException {
		getCategoriaServicio().cargaDesdeExcel(excelSeccion);
	}

	@Override
	@GetMapping("/obtener")
	public SeccionDTO obtener(Long id) {
		return SeccionMapper.entidadADTO(getCategoriaServicio().getSeccionDTOPorID(id));
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getCategoriaServicio().borrar(id);
	}

	@Override
	@GetMapping("/listar")
	public List<SeccionDTO> listar() {
		return SeccionMapper.entidadDTOLista(getCategoriaServicio().listar());
	}

	public SeccionServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(SeccionServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}
