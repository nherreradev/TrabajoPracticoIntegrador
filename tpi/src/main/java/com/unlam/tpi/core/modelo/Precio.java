package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;

public class Precio {

	private String fecha;
	private BigDecimal precio;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

}
