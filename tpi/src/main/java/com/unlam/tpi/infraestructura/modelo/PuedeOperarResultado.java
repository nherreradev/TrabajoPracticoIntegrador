package com.unlam.tpi.infraestructura.modelo;

import java.math.BigDecimal;

public class PuedeOperarResultado {

	Boolean puedeOperar = Boolean.FALSE;

	BigDecimal disponible = BigDecimal.ZERO;

	public Boolean getPuedeOperar() {
		return puedeOperar;
	}

	public void setPuedeOperar(Boolean puedeOperar) {
		this.puedeOperar = puedeOperar;
	}

	public BigDecimal getDisponible() {
		return disponible;
	}

	public void setDisponible(BigDecimal disponible) {
		this.disponible = disponible;
	}

}
