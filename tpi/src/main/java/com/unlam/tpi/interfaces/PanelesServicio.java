package com.unlam.tpi.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.modelo.persistente.Instrumento;

public interface PanelesServicio {
	public Map<String, Instrumento> getPanelDeAcciones();
	public Map<String, Instrumento> getPanelDeBonos();
	public List<Instrumento> convertirListaDeJsonAListaDeIntrumentos(ResponseEntity<String> responseEntity);
	public ResponseEntity<String> postApiAcciones();

}
