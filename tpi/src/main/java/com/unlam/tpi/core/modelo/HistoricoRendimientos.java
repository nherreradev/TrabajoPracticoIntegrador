package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "HISTORICO_RENDIMIENTOS")
public class HistoricoRendimientos extends ObjetoPersistente{

	@Column(name = "SIMBOLO")
	String simbolo;
	
	@Column(name = "TOTAL_GANANCIA_DEL_DIA")
	BigDecimal totalGananciaDelDia;
	
	@Column(name = "TOTAL_PORCENTAJE_DEL_DIA")
	BigDecimal totalPorcentajeDelDia;
	
	@Column(name = "FECHA")
	LocalDate fecha;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getTotalGananciaDelDia() {
		return totalGananciaDelDia;
	}

	public void setTotalGananciaDelDia(BigDecimal totalGananciaDelDia) {
		this.totalGananciaDelDia = totalGananciaDelDia;
	}

	public BigDecimal getTotalPorcentajeDelDia() {
		return totalPorcentajeDelDia;
	}

	public void setTotalPorcentajeDelDia(BigDecimal totalPorcentajeDelDia) {
		this.totalPorcentajeDelDia = totalPorcentajeDelDia;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
