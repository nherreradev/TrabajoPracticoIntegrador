package com.unlam.tpi.servicio;

import java.util.Map;

import com.unlam.tpi.model.Instrumento;

public interface PanelesService {

	public Map<String, Instrumento> getPanelDeAcciones();
	
	public Map<String, Instrumento> getPanelDeBonos();

}
