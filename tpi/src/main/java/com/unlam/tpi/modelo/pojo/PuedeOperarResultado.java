package com.unlam.tpi.modelo.pojo;

import java.math.BigDecimal;

public class PuedeOperarResultado {

	Boolean puedeOperar = Boolean.FALSE;

	BigDecimal montoDisponible = BigDecimal.ZERO;

	public Boolean getPuedeOperar() {
		return puedeOperar;
	}

	public void setPuedeOperar(Boolean puedeOperar) {
		this.puedeOperar = puedeOperar;
	}

	public BigDecimal getMontoDisponible() {
		return montoDisponible;
	}

	public void setMontoDisponible(BigDecimal montoDisponible) {
		this.montoDisponible = montoDisponible;
	}

}
