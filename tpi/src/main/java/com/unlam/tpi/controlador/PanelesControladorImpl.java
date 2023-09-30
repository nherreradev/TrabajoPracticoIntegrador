package com.unlam.tpi.controlador;

import java.util.List;
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
@CrossOrigin
public class PanelesControladorImpl implements PanelesControlador {

	@Autowired
	PanelesService panelesService;

	@Override
	@GetMapping("/acciones")
	public ResponseEntity<String> getPanelDeAcciones() {
		List<Instrumento> panelAcciones = panelesService.getPanelDeAcciones();
		String json = new Gson().toJson(panelAcciones);
		return ResponseEntity.ok(json);
	}

	@Override
	@GetMapping("/bonos")
	public ResponseEntity<String> getPanelDeBonos() {
		List<Instrumento> panelBonos = panelesService.getPanelDeBonos();
		String json = new Gson().toJson(panelBonos);
		return ResponseEntity.ok(json);
	}

}
