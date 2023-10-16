package com.unlam.tpi.dto;

public enum TipoComponente {
   
	RADIO("Radio"),
	CHECKBOX("Checkbox"),
    BOTON("Boton"),
    IMAGEN("Imagen"),
    TEXTO("Texto");

    private final String value;

    TipoComponente(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
