package com.unlam.tpi.enums;

public enum TipoComponente {
   
	RADIO("Radio"),
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
