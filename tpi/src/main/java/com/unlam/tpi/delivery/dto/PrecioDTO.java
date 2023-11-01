package com.unlam.tpi.delivery.dto;

import java.math.BigDecimal;

public class PrecioDTO {

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
