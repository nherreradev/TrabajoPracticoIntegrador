package com.unlam.tpi.core.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.PanelPrecios;
import com.unlam.tpi.core.modelo.Instrumento;

@Service
public class PanelPreciosImpl implements PanelPrecios {
	public static Map<String, Instrumento> panelAcciones = new HashMap<>();
	public static Map<String, Instrumento> panelBonos = new HashMap<>();

	@Override
	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos) {
		for (Instrumento instrumento : instrumentos) {
			if (instrumento.getPuntas() != null) {
				panelAcciones.put(instrumento.getSimbolo(), instrumento);
			}
		}
	}

	@Override
	public void agregarInstrumentosAlPanelDeBonos(List<Instrumento> instrumentos) {
		for (Instrumento instrumento : instrumentos) {
			if (instrumento.getPuntas().getPrecioCompra() != null && instrumento.getPuntas().getPrecioVenta() != null) {
				panelBonos.put(instrumento.getSimbolo(), instrumento);
			}
		}
	}

}
