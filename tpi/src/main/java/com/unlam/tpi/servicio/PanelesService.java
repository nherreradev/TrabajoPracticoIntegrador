package com.unlam.tpi.servicio;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.modelo.persistente.Instrumento;

public interface PanelesService {

	public List<Instrumento> getPanelDeAcciones();
	
	public List<Instrumento> getPanelDeBonos();
	
	public List<Instrumento> convertirListaDeJsonAListaDeIntrumentos(ResponseEntity<String> responseEntity);
	
	public ResponseEntity<String> postApiAcciones();

}
