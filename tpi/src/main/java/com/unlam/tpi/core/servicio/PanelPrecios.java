package com.unlam.tpi.core.servicio;

import java.util.List;
import java.util.Map;

import com.unlam.tpi.infraestructura.modelo.Instrumento;

public interface PanelPrecios {

	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos);

	public void agregarInstrumentosAlPanelDeBonos(List<Instrumento> instrumentos);
}
