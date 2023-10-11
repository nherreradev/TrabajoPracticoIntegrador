package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.modelo.persistente.Instrumento;

public interface PanelPrecios {

	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos);

	public void agregarInstrumentosAlPanelDeBonos(List<Instrumento> instrumentos);
}
