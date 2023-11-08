package com.unlam.tpi.core.modelo;

import java.util.Map;

public class RendimientoActualResponse {

	private Map<String, RendimientoResponse> rendimientosActuales;

	public Map<String, RendimientoResponse> getRendimientosActuales() {
		return rendimientosActuales;
	}

	public void setRendimientosActuales(Map<String, RendimientoResponse> rendimientosActuales) {
		this.rendimientosActuales = rendimientosActuales;
	}

}
