package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.OrdenControlador;
import com.unlam.tpi.core.interfaces.OrdenServicio;
import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.delivery.dto.OrdenDTO;
import com.unlam.tpi.delivery.dto.OrdenMapper;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

@CrossOrigin
@RestController
@RequestMapping("/orden")
public class OrdenControladorImpl implements OrdenControlador {

	@Autowired
	OrdenServicio ordenServicio;

	@Autowired
	AutenticacionService autenticacionServicio;

	@Override
	@PostMapping("/capturar")
	public ResponseEntity<String> capturarOrden(@RequestHeader("Authorization") String headerAuthorization,
			@RequestBody OrdenDTO orden) throws JsonProcessingException {
		String token = headerAuthorization.replaceAll("Bearer ", "");
		UsuarioDTO usuario = autenticacionServicio.obtenerDatosUsuarioByToken(token);
		orden.setUsuarioOid(usuario.getOid());
		Orden entidad = OrdenMapper.ordenDTOAOrden(orden);
		ordenServicio.capturarOrden(entidad);
		return ResponseEntity.ok("Orden creada correctamente");
	}
}
