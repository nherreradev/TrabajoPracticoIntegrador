package com.unlam.tpi.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unlam.tpi.interfaces.PanelesServicio;
import com.unlam.tpi.modelo.persistente.Instrumento;

@RestController
@RequestMapping("/panel")
public class PanelesControladorImpl implements PanelesControlador {

	@Autowired
	PanelesServicio panelesService;

	@Override
	@GetMapping("/acciones")
	public ResponseEntity<String> getPanelDeAcciones() {
		List<Instrumento> panelAcciones = panelesService.getPanelDeAcciones().values().stream()
				.collect(Collectors.toList());
		String json = new Gson().toJson(panelAcciones);
		return ResponseEntity.ok(json);
	}

	@Override
	@GetMapping("/bonos")
	public ResponseEntity<String> getPanelDeBonos() {
		List<Instrumento> panelBonos = panelesService.getPanelDeBonos().values().stream()
			    .collect(Collectors.toList());

			String json = new Gson().toJson(panelBonos);

			return ResponseEntity.ok(json);

	}

}
