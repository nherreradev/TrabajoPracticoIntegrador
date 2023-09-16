package com.unlam.tpi.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unlam.tpi.model.Instrumento;

@Service
public class PanelPreciosImpl implements PanelPrecios {

	public static Map<String, Instrumento> panelAcciones = new HashMap<>();

	@Override
	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos) {

		for (Instrumento instrumento : instrumentos) {
			panelAcciones.put(instrumento.getSimbolo(), instrumento);
		}
	}

}
