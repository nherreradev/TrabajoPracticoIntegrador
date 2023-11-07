package com.unlam.tpi.core.modelo;

import java.util.Map;

public class ResponseTotalPorInstrumentoYPorDia {

	private Map<String, ResponsePorcentajeUnificado> instrumentosDiariosUnificados;

	private Map<String, ResponsePorcentajeDiario> instrumentosDiarios;

	public Map<String, ResponsePorcentajeUnificado> getInstrumentosDiariosUnificados() {
		return instrumentosDiariosUnificados;
	}

	public void setInstrumentosDiariosUnificados(
			Map<String, ResponsePorcentajeUnificado> instrumentosDiariosUnificados) {
		this.instrumentosDiariosUnificados = instrumentosDiariosUnificados;
	}

	public Map<String, ResponsePorcentajeDiario> getInstrumentosDiarios() {
		return instrumentosDiarios;
	}

	public void setInstrumentosDiarios(Map<String, ResponsePorcentajeDiario> instrumentosDiarios) {
		this.instrumentosDiarios = instrumentosDiarios;
	}

}
