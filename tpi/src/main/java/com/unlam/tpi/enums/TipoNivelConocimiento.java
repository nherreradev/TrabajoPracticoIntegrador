package com.unlam.tpi.enums;

public enum TipoNivelConocimiento {

	PRINCIPIANTE("Principiante"), 
    INTERMEDIO("Intermedio"), 
	AVANZADO("Avanzado"),
	EXPERTO("Experto");

	private final String value;

	TipoNivelConocimiento(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
