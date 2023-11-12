package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;

public class RequestCargaDeDinero {

	private BigDecimal cantidadPorAcreditar;
	private String concepto;
	private Long usuarioOid;

	public BigDecimal getCantidadPorAcreditar() {
		return cantidadPorAcreditar;
	}

	public void setCantidadPorAcreditar(BigDecimal cantidadPorAcreditar) {
		this.cantidadPorAcreditar = cantidadPorAcreditar;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Long getUsuarioOid() {
		return usuarioOid;
	}

	public void setUsuarioOid(Long usuarioOid) {
		this.usuarioOid = usuarioOid;
	}

}
