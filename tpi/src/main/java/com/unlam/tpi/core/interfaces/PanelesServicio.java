package com.unlam.tpi.core.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.Instrumento;

public interface PanelesServicio {
	public Map<String, Instrumento> getPanelDeAcciones();
	public Map<String, Instrumento> getPanelDeBonos();
	public List<Instrumento> convertirListaDeJsonAListaDeIntrumentos(String responseEntity);
	public ResponseEntity<String> postApiAcciones();
	public Map<String, Instrumento> getPanelDeCedears();

}
