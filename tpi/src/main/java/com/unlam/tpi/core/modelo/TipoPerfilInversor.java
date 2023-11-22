package com.unlam.tpi.core.modelo;

public enum TipoPerfilInversor {

	CONSERVADOR("Conservador"), 
	MODERADO("Moderado"), 
	AGRESIVO("Agresivo");

	private final String value;

	TipoPerfilInversor(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
