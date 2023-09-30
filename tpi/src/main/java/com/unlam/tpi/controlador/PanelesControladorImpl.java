package com.unlam.tpi.controlador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unlam.tpi.modelo.persistente.Instrumento;
import com.unlam.tpi.servicio.PanelesService;

@RestController
@RequestMapping("/panel")
@CrossOrigin(origins = "http://localhost:4200")
public class PanelesControladorImpl implements PanelesControlador {

	@Autowired
	PanelesService panelesService;

	@Override
	@GetMapping("/acciones")
	public ResponseEntity<String> getPanelDeAcciones() {
		Map<String, Instrumento> panelAcciones = panelesService.getPanelDeAcciones();
		String json = new Gson().toJson(panelAcciones);
		return ResponseEntity.ok(json);
	}

	@Override
	@GetMapping("/bonos")
	public ResponseEntity<String> getPanelDeBonos() {
		Map<String, Instrumento> panelBonos = panelesService.getPanelDeBonos();
		String json = new Gson().toJson(panelBonos);
		return ResponseEntity.ok(json);
	}

}
