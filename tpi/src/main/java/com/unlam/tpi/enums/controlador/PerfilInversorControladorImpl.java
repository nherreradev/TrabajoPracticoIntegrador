package com.unlam.tpi.enums.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.dto.PerfilInversorDTO;
import com.unlam.tpi.servicio.PerfilInversorServicio;

@RestController
@RequestMapping("/api/perfil-inversor")
public class PerfilInversorControladorImpl implements PerfilInversorControlador {

	@Autowired
	private PerfilInversorServicio perfilInversorServicio;

	@Override
	@PostMapping(value = "/resultado-perfil-subjetivo")
	public PerfilInversorDTO resultadoPerfilSubjetivo(@RequestBody PerfilInversorDTO perfilInversorDTO) {
		return getPerfilInversorServicio().resultadoPerfilSubjetivo(perfilInversorDTO);
	}

	@Override
	@PostMapping(value = "/resultado-nivel-conocimiento")
	public PerfilInversorDTO resultadoNivelConocimiento(@RequestBody PerfilInversorDTO perfilInversorDTO) {
		return getPerfilInversorServicio().resultadoNivelConocimiento(perfilInversorDTO);
	}

	@Override
	@PostMapping(value = "/resultado-perfil-inversor")
	public PerfilInversorDTO resultadoPerfilInversor(@RequestBody PerfilInversorDTO perfilInversorDTO) {
		return getPerfilInversorServicio().resultadoPerfilInversor(perfilInversorDTO);
	}

	@Override
	@PostMapping("/guardar")
	public void guardar(PerfilInversorDTO perfilInversorDTO) {
		getPerfilInversorServicio().guardar(perfilInversorDTO);
	}

	@Override
	@PostMapping("/obtener")
	public PerfilInversorDTO obtener(Long id) {
		return getPerfilInversorServicio().obtener(id);
	}

	@Override
	@PostMapping("/borrar")
	public void borrar(Long id) {
		getPerfilInversorServicio().borrar(id);
	}

	@Override
	@PostMapping("/listar")
	public ResponseEntity<List<PerfilInversorDTO>> listar() {
		return ResponseEntity.ok(getPerfilInversorServicio().listar());
	}

	public PerfilInversorServicio getPerfilInversorServicio() {
		return perfilInversorServicio;
	}

	public void setPerfilInversorServicio(PerfilInversorServicio perfilInversorServicio) {
		this.perfilInversorServicio = perfilInversorServicio;
	}

}
