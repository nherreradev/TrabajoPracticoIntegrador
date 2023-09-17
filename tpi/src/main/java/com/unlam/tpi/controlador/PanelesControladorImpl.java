package com.unlam.tpi.controlador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.model.Instrumento;
import com.unlam.tpi.servicio.PanelesService;

@RestController
@RequestMapping("/panel")
public class PanelesControladorImpl implements PanelesControlador {

	@Autowired
	PanelesService panelesService;

	@Override
	@GetMapping("/acciones")
	public ResponseEntity<Map<String, Instrumento>> getPanelDeAcciones() {
		Map<String, Instrumento> panelAcciones = panelesService.getPanelDeAcciones();
		return ResponseEntity.ok(panelAcciones);
	}

}
