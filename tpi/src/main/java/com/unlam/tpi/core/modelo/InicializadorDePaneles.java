package com.unlam.tpi.core.modelo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.PanelesServicio;

@Service
public class InicializadorDePaneles {

	@Autowired
	PanelesServicio panelesServicio;

	@PostConstruct
	public void inicializarPaneles() {
		panelesServicio.getPanelDeAcciones();
		panelesServicio.getPanelDeBonos();
		panelesServicio.getPanelDeCedears();
	}
}
