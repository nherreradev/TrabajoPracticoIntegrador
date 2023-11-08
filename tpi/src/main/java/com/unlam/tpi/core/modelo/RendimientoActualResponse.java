package com.unlam.tpi.core.modelo;

import java.util.Map;

public class RendimientoActualResponse {

	private Map<String, RendimientoResponse> instrumentosDiarios;

	public Map<String, RendimientoResponse> getInstrumentosDiarios() {
		return instrumentosDiarios;
	}

	public void setInstrumentosDiarios(Map<String, RendimientoResponse> instrumentosDiarios) {
		this.instrumentosDiarios = instrumentosDiarios;
	}

}
