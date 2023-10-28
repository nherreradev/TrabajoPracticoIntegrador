package com.unlam.tpi.core.interfaces;

import java.util.List;
import java.util.Map;

import com.unlam.tpi.core.modelo.Instrumento;

public interface PanelPrecios {

	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos);

	public void agregarInstrumentosAlPanelDeBonos(List<Instrumento> instrumentos);
}
