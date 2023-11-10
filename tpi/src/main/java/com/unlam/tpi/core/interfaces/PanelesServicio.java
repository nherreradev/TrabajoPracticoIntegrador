package com.unlam.tpi.core.interfaces;

import java.util.Map;

import com.unlam.tpi.core.modelo.Instrumento;

public interface PanelesServicio {
	
	public Map<String, Instrumento> getPanelDeAcciones();

	public Map<String, Instrumento> getPanelDeBonos();

	public Map<String, Instrumento> getPanelDeCedears();

}
