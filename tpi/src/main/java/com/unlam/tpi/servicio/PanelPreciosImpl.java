package com.unlam.tpi.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unlam.tpi.modelo.persistente.Instrumento;

@Service
public class PanelPreciosImpl implements PanelPrecios {

	public static List<Instrumento> panelAcciones = new ArrayList<Instrumento>();
	public static List<Instrumento> panelBonos =  new ArrayList<Instrumento>();

	@Override
	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos) {
		
		for (Instrumento instrumento : instrumentos) {
			panelAcciones.add(instrumento);
		}
	}
	
	@Override
	public void agregarInstrumentosAlPanelDeBonos(List<Instrumento> instrumentos) {

		for (Instrumento instrumento : instrumentos) {
			panelBonos.add(instrumento);
		}
	}

}
