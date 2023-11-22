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

import com.unlam.tpi.core.interfaces.PreguntaControlador;
import com.unlam.tpi.core.interfaces.PreguntaServicio;
import com.unlam.tpi.delivery.dto.PreguntaDTO;
import com.unlam.tpi.delivery.dto.PreguntaMapper;

@RestController
@RequestMapping("/api/pregunta")
public class PreguntaControladorImpl implements PreguntaControlador {

	@Autowired
	private PreguntaServicio preguntaServicio;

	@Override
	@PostMapping(value = "/guardar", consumes = "application/json", produces = "application/json")
	public void guardar(@RequestBody PreguntaDTO pregunta) {
		getPreguntaServicio().guardar(PreguntaMapper.dTOaEntidad(pregunta));
	}

	@Override
	@RequestMapping(path = "/carga-pregunta-excel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void cargaDesdeExcel(@RequestParam MultipartFile excelPregunta) throws IOException {
		getPreguntaServicio().cargaDesdeExcel(excelPregunta);
	}

	@Override
	@GetMapping("/obtener")
	public PreguntaDTO obtener(Long id) {
		return PreguntaMapper.entidadADTO(getPreguntaServicio().getPreguntaDTOPorID(id));
	}

	@Override
	@GetMapping("/obtener-codigo")
	public PreguntaDTO getPreguntaDTOPorCodigo(String codigo) {
		return PreguntaMapper.entidadADTO(getPreguntaServicio().getPreguntaDTOPorCodigo(codigo));
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getPreguntaServicio().borrar(id);
	}

	@Override
	@GetMapping("/borrar-codigo")
	public void borrar(String codigo) {
		getPreguntaServicio().borrar(codigo);
	}

	@Override
	@GetMapping("/listar")
	public ResponseEntity<List<PreguntaDTO>> listar() {
		return ResponseEntity.ok(PreguntaMapper.entidadDTOLista(getPreguntaServicio().listar()));
	}

	@Override
	@GetMapping("/listar-por-categoria")
	public ResponseEntity<List<PreguntaDTO>> listarPorCategoria(String categoria) {
		return ResponseEntity.ok(PreguntaMapper.entidadDTOLista(getPreguntaServicio().listarPorCategoria(categoria)));
	}

	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicio preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

}
