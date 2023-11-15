package com.unlam.tpi.delivery.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.PerfilInversorControlador;
import com.unlam.tpi.core.interfaces.PerfilInversorServicio;
import com.unlam.tpi.delivery.dto.PerfilInversorDTO;

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
	@RequestMapping(path = "/obtener-certificado", method = RequestMethod.GET)
	public void obtenerCertificado(@RequestParam("nombreUsuario") String nombreUsuario,HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] certificado =  getPerfilInversorServicio().obtenerCertificado(nombreUsuario);
		response.setContentType("application/pdf"); 
		response.getOutputStream().write(certificado, 0, certificado.length);
		response.getOutputStream().flush();
		response.getOutputStream().close();
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
	
	@Override
	@GetMapping("/listar-por-usuario/{id}")
	public ResponseEntity<PerfilInversorDTO> listarPorUsuario(@PathVariable Long id) {
		return ResponseEntity.ok(getPerfilInversorServicio().listarporUsuario(id));
	}

	public PerfilInversorServicio getPerfilInversorServicio() {
		return perfilInversorServicio;
	}

	public void setPerfilInversorServicio(PerfilInversorServicio perfilInversorServicio) {
		this.perfilInversorServicio = perfilInversorServicio;
	}

}
