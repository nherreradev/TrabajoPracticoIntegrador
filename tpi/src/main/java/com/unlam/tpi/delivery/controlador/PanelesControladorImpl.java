package com.unlam.tpi.delivery.controlador;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unlam.tpi.core.interfaces.PanelesControlador;
import com.unlam.tpi.core.interfaces.PanelesServicio;
import com.unlam.tpi.core.interfaces.PlaneadorDeTareasServicio;
import com.unlam.tpi.core.modelo.Instrumento;

@RestController
@RequestMapping("/panel")
public class PanelesControladorImpl implements PanelesControlador {

	@Autowired
	PanelesServicio panelesService;

	@Autowired
	PlaneadorDeTareasServicio planeadorDeTareas;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@PostConstruct
	public void iniciarTareaProgramada() {
		 //scheduler.scheduleAtFixedRate(this::getPanelDeAcciones, 0, 2, TimeUnit.SECONDS);
	}

	@Override
	@GetMapping("/acciones")
	public ResponseEntity<String> getPanelDeAcciones() {
		List<Instrumento> panelAcciones = panelesService.getPanelDeAcciones().values().stream().collect(Collectors.toList());
		String json = new Gson().toJson(panelAcciones);
		return ResponseEntity.ok(json);
	}

	@Override
	@GetMapping("/bonos")
	public ResponseEntity<String> getPanelDeBonos() {
		List<Instrumento> panelBonos = panelesService.getPanelDeBonos().values().stream().collect(Collectors.toList());
		String json = new Gson().toJson(panelBonos);
		return ResponseEntity.ok(json);

	}
	
	@Override
	@GetMapping("/cedears")
	public ResponseEntity<String> getPanelDeCedears() {
		List<Instrumento> panelBonos = panelesService.getPanelDeCedears().values().stream().collect(Collectors.toList());
		String json = new Gson().toJson(panelBonos);
		return ResponseEntity.ok(json);

	}
	
}
