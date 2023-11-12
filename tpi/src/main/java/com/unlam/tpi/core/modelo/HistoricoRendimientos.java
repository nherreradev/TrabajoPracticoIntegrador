package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "HISTORICO_RENDIMIENTOS")
public class HistoricoRendimientos extends ObjetoPersistente {

	@Column(name = "SIMBOLO")
	String simbolo;

	@Column(name = "RENDIMIENTO_TOTAL")
	BigDecimal rendimientoTotal;

	@Column(name = "RENDIMIENTO_TOTAL_PORCENTAJE")
	BigDecimal rendimientoTotalPorcentaje;

	@Column(name = "FECHA")
	LocalDateTime fecha;

	@Column(name = "CANTIDAD_TITULOS")
	BigDecimal cantidadDeTitulos;

	@Column(name = "VALOR_INVERSION")
	BigDecimal valorInversion;

	@Column(name = "USUARIO_OID")
	Long usuarioOid;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getRendimientoTotal() {
		return rendimientoTotal;
	}

	public void setRendimientoTotal(BigDecimal rendimientoTotal) {
		this.rendimientoTotal = rendimientoTotal;
	}

	public BigDecimal getRendimientoTotalPorcentaje() {
		return rendimientoTotalPorcentaje;
	}

	public void setRendimientoTotalPorcentaje(BigDecimal rendimientoTotalPorcentaje) {
		this.rendimientoTotalPorcentaje = rendimientoTotalPorcentaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCantidadDeTitulos() {
		return cantidadDeTitulos;
	}

	public void setCantidadDeTitulos(BigDecimal cantidadDeTitulos) {
		this.cantidadDeTitulos = cantidadDeTitulos;
	}

	public BigDecimal getValorInversion() {
		return valorInversion;
	}

	public void setValorInversion(BigDecimal valorInversion) {
		this.valorInversion = valorInversion;
	}

	public Long getUsuarioOid() {
		return usuarioOid;
	}

	public void setUsuarioOid(Long usuarioOid) {
		this.usuarioOid = usuarioOid;
	}

}
