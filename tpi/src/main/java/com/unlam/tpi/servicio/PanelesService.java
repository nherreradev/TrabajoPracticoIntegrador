package com.unlam.tpi.servicio;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.model.Instrumento;

public interface PanelesService {

	public Map<String, Instrumento> getPanelDeAcciones();

}
