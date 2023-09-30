package com.unlam.tpi.modelo.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ValuacionTotalRespuesta {

	@JsonProperty("totalPortafolio")
	private String totalPortafolio;

	@JsonProperty("totalInstrumentos")
	private String totalInstrumentos;

	@JsonProperty("totalMonedas")
	private String totalMonedas;

	@JsonProperty("totalPortafolio")
	public String getTotalPortafolio() {
		return totalPortafolio;
	}

	public void setTotalPortafolio(String totalPortafolio) {
		this.totalPortafolio = totalPortafolio;
	}

	@JsonProperty("totalInstrumentos")
	public String getTotalInstrumentos() {
		return totalInstrumentos;
	}

	public void setTotalInstrumentos(String totalInstrumentos) {
		this.totalInstrumentos = totalInstrumentos;
	}

	@JsonProperty("totalMonedas")
	public String getTotalMonedas() {
		return totalMonedas;
	}

	public void setTotalMonedas(String totalMonedas) {
		this.totalMonedas = totalMonedas;
	}
}
